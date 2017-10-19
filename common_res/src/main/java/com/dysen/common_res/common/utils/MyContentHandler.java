package com.dysen.common_res.common.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by dysen on 2017/9/4.
 */

public class MyContentHandler extends DefaultHandler {

	private String nodeName;
	private StringBuilder Envelope;
	private StringBuilder Body;
	private StringBuilder getJSONResponse;
	private StringBuilder getJSONReturn ;

	@Override
	public void startDocument() throws SAXException {
		Envelope = new StringBuilder();
		Body = new StringBuilder();
		getJSONResponse = new StringBuilder();
		getJSONReturn = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		nodeName = localName;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (Envelope.equals(nodeName)){
			Envelope.append(ch, start, length);
		}else if (Body.equals(nodeName)){
			Body.append(ch, start, length);
		}else if (getJSONResponse.equals(nodeName)){
			getJSONResponse.append(ch, start, length);
		}else if (getJSONReturn.equals(nodeName)){
			getJSONReturn.append(ch, start, length);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (getJSONReturn.equals(localName)){

		}
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}
}
