package org.example.cloudmall.admin.service;

import org.example.cloudmall.admin.dto.PmsProductParam;
import org.example.cloudmall.model.PmsProduct;

import java.util.List;

public interface PmsProductService {

    List<PmsProduct> listProduct(String keyword, int pageNum, int pageSize);

    PmsProductParam getProduct(Long id);

    int createProduct(PmsProductParam productParam);

    int updateProduct(Long id, PmsProductParam productParam);

    int deleteProduct(Long id);
}
