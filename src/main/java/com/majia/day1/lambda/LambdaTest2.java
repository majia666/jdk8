package com.majia.day1.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * 一、lambda 表达式的基本语法：
 * java8中引入新的操作符"->",该操作符称为箭头操作符或者lambda操作符
 * 箭头操作符将Lambda 表达式拆分成两部分
 * 左侧：Lambda 表达式的参数列表
 * 右侧：Lambda 表达式中所执行的功能，即Lambda 体
 * <p>
 * 语法格式1：无参数，无返回值
 * () -> System.out.println("Hello Lambda");
 * 语法格式2：有一个参数，无返回值
 * (x)-> System.out.println(x);
 * 语法格式3：若有一个参数，lambda 操作符左侧的小括号可以省略不写，无返回值
 * x-> System.out.println(x);
 * 语法格式4：有两个以上的参数，有返回值，并且Lambda体中有多条语句
 * Comparator<Integer> comparable = (x, y) -> {
 * System.out.println("函数式接口");
 * return Integer.compare(x, y);
 * };
 * 语法格式5：若Lambda 中只有一条语句，return 和大括号都可以省略不写
 * Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
 * 语法格式6：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”
 * (Integer x,Integer y) -> Integer.compare(x,y);
 * </p>
 * <p>
 * 总结：左右遇一括号省，左侧推断类型省，能省则省
 * </p>
 * 二、Lambda 表达式需要“函数式接口”的支持
 * 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。可以使用注解@FunctionalInterface 修饰，
 * 可以检测是否是函数式接口
 */

public class LambdaTest2 {

    @Test
    public void test1() {

        int num = 0; // jdk 1.7 前，必须加 final
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World!");
            }
        };
        r1.run();

        Runnable r2 = () -> System.out.println("Hello Lambda!");
        r2.run();
    }

    @Test
    public void test2() {
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("hello");
    }

    @Test
    public void test3() {
        Comparator<Integer> comparable = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };

    }

    @Test
    public void test4() {
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
    }

    // 需求：计算一个数
    @Test
    public void test5() {
        Integer res = operate(100, (x) -> x * x);
        System.out.println(res);

        System.out.println(operate(100, (y) -> y + 100));
    }

    public Integer operate(Integer num, MyFun myFun) {
        return myFun.getValue(num);
    }

    // 测试 Collections.sort();

    List<Employee> employees = Arrays.asList(
            new Employee("张三", 28, 9989.09),
            new Employee("李四", 21, 9229.09),
            new Employee("王五", 35, 5534.09),
            new Employee("赵六", 42, 2784.09),
            new Employee("田七", 46, 6666.09)
    );

    @Test
    public void test6() {
        Collections.sort(employees, (e1, e2) -> {
            if (e1.getAge() == e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return Integer.compare(e1.getAge(), e2.getAge());
            }
        });
        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    // 需求：用于处理字符串
    @Test
    public void test7() {
        String s = strHandler("hfjkkehgfjk", (x) -> x.toUpperCase());
        System.out.println(s);
    }

    public String strHandler(String str, MyFunction mf) {
        return mf.getValue(str);
    }

    // 需求：用于处理两个long 型数据
    @Test
    public void test8() {
        longHandler(100L, 200L, (x, y) -> x + y);
        longHandler(100L, 200L, (x, y) -> x * y);
    }

    public void longHandler(Long l1, Long l2, MyFunction2<Long, Long> mf) {
        System.out.println(mf.getValue(l1, l2));
    }

}
