package com.tedu.controller;

import com.tedu.entity.District;
import com.tedu.service.IDistrictService;
import com.tedu.service.impl.DistrictServiceImpl;
import com.tedu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
@Controller
@RequestMapping("/districts")
public class DistrictController {
    @Autowired
    private IDistrictService districtService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult list(String parent) {
        List<District> districts = districtService.listByParent(parent);
        return JsonResult.getSuccessJR(districts);
    }
}
