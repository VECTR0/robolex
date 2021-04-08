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
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class MainController extends AnimationTimer {
	private final Stage stage;
	private Scene mainScene;
	private final SubScene scene;
	private final Group subRoot;
	public static final Set<KeyCode> keysDown = new HashSet<>();
	
	public Sphere sphere;
	double vx=0, vy=0, vz=0;
	public Box box;
	FirstPersonCamera cam;
	

	public MainController(Stage stage, Scene mainScene, SubScene scene, Group subRoot) {
		this.mainScene = mainScene;
		this.stage = stage;
		this.scene = scene;
		this.subRoot = subRoot;

		mainScene.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, (keyEvent -> {
			keysDown.add(keyEvent.getCode());
		}));
		mainScene.addEventHandler(javafx.scene.input.KeyEvent.KEY_RELEASED, (keyEvent -> {
			keysDown.remove(keyEvent.getCode());
		}));
		cam = new FirstPersonCamera(scene, mainScene, this);

		stage.show();
	}

	public void buildScene() {
		sphere = new Sphere(5);
		
		sphere.setTranslateX(0);
		sphere.setTranslateY(-10);
		subRoot.getChildren().add(sphere);
		
		box = new Box(330, 10, 20);
		box.setRotate(-33);
		box.setTranslateZ(3);
		box.getTransforms().add(new Rotate(120, Rotate.Y_AXIS));
		box.getTransforms().add(new Rotate(120, Rotate.X_AXIS));
		subRoot.getChildren().add(box);
		for (int i = 0; i < 100; i++) {
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
		}
	}

	@Override
	public void handle(long now) {
		cam.handle(now);
		Point3D dist = new Point3D(cam.getX() - sphere.getTranslateX(), cam.getY() - sphere.getTranslateY() + 20, cam.getZ() - sphere.getTranslateZ());
		dist.normalize();
		dist = dist.multiply(0.001);
		vx = vx * 0.99 + dist.getX();
		vy = vy * 0.99 + dist.getY();
		vz = vz * 0.99 + dist.getZ();
		double color = Math.sqrt(vx*vx+vy*vy+vz*vz)/2.0;
		if(color > 1)color = 1;
		sphere.setMaterial(new PhongMaterial(new Color(color, color > 0.5 ? 0 : 0.5 - color/2, 1-color, 1)));
		sphere.setTranslateX(sphere.getTranslateX() + vx);
		sphere.setTranslateY(sphere.getTranslateY() + vy);
		sphere.setTranslateZ(sphere.getTranslateZ() + vz);
	}
}