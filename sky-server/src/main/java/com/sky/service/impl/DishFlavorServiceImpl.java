package com.sky.service.impl;

import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishFlavorServiceImpl implements DishFlavorService {
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Override
    public void addDishFlavor(DishFlavor dishFlavor) {
        dishFlavorMapper.insertDishFlavor(dishFlavor);
    }

    @Override
    public void deleteDishFlavorsByDishIds(List<String> ids) {
        List<Long> dishIds = ids.stream().map(Long::valueOf).collect(Collectors.toList());
        dishFlavorMapper.deleteDishFlavorsByDishIds(dishIds);
    }
}
