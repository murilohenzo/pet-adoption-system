package br.com.murilohenzo.ms.user.config;

import br.com.murilohenzo.ms.user.adapters.inbound.rest.UsersApiDelegateImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MsUserConfiguration {

    @Bean
    public UsersApiDelegateImpl usersApiDelegate() {
        return new UsersApiDelegateImpl();
    }
}
