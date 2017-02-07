package tr.com.muzo.component.dataTable;

public class DataTableColumn {
	private String kolonAdi;
	private String headerName;

	public DataTableColumn(String kolonAdi, String headerName) {
		super();
		this.kolonAdi = kolonAdi;
		this.headerName = headerName;
	}

	public String getKolonAdi() {
		return kolonAdi;
	}

	public void setKolonAdi(String kolonAdi) {
		this.kolonAdi = kolonAdi;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

}