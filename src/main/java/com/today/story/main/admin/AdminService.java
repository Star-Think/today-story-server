package com.today.story.main.admin;

import com.today.story.controller.UserController;
import com.today.story.dto.UserDto;
import com.today.story.dto.response.BaseResponse;
import com.today.story.dto.response.SingleDataResponse;
import com.today.story.exception.UserNotFoundException;
import com.today.story.main.admin.dto.AdminReport;
import com.today.story.main.common.dto.PageVO;
import com.today.story.main.diary.dto.CommentVO;
import com.today.story.main.diary.dto.DiaryVO;
import com.today.story.main.mypage.MyPageMapper;
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
public class AdminService {
    private final ResponseService responseService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private MyPageMapper myPageMapper;

    public ResponseEntity reportDiaryGet(Authentication authentication, PageVO pageVO) {
        ResponseEntity responseEntity = null;
        try {

            if(pageVO.getPage() == 0) {
                pageVO.setPage(1);
            }

            if (pageVO.getRows() == 0) {
                pageVO.setRows(10);
            }

            List<AdminReport> diaryList = adminMapper.reportDiaryGet(pageVO);

            int totalCount = adminMapper.reportDiaryCount(pageVO);

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

    public ResponseEntity reportCommentGet(Authentication authentication, PageVO pageVO) {
        ResponseEntity responseEntity = null;
        try {

            if(pageVO.getPage() == 0) {
                pageVO.setPage(1);
            }

            if (pageVO.getRows() == 0) {
                pageVO.setRows(10);
            }

            List<AdminReport> commentVOList = adminMapper.reportCommentGet(pageVO);

            int totalCount = adminMapper.reportCommentCount(pageVO);

            JSONObject jobj = new JSONObject();

            jobj.put("list",commentVOList);
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

    public ResponseEntity reportUserGet(Authentication authentication, PageVO pageVO) {
        ResponseEntity responseEntity = null;
        try {

            if(pageVO.getPage() == 0) {
                pageVO.setPage(1);
            }

            if (pageVO.getRows() == 0) {
                pageVO.setRows(10);
            }

            List<AdminReport> commentVOList = adminMapper.reportUserGet(pageVO);

            int totalCount = adminMapper.reportUserCount(pageVO);

            JSONObject jobj = new JSONObject();

            jobj.put("list",commentVOList);
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

    public ResponseEntity reportCompletion(Authentication authentication, PageVO pageVO) {
        ResponseEntity responseEntity = null;
        try {



            adminMapper.reportCompletion(pageVO);

            int reportCount = adminMapper.reportCompletionCount(pageVO);

            if (pageVO.getKeyword().equals("1")){
                if (pageVO.getType().equals("0")){
                    adminMapper.adminDiaryDelete(pageVO);
                } else if (pageVO.getType().equals("1")) {
                    adminMapper.adminCommentDelete(pageVO);
                } else {
                    adminMapper.adminUserNameDelete(pageVO);
                }
            }
            if(reportCount > 3) {
                myPageMapper.userDelete(pageVO);

                myPageMapper.receivedCommentDelete(pageVO);
                myPageMapper.userCommentDelete(pageVO);
                myPageMapper.userDiaryDelete(pageVO);
                myPageMapper.userBlockDelete(pageVO);

            }


            BaseResponse response = responseService.getBaseResponse(true, "신고완료 성공");

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }
}
