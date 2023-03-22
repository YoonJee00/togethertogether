package com.together3.web.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.together3.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
    private boolean pageOwnerState;
    private int imageCount;

    private boolean subscribeState;

    private int subscribeCount;

    @JsonIgnoreProperties({"images"})
    private User user;
}
