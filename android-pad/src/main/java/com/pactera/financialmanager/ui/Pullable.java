package com.pactera.financialmanager.ui;

public interface Pullable
{
	/**
	 * 判断是否可以下拉，如果不需要下拉功能可以直接return false
	 * 
	 * @return true如果可以下拉否则返回false
	 */
	boolean canPullDown();

	/**
	 * 判断是否可以上拉，如果不需要上拉功能可以直接return false
	 * 
	 * @return true如果可以上拉否则返回false
	 */
	boolean canPullUp();
	
	/**
	 * 设置是否需要上拉或者下拉
	 * @param isNeedPulldown
	 * @param isPullup
	 */
	void setPullstatus(boolean isNeedPulldown, boolean isPullup);
}
