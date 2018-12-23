package com.pactera.financialmanager.ui.model;

import java.util.Comparator;

/**
 * 存款趋势图日期排序
 * @author Administrator
 * @param <T>
 *
 */
public class ComparatorDeposit<T> implements Comparator<T>{

	@Override
	public int compare(Object lhs, Object rhs) {
		Deposit d1 = (Deposit) lhs;
		Deposit d2 = (Deposit) rhs;

		int flag = d1.getTJ_DATE().compareTo(d2.getTJ_DATE());
		return flag;
	}
	
}
