package com.majia.day1.lambda;

public class FilterBySalary implements Filter<Employee> {
    @Override
    public boolean test(Employee employee) {
        return employee.getSalary() > 5000.0;
    }
}
