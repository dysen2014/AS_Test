package com.pactera.financialmanager.entity;

/**
 * 码值Entity
 * 
 * @author JAY
 *
 */
public class CatevalueEntity {
	private String value;// 码值
	private String label;// 对应文本

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "AreaEntity [id=" + value + ", label=" + label + "]";
	}

	public CatevalueEntity(String id, String name) {
		super();
		this.value = id;
		this.label = name;
	}

	public CatevalueEntity() {
		super();
	}

}
