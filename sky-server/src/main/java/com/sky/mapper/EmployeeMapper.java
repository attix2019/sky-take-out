package com.sky.mapper;

import com.sky.annotation.Autofill;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    @Autofill(operationType = OperationType.INSERT)
    void insertEmployee(Employee employee);

    List<Employee> findEmployees(EmployeePageQueryDTO employeePageQueryDTO);

    int countEmployees(EmployeePageQueryDTO employeePageQueryDTO);

    @Autofill(operationType = OperationType.UPDATE)
    void updateEmployeeById(Employee employee);

    Employee getEmployeeById(int id);
}
