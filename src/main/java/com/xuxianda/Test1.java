package com.xuxianda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by XiandaXu on 2020/4/3.
 */
public class Test1 {

    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(new Card(1,1));
        System.out.println(list);
    }

    @Test
    public void test1(){
        Random random = new Random();
        while (true) {
            int i = random.nextInt(3);
            System.out.println(i);
        }
    }

    @Test
    public void test2(){
        int[] numArray = new int[8];
        System.out.println(numArray);
    }

}
