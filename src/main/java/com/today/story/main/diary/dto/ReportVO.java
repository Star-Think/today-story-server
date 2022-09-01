package com.today.story.main.diary.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReportVO {
    private int seq;
    private int report_seq;
    private String type;
    private String user_id;
    private Date create_date;
    private String state;
    private String content;
}
