package com.together3.web.api;

import com.together3.config.auth.PrincipalDetails;
import com.together3.domain.user.User;
import com.together3.handler.ex.CustomValidationApiException;
import com.together3.handler.ex.CustomValidationException;
import com.together3.service.SubscribeService;
import com.together3.service.UserService;
import com.together3.web.dto.CMRespDto;
import com.together3.web.dto.subscribe.SubscribeDto;
import com.together3.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;
    private final SubscribeService subscribeService;

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable int id, @Valid UserUpdateDto userUpdateDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                //System.out.println("============================");
                //System.out.println(error.getDefaultMessage());
                //System.out.println("============================");
            }

            throw new CustomValidationApiException("유효성검사 실패", errorMap);
        }

        User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
        principalDetails.setUser(userEntity);
        return new CMRespDto<>(1, "회원 수정 완료", userEntity);
    }

    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<SubscribeDto> subscribeDto = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);
        return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보 리스트 불러오기 성공", subscribeDto), HttpStatus.OK);
    }
}
