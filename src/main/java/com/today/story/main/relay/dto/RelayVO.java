package com.today.story.main.relay.dto;

import com.today.story.main.diary.dto.CommentVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RelayVO {
    private int seq;
    private String title;
    private String content;
    private String type;
    private String type_name;
    private String state;
    private String state_name;
    private int view;
    private String user_id;
    private String nickname;
    private Date create_date;
    private Date update_date;
    private List<RelayCommentVO> cList;
}
