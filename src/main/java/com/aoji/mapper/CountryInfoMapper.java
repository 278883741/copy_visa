package com.aoji.mapper;

import com.aoji.model.CountryInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface CountryInfoMapper extends Mapper<CountryInfo> {
    List<CountryInfo> getGroupList(CountryInfo countryInfo);

    Integer oldNationId();


    List<CountryInfo> selectCountryNameByVisaManagerOrDirector(@Param( "oaid") String oaid);
}