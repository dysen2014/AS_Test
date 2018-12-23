package com.dysen.common_res.common.utils.xml;



import java.util.ArrayList;

/**
 * 
 * ResultBeanList<T> 实体类
 * cc.jnbank.mobilemarketing.model.ResultBeanList
 * @author 金志君 <br/>
 * @param <T>
 * create at 2015年10月13日 上午9:24:44
 */
public class ResultBeanList<T> {
  private static final String TAG = "ResultBeanList";
  private Object headBean;
  private ArrayList<T> bodyBean;

  public Object getHeadBean() {
    return this.headBean;
  }

  public void setHeadBean(Object headBean) {
    this.headBean = headBean;
  }

  public ArrayList<T> getBodyBean() {
    return this.bodyBean;
  }

  public void setBodyBean(ArrayList<T> bodyBean) {
    this.bodyBean = bodyBean;
  }

}
