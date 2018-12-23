package com.dysen.common_res.common.utils.xml;

/**
 * ResultBean实体类
 * 
 * cc.jnbank.mobilemarketing.model.ResultBean
 * 
 * @author 金志君 <br/>
 *         create at 2015年10月13日 上午9:24:20
 */
public class ResultBean {
  private static final String TAG = "ResultBean";

  private Object headBean;
  private Object bodyBean;

  public Object getHeadBean() {
    return this.headBean;
  }

  public void setHeadBean(Object headBean) {
    this.headBean = headBean;
  }

  public Object getBodyBean() {
    return this.bodyBean;
  }

  public void setBodyBean(Object bodyBean) {
    this.bodyBean = bodyBean;
  }

}
