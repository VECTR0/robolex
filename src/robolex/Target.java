package robolex;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Target extends Element {

	public Target(String name, Element e) {
		super(name, e);
		
		Sphere s = new Sphere(0.1f);
		s.setMaterial(new PhongMaterial(Color.RED));
		group.getChildren().add(s);
	}
	
	@Override
	public void tick(long now) {
		double speed;
		if(MainController.keysDown.contains(KeyCode.CONTROL))speed = 0.02;
		else speed = .005;
		if(MainController.keysDown.contains(KeyCode.I))setTranslateZ(getTranslateZ() + speed);
		if(MainController.keysDown.contains(KeyCode.J))setTranslateX(getTranslateX() - speed);
		if(MainController.keysDown.contains(KeyCode.K))setTranslateZ(getTranslateZ() - speed);
		if(MainController.keysDown.contains(KeyCode.L))setTranslateX(getTranslateX() + speed);
		if(MainController.keysDown.contains(KeyCode.U))setTranslateY(getTranslateY() + speed);
		if(MainController.keysDown.contains(KeyCode.O))setTranslateY(getTranslateY() - speed);
	}
}
