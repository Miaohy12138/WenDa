/**
 * Author : MIAOHY
 * Time :2019/7/15 17:18
 * Beauty is better than ugly!
 */
package com.miaohy.controller;

import com.github.pagehelper.Page;
import com.miaohy.pojo.Product;
import com.miaohy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @RequestMapping("product")
    public String getProduct(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        Page<Product> productList = productService.getProduct(pageNum,pageSize);
        model.addAttribute("productList",productList);
        return "productList";
    }

}

