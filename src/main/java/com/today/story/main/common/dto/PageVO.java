package com.today.story.main.common.dto;

import lombok.Data;

@Data
public class PageVO {
    private int page;
    private int rows;
    private String user_id;
    private int seq;
}
