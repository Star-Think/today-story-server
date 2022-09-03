package com.today.story.main.admin.dto;

import lombok.Data;

@Data
public class AdminReport {
    private String report_content;
    private int seq;
    private int report_seq;
    private String title;
    private String content;
    private String user_id;
    private String report_id;
    private String nickname;
    private String self;
}
