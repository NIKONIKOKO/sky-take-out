package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishFlavorService;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.Builder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Builder
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Override
    public PageResult getDishPage(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        List<DishVO> dishVOList = dishMapper.selectDishPageByNameOrCategoryIdOrStatus(dishPageQueryDTO);
        Page<DishVO> dishVOPage = (Page<DishVO>) dishVOList;
        return new PageResult(dishVOPage.getTotal(), dishVOPage.getResult());
    }

    @Override
    @Transactional
    public void addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        List<DishFlavor> dishFlavorList = dishDTO.getFlavors();
        try{
            dishMapper.insertDish(dish);
        }catch(Exception e){
            e.printStackTrace();
        }
        for(DishFlavor dishFlavor : dishFlavorList){
            dishFlavor.setDishId(dish.getId());
            dishFlavorService.addDishFlavor(dishFlavor);
        }
    }

    @Override
    public DishVO getDishById(String id) {
        DishVO dishVO = dishMapper.selectDishVOById(Long.valueOf(id));
        if(dishVO == null) {
            throw new RuntimeException(MessageConstant.DISH_NOT_FOUND);
        }
        // 这里联查了dish_flavor表，所以不用再单独设置flavors属性
        return dishVO;
    }

    @Override
    public List<DishVO> getDishListByCategoryId(String categoryId) {
        return dishMapper.selectDishByCategoryId(Long.valueOf(categoryId));
    }

    @Override
    @Transactional
    public void deleteDishes(String ids) {
        String[] idList = ids.split(",");
        for(String id : idList){
            dishFlavorService.deleteDishFlavorByDishId(Long.valueOf(id));
            dishMapper.deleteDishById(Long.valueOf(id));
        }
    }

    @Override
    @Transactional
    public void updateDish(DishDTO dishDTO) {
        Dish dish = dishMapper.selectDishById(dishDTO.getId());
        if(dish == null){
            throw new RuntimeException(MessageConstant.DISH_NOT_FOUND);
        }
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.updateDish(dish);
        dishFlavorService.deleteDishFlavorByDishId(dishDTO.getId());
        List<DishFlavor> dishFlavorList = dishDTO.getFlavors();
        for(DishFlavor dishFlavor : dishFlavorList){
            dishFlavor.setDishId(dishDTO.getId());
            dishFlavorService.addDishFlavor(dishFlavor);
        }
    }

    @Override
    @Transactional
    public void updateDishStatus(String status, String id) {
        Dish dish = dishMapper.selectDishById(Long.valueOf(id));
        if(dish == null) {
            throw new RuntimeException(MessageConstant.DISH_NOT_FOUND);
        }
        dish.setStatus(Integer.valueOf(status));
        dishMapper.updateDish(dish);
    }
}
