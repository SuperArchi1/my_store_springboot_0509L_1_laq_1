package com.tedu.service.impl;

import com.tedu.dao.AddressDao;
import com.tedu.entity.Address;
import com.tedu.entity.User;
import com.tedu.service.IAddressService;
import com.tedu.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("addresServie")
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private IDistrictService districtService;
    @Override
    public void saveAddress(Address address, User user) {
        address.setUid(user.getUid());
        String provinceName = districtService.getName(address.getProvinceCode());
        String cityName = districtService.getName(address.getCityCode());
        String areaName = districtService.getName(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);
        addressDao.saveAddress(address);
    }

    @Override
    public List<Address> list(Integer uid) {
        List<Address> addresses = addressDao.list(uid);
        return addresses;
    }

    @Override
    public Address getById(Integer aid) {
        Address address = addressDao.getById(aid);
        return address;
    }

    @Override
    public void delete(Integer aid){

            addressDao.delete(aid);
    }
    @Override
    public void setDefault(Integer aid) {
        addressDao.setDefault(aid);
    }

    @Override
    public void setNotDefault(Integer aid) {
        addressDao.setNotDefault(aid);
    }
    @Override
    public void saveAddress(Address address) {

    }
    @Override
    public void update(Address address) {
        String provinceName = districtService.getName(address.getProvinceCode());
        String cityName = districtService.getName(address.getCityCode());
        String areaName = districtService.getName(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setAreaName(areaName);
        address.setCityName(cityName);
        addressDao.update(address);
    }


}
