package com.today.story.main.admin;

import com.today.story.main.common.dto.PageVO;
import com.today.story.main.diary.dto.ReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AdminRestController {
    @Autowired
    private AdminService adminService;

    // 신고일기 조회
    @PostMapping("/reportDiaryGet")
    public ResponseEntity reportDiaryGet(final Authentication authentication, @RequestBody PageVO pageVO) {
        return adminService.reportDiaryGet(authentication,pageVO);
    }

    // 신고댓글 조회
    @PostMapping("/reportCommentGet")
    public ResponseEntity reportCommentGet(final Authentication authentication, @RequestBody PageVO pageVO) {
        return adminService.reportCommentGet(authentication,pageVO);
    }

    // 신고회원 조회
    @PostMapping("/reportUserGet")
    public ResponseEntity reportUserGet(final Authentication authentication, @RequestBody PageVO pageVO) {
        return adminService.reportUserGet(authentication,pageVO);
    }

    // 신고처리
    @PostMapping("/reportCompletion")
    public ResponseEntity reportCompletion(final Authentication authentication, @RequestBody PageVO pageVO) {
        return adminService.reportCompletion(authentication,pageVO);
    }

}
