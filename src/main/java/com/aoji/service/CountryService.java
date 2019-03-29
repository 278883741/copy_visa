package com.aoji.service;
import com.aoji.model.*;
import java.util.List;
import java.util.Map;

public interface CountryService {
    List<CountryInfo> getList(CountryInfo countryInfo);
    CountryInfo get(CountryInfo countryInfo);
    List<CountryInfo> getGroupList(CountryInfo countryInfo);

    Integer oldNationId(String nationName);

    List<CountryInfo> selectCountryNameByVisaManagerOrDirector(String oaid);
}
