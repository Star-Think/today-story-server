package com.today.story.main.mypage;

import com.today.story.controller.UserController;
import com.today.story.dto.UserDto;
import com.today.story.dto.response.BaseResponse;
import com.today.story.dto.response.ListDataResponse;
import com.today.story.dto.response.SingleDataResponse;
import com.today.story.exception.LoginFailedException;
import com.today.story.exception.UserNotFoundException;
import com.today.story.main.common.dto.PageVO;
import com.today.story.main.diary.dto.DiaryVO;
import com.today.story.main.mypage.dto.BlockVO;
import com.today.story.main.mypage.dto.SendUserVO;
import com.today.story.main.mypage.dto.UserVO;
import com.today.story.mapper.UserMapper;
import com.today.story.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MyPageService {
    private final ResponseService responseService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private MyPageMapper myPageMapper;



    public ResponseEntity myPageGet(Authentication authentication) {
        ResponseEntity responseEntity = null;
        try {

            PageVO pageVO = new PageVO();

            String userId  = ((UserDto) authentication.getPrincipal()).getUser_id();

            pageVO.setUser_id(userId);

            UserVO userVO = myPageMapper.myPageGet(pageVO);

            SingleDataResponse<UserVO> response = responseService.getSingleDataResponse(true, "?????? ??????",userVO);

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity myPageUpdate(Authentication authentication, UserVO userVO) {
        ResponseEntity responseEntity = null;
        try {
            String userId = ((UserDto) authentication.getPrincipal()).getUser_id();

            userVO.setUser_id(userId);



            UserVO userVO1 = myPageMapper.userEmailGet(userVO);
            BaseResponse response;
            if(userVO1 == null || userVO1.getUser_id().equals(userId)){
                response  = responseService.getBaseResponse(true, "?????? ??????");
                myPageMapper.myPageUpdate(userVO);
            } else {
                response  = responseService.getBaseResponse(false, "?????? ?????? ?????? ??????????????????.");
            }

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity passwordChange(Authentication authentication, SendUserVO userDto) {
        ResponseEntity responseEntity = null;
        try {
            String userId = ((UserDto) authentication.getPrincipal()).getUser_id();

            userDto.setUser_id(userId);

            UserDto findUser = userMapper.findUserByUsername(userDto.getUser_id()).orElseThrow(() -> new LoginFailedException("????????? ??????????????????"));

            if (!passwordEncoder.matches(userDto.getPassword(), findUser.getPassword())) {
                throw new LoginFailedException("????????? ?????????????????????");
            } else{
                userDto.setNew_password(passwordEncoder.encode(userDto.getNew_password()));
                myPageMapper.passwordChange(userDto);
            }

            BaseResponse response = responseService.getBaseResponse(true, "?????? ??????");

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity blockUserGet(Authentication authentication) {
        ResponseEntity responseEntity = null;
        try {
            String userId = "";
            if (authentication != null) {
                userId = ((UserDto) authentication.getPrincipal()).getUser_id();
            }

            PageVO pageVO = new PageVO();
            pageVO.setUser_id(userId);
            List<BlockVO> blockVOList = myPageMapper.blockUserGet(pageVO);



            ListDataResponse<BlockVO> response = responseService.getListDataResponse(true, "?????? ??????",blockVOList);

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity blockUserAdd(Authentication authentication, BlockVO blockVO) {
        ResponseEntity responseEntity = null;
        try {
            String userId = ((UserDto) authentication.getPrincipal()).getUser_id();

            blockVO.setUser_id(userId);

            myPageMapper.blockUserAdd(blockVO);

            BaseResponse response = responseService.getBaseResponse(true, "?????? ??????");

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity blockUserDelete(Authentication authentication, BlockVO blockVO) {
        ResponseEntity responseEntity = null;
        try {
            String userId = ((UserDto) authentication.getPrincipal()).getUser_id();

            blockVO.setUser_id(userId);

            myPageMapper.blockUserDelete(blockVO);

            BaseResponse response = responseService.getBaseResponse(true, "?????? ??????");

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }

    public ResponseEntity blockUserCheck(Authentication authentication,BlockVO blockVO) {
        ResponseEntity responseEntity = null;
        try {
            String userId = ((UserDto) authentication.getPrincipal()).getUser_id();

            blockVO.setUser_id(userId);

            int cnt = myPageMapper.blockUserCheck(blockVO);

            BaseResponse response = null;

            if (cnt == 1) {
                response =   responseService.getBaseResponse(false, "?????? ??????");
            } else {
                response =   responseService.getBaseResponse(true, "?????? ??????");
            }

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }

    public ResponseEntity userDelete(Authentication authentication) {
        ResponseEntity responseEntity = null;
        try {
            String userId = ((UserDto) authentication.getPrincipal()).getUser_id();

            PageVO pageVO = new PageVO();

            pageVO.setUser_id(userId);

            myPageMapper.userDelete(pageVO);

            myPageMapper.receivedCommentDelete(pageVO);
            myPageMapper.userCommentDelete(pageVO);
            myPageMapper.userDiaryDelete(pageVO);
            myPageMapper.userBlockDelete(pageVO);

            BaseResponse response = responseService.getBaseResponse(true, "?????? ??????");

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }
}
