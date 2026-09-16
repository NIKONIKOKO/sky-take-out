package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐管理")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @GetMapping("/page")
    @ApiOperation(value = "分页查询套餐")
    public Result<PageResult> getSetmealPage(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("分页查询套餐，参数：{}", setmealPageQueryDTO);
        return Result.success(setmealService.getSetmealPage(setmealPageQueryDTO));
    }

    @PostMapping
    @ApiOperation(value = "新增套餐")
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐：{}", setmealDTO);
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询套餐")
    public Result<SetmealVO> getSetmealById(@PathVariable String id){
        log.info("根据ID查询套餐，参数：{}", id);
        SetmealVO setmealVO = setmealService.getSetmealById(id);
        if(setmealVO != null) {
            return Result.success(setmealVO);
        }else{
            return Result.error(MessageConstant.SETMEAL_NOT_FOUND);
        }
    }
    @PutMapping
    @ApiOperation(value = "修改套餐")
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("修改套餐：{}", setmealDTO);
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }
    @PostMapping("/status/{status}")
    @ApiOperation(value = "修改套餐状态")
    public Result updateSetmealStatus(@PathVariable String status, @RequestParam("id") String id){
        setmealService.updateSetmealStatus(status, id);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation(value = "批量删除套餐")
    public Result deleteSetmeals(@RequestParam String ids){
        setmealService.deleteSetmeals(ids);
        return Result.success();
    }
}
