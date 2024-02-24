package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

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
        //对前端传递过来的密码进行md5加密，然后跟数据库中的进行比对
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

    /**
     * @Description: 新增员工
     * @Param: [employeeDTO]
     * @return: void
     */
    @Override
    public void addNewEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        //对象属性拷贝
        BeanUtils.copyProperties(employeeDTO, employee);

        //设置账号状态, 1:正常; 0:锁定
        employee.setStatus(StatusConstant.ENABLE);

        //设置默认密码123456并进行MD5加密
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        //设置创建及修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //设置创建人及修改人ID
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.addNewEmployee(employee);
    }

    /**
     * @Description: 员工分页查询功能
     * @Param: [employeePageQueryDTO]
     * @return: com.sky.result.PageResult
     */
    @Override
    public PageResult pageEmployee(EmployeePageQueryDTO employeePageQueryDTO) {

        //使用PageHelper进行分页查询
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        Page<Employee> page = employeeMapper.pageEmployee(employeePageQueryDTO);
        long total = page.getTotal();
        List<Employee> result = page.getResult();
        PageResult pageResult = new PageResult(total, result);
        return pageResult;
    }

    /**
     * @Description: 启用禁用员工账号功能
     * @Param: [status, id]
     * @return: void
     */
    @Override
    public void updateStatus(Integer status, Long id) {

        //传统写法
        /*
        Employee employee = new Employee();
        employee.setStatus(status);
        employee.setId(id);
         */

        //builder写法
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();

        employeeMapper.updateEmployee(employee);
    }

    /**
     * @Description: 根据id查询员工信息
     * @Param: [id]
     * @return: com.sky.entity.Employee
     */
    @Override
    public Employee getEmployeeById(Long id) {
        Employee employee = employeeMapper.getEmployeeById(id);
        employee.setPassword("*****"); //防止密码泄露
        return employee;
    }

    /**
     * @Description: 修改员工信息
     * @Param: [employeeDTO]
     * @return: void
     */
    @Override
    public void editEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        employee.setUpdateTime(LocalDateTime.now()); //设置修改时间
        employee.setUpdateUser(BaseContext.getCurrentId()); //设置修改人id

        employeeMapper.updateEmployee(employee);
    }

}
