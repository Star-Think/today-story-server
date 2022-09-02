package com.today.story.main.mypage.dto;

import lombok.Data;

@Data
public class SendUserVO {
    private String user_id;
    private String password;
    private String new_password;

}
