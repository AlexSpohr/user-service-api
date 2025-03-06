package com.alexspohr.user_service.core.usecase;

import com.alexspohr.user_service.adapters.in.security.JwtContent;
import com.alexspohr.user_service.core.domain.models.UserModel;
import com.alexspohr.user_service.core.port.in.VerifyAndPersistUserInputPort;
import com.alexspohr.user_service.core.port.out.UserPersistenceOutputPort;

public class VerifyAndPersistUserUseCase implements VerifyAndPersistUserInputPort {

    private final UserPersistenceOutputPort userPersistenceOutputPort;

    public VerifyAndPersistUserUseCase(UserPersistenceOutputPort userPersistenceOutputPort) {
        this.userPersistenceOutputPort = userPersistenceOutputPort;
    }

    @Override
    public void verifyAndPersistUser(JwtContent jwtContent) {

        var user = UserModel.builder()
                .name(jwtContent.getName())
                .email(jwtContent.getEmail())
                .build();

        userPersistenceOutputPort.VerifyAndPersistUser(user);
    }
}
