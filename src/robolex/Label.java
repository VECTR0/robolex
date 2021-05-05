package robolex;

import javafx.scene.text.Text;

public class Label extends Element{
	public Text t = new Text();
	public javafx.scene.control.Label l = new javafx.scene.control.Label();
	public Label(String name, Element e) {
		super(name, e);
		group.getChildren().add(t);
		group.getChildren().add(l);
	}

}
