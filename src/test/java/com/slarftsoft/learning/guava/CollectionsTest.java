package com.slarftsoft.learning.guava;

import com.google.common.collect.*;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class CollectionsTest {

    @Test
    //java使用Collections.unmodifiableCollection()的缺点在于
    //只是封装(隐藏)可修改的方法,但对原List修改仍影响线List
    //Guava提供了防御性地拷贝.
    //java8用Stream表示不可变的List
    public void testImmutable() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("once");
        list.add("upon");
        list.add("time");
        list.add("there");
        //生成的不可变List,看上去应该是4个元素
        Collection unmodifiableList = Collections.unmodifiableList(list);
        //Guava的不可变实现,和原来的List完全没关系了.
        ImmutableList<String> immutableList = ImmutableList.copyOf(list);

        //ImmutableList<String> immutableList1 = ImmutableList.of("once","upon");
        list.add("second damon");
        assertEquals(immutableList.size(), 4);
        //原list添加一个后,实际影响了不可变List
        assertEquals(unmodifiableList.size(), 5);
    }

    @Test
    //Guava提供了一个新集合类型 Multiset，它可以多次添加相等的元素
    //Multiset继承自JDK中的Collection接口，而不是Set接口，所以包含重复元素并没有违反原有的接口契约。
    public void testMultiset() throws Exception {
        Multiset multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        //允许重复添加
        multiset.add("a");
        assertEquals(multiset.count("a"), 2);
        //删除也是只删一个
        multiset.remove("a");
        assertEquals(multiset.count("a"), 1);
    }

    @Test
    //Guava提供了Multimap,把键映射到任意多个值
    //从而避免自行实现这样映射,Map<K, List<V>>或Map<K, Set<V>>，并且要忍受这个结构的笨拙。
    //很少会直接使用Multimap接口，更多时候你会用ListMultimap或SetMultimap接口，它们分别把键映射到List或Set。
    //例如，Map<K, Set<V>>通常用来表示非标定有向图。Guava的 Multimap可以很容易地把一个键映射到多个值
    public void testMultimap() throws Exception {
        //注意,用create静态方法
        Multimap<String,Integer> scoreMultimap = ArrayListMultimap.create();
        //
        for(int i=10;i<20;i++){
            Integer score = 100-i;
            scoreMultimap.put("peida",score);
        }
        assertEquals(scoreMultimap.size(),10);
        assertEquals(scoreMultimap.keys().toString(),"[peida x 10]");

        //可以一次性读取一个key的所有values,并做对应的修改
        Collection<Integer> studentScore = scoreMultimap.get("peida");
        studentScore.clear();
        studentScore.add(88);

        assertEquals(scoreMultimap.size(),1);
        assertEquals(scoreMultimap.keys().size(),1);
    }

    @Test
    //Map通常用来通过key找value,但是有时候也需要根据value找key
    //Guava提供了BiMap,实现了这种双向映射.
    //注意:Bimap数据的强制唯一性(key和value必须一一对应.
    public void testBimap() throws Exception {
        //注意,用create静态方法
        BiMap<Integer,String> ageMap = HashBiMap.create();
        ageMap.put(12,"lilei");
        ageMap.put(13,"haimeimei");
        ageMap.put(14,"lily");

        //注意,使用倒转,获得对应的逆map,这两个是视图关联的.
        BiMap<String,Integer> nameMap = ageMap.inverse();
        assertEquals(nameMap.get("haimeimei").intValue(),13);

        //原map修改,也影响逆msp
        ageMap.put(15,"lucy");
        assertEquals(nameMap.get("lucy").intValue(),15);

        //强制一致性,两个同value会报错,使用forcePut,覆盖原值
        //ageMap.put(18,"lucy");
        ageMap.forcePut(18,"lucy");
        assertEquals(nameMap.get("lucy").intValue(),18);
    }

    @Test
    //Guava提供了Table,实现一种2维Map的格式
    //Table支持“row”和“column”,行和列表示一个map,(可以取出某行,某列的hashmap)
    //而不是的Map<FirstKey, Map<SecondeKey, Value>>的格式
    public void testTable() throws Exception {
        //注意,用create静态方法
        Table<String, Integer, String> table = HashBasedTable.create();
        table.put("a",1,"A_First");
        table.put("a",2,"A_Second");
        table.put("b",2,"B_Second");
        table.put("b",4,"B_Fourth");

        //直接取值
        assertEquals(table.get("a",1),"A_First");

        //取出行的map,再取值
        Map<Integer, String> rowMap = table.row("b");
        assertEquals(rowMap.get(4),"B_Fourth");
    }

    //rangeSet
    //java8的stream也有range方法了.
    //rangeClose,rangeOpen等
}
