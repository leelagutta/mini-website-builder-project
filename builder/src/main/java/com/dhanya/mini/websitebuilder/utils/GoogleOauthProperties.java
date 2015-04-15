package com.dhanya.mini.websitebuilder.utils;

/**
 * Set of properties that we need to invoke Google's Oauth2 protocol.

 * @author Leela
 */
public final class GoogleOauthProperties {
	
	/**
	 * Client Id designated for Client application, provided by Google.
	 */
	private String clientId;
	
	/**
	 * Client secret designated for client application, provider by Google.
	 */
	private String clientSecret;

	/**
	 * The registered redirect_uri for the client id.
	 */
	private String redirectUri;
	
	/**
	 * Client redirect url for google authentication and authorization of resources 
	 * When users logs in successfully and grants permission, Google will invoke our
	 * Callback Controller with the auth code in the request 
	 */
	private String authenticationUrl;

	/**
	 * Before your application can access private data using a Google API, 
	 * it must obtain an access token that grants access to that API
	 */
	private String tokenRequestUrl;
	
	/**
	 * Required google param for callback url
	 */
	private String grantType;
	
	/**
	 * The scope of Google Api we are trying to access. In our case, we are accesing just the Google profile api
	 */
	private String apiScope;
	
	/**
	 * The User Profile Google resource api endpoint
	 */
	private String userProfileApiUrl;
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getTokenRequestUrl() {
		return tokenRequestUrl;
	}

	public void setTokenRequestUrl(String tokenRequestUrl) {
		this.tokenRequestUrl = tokenRequestUrl;
	}

	public String getAuthenticationUrl() {
		return authenticationUrl;
	}

	public void setAuthenticationUrl(String authenticationUrl) {
		this.authenticationUrl = authenticationUrl;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getApiScope() {
		return apiScope;
	}

	public void setApiScope(String apiScope) {
		this.apiScope = apiScope;
	}

	public String getUserProfileApiUrl() {
		return userProfileApiUrl;
	}

	public void setUserProfileApiUrl(String userProfileApiUrl) {
		this.userProfileApiUrl = userProfileApiUrl;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}	
}