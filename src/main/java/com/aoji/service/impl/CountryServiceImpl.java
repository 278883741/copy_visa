package com.aoji.service.impl;
import com.aoji.mapper.CountryInfoMapper;
import com.aoji.model.CountryInfo;
import com.aoji.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    CountryInfoMapper countryInfoMapper;

    @Override
    public List<CountryInfo> getList(CountryInfo countryInfo){
        return countryInfoMapper.select(countryInfo);
    }
    @Override
    public CountryInfo get(CountryInfo countryInfo){
        List<CountryInfo> list = countryInfoMapper.select(countryInfo);
        if(list.size() >0){
            return list.get(0);
        }
        return null;
    }


    @Override
    public List<CountryInfo> getGroupList(CountryInfo countryInfo) {
        return countryInfoMapper.getGroupList(countryInfo);
    }

    @Override
    public Integer oldNationId(String nationName) {
        return countryInfoMapper.oldNationId();
    }

    @Override
    public List<CountryInfo> selectCountryNameByVisaManagerOrDirector(String oaid) {
        return countryInfoMapper.selectCountryNameByVisaManagerOrDirector(oaid);
    }
}
