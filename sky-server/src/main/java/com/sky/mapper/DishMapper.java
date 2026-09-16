package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    List<DishVO> selectDishPageByNameOrCategoryIdOrStatus(DishPageQueryDTO dishPageQueryDTO);

    DishVO selectDishVOById(Long dishId);

    @Insert("insert into dish (id, name, category_id, price, image, description, status, create_time, update_time, create_user, update_user) " +
            "values (#{id}, #{name}, #{categoryId}, #{price}, #{image}, #{description}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertDish(Dish dish);



    @Select("select * from dish left join category on dish.category_id = category.id where dish.category_id = #{categoryId}")
    List<DishVO> selectDishByCategoryId(Long categoryId);

    @Delete("delete from dish where id = #{dishId}")
    void deleteDishById(Long dishId);

    @Update("update dish set name = #{name}, category_id = #{categoryId}, price = #{price}, image = #{image}, description = #{description}, status = #{status}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    @AutoFill(value = OperationType.UPDATE)
    void updateDish(Dish dish);



    @Select("select * from dish where id = #{id}")
    Dish selectDishById(Long id);
}
