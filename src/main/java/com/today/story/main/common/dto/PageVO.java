package com.today.story.main.common.dto;

import lombok.Data;

@Data
public class PageVO {
    private int page;
    private int rows;
    private String user_id;
    private String view_id;
    private String keyword;
    private String type;
    private String email;
    private int seq;
}
