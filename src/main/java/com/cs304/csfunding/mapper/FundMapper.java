package com.cs304.csfunding.mapper;

import com.cs304.csfunding.api.FundDTO;
import com.cs304.csfunding.entity.Fund;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface FundMapper {

    @Insert("insert into fund(FundNumber, FundName, Sum, Balance, RemainDays, StartTime, EndTime) values (#{FundNumber}, #{FundName}, #{Sum}, #{Balance}, #{RemainDays}, #{StartTime}, #{EndTime})")
    void addFund(Fund fund);

    @Select("select max(`UUID`) from fund")
    int latestFundId();

    @Select("select * from fund")
    List<Fund> getAllFund();

    @Select("select * from fund where UUID=#{UUID}")
    Fund getFundByID(@Param("UUID") int uuid);

    @Select("select * from fund f join researchgroup_fund rf on rf.Fund_UUID = f.UUID where f.FundNumber like CONCAT('%', #{FundNumber}, '%') and f.FundName like CONCAT('%', #{FundName}, '%') and rf.ResearchGroup_UUID=#{ResearchGroup_UUID}")
    List<Fund> getFundVague(@Param("FundNumber") String FundNumber, @Param("FundName") String FundName, @Param("ResearchGroup_UUID") String researchGroupId);

    @Update("update fund set FundNumber=#{FundNumber}, FundName=#{FundName}, Sum=#{Sum}, Balance=#{Balance}, RemainDays=#{RemainDays}, StartTime=#{StartTime}, EndTime=#{EndTime} where UUID=#{uuid}")
    void modifyFund(Fund fund);

    @Delete("delete from fund where UUID=#{UUID}")
    void deleteFundByID(@Param("UUID") int uuid);
}
