package robolex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

public class Element {
	private final List<Element> children;
	protected Element parent;
	public Group group;
	private final Rotate rx, ry, rz;
	public boolean limitedRotation = false;
	public double minRotX, maxRotX, minRotY, maxRotY, minRotZ, maxRotZ;
	private final String name;

	public Element(String name) {
		this.name = name;
		Box box = new Box(.1, .1, .1);
		children = Collections.synchronizedList(new ArrayList<>());
		group = new Group();
		//group.getChildren().add(box);
		rx = new Rotate(0, Rotate.X_AXIS);
		ry = new Rotate(0, Rotate.Y_AXIS);
		rz = new Rotate(0, Rotate.Z_AXIS);
		group.getTransforms().add(rz);
		group.getTransforms().add(ry);
		group.getTransforms().add(rx);
	}

	public Element(String name, Element e) {
		this(name);
		setParent(e);
	}

	public Element(String name, Group g) {
		this(name);
		g.getChildren().add(group);
	}

	public void add(Element e) {
		children.add(e);
		group.getChildren().add(e.group);
	}

	public void tick(long now) {
		for (int i = 0; i < children.size(); i++)
			children.get(i).tick(now);
	}

	public Element findInChildren(String name) {
		if(this.name == name) return this;
		for (int i = 0; i < children.size(); i++) {//foreach  agregacyjne w kolecjach stream
			Element el = children.get(i).findInChildren(name);
			if(el != null) return el;
		}
		return null;
	}
	
	public void setParent(Element parent) {
		this.parent = parent;
		parent.group.getChildren().add(group);
		parent.children.add(this);
	}

	public Element getParent() {
		return parent;
	}

	public void setTranslateX(double value) {
		group.setTranslateX(value);
	}

	public void setTranslateY(double value) {
		group.setTranslateY(value);
	}

	public void setTranslateZ(double value) {
		group.setTranslateZ(value);
	}

	public double getTranslateX() {
		return group.getTranslateX();
	}

	public double getTranslateY() {
		return group.getTranslateY();
	}

	public double getTranslateZ() {
		return group.getTranslateZ();
	}

	public void setRotateX(double angle) { 
		if(limitedRotation && (fixAngle(angle) < minRotX || fixAngle(angle) > maxRotX))return;
		rx.setAngle(fixAngle(angle));
	}

	public void setRotateY(double angle) {
		if(limitedRotation && (angle < minRotY || angle > maxRotY))return;
		ry.setAngle(fixAngle(angle));
	}

	public void setRotateZ(double angle) {
		if(limitedRotation && (angle < minRotZ || angle > maxRotZ))return;
		rz.setAngle(fixAngle(angle));
	}

	public double getRotateX() {
		return rx.getAngle();
	}

	public double getRotateY() {
		return ry.getAngle();
	}

	public double getRotateZ() {
		return rz.getAngle();
	}
	
	public void copyWorldPosition(Element a, boolean copyY) {
		Point3D local = Point3D.ZERO;
		local = a.group.localToScene(local);
		local = parent.group.sceneToLocal(local);
		setTranslateX(local.getX());
		if(copyY)setTranslateY(local.getY());
		setTranslateZ(local.getZ());
	}
	
	public Point3D getWorldPosition() {
		Point3D local = Point3D.ZERO;
		return group.localToScene(local);
	}
	
	public void moveForward(double front) {
		setTranslateX(getTranslateX() + Math.cos(Math.toRadians(getRotateY()-90)) * front );
		setTranslateZ(getTranslateZ() - Math.sin(Math.toRadians(getRotateY()-90)) * front);
	}
	
	public void moveRight(double right) {
		setTranslateX(getTranslateX() + Math.cos(Math.toRadians(getRotateY())) * right);
		setTranslateZ(getTranslateZ() - Math.sin(Math.toRadians(getRotateY())) * right);
	}

	@Override
	public String toString() {
		return toString(0);
	}

	public String toString(int ind) {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append('[');
		sb.append(children.size());
		if (children.size() != 0) {
			sb.append("]{\n");
			for (int i = 0; i < children.size(); i++) {
				for (int j = 0; j <= ind; j++)
					sb.append("  ");
				sb.append(children.get(i).toString(ind + 1));
			}
			for (int j = 0; j < ind; j++)
				sb.append("  ");
			sb.append("}\n"); 
		}else {
			sb.append("]{}\n");
		}
		return sb.toString();
	}
	
	public static double fixAngle(double a) {
		return (a) % 360.0;
	}
}
