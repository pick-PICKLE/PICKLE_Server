package com.pickle.server.user.service;

import com.pickle.server.common.error.UserNotFoundException;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.dto.UserDto;
import com.pickle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;

    public UserDto getMyProfile(User user){
        if(user == null){
            throw new UserNotFoundException();
        }
        Optional<User> findUser = userRepository.findById(user.getId());
        return new UserDto(findUser.get());
    }
}
