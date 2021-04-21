package robolex;

import com.sun.scenario.effect.Color4f;
import com.sun.scenario.effect.light.SpotLight;

import javafx.scene.AmbientLight;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Environment{
	public static Element center;
	public static Robot robot;
	public static boolean paused = false;
	
	public static void build() {		
		center = new Element("WorldCenter", MainController.subRoot);

		Box ground = new Box(10, 1, 10);
		ground.setTranslateY(0.5);
		PhongMaterial mat = new PhongMaterial(Color.GREENYELLOW);
		mat.setSpecularPower(1);
		ground.setMaterial(mat);
		center.group.getChildren().add(ground);
		
		Element test = new Element("Test", center);
		
		robot = new Robot("Robot", test);
		robot.setTranslateX(-2);
		robot.setTranslateY(-.5);
		System.out.println(center);
		System.out.println(" ");
		System.out.println(center.findInChildren("ArmRF"));
		
		PointLight light = new PointLight(new Color(.3, 0.3, 0, 0.1));
		light.setTranslateY(-3);
		light.setTranslateZ(1);
		robot.group.getChildren().add(light);
		AmbientLight ambient = new AmbientLight(Color.DARKGREY);
		center.group.getChildren().add(ambient);
	}
	
	private static double testValue = 0;
	
	public static void tick(long now) {
		if(paused)return;
		center.tick(now);
		center.findInChildren("Test").setRotateY(testValue*.2);
		testValue += 1;
	}
}
