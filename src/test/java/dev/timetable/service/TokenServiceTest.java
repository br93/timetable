package dev.timetable.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

@SpringBootTest
class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @MockBean
    private OAuth2AuthorizedClient mockAuthorizedClient;
    
    private OAuth2AccessToken mockAccessToken;
    private static final String MOCK_TOKEN = "TEST-TOKEN-VALUE";
    private static final String MOCK_USER = "TEST-USER";

    @BeforeEach
    void setup() {
        mockAccessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, MOCK_TOKEN, null, null);
    }

    @Test
    void getTokenShouldReturnTokenOfOAuth2User() {

        Mockito.when(mockAuthorizedClient.getAccessToken()).thenReturn(mockAccessToken);
        String token = this.tokenService.getToken(mockAuthorizedClient);

        assertEquals(String.format("Bearer %s", MOCK_TOKEN), token);
    }

    @Test
    void getUserShouldReturnNameOfOAuth2User() {

        Mockito.when(mockAuthorizedClient.getPrincipalName()).thenReturn(MOCK_USER);
        String user = this.tokenService.getUser(mockAuthorizedClient);

        assertEquals(MOCK_USER, user);
    }
}
