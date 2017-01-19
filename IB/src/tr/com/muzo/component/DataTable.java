package tr.com.muzo.component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Skin;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Callback;
import tr.com.muzo.application.util.ApplicationContextProvider;
import tr.com.muzo.component.skin.DataTableSkin;
import tr.com.muzo.component.util.DataTableColumn;
import tr.com.muzo.component.util.DialogGridData;
import tr.com.muzo.controller.application.ApplicationController;
import tr.com.muzo.enums.EvetHayirEnum;
import tr.com.muzo.parameter.SimpleAramaKriter;
import tr.com.muzo.service.SimpleService;
import tr.com.muzo.util.ZorunluAlanKontrol;

public class DataTable<T> extends Control {
	
	private ApplicationController applicationController;
	
	private Label baslikLabel = new Label("Baþlýk Girilmemiþ");
	private TableView<T> tableView = new TableView<T>();
	private ButtonBar buttonBar = new ButtonBar();
	private Dialog<T> detayDialog = new Dialog<T>();
	private Dialog<String> parametreDialog = new Dialog<String>();
	private Scene scene;
	private DataTableColumn[] dataTableColumn;
	private T data;
	private DialogGridData[] dialogGridData;
	private String serviceName;
	private Object aramaParametre;
	private Class<T> clazz;
	
	public DataTable() {
		super();
	}

