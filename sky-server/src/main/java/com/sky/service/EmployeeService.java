package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;

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
     * @Author: MichaelLong
     * @Date: 2024/2/22
     */
    void addNewEmployee(EmployeeDTO employeeDTO);
}
