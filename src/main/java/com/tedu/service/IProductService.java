package com.tedu.service;

import com.tedu.entity.PageRecord;
import com.tedu.entity.Product;

import java.util.List;

public interface IProductService {
    public Product getById(Integer id);
    public PageRecord<List<Product>> listAllByCid(Integer cid, Integer currentPage, Integer pageSize);
}
