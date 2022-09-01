package com.today.story.main.diary.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DiaryVO {
    private int seq;
    private String title;
    private String content;
    private String type;
    private String type_name;
    private int view;
    private String user_id;
    private String nickname;
    private Date create_date;
    private Date update_date;
    private List<CommentVO> cList;
}
