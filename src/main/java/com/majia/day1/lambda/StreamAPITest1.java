package com.majia.day1.lambda;

import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * 一、 Steam 的三个操作步骤
 * 1.创建stream
 * 2.中间操作
 * 3.终止操作（终端操作）
 */
public class StreamAPITest1 {

    //创建Stream
    @Test
    public void test1() {
        // 1.可以通过Collection 系列集合提供的stream() 或者 parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();
        // 2.通过array中的静态方法 stream() 获取数组流
        Employee[] employees = new Employee[10];
        Stream<Employee> stream2 = Arrays.stream(employees);
        // 3. 通过stream的静态方法of()
        Stream<String> stream3 = Stream.of("aa", "bb", "cc");
        // 4. 创建无限流
        //(1).迭代
        Stream<Integer> stream4 = Stream.iterate(0, x -> x + 2);
        //stream4.forEach(System.out::println);
//        stream4.limit(10).forEach(System.out::println);
        //(2).生成
        Stream.generate(() -> Math.random())
                .limit(10)
                .forEach(System.out::println);

    }

    List<Employee> employees = Arrays.asList(
            new Employee("张三", 28, 9989.09),
            new Employee("李四", 21, 9229.09),
            new Employee("王五", 35, 5534.09),
            new Employee("赵六", 42, 2784.09),
            new Employee("田七", 46, 6666.09),
            new Employee("田七", 46, 6666.09),
            new Employee("田七", 46, 6666.09)
    );

    // 中间操作

    /**
     * 1.筛选与切片
     *      filter--接收 Lambda，从流中排除某些元素
     *      limit--截断流，使其元素不超过给定数量
     *      skip(n)--跳过元素，返回一个扔掉了前n个元素的流，若流中元素不足n个，则返回一个空流。与limit(n)互斥
     *      distinct--筛选，通过流生成元素的hashCode() 和 equals() 去除重复的元素
     */
    // (1).内部迭代：迭代操作由Stream API 完成
    @Test
    public void test2() {
        // 中间操作: 不会执行任何操作。
        Stream<Employee> stream = employees.stream()
                .filter(e -> {
                    System.out.println("Stream API 中间操作");
                    return e.getAge() > 35;
                });

        //终止操作：一次性执行全部内容，即”惰性求值“
        stream.forEach(System.out::println);
    }

    // (2). 外部迭代
    @Test
    public void test3() {
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void test4() {
        employees.stream()
                .filter(e -> {
                    System.out.println("短路!");
                    return e.getSalary() > 5000;
                })
                .limit(2)
                .forEach(System.out::println);
    }

    @Test
    public void test5() {
        employees.stream()
                .filter(e -> {
                    System.out.println("短路!");
                    return e.getSalary() > 5000;
                })
                .skip(2)
                .forEach(System.out::println);
    }

    @Test
    public void test6() {
        employees.stream()
                .filter(e -> {
                    System.out.println("短路!");
                    return e.getSalary() > 5000;
                })
                .skip(2)
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 2. 映射
     *      map--接收lambda, 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
     *      flatMap--接收一个函数作为参数，将流中的每个值换成一另一个流，然后把所有流连成一个流
     */
    @Test
    public void test7() {
        Arrays.asList("aaa", "bbb", "ccc", "ddd")
                .stream()
                .map(str -> str.toUpperCase())
                .forEach(System.out::println);

        System.out.println("================================");
        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);

        System.out.println("================================");
        Arrays.asList("aaa", "bbb", "ccc", "ddd")
                .stream()
                .map(StreamAPITest1::filterCharacter)
                .forEach(sm -> sm.forEach(System.out::println));

        System.out.println("================================");
        Arrays.asList("aaa", "bbb", "ccc", "ddd")
                .stream()
                .flatMap(StreamAPITest1::filterCharacter)
                .forEach(System.out::println);
    }


    public static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();
        for (Character c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }

    /**
     * 3. 排序
     *      sorted()--自然排序(Comparable)
     *      sorted(Comparator com)--定制排序
     */
    @Test
    public void test8() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        list.stream()
                .sorted()
                .forEach(System.out::println);

        System.out.println("-------------------------------");
        employees.stream()
                .sorted((e1, e2) -> {
                    if (e1.getAge().equals(e2.getAge())) {
                        return e1.getName().compareTo(e2.getName());
                    } else {
                        return -e1.getAge().compareTo(e2.getAge());
                    }
                })
                .forEach(System.out::println);
    }

    // 终止操作
    List<Employee> employees1 = Arrays.asList(
            new Employee("张三", 28, 9989.09, Employee.Status.FREE),
            new Employee("李四", 21, 9229.09, Employee.Status.BUSY),
            new Employee("王五", 35, 5534.09, Employee.Status.VOCATION),
            new Employee("赵六", 42, 2784.09, Employee.Status.FREE),
            new Employee("田七", 46, 6666.09, Employee.Status.BUSY),
            new Employee("田七", 46, 6666.09, Employee.Status.BUSY),
            new Employee("田七", 46, 6666.09, Employee.Status.BUSY),
            new Employee("田七", 46, 6666.09, Employee.Status.BUSY)
    );

