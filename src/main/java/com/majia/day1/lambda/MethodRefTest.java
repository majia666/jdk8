package com.majia.day1.lambda;

import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 一、方法引用：若 Lambda 方法体中的内容有方法已经实现了，我们可以使用“方法引用”（可以理解为方法引用式Lambda 表达式的另一种表现形式）
 * 主要有3种语法格式：
 * 对象::实例方法名
 * 类::静态方法名
 * 类::实例方法名
 * 注意：
 * 1.Lambda 体中调用方法的参数列表和返回值类型，要与函数式接口中抽象方法的函数参数列表和返回值保持一致！
 * 2.若 Lambda 参数列表中的第一个参数是 实例方法的调用者，而第二个参数是实例方法的参数时，可以使用ClassName::method
 * <p>
 * 二、构造器引用
 * 格式：
 * ClassName::new
 * 注意：
 * 需要调用的构造器的参数列表要与函数式接口中的抽象方法的参数列表保持一致
 * 三、数组引用
 * 格式：
 * Type[]::new
 */
public class MethodRefTest {

    // 对象::实例方法名
    @Test
    public void test1() {
        PrintStream ps = System.out;
        Consumer<String> con1 = (x) -> ps.println(x);

        PrintStream ps2 = System.out;
        Consumer<String> con2 = ps2::println;


        Consumer<String> consumer = System.out::println;
        consumer.accept("test");
    }

    @Test
    public void test2() {
        Employee employee = new Employee();

        Supplier<String> sup1 = () -> employee.getName();
        String s = sup1.get();
        System.out.println(s);

        Supplier<Integer> sup2 = employee::getAge;
        System.out.println(sup2.get());

        Supplier<Employee> sup3 = Employee::new;
        System.out.println(sup3.get());
    }

    //  类::静态方法名
    @Test
    public void test3() {
        Comparator<Integer> comparator = Integer::compare;
        int compare = comparator.compare(1, 3);
        System.out.println(compare);
    }

    // 类::实例方法名
    @Test
    public void test4() {
        BiPredicate<String, String> biPredicate = (x, y) -> x.equals(y);
        System.out.println(biPredicate.test("ss", "ssdf"));

        BiPredicate<String, String> bP = String::equals;
        System.out.println(bP.test("ss", "ss"));
    }

    // 构造器引用
    @Test
    public void test5() {
        Supplier<Employee> supplier = Employee::new;

        Function<Integer, Employee> function = Employee::new;
        Employee apply = function.apply(3);
        System.out.println(apply);
    }

    // 数组引用
    @Test
    public void test6() {
        Function<Integer, String[]> fun = (x) -> new String[x];
        System.out.println(fun.apply(10).length);

        Function<Integer,String[]> fun2 = String[]::new;
        System.out.println(fun2.apply(20).length);
    }
}
