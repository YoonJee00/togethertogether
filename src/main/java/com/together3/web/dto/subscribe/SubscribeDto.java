package com.together3.web.dto.subscribe;

import com.together3.domain.subscribe.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
    private int id;
    private String username;
    private String profileImageUrl;
    private BigInteger subscribeState;
    private BigInteger equalUserState;

//    public SubscribeDto(Subscribe subscribe) {
//        this.id = subscribe.getFromUser().getId();
//        this.username = subscribe.getFromUser().getName();
//        this.profileImageUrl = subscribe.getFromUser().getProfileImageUrl();
//        this.subscribeState = false;
//    }
}
