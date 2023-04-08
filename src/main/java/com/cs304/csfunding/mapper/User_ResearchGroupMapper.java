package com.cs304.csfunding.mapper;

import com.cs304.csfunding.api.User_ResearchGroupDTO;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.entity.User_ResearchGroup;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface User_ResearchGroupMapper {

    @Insert("insert into user_researchgroup(User_UUID, ResearchGroup_UUID) values (#{User_UUID}, #{ResearchGroup_UUID})")
    void addUser_ResearchGroup(User_ResearchGroup user_researchGroup);

    @Select("select ResearchGroup_UUID from user_researchgroup where User_UUID=#{User_UUID}")
    List<Integer> findResearchGroupByUser(@Param("User_UUID") int user_UUID);




}
