package com.sky.service;

import com.sky.entity.DishFlavor;

import java.util.List;

public interface DishFlavorService {
    void addDishFlavor(DishFlavor dishFlavor);

    void deleteDishFlavorsByDishIds(List<String> ids);
}
