package com.today.story.main.diary;


import com.today.story.main.common.dto.PageVO;
import com.today.story.main.diary.dto.CommentVO;
import com.today.story.main.diary.dto.DiaryVO;
import com.today.story.main.diary.dto.ReportVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface DiaryMapper {

    List<DiaryVO> diaryGet(PageVO pageVO);

    int diaryCount(PageVO pageVO);

    DiaryVO diaryDetail(PageVO pageVO);

    List<CommentVO> commentList(PageVO pageVO);

    void diaryAdd(DiaryVO diaryVO);

    void diaryUpdate(DiaryVO diaryVO);

    void diaryClick(PageVO pageVO);

    void diaryDelete(DiaryVO diaryVO);

    void commentStoryDelete(DiaryVO diaryVO);

    void commentAdd(CommentVO commentVO);

    void commentUpdate(CommentVO commentVO);

    void commentDelete(CommentVO commentVO);

    void reportAdd(ReportVO reportVO);
}
