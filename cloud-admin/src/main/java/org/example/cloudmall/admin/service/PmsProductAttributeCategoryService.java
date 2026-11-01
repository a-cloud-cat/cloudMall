package org.example.cloudmall.admin.service;

import org.example.cloudmall.model.PmsProductAttributeCategory;

import java.util.List;

public interface PmsProductAttributeCategoryService {

    List<PmsProductAttributeCategory> listAll();

    int create(PmsProductAttributeCategory category);

    int update(Long id, PmsProductAttributeCategory category);

    int delete(Long id);
}
