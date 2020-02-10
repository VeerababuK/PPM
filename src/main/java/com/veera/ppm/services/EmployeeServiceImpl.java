package com.veera.ppm.services;

import com.veera.ppm.domain.Account;
import com.veera.ppm.domain.Employee;
import com.veera.ppm.repositories.AccountRepository;
import com.veera.ppm.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee() {

        Account account = new Account();
        account.setAccountNumber("123-345-65454");

        //Add new Employee object
        Employee emp = new Employee();
        emp.setEmail("demo-user@mail.com");
        emp.setFirstName("demo");
        emp.setLastName("user");

        //Save Account
        account = accountRepository.save(account);
        //Save Employee
        emp.setAccount(account);
        emp.setOldAccount(account);
        emp = employeeRepository.save(emp);

        return emp;
    }

}
