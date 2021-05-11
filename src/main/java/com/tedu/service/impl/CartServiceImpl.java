package com.tedu.service.impl;

import com.tedu.dao.CartDao;
import com.tedu.entity.Cart;
import com.tedu.entity.Product;
import com.tedu.entity.User;
import com.tedu.entity.vo.CartVo;
import com.tedu.service.ICartService;
import com.tedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cartService")
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private IProductService productService;
    @Override
    public void save(Cart cart, User user) {
        cart.setUid(user.getUid());
        //判断购物车中是否有该商品
        Cart newCart = cartDao.getByUidAndPid(cart);
        if(newCart == null) {
            Product product = productService.getById(cart.getPid());
            Long price = new Long(product.getPrice());
            cart.setPrice(price);
            cart.setCreatedUser(user.getUsername());
            cart.setModifiedUser(user.getUsername());
            cartDao.save(cart);
        }
        else {
            //修改商品数量
            newCart.setNum(newCart.getNum() + cart.getNum());
            newCart.setModifiedUser(user.getUsername());
            cartDao.updateNum(newCart);
        }
    }

    @Override
    public List<CartVo> listCart(Integer uid) {
        return cartDao.listCart(uid);
    }

    @Override
    public List<CartVo> findByCids(List<Integer> cids) {
        return cartDao.findByCids(cids);
    }

    @Override
    public Cart getById(Integer cid) {
        return cartDao.getById(cid);
    }

    @Override
    public void delete(Integer cid) {
        cartDao.delete(cid);
    }

    @Override
    public Boolean changeNum(Integer cid, Integer num, User user) {
        Cart oriCart = cartDao.getById(cid);
        if (oriCart.getNum() + num > 0){
            oriCart.setNum(oriCart.getNum() + num);
            oriCart.setModifiedUser(user.getUsername());
            oriCart.setCreatedUser(user.getUsername());
            cartDao.updateNum(oriCart);
            return true;
        }else{
            return false;
        }
    }



    @Override
    public void removeByCidList(List<Integer> cidList) {

        cartDao.removeByCidList(cidList);
    }
}
