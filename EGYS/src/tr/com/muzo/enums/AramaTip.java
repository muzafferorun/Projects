package tr.com.muzo.enums;

public enum AramaTip {
	
	ESITLIK(0, "Eþitlik"), BASINDA(1, "Baþýnda"), SONUNDA(2, "Sonunda"), HER_YERINDE(3, "Her Yerinde"), BUYUKTUR(4,
			"Büyüktür"), BUYUK_ESIT(5, "Büyük Eþit"), KUCUKTUR(6, "Küçüktür"), KUCUK_ESIT(7, "Küçük Eþit");
	
	private int id;
	private String tanim;
	
	private AramaTip(int id, String tanim) {
		this.id = id;
		this.tanim = tanim;
	}

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
