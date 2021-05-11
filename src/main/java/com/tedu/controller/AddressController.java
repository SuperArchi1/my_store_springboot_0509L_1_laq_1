package com.tedu.controller;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.tedu.entity.Address;
import com.tedu.entity.User;
import com.tedu.service.IAddressService;
import com.tedu.service.impl.AddressServiceImpl;
import com.tedu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/addresses")
public class AddressController {
    @Autowired
    private IAddressService addressService;
    @RequestMapping(value = "/createAddress", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(Address address, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if(user == null) {
            return new JsonResult(500, "登录状态异常,请重新登录");
        }
        else {
            address.setCreatedUser(user.getUsername());
            address.setModifiedUser(user.getUsername());
            addressService.saveAddress(address,user);
            return  JsonResult.getSuccessJR();
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult list(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if(user == null) {
            return new JsonResult(500, "登录状态异常,请重新登录");
        }
        else {
            List<Address> addresses = addressService.list(user.getUid());
            return JsonResult.getSuccessJR(addresses);
        }
    }

    @RequestMapping(value = "/{aid}/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete(Integer aid) {
        try {
            addressService.delete(aid);
            return new JsonResult(1000, "删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(500, "该地址不存在");
        }
        }

    @RequestMapping(value = "/{aid}/set_default", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult setDefalt(Integer aid) {
        try {
            Address address = addressService.getById(aid);
            Integer uid = address.getUid();
          List<Address> addressList = addressService.list(uid);
           for(Address a:addressList) {
               if (a.getIsDefault() == 1)
                   addressService.setNotDefault(a.getAid());
           }
            addressService.setDefault(aid);
            return new JsonResult(1000, "设置成功");
        }catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(500, "设置失败");
       }
    }

    @RequestMapping(value = "/listbyaid",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult listbyaid(Integer aid) {
        System.out.println("----------------------controller"+aid);

        try{
            Address address = addressService.getById(aid);
            System.out.println(address.getAid());
            return JsonResult.getSuccessJR(address);
        }catch (Exception e){
            return new JsonResult(500, "查询失败");
        }
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult update(Address address, HttpSession session) {
        User user = (User)session.getAttribute("loginUser");
        if(user == null){
            return new JsonResult(500, "登录状态异常,请重新登录");
        }

        try{
            addressService.update(address);
            System.out.println("-------------------------"+address.getAddress());

                return new JsonResult(1000, "地址更新成功");
            }catch (Exception e) {
                e.printStackTrace();
                return new JsonResult(500, "地址更新失败");
            }
        }
    }



