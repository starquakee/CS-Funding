package com.cs304.csfunding.service;

import com.cs304.csfunding.api.UserDTO;
import com.cs304.csfunding.api.User_ResearchGroupDTO;
import com.cs304.csfunding.controller.User_ResearchGroupController;
import com.cs304.csfunding.entity.User;
import com.cs304.csfunding.entity.User_ResearchGroup;
import com.cs304.csfunding.mapper.UserMapper;
import com.cs304.csfunding.mapper.User_ResearchGroupMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class User_ResearchGroupService {
    private final User_ResearchGroupMapper user_researchGroupMapper;

    public User_ResearchGroupService(User_ResearchGroupMapper user_researchGroupMapper) {
        this.user_researchGroupMapper = user_researchGroupMapper;
    }

    public String delete(User_ResearchGroupDTO user_researchGroupDTO){
        User_ResearchGroup user_researchGroup = new User_ResearchGroup();
        user_researchGroup.setUser_UUID(user_researchGroupDTO.getUserUUID());
        user_researchGroup.setResearchGroup_UUID(user_researchGroupDTO.getResearchGroupUUID());
        user_researchGroupMapper.deleteUserGroup(user_researchGroup);
        return "OK";
    }

    public String testInsert(User_ResearchGroupDTO user_researchGroupDTO) {
        User_ResearchGroup user_researchGroup = new User_ResearchGroup();
        user_researchGroup.setUser_UUID(user_researchGroupDTO.getUserUUID());
        user_researchGroup.setResearchGroup_UUID(user_researchGroupDTO.getResearchGroupUUID());
//        System.out.println(user_researchGroupDTO.getUserUUID());
//        System.out.println(user_researchGroupDTO.getResearchGroupUUID());
        user_researchGroupMapper.addUser_ResearchGroup(user_researchGroup);
        return "";
    }

    public List<Integer> testQueryByUser(int user){
        return user_researchGroupMapper.findResearchGroupByUser(user);
    }

    public List<User> queryUserByGroup(int gid){
        return user_researchGroupMapper.findUserByGroup(gid);
    }

    public List<User> queryUserNotInGroup(int gid){
        return user_researchGroupMapper.findUserNotInGroup(gid);
    }
}
