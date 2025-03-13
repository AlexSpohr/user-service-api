package com.alexspohr.user_service.adapters.out.mapper;

import com.alexspohr.user_service.adapters.out.model.UserEntity;
import com.alexspohr.user_service.core.domain.models.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserEntity userModelToUserEntity(UserModel userModel) {
        return UserEntity.builder()
                .fullName(userModel.getFullName())
                .email(userModel.getEmail())
                .givenName(userModel.getGivenName())
                .familyName(userModel.getFamilyName())
                .pictureUrl(userModel.getPictureUrl())
                .build();
    }
}
