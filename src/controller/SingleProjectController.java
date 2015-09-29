package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.Duration;
import java.time.LocalDateTime;

import wtp.gui.CreateActivityForm;
import wtp.gui.ProjectDetailed;
import wtp.model.Activity;
import wtp.model.DateActivity;
import wtp.model.DurationActivity;
import wtp.model.Project;

public class SingleProjectController {

	private Scene pScene, cScene;
	private Stage s;

	private ProjectDetailed pd;

	private Project p;

	public SingleProjectController(Project p, Stage s) {
		this.p = p;
		this.s = s;
		this.pd = new ProjectDetailed(p.getName());
		pd.updateTable(p.getActivities());
		pScene = new Scene(pd, s.getWidth(), s.getHeight());
		s.setScene(pScene);

		pd.setAddAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				final CreateActivityForm caf = new CreateActivityForm();
				caf.setAbortAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						s.setScene(pScene);
					}

				});

				caf.setCreateAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						Activity a = null;
						try {
							String n = caf.getDescription();
							if (caf.isDurationMode()) {
								Duration d = caf.getDuration();
								a = new DurationActivity(n, d);
							} else {
								LocalDateTime s = caf.getStart();
								LocalDateTime e = caf.getEnd();
								a = new DateActivity(n, s, e);
							}
							p.addActivity(a);
							pd.updateTable(p.getActivities());
							s.setScene(pScene);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				});
				cScene = new Scene(caf, pScene.getWidth(), pScene.getHeight());
				s.setScene(cScene);

			}

		});

		pd.bindProgressProperty(p.percentageProperty());

	}
}
