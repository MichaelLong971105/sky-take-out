package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * @Description: 插入新增员工信息
     * @Param: [employee]
     * @return: void
     */
    @Insert("insert into employee(name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) " +
            "values " +
            "(#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void addNewEmployee(Employee employee);

    /**
     * @Description: 员工分页查询功能
     * @Param: [employeePageQueryDTO]
     * @return: com.github.pagehelper.Page<com.sky.entity.Employee>
     */
    Page<Employee> pageEmployee(EmployeePageQueryDTO employeePageQueryDTO);

    /** 
     * @Description: 根据主键动态修改员工信息
     * @Param: [employee]
     * @return: void
     */
    void updateEmployee(Employee employee);

    /**
     * @Description: 根据id查询员工信息
     * @Param: [id]
     * @return: com.sky.entity.Employee
     */
    Employee getEmployeeById(Long id);
}
