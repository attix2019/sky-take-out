package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;

@Mapper
public interface UserMapper {

    @Select("select * from user where openid=#{openid} ")
    User findUserByOpenId(String openid);

    void insertUser(User user);

    @Select("select count(*) from user where substring(create_time,1,10) >= #{begin}" +
            " and substring(create_time,1,10) <= #{end}")
    Integer countNewUserInRange(LocalDate begin, LocalDate end);
}
