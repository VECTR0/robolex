package robolex;

import java.util.HashSet;
import java.util.Set;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import com.sun.deploy.uitoolkit.impl.fx.Utils;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath.Axis;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class MainController  {
	private static Stage stage;
	public static Scene mainScene;
	public static SubScene scene;
	public static Group subRoot;
	public static final Set<KeyCode> keysDown = new HashSet<>();
	public static AnimationTimer timer;
	static FirstPersonCamera cam;

	public static void initilize(Stage stage, Scene mainScene, SubScene scene, Group subRoot) {
		MainController.mainScene = mainScene;
		MainController.stage = stage;
		MainController.scene = scene;
		MainController.subRoot = subRoot;
		
		mainScene.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, (keyEvent -> {
			keysDown.add(keyEvent.getCode());
		}));
		mainScene.addEventHandler(javafx.scene.input.KeyEvent.KEY_RELEASED, (keyEvent -> {
			keysDown.remove(keyEvent.getCode());
		}));
		cam = new FirstPersonCamera(scene, mainScene);
	
		stage.show();
		Rotate r = new Rotate(0, Rotate.X_AXIS);
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				MainController.handle(now);
			}
		};
		timer.start();
	}

	public static void buildScene() {
		Environment.build();
		
		/*for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				Box s = new Box(5,5,5);
				s.setTranslateX(j * 5);
				s.setTranslateZ(i * 5);
				s.setTranslateY((Math.pow(i - 50, 2) - Math.pow(j - 50, 2)) / 100);
				double y = s.getTranslateY();
				y /= -10;
				if (y > 1)y = 1;
				if (y < -1)y = -1;
				Color c;
				if (y > 0)
					c = new Color(y, 1 - y, 0, 1);
				else
					c = new Color(0, 1 + y, -y, 1);
				s.setMaterial(new PhongMaterial(c));
				subRoot.getChildren().add(s);
			}
		}*/
		
	}


	public static void handle(long now) {
		cam.handle(now);
		Environment.tick(now);
	}
}