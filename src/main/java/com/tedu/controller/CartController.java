package com.tedu.controller;

import com.tedu.entity.Cart;
import com.tedu.entity.User;
import com.tedu.entity.vo.CartVo;
import com.tedu.service.ICartService;
import com.tedu.service.IProductService;
import com.tedu.service.impl.CartServiceImpl;
import com.tedu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private ICartService cartService;
    @Autowired
    private IProductService productService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(Integer pid, Integer num, HttpSession session) {
        //获取session中的登录用户
        User user = (User) session.getAttribute("loginUser");
        if(user == null) {
            return new JsonResult(1000, "error");
        }
        else {
            Cart cart = new Cart();
            cart.setNum(num);
            cart.setPid(pid);
            cart.setUid(user.getUid());
            try {
                cartService.save(cart, user);
            } catch (Exception e) {
                return new JsonResult(500, e.getMessage());
            }
            return JsonResult.getSuccessJR();
        }

    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult list(HttpSession session){
        //获取session中的登录用户
        User user = (User) session.getAttribute("loginUser");
        if(user == null) {
            return new JsonResult(1000, "error");
        }
        else {
            List<CartVo> carts = cartService.listCart(user.getUid());
            return JsonResult.getSuccessJR(carts);
        }
    }

    //获取提交的购物车记录
    @RequestMapping(value = "/findByCids", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult findByCids(String cids) {
        //购物车记录转换为字符串数组
        String strs[] = cids.split(",");
        List<Integer> cds = new ArrayList<>();
        for(String str:strs) {
            cds.add(Integer.parseInt(str));
        }
        List<CartVo> carts = cartService.findByCids(cds);
        return JsonResult.getSuccessJR(carts);
    }

    //修改购物车记录
    @RequestMapping("/changeNum")
    @ResponseBody
    public Map<String,Object> changeNum(HttpServletRequest req, Integer cid, Integer num) {
        Map<String, Object> map = new HashMap<>();

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            map.put("msg", "not login");
        } else {
            Boolean result = cartService.changeNum(cid, num, user);
            if (result) {
                map.put("state", 1000);
            } else {
                map.put("msg", "only 1 in cart");
            }
        }
        return map;
    }

    //删除购物车记录
    @RequestMapping("/remove")
    @ResponseBody
    public Map<String, Object> remove(HttpServletRequest req, Integer cid) {
        Map<String, Object> map = new HashMap<>();
//        System.out.println("123");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            map.put("msg", "not login");
        } else {
            cartService.delete(cid);
            map.put("state", 1000);
        }
        return map;
    }
    //批量删除
    @RequestMapping("/removeList")
    @ResponseBody
    public Map<String, Object> removeList(HttpServletRequest req, Integer[] cids) {
        Map<String, Object> map = new HashMap<>();
//        System.out.println("123");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            map.put("msg", "not login");
        } else {
            List<Integer> cidList = Arrays.asList(cids);
            cartService.removeByCidList(cidList);
            map.put("state", 1000);
        }
        return map;
    }
}
