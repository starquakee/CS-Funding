package com.cs304.csfunding.mapper;

import com.cs304.csfunding.api.User_ResearchGroupDTO;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.entity.User_ResearchGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface User_ResearchGroupMapper {

    @Insert("insert into user_researchgroup(User_UUID, ResearchGroup_UUID) values (#{User_UUID}, #{ResearchGroup_UUID})")
    void addUser_ResearchGroup(User_ResearchGroup user_researchGroup);

    @Select("select ResearchGroup_UUID from user_researchgroup where User_UUID=#{User_UUID}")
    List<Integer> findResearchGroupByUser(@Param("User_UUID") int user_UUID);

    @Select("select * from user u " +
            "join user_researchgroup u_r on u.UUID=u_r.User_UUID " +
            "where u_r.ResearchGroup_UUID = #{gid} and not u.IsAdmin")
    List<User> findUserByGroup(int gid);

    @Select("select * from `user` u where `UUID` not in ( " +
            "select u.`UUID`  from `user` u " +
            "join user_researchgroup u_r on u.UUID=u_r.User_UUID " +
            "where u_r.ResearchGroup_UUID = #{gid}) " +
            "and not IsAdmin ")
    List<User> findUserNotInGroup(int gid);

    @Delete("delete from user_researchgroup where User_UUID=#{User_UUID} and ResearchGroup_UUID=#{ResearchGroup_UUID}")
    void deleteUserGroup(User_ResearchGroup user_researchGroup);

}
