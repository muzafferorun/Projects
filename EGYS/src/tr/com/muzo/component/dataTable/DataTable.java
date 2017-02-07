package tr.com.muzo.component.dataTable;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.PropertyUtils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.control.Skin;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.util.Callback;
import tr.com.muzo.application.db.util.QueryResult;
import tr.com.muzo.application.db.util.SimpleService;
import tr.com.muzo.application.util.Arac;
import tr.com.muzo.application.util.Sabit;
import tr.com.muzo.component.util.AwesomeDude;
import tr.com.muzo.component.util.AwesomeIcons;
import tr.com.muzo.component.util.MessageUtil;
import tr.com.muzo.db.entity.BasePojo;
import tr.com.muzo.enums.IslemTip;

public class DataTable<T> extends Control {

	private Label baslik = new Label();
	private BorderPane rootPanel = new BorderPane();
	private TableView<T> tableView = new TableView<T>();
	private Class<T> clazz;
	private DataTableColumn[] kolonlar;
	private EntityProperty[] entityProperties;
	private Scene scene;
	private ButtonBar actionButtonBar = new ButtonBar();
	private GridPane bottomPane = new GridPane();
	private Button yeniButton = new Button("Yeni");
	private Button guncelleButton = new Button("Güncelle");
	private Button silButton = new Button("Sil");
	private Button detayButton = new Button("Detay");
	private Button parametreButton = new Button("Parametre");
	private Dialog<T> parametreDialog = new Dialog<T>();
	private Dialog<T> detayDialog = new Dialog<T>();
	private GridPane parametreGridPane = new GridPane();
	private GridPane detayGridPane = new GridPane();
	private T parametreData;
	private T data;
	private int sayfaNumarasi;
	private Button sonSayfaButton = AwesomeDude.createIconButton(AwesomeIcons.ICON_DOUBLE_ANGLE_RIGHT);
	private Button ilkSayfaButton = AwesomeDude.createIconButton(AwesomeIcons.ICON_DOUBLE_ANGLE_LEFT);
	private Button sonrakiSayfaButton = AwesomeDude.createIconButton(AwesomeIcons.ICON_ANGLE_RIGHT);
	private Button oncekiSayfaButton = AwesomeDude.createIconButton(AwesomeIcons.ICON_ANGLE_LEFT);
	private ComboBox<Integer> sayfadakiKayitSayisiCombo = new ComboBox<Integer>(
			FXCollections.observableList(Arrays.asList(new Integer[] { 10, 20, 30 })));
	private SimpleService<T> simpleService;
	private MessageUtil messageUtil;
	private int toplamSayfaSayisi;

	private Label toplamKayitSayisiLabel = new Label(Sabit.GOSTERILECEK_KAYIT_SAYISI);

