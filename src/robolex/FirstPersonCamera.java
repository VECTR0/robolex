package robolex;

import java.util.HashSet;
import java.util.Set;

import com.sun.glass.events.KeyEvent;
import com.sun.org.apache.xml.internal.security.Init;

import javafx.scene.Camera;
import javafx.scene.DepthTest;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;

public class FirstPersonCamera {
	private PerspectiveCamera camera;
	private SubScene scene;
	private double x, y, z;
	private double yaw, pitch;
	private Rotate rx = new Rotate(0, Rotate.X_AXIS);
	private Rotate ry = new Rotate(0, Rotate.Y_AXIS);
	private boolean mousePressed = false;
	private double ox = 0, oy = 0;
	private double speed = 0.1;
	private Scene mainScene;
	
	public FirstPersonCamera(SubScene scene, Scene mainScene) {
		this.scene = scene;
		this.mainScene = mainScene;
		Initilize();
	}
	
	private void Initilize() {
		camera = new PerspectiveCamera(true);
        camera.setFieldOfView(90);
        camera.setNearClip(0.01);
        camera.setFarClip(1000);
        scene.setCamera(camera);
        camera.setDepthTest(DepthTest.ENABLE);
        reset();
        update();
        scene.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, (me -> {
        	mousePressed = true;
        	ox = me.getScreenX();
        	oy = me.getScreenY();
        }));
        
        scene.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, (me -> {
        	mousePressed = false;
        }));
        
        scene.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_DRAGGED, (me -> {
        	double deltaRotation = me.getScreenX() - ox;
        	double multiplier = 0.3;
        	ox = me.getScreenX();
        	yaw += deltaRotation * multiplier;
        	
        	deltaRotation = me.getScreenY() - oy;
        	oy = me.getScreenY();
        	pitch -= deltaRotation * multiplier;
        	if(pitch < -90)pitch = -90;
        	if(pitch > 90)pitch = 90;
        	update();
        }));
	}
	
	private void update() {
		camera.getTransforms().clear();
		camera.setTranslateX(x);
    	camera.setTranslateY(y);
    	camera.setTranslateZ(z);
		ry.setAngle(yaw);             
        rx.setAngle(pitch);
		camera.getTransforms().add(ry);
		camera.getTransforms().add(rx);
	}
	
	private void moveForward(double front) {
		x += Math.cos(ToRadians(yaw-90)) * front;
		z -= Math.sin(ToRadians(yaw-90)) * front;
	}
	
	private void moveRight(double right) {
		x += Math.cos(ToRadians(yaw)) * right;
		z -= Math.sin(ToRadians(yaw)) * right;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void handle(long now) {
		if(MainController.keysDown.contains(KeyCode.CONTROL))speed = 0.1;
		else speed = .01;
		if(MainController.keysDown.contains(KeyCode.W))moveForward(speed);
		if(MainController.keysDown.contains(KeyCode.A))moveRight(-speed);
		if(MainController.keysDown.contains(KeyCode.S))moveForward(-speed);
		if(MainController.keysDown.contains(KeyCode.D))moveRight(speed);
		if(MainController.keysDown.contains(KeyCode.SPACE))y-=speed;
		if(MainController.keysDown.contains(KeyCode.SHIFT))y+=speed;
		update();
	}
	
	public double ToRadians(double angle) {
		return angle / 180.0 * Math.PI;
	}
	
	public void reset() {
		x = -1;
		y = -1;
		z = -1;
	}

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public void setYaw(double yaw) {
		this.yaw = yaw;
	}
	
	public void setPitch(double pitch) {
		this.pitch = pitch;
	}
}
