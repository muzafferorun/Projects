package tr.com.muzo.component.dataTable;

import javafx.scene.control.SkinBase;

@SuppressWarnings("rawtypes")
public class DataTableSkin<T> extends SkinBase{

	@SuppressWarnings("unchecked")
	public DataTableSkin(DataTable<T> control) {
		super(control);
	}
	
}
