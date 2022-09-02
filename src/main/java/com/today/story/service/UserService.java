package com.today.story.service;

import com.today.story.controller.UserController;
import com.today.story.dto.response.BaseResponse;
import com.today.story.dto.response.SingleDataResponse;
import com.today.story.exception.UserNotFoundException;
import com.today.story.main.common.dto.PageVO;
import com.today.story.main.diary.dto.CommentVO;
import com.today.story.main.diary.dto.DiaryVO;
import com.today.story.utils.JwtTokenProvider;
import com.today.story.dto.LoginDto;
import com.today.story.dto.UserDto;
import com.today.story.exception.DuplicatedUsernameException;
import com.today.story.exception.LoginFailedException;
import com.today.story.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
}
