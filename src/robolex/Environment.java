package robolex;

import com.sun.scenario.effect.Color4f;
import com.sun.scenario.effect.light.SpotLight;

import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Environment{
	public static Element center;
	public static Robot robot;
	public static boolean paused = false;
	public static AmbientLight ambient;
	
	public static void build() {		
		center = new Element("WorldCenter", MainController.subRoot);

		Box ground = new Box(10, 1, 10);
		ground.setTranslateY(0.5);
		PhongMaterial mat = new PhongMaterial(Color.GREENYELLOW);
		mat.setSpecularPower(1);
		ground.setMaterial(mat);
		//center.group.getChildren().add(ground);
		
		Element test = new Element("Test", center);
		test.setTranslateX(2);
		
		Target t = new Target("Target", center);
		
		
		robot = new Robot("Robot", test);
		robot.setTranslateX(-2);
		robot.setTranslateY(-.5);
		System.out.println(center);
		
		PointLight light = new PointLight(new Color(.3, 0.3, 0, 0.1));
		light.setTranslateY(-3);
		light.setTranslateZ(1);
		robot.group.getChildren().add(light);
		ambient = new AmbientLight(Color.DARKGREY);
		center.group.getChildren().add(ambient);
		
	/*	Label l = new Label("Label1", center);
		l.t.setFont(new Font(30));
		l.t.setText("00:00");
		l.l.setText("XD");
		l.l.setFont(new Font(20));
		l.setTranslateY(-2);*/
		
		
		
	}	
	
	private static double testValue = 0, testValueB = 0, testValueC = .002;
	
	public static void tick(long now) {
		if(paused)return;
		center.tick(now);
		//center.findInChildren("Test").setRotateY(testValue*.2);
		ambient.setColor(new Color(clamp(testValueB, 0, 1)/2, 0.2, clamp(1-testValueB, 0, 1)/2, 0));
		if(testValueB <= 0 || testValueB >= 1)testValueC = -testValueC;
		testValueB += testValueC;
		testValue += 1;
		
	}
	
	public static double clamp(double x, double min, double max) {
		return x > max ? max : x < min ? min : x;
	}
}
