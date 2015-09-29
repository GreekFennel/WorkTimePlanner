package wtp.gui;

import controller.SingleProjectController;
import javafx.application.Application;
import javafx.stage.Stage;
import wtp.model.Project;

public class WTPApp extends Application {

	@Override
	public void start(Stage arg0) throws Exception {
		Project p = new Project("ADS Oktober",40);
		SingleProjectController spc = new SingleProjectController(p,arg0);
		arg0.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

}
