package com.tedu.dao;

import com.tedu.entity.Address;

import java.util.List;

public interface AddressDao {
    //保存一条收货地址
    public void saveAddress(Address address);
    //用户查询收货地址
    public List<Address> list(int uid);
    //用户查询一个地址
    public Address getById(int aid);
    //删除一个地址
    public void delete(Integer aid);
    //更新一个地址为默认地址
    public void setDefault(Integer aid);
    //取消更新一条地址为默认地址
    public void setNotDefault(Integer aid);
    //修改一条地址信息
    public void update(Address address);
}
