package com.tedu.controller;

import com.tedu.entity.Order;
import com.tedu.entity.User;
import com.tedu.service.IOrderService;
import com.tedu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(Integer aid, String cids, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if(user == null) {
            return new JsonResult(500, "登录状态异常,请重新登录");
        }
        else {
            String strs[] = cids.split(",");
            List<Integer> cds = new ArrayList<>();
            for(String str:strs) {
                cds.add(Integer.parseInt(str));
            }
            orderService.insertOrder(aid, cds, user.getUid(), user.getUsername());
            return  JsonResult.getSuccessJR();
        }

    }

    @RequestMapping("/list")
    @ResponseBody
    public JsonResult listByUid(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loginUser");
        //User user = (User) RequestUtil.getSession();
        if (user != null) {
            try {
                int state = Integer.parseInt(req.getParameter("status"));
                List<Order> orders = orderService.listByUid(user.getUid(),state);
                return new JsonResult(200, "查询成功！",orders);
            } catch (Exception e) {
                return new JsonResult(500, "登录状态异常,请重新登录");
            }
        } else {
            return new JsonResult(401, "请登录");
        }
    }
}
