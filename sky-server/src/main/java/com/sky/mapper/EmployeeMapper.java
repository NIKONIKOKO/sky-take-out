package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into employee(username, name, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) " +
            "values(#{username}, #{name}, #{password}, #{phone}, #{sex}, #{idNumber}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Employee employee);

    List<Employee> getPageByName(String name);

    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    @Update("update employee set name = #{name}, username = #{username}, password = #{password}, phone = #{phone}, sex = #{sex}, id_number = #{idNumber}, status = #{status}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    void updateById(Employee employee);
}
