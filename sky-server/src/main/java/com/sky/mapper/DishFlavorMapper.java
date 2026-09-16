package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishFlavorMapper {

    @Insert("insert into dish_flavor (id, dish_id, name, value) " +
            "values (#{id}, #{dishId}, #{name}, #{value})")
    void insertDishFlavor(DishFlavor dishFlavor);

    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteDishFlavorByDishId(Long dishId);
}
