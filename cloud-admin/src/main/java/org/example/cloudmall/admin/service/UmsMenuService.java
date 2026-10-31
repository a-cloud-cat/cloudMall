package org.example.cloudmall.admin.service;

import org.example.cloudmall.model.UmsMenu;

import java.util.List;

public interface UmsMenuService {

    List<UmsMenu> listMenu(Long parentId);

    UmsMenu getMenu(Long id);

    int createMenu(UmsMenu menu);

    int updateMenu(Long id, UmsMenu menu);

    int deleteMenu(Long id);
}
