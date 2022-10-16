package com.today.story.main.admin;

import com.today.story.main.admin.dto.AdminReport;
import com.today.story.main.common.dto.PageVO;
import com.today.story.main.diary.dto.CommentVO;
import com.today.story.main.diary.dto.DiaryVO;
import com.today.story.main.mypage.dto.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {


    List<AdminReport> reportDiaryGet(PageVO pageVO);

    int reportDiaryCount(PageVO pageVO);

    List<AdminReport> reportCommentGet(PageVO pageVO);

    int reportCommentCount(PageVO pageVO);

    List<AdminReport> reportUserGet(PageVO pageVO);

    int reportUserCount(PageVO pageVO);

    void reportCompletion(PageVO pageVO);

    int reportCompletionCount(PageVO pageVO);

    void adminDiaryDelete(PageVO pageVO);

    void adminCommentDelete(PageVO pageVO);

    void adminUserNameDelete(PageVO pageVO);

    UserVO adminUserSeqGet(PageVO pageVO);
}
