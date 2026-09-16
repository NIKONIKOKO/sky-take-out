package com.sky.service.impl;

import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl implements DishFlavorService {
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Override
    public void addDishFlavor(DishFlavor dishFlavor) {
        dishFlavorMapper.insertDishFlavor(dishFlavor);
    }

    @Override
    public void deleteDishFlavorByDishId(Long dishId) {
        dishFlavorMapper.deleteDishFlavorByDishId(dishId);
    }
}
