package com.dysen.common_res.common.utils.xml;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 这是解析行方发送的XML文件。 Bean 需要public 格式的文件 cc.jnbank.mobilemarketing.utils.XMLUtils
 * 
 * @author 金志君 <br/>
 *         create at 2015年10月13日 上午8:53:19
 */
public class XMLUtils {
  /**
   * @param parser
   *          输入的XML文件
   * @param headRoot
   *          head文件的根节点
   * @param headClass
   *          headBean
   * @param bodyRoot
   *          body文件的根节点
   * @param bodyClass
   *          bodyBean
   * @return ResultBean
   */
  public static <T, T1> ResultBean getBeanByParseXml(XmlPullParser parser, String headRoot, Class<T> headClass,
      String bodyRoot, Class<T1> bodyClass) {
    // 创建一个最终返回的容器
    ResultBean result = null;
    // headBean
    T t = null;
    // bodyBean
    T1 t1 = null;

    try {
      // 获得第一个节点
      int eventType = parser.getEventType();
      // 当节点不是最后的根节点时会一直执行
      while (XmlPullParser.END_DOCUMENT != eventType) {
        switch (eventType) {

          case XmlPullParser.START_DOCUMENT:
            // 实例化resultBean;
            result = new ResultBean();
            break;
          case XmlPullParser.START_TAG:
            // 获取节点的名字
            String tagName = parser.getName();
            // 当 headBean实例化时
            if (t != null) {
              try {
                // 判断 headClass 中是否含有这个字段
                Field field = headClass.getField(tagName);
                // 当文件中存在这个字段时
                if (field != null) {
                  // 给headbean中的字段赋值
                  field.set(t,parser.nextText());
                }
              } catch (Exception e) {

              }
            }
            // 以下是对body的操作。 跟上方一样
            if (t1 != null) {
              try {
                Field field = bodyClass.getField(tagName);

                field.set(t1, parser.nextText());
              } catch (Exception e) {
              }
            }
            if (tagName.equals(headRoot)) {
              t = headClass.newInstance();
            }
            if (tagName.equals(bodyRoot)) {
              t1 = bodyClass.newInstance();
            }
            break;
          case XmlPullParser.END_TAG:
            if (bodyRoot.equalsIgnoreCase(parser.getName())) {
              if (t1 != null) {

                result.setBodyBean(t1);
              }
            } else if (headRoot.equalsIgnoreCase(parser.getName())) {
              result.setHeadBean(t);
            }
            break;
          default:
            break;
        }
        eventType = parser.next();
      }
    } catch (Exception e) {

    }
    return result;
  }

  /**
   * @param parser
   *          输入的XML文件
   * @param headRoot
   *          head文件的根节点
   * @param headClass
   *          headBean
   * @param bodyRoot
   *          body文件的根节点
   * @param bodyClass
   *          bodyBean
   * @return ResultBeanList bean的集合
   */
  public static <T, T1> ResultBeanList<T1> getBeanListByParseXml(XmlPullParser parser, String headRoot,
      Class<T> headClass, String bodyRoot, Class<T1> bodyClass) {
    // 最终结果集
    ResultBeanList<T1> result = null;
    // 装有Body的路径
    ArrayList<T1> bodyBean = null;
    // headBean
    T t = null;
    // bodyBean
    T1 t1 = null;

    try {
      int eventType = parser.getEventType();

      while (eventType != XmlPullParser.END_DOCUMENT) {
        switch (eventType) {

          case XmlPullParser.START_DOCUMENT:

            result = new ResultBeanList<T1>();

            bodyBean = new ArrayList<T1>();

            result.setBodyBean(bodyBean);

            break;

          case XmlPullParser.START_TAG:

            String tagName = parser.getName();

            if (t1 != null) {
              try {

                Field field = bodyClass.getField(tagName);

                if (field != null) {

                  field.set(t1, parser.nextText());

                }
              } catch (Exception e) {

              }

            }
            if (t != null) {
              try {

                Field field = headClass.getField(tagName);

                if (field != null) {

                  field.set(t, parser.nextText());
                }
              } catch (Exception e) {

              }
            }

            if (tagName.equals(bodyRoot)) {

              t1 = bodyClass.newInstance();
            }

            if (tagName.equals(headRoot)) {

              t = headClass.newInstance();
            }
            break;

          case XmlPullParser.END_TAG:

            if (bodyRoot.equalsIgnoreCase(parser.getName())) {

              if (t1 != null) {

                bodyBean.add(t1);
                // 需要清空T1 因为有好多ITEM节点
                t1 = null;
              }

            }
            if (headRoot.equalsIgnoreCase(parser.getName())) {

              result.setHeadBean(t);
            }
            break;
        }
        eventType = parser.next();
      }
    } catch (Exception e) {

    }
    return result;
  }
}