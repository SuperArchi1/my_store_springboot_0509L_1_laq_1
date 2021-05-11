package com.tedu.service.impl;

import com.tedu.dao.OrderDao;
import com.tedu.entity.Address;
import com.tedu.entity.Order;
import com.tedu.entity.OrderItem;
import com.tedu.entity.vo.CartVo;
import com.tedu.service.IAddressService;
import com.tedu.service.ICartService;
import com.tedu.service.IOrderService;
import com.tedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IProductService productService;
    @Override
    public void insertOrder(Integer aid, List<Integer> cids, Integer uid, String username) {
        List<CartVo> carts = cartService.findByCids(cids);

        //获取订单商品总价
        long totalPrice = 0L;
        for(CartVo cartVo:carts) {
            totalPrice += cartVo.getRealPrice();
        }

        Address address = addressService.getById(aid);

        Order order = new Order();
        order.setPrice(totalPrice);
        order.setRecvAddress(address.getAddress());
        order.setRecvArea(address.getAreaName());
        order.setRecvCity(address.getCityName());
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setStatus(0);//默认是未付款
        order.setUid(uid);
        order.setCreatedUser(username);
        order.setModifiedUser(username);
        //保存订单信息,返回订单ID
       orderDao.insertOrder(order);
        Integer oid = order.getId();
        for(CartVo cartVo:carts) {
            OrderItem item = new OrderItem();
            item.setImage(cartVo.getImage());
            item.setNum(cartVo.getNum());
            item.setOid(oid);
            item.setPid(cartVo.getPid());
            item.setPrice(cartVo.getRealPrice());
            item.setTitle(cartVo.getTitle());
            item.setCreatedUser(username);
            item.setModifiedUser(username);
            //保存订单详情
            orderDao.insertOrderItem(item);
            //删除购物车记录
            cartService.delete(cartVo.getCid());
        }
    }

    @Override
    public List<Order> listByUid(Integer uid,int status) {
        return orderDao.listByUid(uid,status);
    }
}
