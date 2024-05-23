package br.com.murilohenzo.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gateway.resource.client")
public class AuthProperties {

    private String jwtUrl;
    private String clientId;
    private String clientSecret;
    private String grantType;
    private String scope;

    public String getJwtUrl() {
        return jwtUrl;
    }

    public void setJwtUrl(String jwtUrl) {
        this.jwtUrl = jwtUrl;
    }

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

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
