package com.tedu.service;

import com.tedu.entity.Cart;
import com.tedu.entity.User;
import com.tedu.entity.vo.CartVo;

import java.util.List;

public interface ICartService {
    //添加商品到购物车
    public void save(Cart cart, User user);
    public List<CartVo> listCart(Integer uid);
    public List<CartVo> findByCids(List<Integer> cids);
    public Cart getById(Integer cid);
    public void delete(Integer cid);
    //更新购物车数量
    public Boolean changeNum(Integer cid, Integer num, User user);
    public void removeByCidList(List<Integer> cidList);
}

