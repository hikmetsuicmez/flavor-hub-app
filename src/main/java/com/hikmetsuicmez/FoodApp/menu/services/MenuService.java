package com.hikmetsuicmez.FoodApp.menu.services;

import com.hikmetsuicmez.FoodApp.menu.dtos.MenuDTO;
import com.hikmetsuicmez.FoodApp.response.Response;

import java.util.List;

public interface MenuService {

    Response<MenuDTO> createMenu(MenuDTO menuDTO);
    Response<MenuDTO> updateMenu(MenuDTO menuDTO);
    Response<MenuDTO> getMenuById(Long menuId);
    Response<?> deleteMenu(Long menuId);
    Response<List<MenuDTO>> getMenus(Long categoryId, String search);
}
