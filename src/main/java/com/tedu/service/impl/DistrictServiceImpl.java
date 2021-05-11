package com.tedu.service.impl;

import com.tedu.dao.DistrictDao;
import com.tedu.entity.District;
import com.tedu.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("districtService")
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    private DistrictDao districtDao;

    @Override
    public String getName(Integer code) {
        return districtDao.getName(code);
    }

    @Override
    public List<District> listByParent(String parent) {
        List<District> districts = districtDao.listByParent(parent);
        return districts;
    }
}
