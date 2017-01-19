package tr.com.muzo.enums;

public enum EvetHayirEnum {
	
	SECINIZ(0 , "Lütfen Seçiniz" , null),
	EVET(1 , "Evet" , Boolean.TRUE),
	HAYIR(2 , "Hayýr" , Boolean.FALSE);
	
	
	private int id;
	private String tanim;
	private Boolean data;
	private EvetHayirEnum(int id, String tanim, Boolean data) {
		this.id = id;
		this.tanim = tanim;
		this.data = data;
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
	public Boolean getData() {
		return data;
	}
	public void setData(Boolean data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return getTanim();
	}
	
	
}
