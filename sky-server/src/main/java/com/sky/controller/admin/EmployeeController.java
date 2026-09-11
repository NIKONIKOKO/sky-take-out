package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工管理")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "员工退出")
    public Result<String> logout() {
        return Result.success();
    }

    @PostMapping
    @ApiOperation(value = "新增员工")
    public Result addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工：{}", employeeDTO);
        employeeService.addEmployee(employeeDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation(value = "分页查询员工")
    public Result<PageResult> getEmployeePage(@RequestParam("page") Integer page,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam(value = "name", required = false) String name) {
        log.info("分页查询员工：page={}, pageSize={}, name={}", page, pageSize, name);
        // 调用服务层方法进行分页查询
        PageResult pageResult = employeeService.getEmployeePage(page, pageSize, name);
        return Result.success(pageResult);
    }
    @PostMapping("/status/{status}")
    @ApiOperation(value = "修改员工状态")
    public Result updateEmployeeStatus(@RequestParam String id, @PathVariable String status) {
        // status为1，表示启用；status为0，表示禁用
        if (status.equals("1")) {
            log.info("启用员工：id={}", id);
        } else if (status.equals("0")) {
            log.info("禁用员工：id={}", id);
        } else {
            return Result.error("无效的状态值");
        }
        employeeService.updateEmployeeStatus(id, status);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询员工")
    public Result<Employee> getEmployeeById(@PathVariable String id) {
        log.info("根据id查询员工：id={}", id);
        Employee employee = employeeService.getEmployeeById(id);
        return Result.success(employee);
    }
    @PutMapping
    @ApiOperation(value = "修改员工信息")
    public Result updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("修改员工信息：{}", employeeDTO);
        employeeService.updateEmployeeInfo(employeeDTO);
        return Result.success();
    }
    @PutMapping("/editPassword")
    @ApiOperation(value = "修改员工密码")
    public Result editPassword(@RequestBody PasswordEditDTO passwordEditDTO) {
        log.info("修改密码：{}", passwordEditDTO);
        employeeService.updateEmployeePassword(passwordEditDTO);
        return Result.success();
    }
}
