package com.dysen.common_res.common.utils.xmls;

import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * =====作者=====
 * 许英俊
 * =====时间=====
 * 2016/11/9.
 */
public class XmlUtils {

    /**
     * DOM解析XML文件时，会将XML文件的所有内容读取到内存中（内存的消耗比较大），然后允许您使用DOM API遍历XML树、检索所需的数据
     */
    public List<ResultBean> dom2xml(InputStream is) throws Exception {
        //一系列的初始化
        List<ResultBean> list = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        //获得Document对象
        Document document = builder.parse(is);
        //获得ResultBean的List
        NodeList ResultBeanList = document.getElementsByTagName("getJSONResponse");
        //遍历ResultBean标签
        for (int i = 0; i < ResultBeanList.getLength(); i++) {
            //获得ResultBean标签
            Node node_ResultBean = ResultBeanList.item(i);
            //获得ResultBean标签里面的标签
            NodeList childNodes = node_ResultBean.getChildNodes();
            //新建ResultBean对象
            ResultBean ResultBean = new ResultBean();
            //遍历ResultBean标签里面的标签
            for (int j = 0; j < childNodes.getLength(); j++) {
                //获得name和nickName标签
                Node childNode = childNodes.item(j);
                //判断是name还是nickName
                if ("getJSONReturn".contains(childNode.getNodeName())) {
                    String json = childNode.getTextContent();
                    ResultBean.setResult(json);
                    //获取name的属性
                    NamedNodeMap nnm = childNode.getAttributes();
                }
            }
            //加到List中
            list.add(ResultBean);
        }
        return list;
    }

    /**
     * SAX是一个解析速度快并且占用内存少的xml解析器，SAX解析XML文件采用的是事件驱动，它并不需要解析完整个文档，而是按内容顺序解析文档的过程
     */
    public List<ResultBean> sax2xml(InputStream is) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        //初始化Sax解析器
        SAXParser sp = spf.newSAXParser();
        //新建解析处理器
        MyHandler handler = new MyHandler();
        //将解析交给处理器
        sp.parse(is, handler);
        //返回List
        return handler.getList();
    }

    public class MyHandler extends DefaultHandler {

        private List<ResultBean> list;
        private ResultBean ResultBean;
        //用于存储读取的临时变量
        private String tempString;

        /**
         * 解析到文档开始调用，一般做初始化操作
         *
         * @throws SAXException
         */
        @Override
        public void startDocument() throws SAXException {
            list = new ArrayList<>();
            super.startDocument();
        }

        /**
         * 解析到文档末尾调用，一般做回收操作
         *
         * @throws SAXException
         */
        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        /**
         * 每读到一个元素就调用该方法
         *
         * @param uri
         * @param localName
         * @param qName
         * @param attributes
         * @throws SAXException
         */
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if ("ResultBean".equals(qName)) {
                //读到ResultBean标签
                ResultBean = new ResultBean();
            } else if ("getJSONReturn".equals(qName)) {
                //获取name里面的属性
//                String sex = attributes.getValue("xsi:type");
//                ResultBean.setSex(sex);
            }
            super.startElement(uri, localName, qName, attributes);
        }

        /**
         * 读到元素的结尾调用
         *
         * @param uri
         * @param localName
         * @param qName
         * @throws SAXException
         */
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("ResultBean".equals(qName)) {
                list.add(ResultBean);
            }
            if ("getJSONReturn".equals(qName)) {
                ResultBean.setResult(tempString);
            }
            super.endElement(uri, localName, qName);
        }

        /**
         * 读到属性内容调用
         *
         * @param ch
         * @param start
         * @param length
         * @throws SAXException
         */
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            tempString = new String(ch, start, length);
            super.characters(ch, start, length);
        }

        /**
         * 获取该List
         *
         * @return
         */
        public List<ResultBean> getList() {
            return list;
        }
    }

    /**
     * Pull解析器的运行方式与 SAX 解析器相似。它提供了类似的事件，可以使用一个switch对感兴趣的事件进行处理
     */
    public List<ResultBean> pull2xml(InputStream is) throws Exception {
        List<ResultBean> list = null;
        ResultBean ResultBean = null;
        //创建xmlPull解析器
        XmlPullParser parser = Xml.newPullParser();
        ///初始化xmlPull解析器
        parser.setInput(is, "utf-8");
        //读取文件的类型
        int type = parser.getEventType();
        //无限判断文件类型进行读取
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                //开始标签
                case XmlPullParser.START_TAG:
                    if ("ResultBeans".equals(parser.getName())) {
                        list = new ArrayList<>();
                    } else if ("ResultBean".equals(parser.getName())) {
                        ResultBean = new ResultBean();
                    } else if ("getJSONReturn".equals(parser.getName())) {
                        //获取name值
                        String json = parser.nextText();
                        ResultBean.setResult(json);
                    }
                    break;
                //结束标签
                case XmlPullParser.END_TAG:
                    if ("ResultBean".equals(parser.getName())) {
                        list.add(ResultBean);
                    }
                    break;
            }
            //继续往下读取标签类型
            type = parser.next();
        }
        return list;
    }

    /**
     * SAX和Pull的区别：SAX解析器的工作方式是自动将事件推入事件处理器进行处理，因此你不能控制事件的处理主动结束；而Pull解析器的工作方式为允许你的应用程序代码主动从解析器中获取事件，正因为是主动获取事件，因此可以在满足了需要的条件后不再获取事件，结束解析。
     */
}
