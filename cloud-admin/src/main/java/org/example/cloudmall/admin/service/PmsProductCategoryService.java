package org.example.cloudmall.admin.service;

import org.example.cloudmall.model.PmsProductCategory;

import java.util.List;

public interface PmsProductCategoryService {

    List<PmsProductCategory> listCategory(Long parentId);

    PmsProductCategory getCategory(Long id);

    int createCategory(PmsProductCategory category);

    int updateCategory(Long id, PmsProductCategory category);

    int deleteCategory(Long id);
}
