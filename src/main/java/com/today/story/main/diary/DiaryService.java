package com.today.story.main.diary;

import com.today.story.controller.UserController;
import com.today.story.dto.UserDto;
import com.today.story.dto.response.BaseResponse;
import com.today.story.dto.response.ListDataResponse;
import com.today.story.dto.response.SingleDataResponse;
import com.today.story.exception.UserNotFoundException;
import com.today.story.main.common.dto.PageVO;
import com.today.story.main.diary.dto.CommentVO;
import com.today.story.main.diary.dto.DiaryVO;
import com.today.story.main.diary.dto.ReportVO;
import com.today.story.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final ResponseService responseService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private DiaryMapper diaryMapper;

    public ResponseEntity diaryGet(Authentication authentication, PageVO pageVO) {
        ResponseEntity responseEntity = null;
        try {
            String userId = "";
            if (authentication != null) {
                userId = ((UserDto) authentication.getPrincipal()).getUser_id();
            }

            pageVO.setUser_id(userId);

            if(pageVO.getPage() == 0) {
                pageVO.setPage(1);
            }

            if (pageVO.getRows() == 0) {
                pageVO.setRows(10);
            }

            List<DiaryVO> diaryList = diaryMapper.diaryGet(pageVO);

            int totalCount = diaryMapper.diaryCount(pageVO);

            JSONObject jobj = new JSONObject();

            jobj.put("list",diaryList);
            jobj.put("count",totalCount);



            SingleDataResponse<JSONObject> response = responseService.getSingleDataResponse(true, "조회 성공",jobj);

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity diaryDetail(Authentication authentication, PageVO pageVO) {
        ResponseEntity responseEntity = null;
        try {
            String userId = "";

            if(authentication != null){
                userId = ((UserDto) authentication.getPrincipal()).getUser_id();
            }

            pageVO.setUser_id(userId);



            DiaryVO diary = diaryMapper.diaryDetail(pageVO);

            List<CommentVO> cList = diaryMapper.commentList(pageVO);

            diary.setCList(cList);
            diaryMapper.diaryClick(pageVO);

            SingleDataResponse<DiaryVO> response = responseService.getSingleDataResponse(true, "조회 성공",diary);

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity diaryAdd(Authentication authentication, DiaryVO diaryVO) {
        ResponseEntity responseEntity = null;
        try {
            String userId = ((UserDto) authentication.getPrincipal()).getUser_id();

            diaryVO.setUser_id(userId);

            diaryMapper.diaryAdd(diaryVO);

            BaseResponse response = responseService.getBaseResponse(true, "저장 성공");

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity diaryUpdate(Authentication authentication, DiaryVO diaryVO) {
        ResponseEntity responseEntity = null;
        try {
            String userId = ((UserDto) authentication.getPrincipal()).getUser_id();

            diaryVO.setUser_id(userId);

            diaryMapper.diaryUpdate(diaryVO);

            BaseResponse response = responseService.getBaseResponse(true, "수정 성공");

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity diaryDelete(Authentication authentication, DiaryVO diaryVO) {
        ResponseEntity responseEntity = null;
        try {
            String userId = ((UserDto) authentication.getPrincipal()).getUser_id();

            diaryVO.setUser_id(userId);

            diaryMapper.diaryDelete(diaryVO);
            diaryMapper.commentStoryDelete(diaryVO);

            BaseResponse response = responseService.getBaseResponse(true, "삭제 성공");

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity commentAdd(Authentication authentication, CommentVO commentVO) {
        ResponseEntity responseEntity = null;
        try {
            String userId = ((UserDto) authentication.getPrincipal()).getUser_id();

            commentVO.setUser_id(userId);

            diaryMapper.commentAdd(commentVO);

            BaseResponse response = responseService.getBaseResponse(true, "저장 성공");

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity commentUpdate(Authentication authentication, CommentVO commentVO) {
        ResponseEntity responseEntity = null;
        try {
            String userId = ((UserDto) authentication.getPrincipal()).getUser_id();

            commentVO.setUser_id(userId);

            diaryMapper.commentUpdate(commentVO);

            BaseResponse response = responseService.getBaseResponse(true, "수정 성공");

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity commentDelete(Authentication authentication, CommentVO commentVO) {
        ResponseEntity responseEntity = null;
        try {
            String userId = ((UserDto) authentication.getPrincipal()).getUser_id();

            commentVO.setUser_id(userId);

            diaryMapper.commentDelete(commentVO);

            BaseResponse response = responseService.getBaseResponse(true, "삭제 성공");

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity reportAdd(Authentication authentication, ReportVO reportVO) {
        ResponseEntity responseEntity = null;
        try {
            String userId = ((UserDto) authentication.getPrincipal()).getUser_id();

            reportVO.setUser_id(userId);

            diaryMapper.reportAdd(reportVO);

            BaseResponse response = responseService.getBaseResponse(true, "신고 성공");

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }
}
