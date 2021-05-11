package com.tedu.controller;

import com.tedu.entity.Category;
import com.tedu.service.ICategoryService;
import com.tedu.service.impl.CategoryServiceImpl;
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
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult list() {
        List<Category> categories = categoryService.list();
        return JsonResult.getSuccessJR(categories);
    }
}
