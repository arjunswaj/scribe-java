package org.scribe.builder.api;

import org.scribe.extractors.*;
import org.scribe.model.*;
import org.scribe.utils.*;

/**
 * LinksApi open platform api based on OAuth 2.0.
 */
public class LinksApi extends DefaultApi20 {
  private static String BASE_URL = "http://w.fieldsofview.in:3000";
  
  private static final String AUTHORIZE_URL = BASE_URL
      + "/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code";
  private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL
      + "&scope=%s";
  private static final String ACCESS_TOKEN_ENDPOINT = BASE_URL
      + "/oauth/token?grant_type=authorization_code";

  @Override
  public AccessTokenExtractor getAccessTokenExtractor() {
    return new JsonTokenExtractor();
  }

  @Override
  public String getAccessTokenEndpoint() {
    return ACCESS_TOKEN_ENDPOINT;
  }

  @Override
  public Verb getAccessTokenVerb() {
    return Verb.POST;
  }

  @Override
  public String getAuthorizationUrl(OAuthConfig config) {
    // Append scope if present
    if (config.hasScope()) {
      return String.format(SCOPED_AUTHORIZE_URL, config.getApiKey(),
          OAuthEncoder.encode(config.getCallback()),
          OAuthEncoder.encode(config.getScope()));
    } else {
      return String.format(AUTHORIZE_URL, config.getApiKey(),
          OAuthEncoder.encode(config.getCallback()));
    }
  }
}
