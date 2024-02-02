package dev.timetable.service;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    public String getToken(OAuth2AuthorizedClient authorizedClient) {
        return String.format("Bearer %s", authorizedClient.getAccessToken().getTokenValue());
    }

    public String getUser(OAuth2AuthorizedClient authorizedClient) {
        return authorizedClient.getPrincipalName();
    }
}
