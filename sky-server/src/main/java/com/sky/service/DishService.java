package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    PageResult getDishPage(DishPageQueryDTO dishPageQueryDTO);

    void addDish(DishDTO dishDTO);

    DishVO getDishById(String id);

    List<DishVO> getDishListByCategoryId(String categoryId);

    void deleteDishes(String ids);

    void updateDish(DishDTO dishDTO);

    void updateDishStatus(String status, String id);
}
