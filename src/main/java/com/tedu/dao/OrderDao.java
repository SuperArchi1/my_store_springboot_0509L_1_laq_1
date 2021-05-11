package com.tedu.dao;

import com.tedu.entity.Order;
import com.tedu.entity.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDao {
    public Integer insertOrder(Order order);

    public void insertOrderItem(OrderItem orderItem);

    public List<Order> listByUid(@Param("uid") Integer uid,@Param("status") int status);
}
