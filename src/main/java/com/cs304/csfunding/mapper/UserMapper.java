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


    User findUserById(String id);

    @Select("SELECT * FROM user WHERE uuid = #{uuid}")
    User findUserByUuid(int uuid);


    @Select("select * from user where PhoneNum=#{PhoneNum}")
    User findUserByPhoneNum(@Param("PhoneNum") String PhoneNum);


}
