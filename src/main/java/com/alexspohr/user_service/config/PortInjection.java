package com.alexspohr.user_service.config;

import com.alexspohr.user_service.core.port.in.VerifyAndPersistUserInputPort;
import com.alexspohr.user_service.core.port.out.UserPersistenceOutputPort;
import com.alexspohr.user_service.core.usecase.VerifyAndPersistUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PortInjection {

    @Primary
    @Bean
    public VerifyAndPersistUserInputPort verifyAndPersistUserInputPort(UserPersistenceOutputPort userPersistenceOutputPort) {
        return new VerifyAndPersistUserUseCase(userPersistenceOutputPort);
    }
}
