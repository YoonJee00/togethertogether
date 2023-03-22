package com.together3.service;

import com.together3.domain.subscribe.SubscribeRepository;
import com.together3.domain.user.User;
import com.together3.domain.user.UserRepository;
import com.together3.handler.ex.CustomException;
import com.together3.handler.ex.CustomValidationApiException;
import com.together3.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User 회원수정(int id, User user) {
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
           return new CustomValidationApiException("찾을 수 없는 id입니다.");
        });

        userEntity.setName(user.getName());

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
    }

    @Transactional(readOnly = true)
    public UserProfileDto 회원프로필(int pageUserid, int principalId) {
        UserProfileDto dto = new UserProfileDto();
        User userEntity = userRepository.findById(pageUserid).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });

        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserid == principalId);
        dto.setImageCount(userEntity.getImages().size());

        int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserid);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserid);

        dto.setSubscribeState(subscribeState == 1);
        dto.setSubscribeCount(subscribeCount);

        return dto;
    }


}
