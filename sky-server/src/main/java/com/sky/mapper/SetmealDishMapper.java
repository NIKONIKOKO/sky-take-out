package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper{
    @Insert("insert into setmeal_dish (id, setmeal_id, dish_id, name, price, copies) " +
            "values (#{id}, #{setmealId}, #{dishId}, #{name}, #{price}, #{copies})")
    void insertSetmealDish(SetmealDish setmealDish);


    List<SetmealDish> selectSetmealDishesBySetmealId(Long setmealId);

    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteSetmealDishesBySetmealId(Long setmealId);
}
