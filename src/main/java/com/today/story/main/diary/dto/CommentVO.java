package com.today.story.main.diary.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVO {
    private int seq;
    private int story_seq;
    private String content;
    private String type;
    private String type_name;
    private String user_id;
    private String nickname;
    private Date create_date;
    private Date update_date;
}
