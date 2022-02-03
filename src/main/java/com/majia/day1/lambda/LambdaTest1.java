package com.majia.day1.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 为什么要使用lambda
 */
public class LambdaTest1 {

    List<Employee> employees = Arrays.asList(
            new Employee("张三", 28, 9989.09),
            new Employee("李四", 21, 9229.09),
            new Employee("王五", 35, 5534.09),
            new Employee("赵六", 42, 2784.09),
            new Employee("田七", 46, 6666.09)
    );

    // 过滤年龄大于40的员工
    @Test
    public void test1() {
        List<Employee> emps = new ArrayList<Employee>();
        for (Employee employee : employees) {
            if (employee.getAge() > 40) {
                emps.add(employee);
            }
        }
        for (Employee employee : emps) {
            System.out.println(employee);
        }
    }

    // 过滤工资大于5000的员工
    @Test
    public void test2() {
        List<Employee> emps = new ArrayList<Employee>();
        for (Employee employee : employees) {
            if (employee.getSalary() > 5000) {
                emps.add(employee);
            }
        }
        for (Employee employee : emps) {
            System.out.println(employee);
        }
    }

    // 优化方式1 策略模式

    @Test
    public void test3(){
        List<Employee> employees = filterEmployee(this.employees, new FilterByAge());
        for (Employee e:employees) {
            System.out.println(e);
        }

        System.out.println("------------------------");

        List<Employee> employees2 = filterEmployee(this.employees, new FilterBySalary());
        for (Employee e:employees2) {
            System.out.println(e);
        }
    }
    // 优化方式2 匿名内部类
    @Test
    public void test4(){
        List<Employee> list = filterEmployee(this.employees, new Filter<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getAge() > 40;
            }
        });
        for (Employee e:list) {
            System.out.println(e);
        }
        System.out.println("========================");

        List<Employee> list2 = filterEmployee(this.employees, new Filter<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary() >8000;
            }
        });
        for (Employee e:list) {
            System.out.println(e);
        }
    }

    // 优化方式3 lambda表达式
    @Test
    public void test5(){
        filterEmployee(employees,(e)->e.getAge()>40).forEach(System.out::println);
        Optional.of("=======================").ifPresent(System.out::println);
        filterEmployee(employees,(e)->e.getSalary()<5000).forEach(System.out::println);
    }
    // 优化方式4 stream API
    @Test
    public void test6(){
        employees.stream().filter((e)->e.getAge()<30).forEach(System.out::println);
        Optional.of("=======================").ifPresent(System.out::println);
        employees.stream().filter((e)->e.getSalary()<8000).forEach(System.out::println);
        Optional.of("=======================").ifPresent(System.out::println);
        employees.stream()
                .filter((e)->e.getSalary()<8000)
                .map(Employee::getName)
                .forEach(System.out::println);

    }

    public static List<Employee> filterEmployee(List<Employee> list,Filter<Employee> filter){
        List<Employee> emps = new ArrayList<Employee>();
        for (Employee employee:list) {
            if (filter.test(employee)){
                emps.add(employee);
            }
        }
        return emps;
    }
}
