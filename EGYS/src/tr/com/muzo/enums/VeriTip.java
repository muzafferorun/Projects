package tr.com.muzo.enums;

public enum VeriTip {

	TEXT(0, "Text"), PASSWORD(1, "Password"), DROP_DOWN(2, "Drop Down");

	private VeriTip(int id, String deger) {
		this.id = id;
		this.deger = deger;
	}

	private int id;
	private String deger;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeger() {
		return deger;
	}

	public void setDeger(String deger) {
		this.deger = deger;
	}
}