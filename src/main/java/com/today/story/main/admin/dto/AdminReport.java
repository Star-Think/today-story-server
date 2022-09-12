package com.today.story.main.admin.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AdminReport {
    private String report_content;
    private int seq;
    private int report_seq;
    private String title;
    private String type;
    private String content;
    private String user_id;
    private String report_id;
    private String nickname;
    private String username;
    private String self;
    private Date create_date;
    private String state;
    private String state_name;
}
