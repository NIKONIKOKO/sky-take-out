package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void addEmployee(EmployeeDTO employeeDTO);

    PageResult getEmployeePage(Integer page, Integer pageSize, String name);

    Employee getEmployeeById(String id);

    void updateEmployeeStatus(String id, String status);

    void updateEmployeeInfo(EmployeeDTO employeeDTO);

    void updateEmployeePassword(PasswordEditDTO passwordEditDTO);
}
