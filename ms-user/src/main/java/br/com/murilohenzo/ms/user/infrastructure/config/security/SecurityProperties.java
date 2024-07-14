package br.com.murilohenzo.ms.user.infrastructure.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private List<String> publicEndpoints;
    private Map<String, Set<String>> roles;
}
