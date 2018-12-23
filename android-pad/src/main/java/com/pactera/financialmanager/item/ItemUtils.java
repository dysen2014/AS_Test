package com.pactera.financialmanager.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dy on 2016-11-19.
 */

public class ItemUtils {

    /**
     * 标题内容
     * @param values
     * @return
     */
    public static List<String> getValueList(String[] values){

        List<String> valueList = new ArrayList<>();

        for (int i = 0; i < values.length; i++){

            valueList.add(values[i]);
        }

        return valueList;
    }

    /**
     * 右边文字  提示内容
     * @param infos
     * @return
     */
    public static List<String> getInfoList(String[] infos){

        List<String> valueList = new ArrayList<>();

        for (int i = 0; i < infos.length; i++){

            valueList.add(infos[i]);
        }

        return valueList;
    }

    /**
     * 图标 图片数组
     * @param resIds
     * @return
     */
    public static List<Integer> getResIdList(int[] resIds){

        List<Integer> resIdList = new ArrayList<>();

        for (int i = 0; i < resIds.length; i++){

            resIdList.add(resIds[i]);
        }

        return resIdList;
    }
}
