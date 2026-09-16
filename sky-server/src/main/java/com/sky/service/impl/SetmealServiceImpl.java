package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealDishService;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.Builder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Builder
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    public PageResult getSetmealPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        List<SetmealVO> setmealVOList = setmealMapper.selectSetmealPageByNameOrCategoryId(setmealPageQueryDTO);
        Page<SetmealVO> setmealVOPage = (Page<SetmealVO>) setmealVOList;
        return new PageResult(setmealVOPage.getTotal(), setmealVOPage.getResult());
    }

    @Override
    @Transactional
    public void addSetmeal(SetmealDTO setmealDTO) {
        if(setmealMapper.selectSetmealById(setmealDTO.getId()) != null) {
            throw new RuntimeException(MessageConstant.SETMEAL_ALREADY_EXISTS);
        }
        // 先拆分setmeal和setmeal_dish
        List<SetmealDish> setmealDishList = setmealDTO.getSetmealDishes();
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insertSetmeal(setmeal);
        for (SetmealDish setmealDish : setmealDishList) {
            setmealDish.setSetmealId(setmeal.getId());
            setmealDishService.addSetmealDish(setmealDish);
        }
    }

    @Override
    public SetmealVO getSetmealById(String id) {
        SetmealVO setmealVO = setmealMapper.selectSetmealVOById(Long.valueOf(id));
        if(setmealVO == null){
            return null;
        }
        setmealVO.setSetmealDishes(setmealDishService.selectSetmealDishesBySetmealId(Long.valueOf(id))); // 这里没有联查setmeal_dish表，所以要单独设置setmealVO的setmealDishes属性
        return setmealVO;
    }

    @Override
    @Transactional
    public void updateSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = setmealMapper.selectSetmealById(setmealDTO.getId());
        if(setmeal == null) {
            throw new RuntimeException(MessageConstant.SETMEAL_NOT_FOUND);
        }
        // 先拆分setmeal和setmeal_dish
        List<SetmealDish> setmealDishList = setmealDTO.getSetmealDishes();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.updateSetmeal(setmeal);
        setmealDishService.deleteSetmealDishesBySetmealIds(Collections.singletonList(setmealDTO.getId()));
        for (SetmealDish setmealDish : setmealDishList) {
            setmealDish.setSetmealId(setmealDTO.getId());
            setmealDishService.addSetmealDish(setmealDish);
        }
    }

    @Override
    @Transactional
    public void updateSetmealStatus(String status, String id) {
        if(setmealMapper.selectSetmealVOById(Long.valueOf(id)) == null) {
            throw new RuntimeException(MessageConstant.SETMEAL_NOT_FOUND);
        }
        Setmeal setmeal = setmealMapper.selectSetmealById(Long.valueOf(id));
        setmeal.setStatus(Integer.valueOf(status));
        setmealMapper.updateSetmeal(setmeal);
    }

    @Override
    @Transactional
    public void deleteSetmeals(List<String> ids) {
        List<Long> setmealIds = ids.stream()
                .map(Long::parseLong)  // 字符串转 Long
                .collect(Collectors.toList());
        setmealMapper.deleteSetmealByIds(setmealIds);
        setmealDishService.deleteSetmealDishesBySetmealIds(setmealIds);
    }
}
