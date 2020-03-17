package com.majia.day1.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * java8 内置的四大核心函数式接口
 * <p>
 * Consumer<T>: 消费型接口 void accept(T t)
 * Supplier<T>: 供给型接口 T get();
 * Function<T,R>: 函数型接口 R apply(T t);
 * Predicate<T>: 断言型接口 boolean test(T t);
 */
public class LambdaTest3 {


    //Consumer<T>: 消费型接口 void accept(T t)
    @Test
    public void testConsumer() {
        play(1000, m -> System.out.println("play consume " + m + " yuan!"));
    }

    public void play(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }

    //Supplier<T>: 供给型接口 T get();
    // 需求：产生指定个数的整数，并放入集合中
    @Test
    public void testSupplier() {
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
        for (Integer num : numList) {
            System.out.println(num);
        }
    }

    public List<Integer> getNumList(Integer num, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    // Function<T,R>: 函数型接口 R apply(T t);
    // 需求：用于处理字符串
    @Test
    public void testFunction() {
        String hello = strHandler("hello", str -> str.toUpperCase());
        System.out.println(hello);
    }

    public String strHandler(String string, Function<String, String> function) {
        return function.apply(string);
    }

    // Predicate<T>: 断言型接口 boolean test(T t);
    // 需求：将满足条件的字符串，放入到集合中
    @Test
    public void testPredicate() {
        List<String> list = Arrays.asList("Hello","java","python","Lambda","netty");
        List<String> strings = filterStr(list, str -> str.length() > 4);
        for (String s:strings) {
            System.out.println(s);
        }

    }

    public List<String> filterStr(List<String> list, Predicate<String> predicate) {
        List<String> result = new ArrayList<>();
        for (String str : list) {
            if(predicate.test(str)){
                result.add(str);
            }
        }
        return result;
    }
}
