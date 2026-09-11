package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

@Builder(toBuilder = true)
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 对前端传来的密码进行md5加密与后端进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    @Transactional
    public void addEmployee(EmployeeDTO employeeDTO) {
        // 先查询用户名是否存在
        Employee existingEmployee = employeeMapper.getByUsername(employeeDTO.getUsername());
        if (existingEmployee != null) {
            throw new RuntimeException("用户名已存在");
        }
        // 1、将DTO对象转换为实体对象
        Employee employee = Employee.builder()
                .name(employeeDTO.getName())
                .username(employeeDTO.getUsername())
                .password(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes())) // 默认密码为123456，并进行MD5加密
                .phone(employeeDTO.getPhone())
                .sex(employeeDTO.getSex())
                .idNumber(employeeDTO.getIdNumber())
                .status(StatusConstant.ENABLE) // 新增员工默认启用状态
                .createTime(java.time.LocalDateTime.now())
                .updateTime(java.time.LocalDateTime.now())
                .createUser(BaseContext.getCurrentId())
                .updateUser(BaseContext.getCurrentId())
                .build();

        employeeMapper.insert(employee);
    }

    @Override
    public PageResult getEmployeePage(Integer page, Integer pageSize, String name) {
        PageHelper.startPage(page, pageSize);
        // 这里应该调用Mapper的方法来查询员工分页数据
        List<Employee> employees = employeeMapper.getPageByName(name); // 假设你有一个根据姓名查询员工的方法
        return new PageResult(employees.size(), employees);
    }

    @Override
    public void updateEmployeeStatus(String id, String status) {
        // 先查询员工是否存在
        Employee existEmployee = employeeMapper.getById(Long.parseLong(id));
        if (existEmployee == null) {
            throw new RuntimeException("员工不存在");
        }
        // 更新员工状态
        existEmployee.setStatus(Integer.parseInt(status));
        existEmployee.setUpdateTime(java.time.LocalDateTime.now());
        existEmployee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.updateById(existEmployee);
    }

    @Override
    public Employee getEmployeeById(String id) {
        log.info("根据id查询员工：id={}", id);
        Employee employee = employeeMapper.getById(Long.parseLong(id));
        employee.setPassword("***");
        return employee;
    }
    @Override
    public void updateEmployeeInfo(EmployeeDTO employeeDTO) {
        // 先查询员工是否存在
        Employee existEmployee = employeeMapper.getById(employeeDTO.getId());
        if (existEmployee == null) {
            throw new RuntimeException("员工不存在");
        }
        // 更新员工信息
        existEmployee.setId(employeeDTO.getId());
        existEmployee.setName(employeeDTO.getName());
        existEmployee.setUsername(employeeDTO.getUsername());
        existEmployee.setPhone(employeeDTO.getPhone());
        existEmployee.setSex(employeeDTO.getSex());
        existEmployee.setIdNumber(employeeDTO.getIdNumber());
        existEmployee.setUpdateTime(java.time.LocalDateTime.now());
        existEmployee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.updateById(existEmployee);
    }

    @Override
    public void updateEmployeePassword(PasswordEditDTO passwordEditDTO) {
        // 先查询员工是否存在
        Employee existEmployee = employeeMapper.getById(passwordEditDTO.getEmpId());
        if (existEmployee == null) {
            throw new RuntimeException("员工不存在");
        }
        // 不确定是否需要验证旧密码
        // 需要验证旧密码的开关
        if (!existEmployee.getPassword().equals(DigestUtils.md5DigestAsHex(passwordEditDTO.getOldPassword().getBytes()))) {
            throw new RuntimeException("旧密码错误");
        }
        // 更新员工密码
        String newPassword = DigestUtils.md5DigestAsHex(passwordEditDTO.getNewPassword().getBytes());
        existEmployee.setPassword(newPassword);
        existEmployee.setUpdateTime(java.time.LocalDateTime.now());
        existEmployee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.updateById(existEmployee);
    }

}
