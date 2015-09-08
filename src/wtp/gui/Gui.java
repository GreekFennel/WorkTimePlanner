package wtp.gui;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import wtp.io.ProjectReader;
import wtp.model.Activity;
import wtp.model.Project;

public class Gui extends Application {

	private double height = 600.0;
	private double width = 800.0;

	private Stage primaryStage;

	private Scene introScene;
	private Scene mainScene;
	private ProgressBar bar;
	private VBox center;
	private VBox activityView;
	private Project p;

	public void createIntroScene() {
		VBox vb = new VBox();
		introScene = new Scene(vb, width / 2, height / 2);
		vb.setAlignment(Pos.CENTER);
		Label welcome = new Label("Welcome to WorkTimePlanner!");
		welcome.setStyle("-fx-font-size:20px;");
		Region spacer = new Region();
		Button load = new Button("Load Project");
		
		load.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fc = new FileChooser();
				File f = fc.showOpenDialog(primaryStage);
				try {
					p = ProjectReader.read(f);
					showMainStage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		Separator line = new Separator(Orientation.HORIZONTAL);

		HBox nameBox = new HBox();
		Label nameLabel = new Label("Project Title");
		TextField nameField = new TextField();
		HBox.setHgrow(nameField, Priority.ALWAYS);
		nameBox.getChildren().addAll(nameLabel, nameField);

		HBox requiredBox = new HBox();
		Label requiredLabel = new Label("Required Hours");
		TextField requiredField = new TextField();
		HBox.setHgrow(requiredField, Priority.ALWAYS);
		requiredBox.getChildren().addAll(requiredLabel, requiredField);

		Button create = new Button("Create Project");

		vb.getChildren().addAll(welcome, spacer, load, line, nameBox, requiredBox, create);
		for (Node n : vb.getChildren()) {
			VBox.setMargin(n, new Insets(10));
		}

		create.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					p = new Project(nameField.getText(), Long.parseLong(requiredField.getText()));
					showMainStage();
				} catch (NumberFormatException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Creation of Project failed.");
					alert.setContentText("Required Hours needs to be a positive Number");
					alert.showAndWait();
				}

			}

		});

	}

	public void createMainScene() {
		BorderPane bp = new BorderPane();
		HBox top = new HBox();
		top.setPadding(new Insets(10, 12, 10, 12));
		top.setSpacing(10);
		Button load = new Button("Load Project");
		
		load.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fc = new FileChooser();
				File f = fc.showOpenDialog(primaryStage);
				try {
					p = ProjectReader.read(f);
					showMainStage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
		Button export = new Button("Export Project to Text");
		
		export.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fc = new FileChooser();
				File f = fc.showSaveDialog(primaryStage);
				try {
					PrintWriter fw = new PrintWriter(f);
					String[] s = p.toString().split("\n");
					for(String t : s){
						fw.println(t);
					}
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
		Button add = new Button("Add Activity");

		top.getChildren().addAll(load, export, add);
		bp.setTop(top);

		center = new VBox();
		Separator line = new Separator(Orientation.HORIZONTAL);
		center.setFillWidth(true);

		bar = new ProgressBar(0.0);
		bar.setPrefWidth(width);
		bar.setPrefHeight(50);

		Region spacer = new Region();
		VBox.setVgrow(spacer, Priority.ALWAYS);
		center.getChildren().addAll(line, spacer, bar);

		bp.setCenter(center);
		mainScene = new Scene(bp, width, height);

		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Popup createActivity = getPopUp();
				createActivity.show(primaryStage);

			}

		});
	}

	public Popup getPopUp() {
		VBox vb = new VBox();
		vb.setFillWidth(true);
		vb.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
		vb.setPadding(new Insets(10));
		Label label = new Label("Create new activity");
		HBox descriptionBox = new HBox();
		Label descriptionLabel = new Label("Description");
		TextField descriptionField = new TextField();
		HBox.setHgrow(descriptionField, Priority.ALWAYS);
		descriptionBox.getChildren().addAll(descriptionLabel, descriptionField);

		HBox durationBox = new HBox();
		Label durationLabel = new Label("Duration(min)");
		TextField durationField = new TextField();
		HBox.setHgrow(durationField, Priority.ALWAYS);
		durationBox.getChildren().addAll(durationLabel, durationField);

		HBox buttonBox = new HBox();
		Button save = new Button("Save");
		Button cancel = new Button("Cancel");
		buttonBox.getChildren().addAll(save, cancel);

		vb.getChildren().addAll(label, descriptionBox, durationBox, buttonBox);

		Popup popup = new Popup();
		popup.getContent().add(vb);
		save.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					Activity a = new Activity(descriptionField.getText(), Long.parseLong(durationField.getText()));
					p.addActivity(a);
					repaintActivities();
					popup.hide();
				} catch (NumberFormatException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Creation of Activity failed.");
					alert.setContentText("Duration needs to be a positive Number");
					alert.showAndWait();
				}

			}

		});

		cancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				popup.hide();

			}

		});

		return popup;
	}

	private void repaintActivities() {
		center.getChildren().remove(activityView);
		activityView = new VBox();
		activityView.setSpacing(10);
		activityView.setPadding(new Insets(10));
		activityView.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE,null,null)));
		HBox tBox = new HBox();
		Label des = new Label("Description");
		Label dur = new Label("Duration(Min)");
		Separator line = new Separator(Orientation.HORIZONTAL);
		tBox.getChildren().addAll(des,dur,line);
		activityView.getChildren().addAll(tBox, line);
		if (p != null) {
			List<Activity> al = p.getActivities();
			for (Activity a : al) {
				String[] text = a.toString().split(":");
				HBox textBox = new HBox();
				Label name = new Label(text[0]);
				Label minutes = new Label(text[1]);
				textBox.setSpacing(20);
				textBox.getChildren().addAll(name,minutes);
				activityView.getChildren().addAll(textBox);
			}
		}
		
		center.getChildren().add(1, activityView);

	}

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		this.primaryStage = arg0;
		createIntroScene();
		createMainScene();
		showIntroStage();

	}

	public void showIntroStage() {
		primaryStage.setTitle("Welcome");
		primaryStage.setScene(introScene);
		primaryStage.show();
	}

	public void showMainStage() {
		bar.progressProperty().bind(p.percentageProperty());
		repaintActivities();
		primaryStage.setTitle("WorkTimePlanner");
		primaryStage.setScene(mainScene);
		primaryStage.show();

	}

}
