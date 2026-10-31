package org.example.cloudmall.admin.service;

import org.example.cloudmall.model.UmsAdmin;

import java.util.List;

public interface UmsAdminService {

    List<UmsAdmin> listAdmin(String keyword, int pageNum, int pageSize);

    UmsAdmin getAdmin(Long id);

    int createAdmin(UmsAdmin admin);

    int updateAdmin(Long id, UmsAdmin admin);

    int deleteAdmin(Long id);
}
