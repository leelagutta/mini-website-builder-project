package com.dhanya.mini.websitebuilder.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dhanya.mini.websitebuilder.utils.GoogleOauthProperties;

/**
 * Login controller for providing users the endpoint to authenticate with Oauth clients (Google).
 *
 * @author Leela
 */
@Controller
public class HomeController {

	private String googleOauthAuthUrl;
	
	private GoogleOauthProperties googleOauthProps;

	@Autowired
	public HomeController(GoogleOauthProperties googleOuthProperties) {
		this.googleOauthProps = googleOuthProperties;
		this.googleOauthAuthUrl = buildGoogleAuthUrlForClient();		
	}

	public HomeController() {
		this.googleOauthAuthUrl = buildGoogleAuthUrlForClient();		
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String googleAuthUrlGen(Model model) {
		model.addAttribute("googleOauthAuthUrl", googleOauthAuthUrl);
		return "login";
	}
	
	private String buildGoogleAuthUrlForClient() {		
		String authUrl = this.googleOauthProps.getAuthenticationUrl();
		String clientId = this.googleOauthProps.getClientId();
		String apiScope = this.googleOauthProps.getApiScope();
		String redirectUrl = this.googleOauthProps.getRedirectUri();
		
		StringBuilder builder = new StringBuilder(authUrl).append("?")
		.append("client_id=").append(clientId).append("&")
		.append("redirect_uri=").append(redirectUrl).append("&")
		.append("scope=").append(apiScope).append("&")
		.append("response_type=code");
		return builder.toString();
	}
	
}
