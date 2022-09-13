package com.today.story.main.diary;

import com.today.story.controller.UserController;
import com.today.story.dto.UserDto;
import com.today.story.dto.response.BaseResponse;
import com.today.story.dto.response.SingleDataResponse;
import com.today.story.exception.UserNotFoundException;
import com.today.story.main.common.dto.PageVO;
import com.today.story.main.diary.dto.CommentVO;
import com.today.story.main.diary.dto.DiaryVO;
import com.today.story.main.diary.dto.ReportVO;
import com.today.story.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api")
public class DiaryRestController {
    @Autowired
    private DiaryService diaryService;

    // 일기조회
    @PostMapping("/diaryGet")
    public ResponseEntity diaryGet(final Authentication authentication, @RequestBody PageVO pageVO) {
        return diaryService.diaryGet(authentication,pageVO);
    }

    // 일기상세
    @PostMapping("/diaryDetail")
    public ResponseEntity diaryDetail(final Authentication authentication, @RequestBody PageVO pageVO) {
        return diaryService.diaryDetail(authentication,pageVO);
    }

    // 일기저장
    @PostMapping("/diaryAdd")
    public ResponseEntity diaryAdd(final Authentication authentication, @RequestBody DiaryVO diaryVO) {
        return diaryService.diaryAdd(authentication,diaryVO);
    }

    // 일기수정
    @PostMapping("/diaryUpdate")
    public ResponseEntity diaryUpdate(final Authentication authentication, @RequestBody DiaryVO diaryVO) {
        return diaryService.diaryUpdate(authentication,diaryVO);
    }

    // 일기삭제
    @PostMapping("/diaryDelete")
    public ResponseEntity diaryDelete(final Authentication authentication, @RequestBody DiaryVO diaryVO) {
        return diaryService.diaryDelete(authentication,diaryVO);
    }

    // 댓글추가
    @PostMapping("/commentAdd")
    public ResponseEntity commentAdd(final Authentication authentication, @RequestBody CommentVO commentVO) {
        return diaryService.commentAdd(authentication,commentVO);
    }

    // 댓글수정
    @PostMapping("/commentUpdate")
    public ResponseEntity commentUpdate(final Authentication authentication, @RequestBody CommentVO commentVO) {
        return diaryService.commentUpdate(authentication,commentVO);
    }

    // 댓글삭제
    @PostMapping("/commentDelete")
    public ResponseEntity commentDelete(final Authentication authentication, @RequestBody CommentVO commentVO) {
        return diaryService.commentDelete(authentication,commentVO);
    }

    // 신고추가
    @PostMapping("/reportAdd")
    public ResponseEntity reportAdd(final Authentication authentication, @RequestBody ReportVO reportVO) {
        return diaryService.reportAdd(authentication,reportVO);
    }

    // 댓글쓴거조회
    @PostMapping("/commentGet")
    public ResponseEntity commentGet(final Authentication authentication, @RequestBody PageVO pageVO) {
        return diaryService.commentGet(authentication,pageVO);
    }

    // 댓글받은거조회
    @PostMapping("/commentReceivedGet")
    public ResponseEntity commentReceivedGet(final Authentication authentication, @RequestBody PageVO pageVO) {
        return diaryService.commentReceivedGet(authentication,pageVO);
    }

}
