package robolex;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class FXMLController {
	@FXML
	public Slider simulationSpeedSlider;

	public void initilize() {
		simulationSpeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			//Envit.box.setRotate((double)newValue);
		});
		//MainController.box.setRotate((double)simulationSpeedSlider.getValue());
	}

	public void runSimulationButtonAction(Event event) {
		System.out.println("Pressed runSimulationButton");
	}

	public void runEditorButtonAction(Event event) {
		System.out.println("Pressed runEditorButton");
	}

	public void restartCamera(Event event) {
		MainController.cam.reset();
	}
	
	public void restartRobot(Event event) {
		//Robot.restartRobotPosition();
	}
	
	public void pauseRobot(Event event) {
	/*	if(MainController.robotPaused == 0){
			MainController.robotPaused = 1;
			pauseRobotButton.setText("Pause Robot");
		}
		else{
			MainController.robotPaused = 0;
			pauseRobotButton.setText("Start Robot");
		}*/
	}
}
