package org.example.cloudmall.admin.service;

import org.example.cloudmall.model.PmsProductAttribute;

import java.util.List;

public interface PmsProductAttributeService {

    List<PmsProductAttribute> listByCategory(Long cid, Integer type, int pageNum, int pageSize);

    PmsProductAttribute getItem(Long id);

    int create(PmsProductAttribute attribute);

    int update(Long id, PmsProductAttribute attribute);

    int delete(Long id);
}
