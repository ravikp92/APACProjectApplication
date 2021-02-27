package com.ravi.patient.app.enums;

public enum AffectedOrgan {
	LUNGS(1), HEART(2), LEG(3), HANDS(4), KIDNEY(5),OTHERS(6);

	private int organ;
	AffectedOrgan(int organ) {
		this.setOrgan(organ);
	}
	public int getOrgan() {
		return organ;
	}
	public void setOrgan(int organ) {
		this.organ = organ;
	}
}