	@SuppressWarnings("unchecked")
	public DataTable(Scene scene, Class<T> clazz) {
		super();
		this.scene = scene;
		this.clazz = clazz;
		BasePojo basePojo = null;
		try {
			basePojo = (BasePojo) this.clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
		}
		if (parametreData == null) {
			try {
				parametreData = this.clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
			}
		}
		this.baslik.setText(basePojo.getListeAdi());
		this.kolonlar = basePojo.getKolonlar();
		this.entityProperties = basePojo.getEntityProperties();
		tableView.setPlaceholder(new Label("Gösterilecek Kayýt Bulunamadý"));
		sayfaNumarasi = 1;
		toplamSayfaSayisi = 1;
		sayfadakiKayitSayisiCombo.setValue(10);
		sayfadakiKayitSayisiCombo.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			@SuppressWarnings("rawtypes")
			public void changed(ObservableValue ov, Integer t, Integer t1) {
				toplamSayfaSayisi = 1;
				sayfaNumarasi = 1;
				listele();
			}
		});
		simpleService = (SimpleService<T>) Arac.getBean(Arac.getServiceClassName(this.clazz));
		messageUtil = (MessageUtil) Arac.getBean(MessageUtil.class);
		initialize();
	}

	public void initialize() {
		double width = scene.widthProperty().doubleValue() - 3;
		this.baslik.setPrefWidth(Double.MAX_VALUE);
		this.baslik.getStyleClass().add("dataTableHeader");
		this.bottomPane.setPrefWidth(width + 3);
		this.bottomPane.setMinWidth(width + 3);
		rootPanel.setPadding(new Insets(0, 0, 0, 0));
		rootPanel.setTop(baslik);

		disableDurumDegistir(Boolean.TRUE);

		for (DataTableColumn dataTableColumn : kolonlar) {
			TableColumn<T, String> kolon = new TableColumn<T, String>(dataTableColumn.getHeaderName());
			kolon.setMinWidth(width / this.kolonlar.length);
			kolon.setMaxWidth(width / this.kolonlar.length);
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
						returnDeger = ((Boolean) o) ? new SimpleStringProperty("Evet")
								: new SimpleStringProperty("Hayýr");
					}
					return returnDeger;
				}
			});
			tableView.getColumns().add(kolon);
		}

		tableView.setRowFactory(tv -> {
			TableRow<T> row = new TableRow<T>();
			row.setOnMouseClicked(event -> {
				data = row.getItem();
				disableDurumDegistir(Boolean.FALSE);
			});
			return row;
		});

		scene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
					Number newSceneWidth) {
				double width = newSceneWidth.doubleValue() - 3;
				for (TableColumn<T, ?> column : tableView.getColumns()) {
					column.setMinWidth(width / tableView.getColumns().size());
					column.setMaxWidth(width / tableView.getColumns().size());
				}
			}
		});

		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
					Number newSceneHeight) {
				double height = newSceneHeight.doubleValue() - baslik.getHeight() - 25;
				tableView.setMinHeight(height);
				tableView.setMaxHeight(height);
			}
		});

		rootPanel.setCenter(tableView);
		bottomPaneYerlestir(width);

		parametreButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				parametreDialogAc();
			}
		});

		yeniButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialogAc(IslemTip.YENI);
			}
		});

		detayButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialogAc(IslemTip.DETAY);
			}
		});

		guncelleButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialogAc(IslemTip.GUNCELLE);
			}
		});

		ilkSayfaButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sayfaNumarasi = 1;
				listele();
			}
		});

		oncekiSayfaButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sayfaNumarasi--;
				listele();
			}
		});

		sonrakiSayfaButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sayfaNumarasi++;
				listele();
			}
		});

		sonSayfaButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sayfaNumarasi = toplamSayfaSayisi;
				listele();
			}
		});

		silButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean durum = messageUtil.confirmDialog("Uyarý", "Uyarý !!! ",
						"Seçmiþ olduðunuz kayýt silinecektir. \nÝþlemi yapmak istediðinizden emin misiniz ?");
				if (durum) {
					simpleService.sil(data);
					listele();
				}
				disableDurumDegistir(Boolean.TRUE);
				tableView.getSelectionModel().clearSelection();
			}
		});

		setPadding(new Insets(0, 0, 0, 0));
		getChildren().removeAll(getChildren());
		getChildren().add(rootPanel);
		listele();
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new DataTableSkin<T>(this);
	}

	public void addRow(T obje) {
		tableView.getItems().add(obje);
	}

	public void addAllRow(List<T> liste) {
		for (T obje : liste) {
			addRow(obje);
		}
	}

	public void parametreDialogAc() {
		parametreDialog = new Dialog<T>();
		parametreDialog.setTitle("Parametre");
		parametreGridPane.setHgap(10);
		parametreGridPane.setVgap(10);
		parametreGridPane.setPadding(new Insets(20, 150, 10, 10));

		int rowIndex = 0;
		for (EntityProperty entityProperty : entityProperties) {
			if (entityProperty.isParametreyeEklenebilir()) {
				try {
					Object val = PropertyUtils.getProperty(parametreData, entityProperty.getKolonAdi());
					parametreGridPane.add(new Label(entityProperty.getLabel()), 0, rowIndex);
					parametreGridPane.add(Arac.getNode(entityProperty, val), 1, rowIndex);
					rowIndex++;
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				}
			}
		}

		parametreDialog.getDialogPane().setContent(parametreGridPane);

		ButtonType aramaButtonType = new ButtonType("Arama", ButtonData.OK_DONE);
		parametreDialog.initOwner(scene.getWindow());
		parametreDialog.initModality(Modality.APPLICATION_MODAL);
		parametreDialog.getDialogPane().getButtonTypes().add(aramaButtonType);
		parametreDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		Node closeButton = parametreDialog.getDialogPane().lookupButton(ButtonType.CLOSE);
		closeButton.managedProperty().bind(closeButton.visibleProperty());
		closeButton.setVisible(false);
		parametreDialog.setResultConverter(dialogButton -> {
			if (dialogButton == aramaButtonType) {
				try {
					int i = 0;
					for (Node node : parametreGridPane.getChildren()) {
						if (i % 2 != 0) {
							String kolonAdi = node.getId();
							Object val = Arac.getNodeValue(node, entityProperties);
							PropertyUtils.setProperty(parametreData, kolonAdi, val);
						}
						i++;
					}
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				}
				return parametreData;
			}
			return null;
		});
		Optional<T> sonuc = parametreDialog.showAndWait();
		if (sonuc.isPresent()) {
			listele();
		}

	}

	public void dialogAc(IslemTip islemTip) {
		boolean disable = false;
		if (islemTip.equals(IslemTip.DETAY)) {
			disable = true;
		}

		detayDialog = new Dialog<T>();
		detayDialog.setTitle(islemTip.getTanim());

		detayGridPane.setHgap(10);
		detayGridPane.setVgap(10);
		detayGridPane.setPadding(new Insets(20, 150, 10, 10));
		detayGridPane.getChildren().removeAll(detayGridPane.getChildren());

		int rowIndex = 0;
		for (EntityProperty entityProperty : entityProperties) {
			if (Arac.isIslemTip(islemTip, entityProperty)) {
				try {
					if (data == null) {
						data = clazz.newInstance();
					}
					if (islemTip.equals(IslemTip.YENI)) {
						tableView.getSelectionModel().clearSelection();
						disableDurumDegistir(Boolean.TRUE);
						data = clazz.newInstance();
					}
					Object val = PropertyUtils.getProperty(data, entityProperty.getKolonAdi());
					detayGridPane.add(new Label(entityProperty.getLabel()), 0, rowIndex);
					Node node = Arac.getNode(entityProperty, val);
					node.setDisable(disable);
					detayGridPane.add(node, 1, rowIndex);
					rowIndex++;
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException
						| InstantiationException e) {
				}
			}
		}
		detayDialog.getDialogPane().setContent(detayGridPane);

		ButtonType kaydetButtonType = new ButtonType("Kaydet", ButtonData.OK_DONE);
		if (!islemTip.equals(IslemTip.DETAY)) {
			detayDialog.getDialogPane().getButtonTypes().add(kaydetButtonType);
		}

		detayDialog.initOwner(scene.getWindow());
		detayDialog.initModality(Modality.APPLICATION_MODAL);
		detayDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		Node closeButton = detayDialog.getDialogPane().lookupButton(ButtonType.CLOSE);
		closeButton.managedProperty().bind(closeButton.visibleProperty());
		closeButton.setVisible(false);
		detayDialog.setResultConverter(dialogButton -> {
			if (dialogButton == kaydetButtonType) {
				try {
					int i = 0;
					for (Node node : detayGridPane.getChildren()) {
						if (i % 2 != 0) {
							String kolonAdi = node.getId();
							Object val = Arac.getNodeValue(node, entityProperties);
							PropertyUtils.setProperty(data, kolonAdi, val);
						}
						i++;
					}
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				}
				return data;
			}
			disableDurumDegistir(Boolean.TRUE);
			tableView.getSelectionModel().clearSelection();
			return null;
		});
		Optional<T> sonuc = detayDialog.showAndWait();

		if (sonuc.isPresent() && !islemTip.equals(IslemTip.DETAY)) {
			simpleService.kaydet(data);
			listele();
			messageUtil.uyariGoster("Uyarý", "Kaydýnýz Baþarýlý Bir Þekilde Tamamlanmýþtýr");
		}

		data = null;
	}

	public void disableDurumDegistir(Boolean durum) {
		guncelleButton.setDisable(durum);
		silButton.setDisable(durum);
		detayButton.setDisable(durum);
	}

	public void listele() {
		QueryResult<T> queryResult = simpleService.getListe(parametreData, sayfaNumarasi,
				sayfadakiKayitSayisiCombo.getValue());

		toplamKayitSayisiLabel
				.setText(Sabit.GOSTERILECEK_KAYIT_SAYISI
						.replaceAll("&kayitSayisi&", "" + queryResult.getToplamKayitSayisi())
						.replace("&sayfaNumarasi&", "" + sayfaNumarasi)
						.replaceAll("&toplamSayfaNumarasý&", "" + queryResult.getToplamSayfaSayisi()));
		sayfalamaButtonDisableAyarla(queryResult.getToplamSayfaSayisi());
		tableView.getItems().removeAll(tableView.getItems());
		addAllRow(queryResult.getListe());
		toplamSayfaSayisi = queryResult.getToplamSayfaSayisi();
	}

	public void bottomPaneYerlestir(double width) {
		actionButtonBar.getButtons().add(parametreButton);
		actionButtonBar.getButtons().add(silButton);
		actionButtonBar.getButtons().add(detayButton);
		actionButtonBar.getButtons().add(guncelleButton);
		actionButtonBar.getButtons().add(yeniButton);
		Pane leftSpacer = new Pane();
		HBox.setHgrow(leftSpacer, Priority.SOMETIMES);
		Pane rightSpacer = new Pane();
		HBox.setHgrow(rightSpacer, Priority.SOMETIMES);
		ToolBar toolBar = new ToolBar();
		toolBar.setPrefWidth(width + 3);
		toolBar.setMinWidth(width + 3);
		toolBar.getItems().add(leftSpacer);
		toolBar.getItems().add(ilkSayfaButton);
		toolBar.getItems().add(oncekiSayfaButton);
		toolBar.getItems().add(sayfadakiKayitSayisiCombo);
		toolBar.getItems().add(toplamKayitSayisiLabel);
		toolBar.getItems().add(sonrakiSayfaButton);
		toolBar.getItems().add(sonSayfaButton);
		toolBar.getItems().add(rightSpacer);
		bottomPane.add(toolBar, 0, 0);
		bottomPane.add(actionButtonBar, 0, 1);
		rootPanel.setBottom(bottomPane);
	}

	public void sayfalamaButtonDisableAyarla(int toplamSayfaSayisi) {
		if (toplamSayfaSayisi == sayfaNumarasi && sayfaNumarasi == 1) {
			ilkSayfaButton.setDisable(true);
			oncekiSayfaButton.setDisable(true);
			sonrakiSayfaButton.setDisable(true);
			sonSayfaButton.setDisable(true);
		} else if (toplamSayfaSayisi == sayfaNumarasi && sayfaNumarasi != 1) {
			ilkSayfaButton.setDisable(false);
			oncekiSayfaButton.setDisable(false);
			sonrakiSayfaButton.setDisable(true);
			sonSayfaButton.setDisable(true);
		} else if (toplamSayfaSayisi > sayfaNumarasi && sayfaNumarasi == 1) {
			ilkSayfaButton.setDisable(true);
			oncekiSayfaButton.setDisable(true);
			sonrakiSayfaButton.setDisable(false);
			sonSayfaButton.setDisable(false);
		} else if (toplamSayfaSayisi > sayfaNumarasi && sayfaNumarasi != 1) {
			ilkSayfaButton.setDisable(false);
			oncekiSayfaButton.setDisable(false);
			sonrakiSayfaButton.setDisable(false);
			sonSayfaButton.setDisable(false);
		}
	}

}
