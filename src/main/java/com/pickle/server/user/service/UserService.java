package com.pickle.server.user.service;

import com.pickle.server.common.error.UserNotFoundException;
import com.pickle.server.config.PropertyUtil;
import com.pickle.server.dress.dto.UpdateDressLikeDto;
import com.pickle.server.store.domain.Store;
import com.pickle.server.user.domain.User;
import com.pickle.server.user.dto.UserDto;
import com.pickle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;

    public JSONObject getMyProfile(User user){
        if(user == null){
            return PropertyUtil.responseMessage("유저 로그인 안 되어있음");
        }
        Optional<User> findUser = userRepository.findById(user.getId());
        return PropertyUtil.response(new UserDto(findUser.get()));
    }

    public void updateProfile(User user, UpdateDressLikeDto updatedressLikeDto) {

    }

    /*public void updateProfile(User user, String nickname, MultipartFile image){
        imageUploadService.deleteImage(user.getImage_url());

        if(!image.isEmpty())
            user.updateProfile(nickname, imageUploadService.restore(image));
        else
            user.updateProfile(nickname, null);

        userRepository.save(user);
    }*/
}
