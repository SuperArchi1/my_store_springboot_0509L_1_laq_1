package com.tedu.controller;

import com.tedu.entity.PageRecord;
import com.tedu.entity.Product;
import com.tedu.service.IProductService;
import com.tedu.service.impl.ProductServiceImpl;
import com.tedu.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @RequestMapping(value = "/{id}/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getById(@PathVariable Integer id) {
        Product product = productService.getById(id);
        return JsonResult.getSuccessJR(product);
    }
    @RequestMapping(value = "/findProductByCid", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult findProductByCid(Integer cid, Integer currentPage, Integer pageSize){
        PageRecord pageRecord = productService.listAllByCid(cid, currentPage, pageSize);
        return JsonResult.getSuccessJR(pageRecord);
    }
}
