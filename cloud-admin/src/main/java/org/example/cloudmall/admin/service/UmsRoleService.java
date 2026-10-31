package org.example.cloudmall.admin.service;

import org.example.cloudmall.model.UmsRole;

import java.util.List;

public interface UmsRoleService {

    List<UmsRole> listRole(String keyword, int pageNum, int pageSize);

    UmsRole getRole(Long id);

    int createRole(UmsRole role);

    int updateRole(Long id, UmsRole role);

    int deleteRole(Long id);
}
