package tr.com.muzo.enums;

public enum IslemTip {

	YENI(0, "Yeni"), DETAY(1, "Detay"), GUNCELLE(2, "Güncelle");

	private IslemTip(int id, String tanim) {
		this.id = id;
		this.tanim = tanim;
	}

	private int id;
	private String tanim;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTanim() {
		return tanim;
	}
	public void setTanim(String tanim) {
		this.tanim = tanim;
	}
	
}
