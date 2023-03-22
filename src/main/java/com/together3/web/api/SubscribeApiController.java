package com.together3.web.api;

import com.together3.config.auth.PrincipalDetails;
import com.together3.service.SubscribeService;
import com.together3.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {
    private final SubscribeService subscribeService;

    @PostMapping("/api/subscribe/{to_user_id}")
    public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int to_user_id) {
        subscribeService.구독하기(principalDetails.getUser().getId(), to_user_id);
        return new ResponseEntity<>(new CMRespDto<>(1, "구독하기 성공", null), HttpStatus.OK);
    }

    @DeleteMapping("/api/subscribe/{to_user_id}")
    public ResponseEntity<?> unsubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int to_user_id) {
        subscribeService.구독취소하기(principalDetails.getUser().getId(), to_user_id);
        return new ResponseEntity<>(new CMRespDto<>(1, "구독취소 성공", null), HttpStatus.OK);
    }
}
