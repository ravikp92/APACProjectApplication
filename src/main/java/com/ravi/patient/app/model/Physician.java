package com.ravi.patient.app.model;

import java.io.Serializable;

/**
 * @author RaviP
 *
 */
@SuppressWarnings("serial")
public class Physician implements Serializable {

	private int id;
	private String name;

	public Physician() {

	}

	public Physician(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
		return "Physician [id=" + id + ", name=" + name + "]";
	}

}
