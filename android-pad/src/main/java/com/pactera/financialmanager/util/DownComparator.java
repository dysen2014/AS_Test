package com.pactera.financialmanager.util;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Tiger on 2018/7/24.
 * 降序
 */

public class DownComparator implements Comparator<List<String>> {

    //    如果要按照升序排序，
//    则o1 小于o2，返回-1（负数），相等返回0，01大于02返回1（正数）
//    如果要按照降序排序
//    则o1 小于o2，返回1（正数），相等返回0，01大于02返回-1（负数）

    int index;

    public DownComparator(int num) {
        index = num;
    }
    @Override
    public int compare(List<String> l1, List<String> l2) {
        String s1 = (String) l1.get(index);
        String s2 = (String) l2.get(index);

        if (isDouble(s1)){

            s1 = s1.replace(",","");
            s2 = s2.replace(",","");
            if (s1 == null){
                s1 = "0";
            }
            if (s2 == null){
                s2 = "0";
            }
            return new Double(s2).compareTo(new Double(s1));

        }else {

            if (s1.compareTo(s2) > 0) {
                return -1;
            } else if (s1.compareTo(s2) == 0) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    boolean isDouble(String str)
    {
        try
        {
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException ex){}
        return false;
    }

}
