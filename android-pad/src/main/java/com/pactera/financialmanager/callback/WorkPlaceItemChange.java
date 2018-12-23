package com.pactera.financialmanager.callback;

/**
 * tab标签切换的回调接口
 * @author Administrator
 */
public interface WorkPlaceItemChange {
	
	public void workPlacestyleChange(int tag);
	
	public void workPlacestyleChange(int tag, boolean isForPerson);
	
	public void workMyreportPersonChange(int tag, boolean isForPerson);
	
}
