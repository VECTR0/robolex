package robolex;

import javax.swing.event.ChangeEvent;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class FXMLController {

	public MainController mainController;
	@FXML
	public Slider simulationSpeedSlider;

	public void initilize() {
		simulationSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			mainController.box.setRotate((double)newValue);
		});
		mainController.box.setRotate((double)simulationSpeedSlider.getValue());
	}

	public void runSimulationButtonAction(Event event) {
		System.out.println("Pressed runSimulationButton");

	}

	public void runEditorButtonAction(Event event) {
		System.out.println("Pressed runEditorButton");
	}

	public void restartCamera(Event event) {
		mainController.cam.reset();
	}
}
