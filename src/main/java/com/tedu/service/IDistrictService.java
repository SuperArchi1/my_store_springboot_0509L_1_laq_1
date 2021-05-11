package com.tedu.service;

import com.tedu.entity.District;

import java.util.List;

public interface IDistrictService {
    public String getName(Integer code);
    public List<District> listByParent(String parent);
}
