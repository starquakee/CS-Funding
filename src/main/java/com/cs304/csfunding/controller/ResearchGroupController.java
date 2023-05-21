package com.cs304.csfunding.controller;

import com.cs304.csfunding.api.ResearchGroupDTO;
import com.cs304.csfunding.api.ResearchGroupFundVO;
import com.cs304.csfunding.api.Result;
import com.cs304.csfunding.entity.Fund;
import com.cs304.csfunding.entity.ResearchGroup;
import com.cs304.csfunding.service.FundService;
import com.cs304.csfunding.service.ResearchGroupService;
import com.cs304.csfunding.service.ResearchGroup_FundService;
import com.cs304.csfunding.util.HttpContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ResearchGroupController {

    @Autowired
    private ResearchGroupService researchGroupService;
    @Autowired
    private FundService fundService;
    @Autowired
    private ResearchGroup_FundService researchGroup_fundService;


    @PostMapping(value = "/register/researchgroup")
    public String testAddResearchGroup(@RequestBody ResearchGroupDTO researchGroupDTO) {
        System.out.println(researchGroupDTO);
        return researchGroupService.testInsert(researchGroupDTO);
    }

    @GetMapping("/get-all-research-groups")
    public Result getAllResearchGroup() {
        List<ResearchGroup> researchgroups = researchGroupService.testQueryAll();
        if (researchgroups == null) {
            return new Result(404, "researchgroups not found", null);
        } else {
            return new Result(researchgroups);
        }
    }

    @GetMapping("/get-research-groups-by-name")
    public Result getResearchGroupByName(String teacherName) {
        List<ResearchGroup> researchgroups = researchGroupService.testQueryByName(teacherName);
        if (researchgroups == null) {
            return new Result(404, "researchgroups not found", null);
        } else {
            return new Result(researchgroups);
        }
    }

    @GetMapping("/get-current-group")
    public Result getCurrentGroup() {
        int uid = HttpContextUtil.getRequestUuid();
        return getResearchGroupByUser(uid);
    }

    @GetMapping("/get-research-groups-by-user")
    public Result getResearchGroupByUser(int UserID) {
        List<ResearchGroup> researchgroups = researchGroupService.testQueryByUser(UserID);
        if (researchgroups == null) {
            return new Result(404, "research groups not found", null);
        } else {
            return new Result(researchgroups);
        }
    }

    @GetMapping("/current-group-data")
    public Result getCurrentResearchGroupData() {
        return getResearchGroupByUser(HttpContextUtil.getRequestUuid());
    }

    @GetMapping("/user-group-fund-data")
    public Result getUserResearchGroupFundData(@RequestParam int uid) {
        List<ResearchGroup> researchgroups = researchGroupService.testQueryByUser(uid);
        List<ResearchGroupFundVO> vos = new ArrayList<>();
        for (ResearchGroup group : researchgroups) {
            ResearchGroupFundVO vo = new ResearchGroupFundVO();
            vo.setValue(group.getUuid());
            vo.setLabel(group.getTeacher());
            List<Integer> fundIds = researchGroup_fundService.testQueryByResearchGroup(group.getUuid());
            List<ResearchGroupFundVO.FundOpVO> funds = new ArrayList<>();
            for (int fid : fundIds) {
                Fund fd = fundService.queryByID(fid);
                ResearchGroupFundVO.FundOpVO fdVO = new ResearchGroupFundVO.FundOpVO();
                fdVO.setLabel(fd.getFundName());
                HashMap<String, Object> fdVal = new HashMap<>();
                fdVal.put("uuid", fd.getUuid());
                fdVal.put("balance", fd.getBalance());
                fdVO.setValue(fdVal);
                funds.add(fdVO);
            }
            vo.setChildren(funds);
            vos.add(vo);
        }
        return new Result(vos);
    }

    @GetMapping("/current-group-fund-data")
    public Result getCurrentResearchGroupFundData() {
        return getUserResearchGroupFundData(HttpContextUtil.getRequestUuid());
    }


}
