package com.sky.service;

import com.sky.entity.DishFlavor;

public interface DishFlavorService {
    void addDishFlavor(DishFlavor dishFlavor);

    void deleteDishFlavorByDishId(Long dishId);
}
