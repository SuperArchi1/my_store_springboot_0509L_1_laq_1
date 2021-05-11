package com.tedu.service;

import com.tedu.entity.Address;
import com.tedu.entity.User;

import java.util.List;

public interface IAddressService {
    public void saveAddress(Address address, User user);
    public List<Address> list(Integer uid);
    public Address getById(Integer aid);
    public void delete(Integer aid);
    public void saveAddress(Address address);
    public void setDefault(Integer aid);
    public void setNotDefault(Integer aid);
    public void update(Address address);
}
