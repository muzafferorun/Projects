package tr.com.muzo.enums;

public enum AramaTip {
	
	ESITLIK(0, "E�itlik"), BASINDA(1, "Ba��nda"), SONUNDA(2, "Sonunda"), HER_YERINDE(3, "Her Yerinde"), BUYUKTUR(4,
			"B�y�kt�r"), BUYUK_ESIT(5, "B�y�k E�it"), KUCUKTUR(6, "K���kt�r"), KUCUK_ESIT(7, "K���k E�it");
	
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
