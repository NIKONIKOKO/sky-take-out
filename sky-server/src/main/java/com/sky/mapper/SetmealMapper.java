package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param categoryId 分类id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);


    List<SetmealVO> selectSetmealPageByNameOrCategoryId(SetmealPageQueryDTO setmealPageQueryDTO);

    @Insert("insert into setmeal (id, name, category_id, price, status, description, image, create_time, update_time, create_user, update_user) " +
            "values (#{id}, #{name}, #{categoryId}, #{price}, #{status}, #{description}, #{image}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertSetmeal(Setmeal setmeal);



    SetmealVO selectSetmealVOById(Long setmealId);



    @Update("update setmeal set name = #{name}, category_id = #{categoryId}, price = #{price}, status = #{status}, description = #{description}, image = #{image}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    @AutoFill(value = OperationType.UPDATE)
    void updateSetmeal(Setmeal setmeal);



    @Delete("delete from setmeal where id = #{setmealId}")
    void deleteSetmealById(Long setmealId);

    @Select("select * from setmeal where id = #{setmealId}")
    Setmeal selectSetmealById(Long setmealId);
}
