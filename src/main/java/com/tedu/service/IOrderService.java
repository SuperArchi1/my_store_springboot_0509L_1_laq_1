package com.tedu.service;

import com.tedu.entity.Order;

import java.util.List;

public interface IOrderService {
    //保存订单信息
    //参数:aid 地址, cids 购物车记录, uid 用户
    public void insertOrder(Integer aid, List<Integer> cids, Integer uid, String username);

    public List<Order> listByUid(Integer uid,int status);
}
