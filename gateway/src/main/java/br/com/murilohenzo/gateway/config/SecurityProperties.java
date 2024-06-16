package br.com.murilohenzo.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "gateway")
public class SecurityProperties {

    private ResourceClient resourceClient;
    private Set<String> noCsrfEndpoints;
    private Set<String> publicEndpoints;

    public Set<String> getNoCsrfEndpoints() {
        return noCsrfEndpoints;
    }

    public void setNoCsrfEndpoints(Set<String> noCsrfEndpoints) {
        this.noCsrfEndpoints = noCsrfEndpoints;
    }

    public Set<String> getPublicEndpoints() {
        return publicEndpoints;
    }

    public void setPublicEndpoints(Set<String> publicEndpoints) {
        this.publicEndpoints = publicEndpoints;
    }

    public ResourceClient getResourceClient() {
        return resourceClient;
    }

    public void setResourceClient(ResourceClient resourceClient) {
        this.resourceClient = resourceClient;
    }

    public static class ResourceClient {

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

}
