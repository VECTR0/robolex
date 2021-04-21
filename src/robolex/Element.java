package robolex;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javafx.scene.Group;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

public class Element {
	private final List<Element> children;
	private Element parent;
	public Group group;
	private final Rotate rx, ry, rz;
	private final String name;

	public Element(String name) {
		this.name = name;
		Box box = new Box(.1, .1, .1);
		children = new Vector<Element>(); // thread safety
		group = new Group();
		group.getChildren().add(box);
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
		for (int i = 0; i < children.size(); i++) {
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
		rx.setAngle(angle);
	}

	public void setRotateY(double angle) {
		ry.setAngle(angle);
	}

	public void setRotateZ(double angle) {
		rz.setAngle(angle);
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

	@Override
	public String toString() {
		return toString(0);
	}

	public String toString(int ind) {
		StringBuilder sb = new StringBuilder(); // speed
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
}
