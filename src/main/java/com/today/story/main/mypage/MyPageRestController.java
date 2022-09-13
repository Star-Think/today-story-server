package com.today.story.main.mypage;

import com.today.story.dto.UserDto;
import com.today.story.main.diary.dto.DiaryVO;
import com.today.story.main.mypage.dto.BlockVO;
import com.today.story.main.mypage.dto.SendUserVO;
import com.today.story.main.mypage.dto.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MyPageRestController {
    @Autowired
    private MyPageService myPageService;

    // 마이페이지 조회
    @PostMapping("/myPageGet")
    public ResponseEntity myPageGet(final Authentication authentication) {
        return myPageService.myPageGet(authentication);
    }

    // 마이페이지 수정
    @PostMapping("/myPageUpdate")
    public ResponseEntity myPageUpdate(final Authentication authentication, @RequestBody UserVO userVO) {
        return myPageService.myPageUpdate(authentication,userVO);
    }

    // 마이페이지 수정
    @PostMapping("/passwordChange")
    public ResponseEntity passwordChange(final Authentication authentication, @RequestBody SendUserVO userVO) {
        return myPageService.passwordChange(authentication,userVO);
    }

    // 차단회원 조회
    @PostMapping("/blockUserGet")
    public ResponseEntity blockUserGet(final Authentication authentication) {
        return myPageService.blockUserGet(authentication);
    }

    // 차단회원 추가
    @PostMapping("/blockUserAdd")
    public ResponseEntity blockUserAdd(final Authentication authentication, @RequestBody BlockVO blockVO) {
        return myPageService.blockUserAdd(authentication,blockVO);
    }

    // 차단회원 삭제
    @PostMapping("/blockUserDelete")
    public ResponseEntity blockUserDelete(final Authentication authentication, @RequestBody BlockVO blockVO) {
        return myPageService.blockUserDelete(authentication,blockVO);
    }

    // 차단회원 체크
    @PostMapping("/blockUserCheck")
    public ResponseEntity blockUserCheck(final Authentication authentication, @RequestBody BlockVO blockVO) {
        return myPageService.blockUserCheck(authentication,blockVO);
    }

    // 회원탈퇴
    @PostMapping("/userDelete")
    public ResponseEntity userDelete(final Authentication authentication) {
        return myPageService.userDelete(authentication);
    }

}
