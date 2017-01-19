package tr.com.muzo.controller.view.kullanici;

import org.springframework.stereotype.Controller;

import tr.com.muzo.application.util.BaseController;
import tr.com.muzo.component.DataTable;
import tr.com.muzo.component.util.DataTableColumn;
import tr.com.muzo.component.util.DialogGridData;
import tr.com.muzo.entity.Kullanici;
import tr.com.muzo.parameter.KullaniciAramaParametre;

@Controller
public class KullaniciController extends BaseController{

	private static final long serialVersionUID = 1961450543224589724L;
	
	@Override
	public void initialize() {
		DataTableColumn[] kolonList = new DataTableColumn[]{
				new DataTableColumn("kullaniciAdi", "Kullan�c� Ad�"),
				new DataTableColumn("aktif", "Aktif")
				};
		DialogGridData[] dialogGridData = new DialogGridData[]{
				new DialogGridData("kullaniciAdi", "Kullan�c� Ad�" , "text" , null , Boolean.TRUE),
				new DialogGridData("aktif", "Aktif" , "boolean" , null , Boolean.TRUE),
				new DialogGridData("sifre", "�ifre" , "password" , null , Boolean.TRUE),
		};
		super.initialize();
		DataTable<Kullanici> dataTable = new DataTable<Kullanici>(kolonList , dialogGridData ,root.getScene() , 
				"kullaniciService" ,new KullaniciAramaParametre() , Kullanici.class);
		dataTable.getBaslikLabel().setText("Kullan�c� Listesi");
		
		root.setCenter(dataTable);
		
	}
	
}
