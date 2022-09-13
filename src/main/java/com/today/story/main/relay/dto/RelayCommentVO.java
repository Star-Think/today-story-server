package com.today.story.main.relay.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RelayCommentVO {
    private int seq;
    private int relay_seq;
    private String content;
    private String type;
    private String type_name;
    private String user_id;
    private String nickname;
    private Date create_date;
    private Date update_date;
}
