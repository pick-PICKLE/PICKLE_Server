package com.pickle.server.user.service;

import com.pickle.server.common.error.UserNotFoundException;
import com.pickle.server.likes.dto.LikesDto;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.dto.UserDto;
import com.pickle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService{
    @Autowired
    private final UserRepository userRepository;

    public UserDto getMyProfile(User user){
        if(user == null){
            throw new UserNotFoundException();
        }
        Optional<User> findUser = userRepository.findById(user.getId());
        return new UserDto(findUser.get());
    }
    @Transactional(readOnly = true)     //test용
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserDto::new).collect(Collectors.toList());
    }
}
