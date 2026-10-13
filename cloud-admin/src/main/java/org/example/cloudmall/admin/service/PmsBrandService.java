package org.example.cloudmall.admin.service;

import org.example.cloudmall.model.PmsBrand;

import java.util.List;

/**
 * 商品品牌业务接口
 */
public interface PmsBrandService {

    List<PmsBrand> listAllBrand();

    int createBrand(PmsBrand brand);

    int updateBrand(Long id, PmsBrand brand);

    int deleteBrand(Long id);

    List<PmsBrand> listBrand(String keyword, int pageNum, int pageSize);

    PmsBrand getBrand(Long id);
}
