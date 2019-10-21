package com.miaohy.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.miaohy.dao.ProductMapper;
import com.miaohy.pojo.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class ProductService{

    @Resource
    private ProductMapper productMapper;

    
    public int deleteByPrimaryKey(Integer pid) {
        return productMapper.deleteByPrimaryKey(pid);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public int insert(Product record) {
        return productMapper.insert(record);
    }

    
    public int insertSelective(Product record) {
        return productMapper.insertSelective(record);
    }

    
    public Product selectByPrimaryKey(Integer pid) {
        return productMapper.selectByPrimaryKey(pid);
    }

    
    public int updateByPrimaryKeySelective(Product record) {
        return productMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Product record) {
        return productMapper.updateByPrimaryKey(record);
    }

    public Page<Product> getProduct(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<Product> productList = productMapper.getProductList();
        return  productList;
    }
}
