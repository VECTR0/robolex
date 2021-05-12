package robolex;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.geometry.Point3D;

class ElementTest {

	@Test
	void test_translateX() {
		Element root = new Element("root");
		Element e = new Element("e", root);
		e.setTranslateX(21);
		assertEquals(21, e.getTranslateX());
	}

	@Test
	void test_rotateX() {
		Element root = new Element("root");
		Element e = new Element("e", root);
		e.setRotateX(37);
		assertEquals(37, e.getRotateX());
	}
	
	@Test
	void test_translateY() {
		Element root = new Element("root");
		Element e = new Element("e", root);
		e.setTranslateY(37);
		assertEquals(37, e.getTranslateY());
	}

	@Test
	void test_rotateY() {
		Element root = new Element("root");
		Element e = new Element("e", root);
		e.setRotateY(13);
		assertEquals(13, e.getRotateY());
	}
	
	@Test
	void test_translateZ() {
		Element root = new Element("root");
		Element e = new Element("e", root);
		e.setTranslateZ(37);
		assertEquals(37, e.getTranslateZ());
	}

	@Test
	void test_rotateZ() {
		Element root = new Element("root");
		Element e = new Element("e", root);
		e.setRotateZ(37);
		assertEquals(37, e.getRotateZ());
	}
	
	@Test
	void test_getWorldPos() {
		Element root = new Element("root");
		Element a = new Element("a", root);
		a.setTranslateX(3);
		a.setTranslateY(2);
		a.setTranslateZ(1);
		Element b = new Element("b", a);
		b.setTranslateX(1);
		b.setTranslateY(2);
		b.setTranslateZ(3);
		
		Point3D p = b.getWorldPosition();

		assertEquals(4, p.getX());
		assertEquals(4, p.getY());
		assertEquals(4, p.getZ());
	}

	@Test
	void test_copyWorldPos() {
		Element root = new Element("root");
		Element a = new Element("a", root);
		a.setTranslateX(3);
		a.setTranslateY(2);
		a.setTranslateZ(1);
		Element b = new Element("b", root);
		
		b.copyWorldPosition(a, true);
		
		assertEquals(3, b.getTranslateX());
		assertEquals(2, b.getTranslateY());
		assertEquals(1, b.getTranslateZ());
		
		b.setTranslateY(0);
		b.copyWorldPosition(a, false);
		
		assertEquals(3, b.getTranslateX());
		assertEquals(0, b.getTranslateY());
		assertEquals(1, b.getTranslateZ());
	}

	@Test
	void test_findInChildren() {
		Element root = new Element("root");
		Element a = new Element("a", root);
		Element b = new Element("b", root);

		Element f = root.findInChildren("b");
		Element g = root.findInChildren("c");
		
		assertEquals(b, f);
		assertNull(g);
	}

	@Test
	void test_moveForward() {
		Element root = new Element("root");
		Element a = new Element("a", root);
		
		a.setRotateY(90);
		a.moveForward(1);

		assertEquals(1, a.getTranslateX());
		assertEquals(0, a.getTranslateZ());
	}

	@Test
	void test_moveRight() {
		Element root = new Element("root");
		Element a = new Element("a", root);
		
		a.setRotateY(90);
		a.moveRight(1);

		assertEquals(0, Math.round(100 * a.getTranslateX()));
		assertEquals(-1, a.getTranslateZ());
	}
	
	@Test
	void test_fixAngle() {
		assertEquals(310, Element.fixAngle(6070));
		assertEquals(345, Element.fixAngle(345));
		assertEquals(-213, Element.fixAngle(-213));
		assertEquals(-333, Element.fixAngle(-2133));
	}
}
