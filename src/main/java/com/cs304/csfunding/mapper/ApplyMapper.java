package com.cs304.csfunding.mapper;

import com.cs304.csfunding.entity.Apply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApplyMapper {

    @Insert("insert into apply(Name, Money, Type1, Type2, FundID, ResearchGroupID, UserID, State, ContentSummary, Remark, Time) values " +
            "(#{Name}, #{Money}, #{Type1}, #{Type2}, #{FundID}, #{ResearchGroupID}, #{UserID}, #{State}, #{ContentSummary}, #{Remark}, #{Time})")
    void addApply(Apply apply);

    @Select("select * from apply")
    List<Apply> getAllApply();

    @Select("select * from apply where FundID=#{FundID}")
    List<Apply> findApplyByFundID(@Param("FundID") int fundID);

    @Select("select * from apply where ResearchGroupID=#{ResearchGroupID}")
    List<Apply> findApplyByResearchGroupID(@Param("ResearchGroupID") int researchGroupID);

    @Select("select * from apply where UserID=#{UserID} order by Time desc")
    List<Apply> findApplyByUserID(@Param("UserID") int userID);


}
