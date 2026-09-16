package com.sky.service.impl;

import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealDishServiceImpl implements SetmealDishService {

    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Override
    public void addSetmealDish(SetmealDish setmealDish) {
        try {
            setmealDishMapper.insertSetmealDish(setmealDish);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SetmealDish> selectSetmealDishesBySetmealId(Long setmealId) {
        return setmealDishMapper.selectSetmealDishesBySetmealId(setmealId);
    }

    @Override
    public void deleteSetmealDishesBySetmealIds(List<Long> setmealIds) {
        setmealDishMapper.deleteSetmealDishesBySetmealIds(setmealIds);
    }

    @Override
    public Long selectSetmealIdByDishId(String id) {
        SetmealDish setmealDish = setmealDishMapper.selectSetmealIdByDishId(Long.valueOf(id));
        if(setmealDish == null){
            return null;
        }
        return setmealDish.getSetmealId();
    }
}
