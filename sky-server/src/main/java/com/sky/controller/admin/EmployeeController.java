package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
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
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api("员工相关接口")
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
    @ApiOperation("员工登录")
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
    @ApiOperation("员工退出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * @Description: 新增员工功能
     * @Param: [employeeDTO] 跟前端交互的对象
     * @return: 约定的返回类型Result
     */
    @PostMapping
    @ApiOperation("新增员工功能")
    public Result<T> addNewEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工{}", employeeDTO);
        employeeService.addNewEmployee(employeeDTO);
        return Result.success();
    }

    /**
     * @Description: 员工分页查询功能
     * @Param: [employeePageQueryDTO]
     * @return: com.sky.result.Result<com.sky.result.PageResult>
     */
    @GetMapping("/page")
    @ApiOperation("员工分页查询")
    public Result<PageResult> pageEmployee(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("员工分页查询{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageEmployee(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * @Description: 启用禁用员工账号功能
     * @Param: [status, id]
     * @return: com.sky.result.Result
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用员工账号功能")
    public Result updateStatus(@PathVariable Integer status, Long id) {
        log.info("启用禁用员工账号:{}, {}", status, id);
        employeeService.updateStatus(status, id);
        return Result.success();
    }

    /**
     * @Description: 根据id查询员工信息
     * @Param: [id]
     * @return: com.sky.result.Result<com.sky.entity.Employee>
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工信息")
    public Result<Employee> getEmployeeById(@PathVariable Long id) {
        log.info("根据id查询员工信息：{}", id);
        Employee employee = employeeService.getEmployeeById(id);
        return Result.success(employee);
    }

    /**
     * @Description: 修改员工信息
     * @Param: [employeeDTO]
     * @return: com.sky.result.Result
     */
    @PutMapping
    @ApiOperation("修改员工信息")
    public Result editEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("编辑员工信息: {}", employeeDTO);
        employeeService.editEmployee(employeeDTO);
        return Result.success();
    }

}
