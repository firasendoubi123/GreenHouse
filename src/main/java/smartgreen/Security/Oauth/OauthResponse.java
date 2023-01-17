package smartgreen.Security.Oauth;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbVisibility;
import smartgreen.Security.FieldPropertyVisibilityStrategy;
@JsonbVisibility(FieldPropertyVisibilityStrategy.class)
public class OauthResponse {

    @JsonbProperty("access_token")
    private String accessToken;

    @JsonbProperty("username")
    private int expiresIn;

    @JsonbProperty("refresh_token")
    private String refreshToken;


    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    static OauthResponse of(AccessToken accessToken, RefreshToken refreshToken, int expiresIn) {
        OauthResponse response = new OauthResponse();
        response.accessToken = accessToken.getToken();
        response.refreshToken = refreshToken.getToken();
        response.expiresIn = expiresIn;
        return response;
    }

}
