package com.sky.service;

import com.sky.entity.SetmealDish;

import java.util.List;

public interface SetmealDishService {
    void addSetmealDish(SetmealDish setmealDish);

    List<SetmealDish> selectSetmealDishesBySetmealId(Long setmealId);

    void deleteSetmealDishesBySetmealIds(List<Long> setmealIds);

    Long selectSetmealIdByDishId(String id);
}
