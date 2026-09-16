package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    @Insert("insert into dish_flavor (id, dish_id, name, value) " +
            "values (#{id}, #{dishId}, #{name}, #{value})")
    void insertDishFlavor(DishFlavor dishFlavor);


    void deleteDishFlavorsByDishIds(List<Long> dishIds);
}