	public DataTable(DataTableColumn[] dataTableColumn , DialogGridData[] dialogGridData, Scene scene , String serviceName , Object aramaParametre , Class<T> clazz) {
		super();
		this.dataTableColumn = dataTableColumn;
		this.dialogGridData = dialogGridData;
		this.scene = scene;
		this.serviceName = serviceName;
		this.aramaParametre = aramaParametre;
		this.clazz = clazz;
		init();
		applicationController = ApplicationContextProvider.getContext().getBean("applicationController", ApplicationController.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void init() {
		double height = scene.heightProperty().doubleValue();
		double width = scene.widthProperty().doubleValue() - 2;
		setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
		baslikLabel.setPrefWidth(Double.MAX_VALUE);
		tableView.setPrefSize(Double.MAX_VALUE, height-baslikLabel.getHeight()-buttonBar.getHeight());
		VBox vbox = new VBox();
		vbox.setSpacing(0);
		vbox.setPadding(new Insets(0, 0, 0, 0));
		vbox.getChildren().addAll(baslikLabel, tableView, buttonBar);
		baslikLabel.getStyleClass().add("dataTableHeader");
		for (DataTableColumn dataTableColumn : dataTableColumn) {
			TableColumn kolon = new TableColumn(dataTableColumn.getHeaderName());
			kolon.setMinWidth(width / this.dataTableColumn.length);
			kolon.setMaxWidth(width / this.dataTableColumn.length);
			kolon.setCellValueFactory(new Callback<CellDataFeatures<T, String>, ObservableValue<String>>() {
				public ObservableValue<String> call(CellDataFeatures<T, String> p) {
					Object o = null;
					try {
						o = PropertyUtils.getProperty((T) p.getValue(), dataTableColumn.getKolonAdi());
					} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					}
					StringProperty returnDeger = null;
					if (o instanceof String) {
						returnDeger = new SimpleStringProperty((String) o);
					} else if (o instanceof Boolean) {
						returnDeger = ((Boolean) o) ? new SimpleStringProperty("Evet") : new SimpleStringProperty("Hayýr");
					}
					return returnDeger;
				}
			});
			tableView.getColumns().add(kolon);
		}
		scene.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		    	double width = newSceneWidth.doubleValue()-2;
		    	for (TableColumn column : tableView.getColumns()) {
		    		column.setMinWidth(width/tableView.getColumns().size());
		    		column.setMaxWidth(width/tableView.getColumns().size());
		    	}
		    }
		});
		
		scene.heightProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
		    	double height = newSceneHeight.doubleValue()-baslikLabel.getHeight()-buttonBar.getHeight()-25;
		    	tableView.setMinHeight(height);
		    	tableView.setMaxHeight(height);
		    }
		});
		
		tableView.setRowFactory( tv -> {
		    TableRow<T> row = new TableRow<T>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            T rowData = row.getItem();
		            data = rowData;
		            dialogAc(rowData);
		        }
		    });
		    return row ;
		});
		
		Button yeniButon = new Button("Yeni");
		Button parametreButon = new Button("Parametre");
		
		yeniButon.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					data =  clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				dialogAc(data);
			}
		});

		parametreButon.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				parametreDialogAc();
			}
		});
		buttonBar.getButtons().add(parametreButon);
		buttonBar.getButtons().add(yeniButon);
		getChildren().removeAll(getChildren());
		getChildren().add(vbox);
		SimpleService service = (SimpleService) ApplicationContextProvider.getContext().getBean(serviceName);
		this.addAllRow(service.listele(aramaParametre));
	}
	
	@Override
	protected Skin<?> createDefaultSkin() {
		return new DataTableSkin<T>(this);
	}
	
	public Label getBaslikLabel() {
		return baslikLabel;
	}

	public void setBaslikLabel(Label baslikLabel) {
		this.baslikLabel = baslikLabel;
	}

	public ButtonBar getButtonBar() {
		return buttonBar;
	}

	public void setButtonBar(ButtonBar buttonBar) {
		this.buttonBar = buttonBar;
	}
	
	public void addRow(T obje){
		tableView.getItems().add(obje);
	}
	
	public void addAllRow(List<T> liste){
		for(T obje : liste){
			addRow(obje);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void dialogAc(T data){
		detayDialog = new Dialog<T>();
		detayDialog.setTitle("Detay");
		GridPane grid = new GridPane();
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		int rowNum = 0;
		try {
			for(DialogGridData gridData : dialogGridData){
				Label label = new Label((gridData.getRequired() ? " * " : "") + gridData.getLabel());
				grid.add(label, 0, rowNum);
				Node node = null;
				if("text".equals(gridData.getDataTip())){
					node = new TextField();
					((TextField)node).setAccessibleText(gridData.getPlaceHolder());
					((TextField)node).setPrefWidth(200);
					String deger = (String) PropertyUtils.getProperty(data, gridData.getKolonAdi());
					((TextField)node).setText(deger);
				}else if("boolean".equals(gridData.getDataTip())){
					node = new ComboBox<EvetHayirEnum>(applicationController.getEvetHayirList());
					((ComboBox<EvetHayirEnum>)node).setAccessibleText(gridData.getPlaceHolder());
					((ComboBox<EvetHayirEnum>)node).setValue(EvetHayirEnum.SECINIZ);
					((ComboBox<EvetHayirEnum>)node).setPrefWidth(200);
					Boolean deger = (Boolean) PropertyUtils.getProperty(data, gridData.getKolonAdi());
					if(deger == null){
						((ComboBox<EvetHayirEnum>)node).setValue(EvetHayirEnum.SECINIZ);
					}else if(deger){
						((ComboBox<EvetHayirEnum>)node).setValue(EvetHayirEnum.EVET);
					}else if(!deger){
						((ComboBox<EvetHayirEnum>)node).setValue(EvetHayirEnum.HAYIR);
					}
				}else if("password".equals(gridData.getDataTip())){
					node = new PasswordField();
					((PasswordField)node).setAccessibleText(gridData.getPlaceHolder());
					((PasswordField)node).setPrefWidth(200);
					String deger = (String) PropertyUtils.getProperty(data, gridData.getKolonAdi());
					((PasswordField)node).setText(deger);
				}
				node.setId(gridData.getKolonAdi());
				grid.add(node, 1, rowNum);
				rowNum++;
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		ButtonType kaydetButtonType = new ButtonType("Kaydet", ButtonData.OK_DONE);
		detayDialog.getDialogPane().setContent(grid);
		detayDialog.initOwner(scene.getWindow());
		detayDialog.initModality(Modality.APPLICATION_MODAL);
		detayDialog.getDialogPane().getButtonTypes().add(kaydetButtonType);
		detayDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = detayDialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        Map<String,Map<String,String>> aramaMap = ((SimpleAramaKriter)aramaParametre).aramaKriter();
        detayDialog.setResultConverter(dialogButton -> {
        	if (dialogButton == kaydetButtonType) {
        		SimpleService service = (SimpleService) ApplicationContextProvider.getContext().getBean(serviceName);
        		ZorunluAlanKontrol zorunluAlanKontrol = (ZorunluAlanKontrol) ApplicationContextProvider.getContext().getBean("zorunluAlanKontrol");
        		Object[] fields = new Object[100];
				try {
					
					int i=0;
					for(Node node : ((GridPane)detayDialog.getDialogPane().getChildren().get(3)).getChildren()){
						for(DialogGridData gridData : dialogGridData){
							if(gridData.getKolonAdi().equals(node.getId())){
								if("text".equals(gridData.getDataTip())){
									PropertyUtils.setProperty(this.data, gridData.getKolonAdi(), ((TextField)node).getText());
									if(gridData.getRequired() != null && gridData.getRequired()){
										((TextField)node).setAccessibleText(gridData.getLabel());
										fields[i] = ((TextField)node);
										i++;
									}
								}else if("boolean".equals(gridData.getDataTip())){
									PropertyUtils.setProperty(this.data, gridData.getKolonAdi(), ((ComboBox<EvetHayirEnum>)node).getSelectionModel().getSelectedItem().getData());
									if(gridData.getRequired() != null && gridData.getRequired()){
										((ComboBox<EvetHayirEnum>)node).setAccessibleText(gridData.getLabel());
										fields[i] = ((ComboBox<EvetHayirEnum>)node);
										i++;
									}
								}else if("password".equals(gridData.getDataTip())){
									PropertyUtils.setProperty(this.data, gridData.getKolonAdi(), ((PasswordField)node).getText());
									if(gridData.getRequired() != null && gridData.getRequired()){
										((PasswordField)node).setAccessibleText(gridData.getLabel());
										fields[i] = ((PasswordField)node);
										i++;
									}
								}
							}else if(aramaMap.get(gridData.getKolonAdi()) != null && gridData.getKolonAdi().equals(node.getId())){
								if("text".equals(gridData.getDataTip())){
									PropertyUtils.setProperty(aramaParametre, gridData.getKolonAdi(), ((TextField)node).getText());
								}else if("boolean".equals(gridData.getDataTip())){
									PropertyUtils.setProperty(aramaParametre, gridData.getKolonAdi(), ((ComboBox<EvetHayirEnum>)node).getSelectionModel().getSelectedItem().getData());
								}else if("password".equals(gridData.getDataTip())){
									PropertyUtils.setProperty(aramaParametre, gridData.getKolonAdi(), ((PasswordField)node).getText());
								}
							}
						}
					}
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
				zorunluAlanKontrol.kontrol(fields);
				service.kaydet(this.data);
				tableView.getItems().removeAll(tableView.getItems());
				addAllRow(service.listele(aramaParametre));
		    }
		    return null;
        });
        detayDialog.show();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void parametreDialogAc(){
		parametreDialog = new Dialog();
		parametreDialog.setTitle("Parametre");
		GridPane grid = new GridPane();
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setId("aramaGrid");
		grid.setPadding(new Insets(10, 10, 10, 10));
		int rowNum = 0;
		
		Map<String,Map<String,String>> aramaMap = ((SimpleAramaKriter)aramaParametre).aramaKriter();
		
		try {
			for(String key : aramaMap.keySet()){
				Map<String,String> kriterMap = aramaMap.get(key);
				Label label = new Label(kriterMap.get("label"));
				grid.add(label, 0, rowNum);
				Node node = null;
				if("String".equals(kriterMap.get("dataTip"))){
					node = new TextField();
					((TextField)node).setPrefWidth(200);
					String deger = (String) PropertyUtils.getProperty(aramaParametre, key);
					((TextField)node).setText(deger);
				}else if("Boolean".equals(kriterMap.get("dataTip"))){
					node = new ComboBox<EvetHayirEnum>(applicationController.getEvetHayirList());
					((ComboBox<EvetHayirEnum>)node).setValue(EvetHayirEnum.SECINIZ);
					((ComboBox<EvetHayirEnum>)node).setPrefWidth(200);
					Boolean deger = (Boolean) PropertyUtils.getProperty(aramaParametre, key);
					if(deger == null){
						((ComboBox<EvetHayirEnum>)node).setValue(EvetHayirEnum.SECINIZ);
					}else if(deger){
						((ComboBox<EvetHayirEnum>)node).setValue(EvetHayirEnum.EVET);
					}else if(!deger){
						((ComboBox<EvetHayirEnum>)node).setValue(EvetHayirEnum.HAYIR);
					}
					
				}
				node.setId(key);
				grid.add(node, 1, rowNum);
				rowNum++;
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		} 
		
		ButtonType aramaButtonType = new ButtonType("Arama", ButtonData.OK_DONE);
		parametreDialog.getDialogPane().setContent(grid);
		parametreDialog.initOwner(scene.getWindow());
		parametreDialog.initModality(Modality.APPLICATION_MODAL);
		parametreDialog.getDialogPane().getButtonTypes().add(aramaButtonType);
		parametreDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		Node closeButton = parametreDialog.getDialogPane().lookupButton(ButtonType.CLOSE);
		closeButton.managedProperty().bind(closeButton.visibleProperty());
		closeButton.setVisible(false);
		
		parametreDialog.setResultConverter(dialogButton -> {
		    if (dialogButton == aramaButtonType) {
		    	SimpleService service = (SimpleService) ApplicationContextProvider.getContext().getBean(serviceName);
				try {
					for(Node node : ((GridPane)parametreDialog.getDialogPane().getChildren().get(3)).getChildren()){
						for(String key : aramaMap.keySet()){
							Map<String,String> kriterMap = aramaMap.get(key);
							if(key.equals(node.getId())){
								if("String".equals(kriterMap.get("dataTip"))){
										PropertyUtils.setProperty(aramaParametre, key, ((TextField)node).getText());
								}else if("Boolean".equals(kriterMap.get("dataTip"))){
									PropertyUtils.setProperty(aramaParametre, key, ((ComboBox<EvetHayirEnum>)node).getSelectionModel().getSelectedItem().getData());
								}
							}
						}
					}
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
				tableView.getItems().removeAll(tableView.getItems());
				addAllRow(service.listele(aramaParametre));
		    }
		    return "1";
		});
		parametreDialog.show();
		
	}

}
