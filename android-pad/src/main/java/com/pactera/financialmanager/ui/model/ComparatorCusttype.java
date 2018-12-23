package com.pactera.financialmanager.ui.model;

import java.util.Comparator;

import android.text.TextUtils;

/**
 * 客户价值评比排序
 * @author Administrator
 * @param <T>
 *
 */
public class ComparatorCusttype<T> implements Comparator<T>{

	@Override
	public int compare(Object lhs, Object rhs) {
		Proportion p1 = (Proportion) lhs;
		Proportion p2 = (Proportion) rhs;

		// 判断数据
		String type1 = p1.getCUSTTYPE();
		String type2 = p2.getCUSTTYPE();
		if(TextUtils.isEmpty(type1)){
			type1 = "0";
		}
		if(TextUtils.isEmpty(type2)){
			type2 = "0";
		}
		int c1 = Integer.parseInt(type1);
		int c2 = Integer.parseInt(type2);
		if(c1 > c2){
			return 1;
		}else if(c1 == c2){
			return 0;
		}else{
			return -1;
		}
	}
	
}
