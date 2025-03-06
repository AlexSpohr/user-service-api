package com.alexspohr.user_service.core.port.out;

import com.alexspohr.user_service.core.domain.models.UserModel;

public interface UserPersistenceOutputPort {
    void VerifyAndPersistUser(UserModel userModel);
}
