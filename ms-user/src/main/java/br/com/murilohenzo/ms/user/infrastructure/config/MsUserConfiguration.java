package br.com.murilohenzo.ms.user.infrastructure.config;

import br.com.murilohenzo.ms.user.adapters.inbound.rest.UsersApiDelegateImpl;
import br.com.murilohenzo.ms.user.domain.mapper.UserMapper;
import br.com.murilohenzo.ms.user.domain.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MsUserConfiguration {

    @Bean
    public UsersApiDelegateImpl usersApiDelegate(UserService userService, UserMapper userMapper) {
        return new UsersApiDelegateImpl(userService, userMapper);
    }

    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }
}
