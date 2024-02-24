package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * @Description: 新增员工
     * @Param: [employeeDTO]
     * @return: void
     */
    void addNewEmployee(EmployeeDTO employeeDTO);

    /**
     * @Description: 分页查询功能
     * @Param: [employeePageQueryDTO]
     * @return: com.sky.result.PageResult
     */
    PageResult pageEmployee(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * @Description: 启用禁用员工账号功能
     * @Param: [status, id]
     * @return: void
     */
    void updateStatus(Integer status, Long id);

    /**
     * @Description: 根据id查询员工信息
     * @Param: [id]
     * @return: com.sky.entity.Employee
     */
    Employee getEmployeeById(Long id);

    /**
     * @Description: 修改员工信息
     * @Param: [employeeDTO]
     * @return: void
     */
    void editEmployee(EmployeeDTO employeeDTO);
}
