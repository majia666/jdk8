package com.majia.day1.lambda;

public class FilterByAge implements Filter<Employee> {
    @Override
    public boolean test(Employee employee) {
        return employee.getAge() > 40;
    }
}
