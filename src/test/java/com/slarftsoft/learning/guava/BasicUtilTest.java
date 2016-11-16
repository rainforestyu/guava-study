package com.slarftsoft.learning.guava;

import com.google.common.base.Optional;
import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


import static org.junit.Assert.assertEquals;


public class BasicUtilTest {

    @Test
    //java8已提供java.util.Optional实现一样的功能
    public void testGoolgeOptional() throws Exception{
        //有值
        Optional<Integer> possible = Optional.of(5);
        assertEquals(possible.get(),Integer.valueOf(5));

        //无值
        Optional<Integer> possible2 = Optional.absent();
        assertEquals(possible2.orNull(),null);
        assertEquals(possible2.or(5),Integer.valueOf(5));
    }

    /*
    常见的静态方法：
　　 natural()：使用Comparable类型的自然顺序， 例如：整数从小到大，字符串是按字典顺序;
　　 usingToString() ：使用toString()返回的字符串按字典顺序进行排序；
　　 arbitrary() ：返回一个所有对象的任意顺序， 即compare(a, b) == 0 就是 a == b (identity equality)。 本身的排序是没有任何含义， 但是在VM的生命周期是一个常量。
    */
    //java8的Compartor和stream结合貌似也挺好
    @Test
    public void testOrdering()throws Exception
    {
        List list = Arrays.asList("helly","pig","monkey","horse");
        List sortedNaturalList = Ordering.natural().reverse().sortedCopy(list);
        assertEquals(sortedNaturalList.get(0),"pig");
    }
}
