package tr.com.muzo.component.commandButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import tr.com.muzo.application.util.Arac;
import tr.com.muzo.component.base.ButtonActionEvent;
import tr.com.muzo.component.base.ButtonBase;
import tr.com.muzo.component.base.ComponentBase;
import tr.com.muzo.component.util.AwesomeDude;
import tr.com.muzo.component.util.ZorunluAlanKontrol;

public class CommandButton extends ButtonBase {

	public CommandButton() {
		super();
		initialize();
	}

	public CommandButton(String label) {
		super(label);
		initialize();
	}

	public CommandButton(String label, String icon) {
		super(label, icon);
		initialize();
	}

	public void initialize() {
		if (icon != null) {
			button = AwesomeDude.createIconButton(icon);
		}
		if (button == null) {
			button = new Button();
		}

		if (label != null) {
			button.setText(label);
		}

		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ZorunluAlanKontrol zorunluAlanKontrol = (ZorunluAlanKontrol) Arac.getBean(ZorunluAlanKontrol.class);
				boolean kontrolTamam = true;
				ObservableList<ComponentBase> kontrolEdilecekComponentList = FXCollections.observableArrayList();

				if(process != null){
					if (!process.equals("@this")) {
						if (process.equals("@scene")) {
							Scene scene = button.getScene();
							Pane root = (Pane) scene.getRoot();
							ObservableList<Node> children = root.getChildren();
							for (Node node : children) {
								if (node instanceof ComponentBase) {
									if (((ComponentBase) node).isRequired()) {
										kontrolEdilecekComponentList.add((ComponentBase) node);
									}
								} else if (node instanceof Pane) {
									kontrolEdilecekComponentList.addAll(getComponentList((Pane) node));
								}
							}
						} else {
							Scene scene = button.getScene();
							Pane root = (Pane) scene.getRoot();
							if (root.getId() != null && root.getId().equals(process)) {
								kontrolEdilecekComponentList.addAll(getComponentList((Pane) root));
							} else {
								Node donenNode = getPane(root, process);
								if (donenNode != null) {
									if (donenNode instanceof Pane) {
										kontrolEdilecekComponentList.addAll(getComponentList((Pane) donenNode));
									} else if (donenNode instanceof ComponentBase) {
										kontrolEdilecekComponentList.add((ComponentBase) donenNode);
									}

								}
							}

						}
					}

					kontrolTamam = zorunluAlanKontrol.kontrol(kontrolEdilecekComponentList);
				}
				
				if (kontrolTamam) {
					if (preAction != null) {
						button.fireEvent(new ButtonActionEvent(Arac.getPreAction()));
					}

					if (action != null) {
						button.fireEvent(new ButtonActionEvent(Arac.getLastAction()));
					}
				}
			}
		});

		getChildren().removeAll(getChildren());
		getChildren().add(button);

	}

	public ObservableList<ComponentBase> getComponentList(Pane pane) {
		ObservableList<ComponentBase> returnList = FXCollections.observableArrayList();
		ObservableList<Node> children = pane.getChildren();
		for (Node node : children) {
			if (node instanceof ComponentBase) {
				if (((ComponentBase) node).isRequired()) {
					returnList.add((ComponentBase) node);
				}
			} else if (node instanceof Pane) {
				returnList.addAll(getComponentList((Pane) node));
			}
		}
		return returnList;
	}

	public Node getPane(Pane root, String id) {
		ObservableList<Node> children = root.getChildren();
		for (Node node : children) {
			if (node instanceof Pane) {
				if (node.getId() != null && node.getId().equals(id)) {
					return (Pane) node;
				} else {
					Node donenNode = getPane((Pane) node, id);
					if (donenNode != null) {
						return donenNode;
					}
				}
			} else {
				if (node.getId() != null && node.getId().equals(id)) {
					return node;
				}
			}
		}
		return null;
	}

}
