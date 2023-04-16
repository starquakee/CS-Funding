package com.cs304.csfunding.mapper;

import com.cs304.csfunding.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into user(ID, `Key`, IsAdmin, PhoneNum, Name) values (#{ID}, #{Key}, #{IsAdmin}, #{PhoneNum}, #{Name})")
    void addUser(User user);

    @Select("select * from user")
    List<User> getAllUser();

    @Select("select * from user where IsAdmin = 1")
    List<User> getAllAdmin();

    User findUserById(String id);


    @Select("select * from user where PhoneNum=#{PhoneNum}")
    User findUserByPhoneNum(@Param("PhoneNum") String PhoneNum);


}
