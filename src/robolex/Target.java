package robolex;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Target extends Element {
	public static Target LF, LB, RF, RB;
	
	public boolean humanControlled = false;
	
	public Target(String name, Element e, Color c) {
		super(name, e);
		Sphere s = new Sphere(0.1f);
		s.setMaterial(new PhongMaterial(c));
		 group.getChildren().add(s);
	}
	
	@Override
	public void tick(long now) {
		if(!humanControlled)return;
		double speed;
		if(MainController.keysDown.contains(KeyCode.CONTROL))speed = 0.02;
		else speed = .005;
		if(MainController.keysDown.contains(KeyCode.R))setTranslateZ(getTranslateZ() + speed);
		if(MainController.keysDown.contains(KeyCode.T))setTranslateX(getTranslateX() - speed);
	}
}