    /**
     * 1.查找与匹配
     *      allMatch--检查是否匹配所有元素
     *      anyMatch--检查是否至少匹配一个元素
     *      noneMatch--检查是否没有匹配所有元素
     *      findFirst--返回第一个元素
     *      findAny--返回当前流中的任意元素
     *      count--返回流中元素的总个数
     *      max--返回流中的最大值
     *      min--返回流中的最小值
     */
    @Test
    public void test9() {
        boolean b1 = employees1.stream()
                .allMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b1);

        boolean b2 = employees1.stream()
                .anyMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b2);

        boolean b3 = employees1.stream()
                .noneMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b3);

        Optional<Employee> op = employees1.stream()
                .sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .findFirst();
        System.out.println(op.get());

        Optional<Employee> any = employees1.stream()
                .filter(e -> e.getStatus().equals(Employee.Status.FREE))
                .findAny();
        System.out.println(any.get());

        long count = employees1.stream()
                .count();
        System.out.println(count);

        Optional<Employee> max = employees1.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(max.get());

        Optional<Double> min = employees1.stream()
                .map(Employee::getSalary)
                .min((s1, s2) -> Double.compare(s1, s2));
        System.out.println(min);

    }

    /**
     * 2.规约
     *  reduce(T identity,BinaryOperator) / reduce(BinaryOperator)--可以将流中元素反复结合起来，得到一个值
     */
    @Test
    public void test10() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(sum);
        System.out.println("===============");
        Optional<Double> sum2 = employees1.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(sum2.get());

    }

    /**
     * 3.收集
     * collect-- 将流转换成其他形式，接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
     */
    @Test
    public void test11() {
        List<String> collect = employees1.stream()
                .map(Employee::getName)
                .collect(toList());
        collect.forEach(System.out::println);

        System.out.println("--------------------------------");

        Set<String> collect1 = employees1.stream()
                .map(Employee::getName)
                .collect(toSet());
        collect1.forEach(System.out::println);

        System.out.println("--------------------------------");
        HashSet<String> collect2 = employees1.stream()
                .map(Employee::getName)
                .collect(toCollection(HashSet::new));
        collect2.forEach(System.out::println);
        System.out.println("--------------------------------");
        //总数
        Long collect3 = employees1.stream()
                .collect(counting());
        System.out.println(collect3);
        System.out.println("--------------------------------");
        //平均值
        Double collect4 = employees1.stream()
                .collect(averagingDouble(Employee::getSalary));
        System.out.println(collect4);
        System.out.println("--------------------------------");
        //总和
        Double collect5 = employees1.stream()
                .collect(summingDouble(Employee::getSalary));
        System.out.println(collect5);
        System.out.println("--------------------------------");
        // 最大值
        Optional<Employee> collect6 = employees1.stream()
                .collect(maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(collect6);
        System.out.println("--------------------------------");
        // 最小值
        Optional<Double> collect7 = employees1.stream()
                .map(Employee::getSalary)
                .collect(minBy((s1, s2) -> Double.compare(s1, s2)));
        System.out.println(collect7);
        System.out.println("--------------------------------");
        // 分组
        Map<Employee.Status, List<Employee>> collect8 = employees1.stream()
                .collect(groupingBy(Employee::getStatus));
        System.out.println(collect8);
        System.out.println("--------------------------------");
        // 多级分组
        Map<Employee.Status, Map<String, List<Employee>>> collect9 = employees1.stream()
                .collect(groupingBy(Employee::getStatus, groupingBy(e -> {
                    if (e.getAge() <= 35) {
                        return "青年";
                    } else if (e.getAge() <= 50) {
                        return "中年";
                    } else {
                        return "老年";
                    }
                })));
        System.out.println(collect9);
        System.out.println("--------------------------------");
        // 分区
        Map<Boolean, List<Employee>> collect10 = employees1.stream()
                .collect(partitioningBy(e -> e.getSalary() > 8000));
        System.out.println(collect10);
    }

    // 练习
    /**
     * 1.给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？，给定数组[1,2,3,4,5,6],应该返回[1,4,9,16,25,36]
     */
    @Test
    public void testP1() {
        Integer[] integers = {1, 2, 3, 4, 5, 6};
        Arrays.stream(integers)
                .map(x -> x * x)
                .forEach(System.out::println);

    }
    /**
     * 2.怎么用map和reduce方法数一数流中有多少个Employee呢？
     *
     */
    @Test
    public void testP2(){
        Optional<Integer> reduce = employees1.stream()
                .map(e -> 1)
                .reduce(Integer::sum);
        System.out.println(reduce.get());
    }
}
