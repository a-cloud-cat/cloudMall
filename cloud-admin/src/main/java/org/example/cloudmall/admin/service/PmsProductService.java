package org.example.cloudmall.admin.service;

import org.example.cloudmall.model.PmsProduct;

import java.util.List;

public interface PmsProductService {

    List<PmsProduct> listProduct(String keyword, int pageNum, int pageSize);
}
