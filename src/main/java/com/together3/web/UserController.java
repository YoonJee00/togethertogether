package com.together3.web;

import com.together3.config.auth.PrincipalDetails;
import com.together3.domain.user.User;
import com.together3.service.UserService;
import com.together3.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    @GetMapping("/user/{pageUserid}")
    public String profile(@PathVariable int pageUserid, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        UserProfileDto dto = userService.회원프로필(pageUserid, principalDetails.getUser().getId());
        model.addAttribute("dto", dto);
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String profile(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        //System.out.println("세션 정보 : " + principalDetails.getUser());

        return "user/update";
    }
}
