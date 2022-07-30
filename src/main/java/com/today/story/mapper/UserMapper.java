package com.today.story.mapper;

import com.today.story.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<UserDto> findUserByUsername(String username);
    Optional<UserDto> findByUserId(String userId);
    void save(UserDto userDto);
}
