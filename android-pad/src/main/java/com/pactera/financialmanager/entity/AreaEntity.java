package com.pactera.financialmanager.entity;

/**
 * 地址选择Entity
 * 
 * @author JAY
 *
 */
public class AreaEntity {
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AreaEntity [id=" + id + ", name=" + name + "]";
	}

	public AreaEntity(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public AreaEntity() {
		super();
	}

}
