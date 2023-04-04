package com.cs304.csfunding.mapper;

import com.cs304.csfunding.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into user(ID, `Key`, IsAdmin) values (#{ID}, #{Key}, #{IsAdmin})")
    void addUser(User user);

    @Select("select * from user")
    List<User> getAllUser();

    User findUserById(String id);

}
