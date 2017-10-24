package com.dysen.common_res.common.utils;

/**
 * Created by dysen on 10/24/2017.
 */

public class Utils  {

    public static String getTypeName(String customerType) {

        String name = "";
        if (customerType.equals("010"))
            name = "对公";
        else if (customerType.equals("030"))
            name = "个人";
        else  if (customerType.equals("040"))
            name = "农户";
        else if(customerType.equals("010070"))
            name = "其它";
        else
            name = "其它";
        return name;
    }
}
