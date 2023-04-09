package com.cs304.csfunding.mapper;

import com.cs304.csfunding.entity.Fund_Apply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Fund_ApplyMapper {

    @Insert("insert into fund_apply(Fund_UUID, Apply_UUID) values (#{Fund_UUID}, #{Apply_UUID})")
    void addFund_Apply(Fund_Apply fund_apply);

    @Select("select Apply_UUID from fund_apply where Fund_UUID=#{Fund_UUID}")
    List<Integer> findApplyByFund(@Param("Fund_UUID") int fund_UUID);



}
