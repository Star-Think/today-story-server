package com.today.story.main.mypage;

import com.today.story.main.common.dto.PageVO;
import com.today.story.main.mypage.dto.BlockVO;
import com.today.story.main.mypage.dto.SendUserVO;
import com.today.story.main.mypage.dto.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {


    UserVO myPageGet(PageVO pageVO);

    void myPageUpdate(UserVO userVO);

    void passwordChange(SendUserVO userDto);

    List<BlockVO> blockUserGet(PageVO pageVO);

    void blockUserAdd(BlockVO blockVO);

    void blockUserDelete(BlockVO blockVO);

    int blockUserCheck(BlockVO blockVO);
}
