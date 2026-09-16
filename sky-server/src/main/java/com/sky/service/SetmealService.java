package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    PageResult getSetmealPage(SetmealPageQueryDTO setmealPageQueryDTO);

    void addSetmeal(SetmealDTO setmealDTO);

    SetmealVO getSetmealById(String id);

    void updateSetmeal(SetmealDTO setmealDTO);

    void updateSetmealStatus(String status, String id);

    void deleteSetmeals(List<String> ids);
}
