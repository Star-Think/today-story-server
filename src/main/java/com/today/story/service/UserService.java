package com.today.story.service;

import com.today.story.controller.UserController;
import com.today.story.dto.response.BaseResponse;
import com.today.story.dto.response.SingleDataResponse;
import com.today.story.exception.UserNotFoundException;
import com.today.story.main.common.dto.PageVO;
import com.today.story.main.diary.dto.CommentVO;
import com.today.story.main.diary.dto.DiaryVO;
import com.today.story.main.mypage.MyPageMapper;
import com.today.story.main.mypage.dto.SendUserVO;
import com.today.story.utils.JwtTokenProvider;
import com.today.story.dto.LoginDto;
import com.today.story.dto.UserDto;
import com.today.story.exception.DuplicatedUsernameException;
import com.today.story.exception.LoginFailedException;
import com.today.story.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final ResponseService responseService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MyPageMapper myPageMapper;

    @Transactional
    public UserDto join(UserDto userDto) {
        if (userMapper.findUserByUsername(userDto.getUsername()).isPresent()) {
            throw new DuplicatedUsernameException("이미 가입된 유저입니다");
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userMapper.save(userDto);

        return userMapper.findUserByUsername(userDto.getUsername()).get();
    }

    public String login(LoginDto loginDto) {
        UserDto userDto = userMapper.findUserByUsername(loginDto.getUser_id())
                .orElseThrow(() -> new LoginFailedException("잘못된 아이디입니다"));

        if (!passwordEncoder.matches(loginDto.getPassword(), userDto.getPassword())) {
            throw new LoginFailedException("잘못된 비밀번호입니다");
        }

        return jwtTokenProvider.createToken(userDto.getUser_id(), Collections.singletonList(userDto.getRole()));
    }

    public UserDto findByUserId(String userId) {
        return userMapper.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("없는 유저입니다."));
    }

    public ResponseEntity userNameGet(PageVO pageVO) {
        ResponseEntity responseEntity = null;
        try {
            UserDto userDto = userMapper.userNameGet(pageVO);

            SingleDataResponse<UserDto> response = responseService.getSingleDataResponse(true, "조회 성공",userDto);

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity userIdFindGet(PageVO pageVO) {
        ResponseEntity responseEntity = null;
        try {
            UserDto userDto = userMapper.userIdFindGet(pageVO);
            SingleDataResponse<UserDto> response;
            if(userDto == null) {
                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(responseService.getBaseResponse(false, "없는 이메일 입니다."));
            } else {
                response = responseService.getSingleDataResponse(true, "조회 성공",userDto);


                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(pageVO.getEmail());
                message.setSubject("오늘의일기 아이디찾기");
                message.setText(userDto.getUser_id());
                javaMailSender.send(message);


                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
            }

        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    public ResponseEntity userPwdFindGet(PageVO pageVO) {
        ResponseEntity responseEntity = null;
        try {
            UserDto userDto = userMapper.userPwdFindGet(pageVO);
            SingleDataResponse<UserDto> response;
            if(userDto == null) {
                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(responseService.getBaseResponse(false, "비밀번호를 찾을 수 없습니다."));
            } else {
                response = responseService.getSingleDataResponse(true, "조회 성공",userDto);


                String pwd = getRamdomPassword(10);
                SendUserVO sendUserVO = new SendUserVO();
                sendUserVO.setUser_id(userDto.getUser_id());
                sendUserVO.setNew_password(passwordEncoder.encode(pwd));
                myPageMapper.passwordChange(sendUserVO);

                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(pageVO.getEmail());
                message.setSubject("오늘의일기 비번찾기");
                message.setText(pwd);
                javaMailSender.send(message);


                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
            }

        } catch (UserNotFoundException exception) {
            logger.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }


    public String getRamdomPassword(int size) {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<size; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }
}
