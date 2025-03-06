package com.alexspohr.user_service.core.port.in;

import com.alexspohr.user_service.adapters.in.security.JwtContent;

public interface VerifyAndPersistUserInputPort {

    void verifyAndPersistUser(JwtContent jwtContent);
}
