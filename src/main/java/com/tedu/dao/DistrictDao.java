package com.tedu.dao;

import com.tedu.entity.District;

import java.util.List;

public interface DistrictDao {
    //根据省市县的code编号查询省市县的名字
    public String getName(Integer code);
    //根据父级编号查询所有的子级数据
    public List<District> listByParent(String parent);
}
