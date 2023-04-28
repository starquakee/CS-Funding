package com.cs304.csfunding.mapper;

import com.cs304.csfunding.entity.Fund;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FundMapper {

    @Insert("insert into fund(FundNumber, FundName, Sum, Balance, RemainDays, StartTime, EndTime) values (#{FundNumber}, #{FundName}, #{Sum}, #{Balance}, #{RemainDays}, #{StartTime}, #{EndTime})")
    void addFund(Fund fund);

    @Select("select * from fund")
    List<Fund> getAllFund();

    @Select("select * from fund where UUID=#{UUID}")
    Fund getFundByID(@Param("UUID") int uuid);


}
