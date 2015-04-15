package com.dhanya.mini.websitebuilder.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dhanya.mini.websitebuilder.exception.OauthException;
import com.dhanya.mini.websitebuilder.utils.GoogleOauthProperties;
import com.leela.mini.weeblycommonlib.cache.WeeblyCacheKey;
import com.leela.mini.weeblycommonlib.cache.WeeblyRedisCache;
import com.leela.mini.weeblycommonlib.dao.UserDao;
import com.leela.mini.weeblycommonlib.domain.UserDomain;
import com.leela.mini.weeblycommonlib.model.UserModel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Authorization controller for Outh purposes. Currently supporting Google Oauth. 
 * 
 * @author Leela
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

	private static final Logger log = Logger.getLogger(AuthController.class.getName());

	private UserDao userDao;

	private WeeblyRedisCache weeblyRedisCache;

	private GoogleOauthProperties googleOuthProperties;

	@Autowired
	public AuthController(GoogleOauthProperties googleOuthProperties, UserDao userDao, WeeblyRedisCache weeblyRedisCache) {
		this.googleOuthProperties = googleOuthProperties;
		this.userDao = userDao;
		this.weeblyRedisCache = weeblyRedisCache;
	}

	@RequestMapping(value = "/callback/google", method = RequestMethod.GET)
	public String callbackClient(@RequestParam("code") String authCode, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			// We make another request here to google. Here we are giving our auth code to google to allow access
			HttpResponse<JsonNode> tokenResponseJson = Unirest
					.post(googleOuthProperties.getTokenRequestUrl())
					.header("Content-Type", "application/x-www-form-urlencoded")
					.body(generateAuthRequestBody(authCode)).asJson();

			JsonNode jsonResponse = tokenResponseJson.getBody();
			if (jsonResponse == null || jsonResponse.getObject() == null) {
				throw new OauthException(
						"Unable to access token response from token req");
			}
			// Ones the entry is allowed, We make another call here to google with access_token, to access profile details
			String accessToken = (String) jsonResponse.getObject().get(
					"access_token");
			HttpResponse<JsonNode> userProfileJsonResponse = Unirest
					.get(googleOuthProperties.getUserProfileApiUrl())
					.queryString("access_token", accessToken).asJson();

			JsonNode userProfileJsonBody = userProfileJsonResponse.getBody();

			UserModel userModel = checkUserModel(userProfileJsonBody, response, model);

			String apiKey = UUID.randomUUID().toString();
			addApiKeyToResonse(request, response, apiKey);
			weeblyRedisCache.getInstance().setex(WeeblyCacheKey.USER_AUTH_KEY + apiKey, WeeblyRedisCache.ONE_MONTH_TTL, "true");
			model.addAttribute("userModel", userModel);
		} catch (UnirestException e) {
			log.info(e);
		}
		return "api";
	}

	public UserModel checkUserModel(JsonNode userProfileJsonBody,
			HttpServletResponse response, Model model) {

	 UserDomain userDomain = new UserDomain();
	 String userGoogleKey = (String) userProfileJsonBody.getObject().get("id");
	 UserModel userModel = userDao.getByUserGoogleId(userGoogleKey);

	 if (userModel == null) {
			String userId = UUID.randomUUID().toString();
			userDomain.setUuid(userId);
			userDomain.setFirstName((String) userProfileJsonBody.getObject().get("given_name"));
			userDomain.setLastName((String) userProfileJsonBody.getObject().get("family_name"));
			userDomain.setGender((String) userProfileJsonBody.getObject().get("gender"));
			userDomain.setGoogleId((String) userProfileJsonBody.getObject().get("id"));
			userModel = userDao.create(userDomain);
			return userModel;
		} else {
			return userModel;
	  }

	}

	private String generateAuthRequestBody(String authCode) {
		StringBuilder builder = new StringBuilder(getDefaultTokenRequestBody())
				.append("code=" + authCode);
		return builder.toString();
	}

	private String getDefaultTokenRequestBody() {
		StringBuilder builder = new StringBuilder().append("client_id=")
				.append(googleOuthProperties.getClientId()).append("&")
				.append("client_secret=")
				.append(googleOuthProperties.getClientSecret()).append("&")
				.append("redirect_uri=")
				.append(googleOuthProperties.getRedirectUri()).append("&")
				.append("grant_type=")
				.append(googleOuthProperties.getGrantType()).append("&");
		return builder.toString();
	}

	private void addApiKeyToResonse(HttpServletRequest request, HttpServletResponse response, String apiKey) {
		Cookie cookie = new Cookie("authApiKey", apiKey);
		cookie.setMaxAge(WeeblyRedisCache.ONE_MONTH_TTL);
		cookie.setPath("/");
		response.addCookie(cookie);
		String origin = request.getHeader("Origin");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Origin", origin);
        response.addHeader("Access-Control-Allow-Methods", "OPTIONS,GET,HEAD,POST,PUT,DELETE,TRACE,CONNECT");
        response.addHeader("Access-Control-Allow-Headers", "content-type");	
    }
}