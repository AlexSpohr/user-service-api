package com.alexspohr.user_service.adapters.out;

import com.alexspohr.user_service.adapters.out.mapper.UserMapper;
import com.alexspohr.user_service.adapters.out.repository.UserEntityRepository;
import com.alexspohr.user_service.core.domain.models.UserModel;
import com.alexspohr.user_service.core.port.out.UserPersistenceOutputPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserPersistenceAdapter implements UserPersistenceOutputPort {

    private final UserEntityRepository userEntityRepository;

    public UserPersistenceAdapter(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Transactional
    @Override
    public void VerifyAndPersistUser(UserModel userModel) {

        var existingUser = userEntityRepository.findUserByEmail(userModel.getEmail());
        if (existingUser.isPresent()) {
            return;
        }
        var user = UserMapper.userModelToUserEntity(userModel);

        userEntityRepository.save(user);
    }
}
