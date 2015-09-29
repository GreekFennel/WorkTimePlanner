package wtp.gui;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class CreateActivityForm extends VBox {
	
	private DateTimeFormatter mdf= DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	
	private Label descriptionLabel, durationLabel, startLabel, endLabel;
	private TextField descriptionField, durationField, startField, endField;
	private ToggleButton durationButton, datesButton;
	private final ToggleGroup group;
	private Button abortButton, createButton;
	
	public CreateActivityForm(){
		group = new ToggleGroup();
		Label header = new Label("Create Activity");
		
		descriptionLabel = new Label("Description");
		descriptionField = new TextField();
		HBox.setHgrow(descriptionField, Priority.ALWAYS);
		HBox description = new HBox(descriptionLabel,descriptionField);
		
		durationButton = new ToggleButton();
		durationLabel = new Label("Duration (hh:mm)");
		durationField = new TextField("01:00");
		HBox.setHgrow(durationField, Priority.ALWAYS);
		HBox duration = new HBox(durationButton, durationLabel,durationField);
		
		datesButton = new ToggleButton();
		startLabel = new Label ("Start (dd.MM.yyyy hh:mm");
		LocalDateTime now = LocalDateTime.now();
		startField = new TextField(mdf.format(now));
		HBox.setHgrow(startField, Priority.ALWAYS);
		HBox start = new HBox(datesButton,startLabel,startField);
		now = now.plusHours(1);
		
		Region bSpacer = new Region();
		bSpacer.prefWidthProperty().bind(datesButton.widthProperty());
		bSpacer.prefHeightProperty().bind(datesButton.heightProperty());
		endLabel = new Label("End (dd.MM.yyyy hh:mm");
		endField = new TextField(mdf.format(now));
		HBox.setHgrow(endField, Priority.ALWAYS);
		HBox end = new HBox(bSpacer,endLabel,endField);
		VBox dates = new VBox(start,end);
		
		durationButton.setToggleGroup(group);
		datesButton.setToggleGroup(group);
		group.selectToggle(durationButton);
		
		
		abortButton = new Button("abort");
		createButton = new Button ("create");
		
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		HBox buttons = new HBox(spacer,abortButton,createButton);
		
		this.getChildren().addAll(header,description,duration,dates,buttons);
	}
	
	public String getDescription(){
		return this.descriptionField.getText();
	}
	
	public Duration getDuration(){
		String[] d = durationField.getText().split(":");
		long h = Long.parseLong(d[0]);
		long m = Long.parseLong(d[1]);
		Duration du = Duration.ofHours(h);
		return du.plusMinutes(m);
	}
	
	public LocalDateTime getStart(){
		return LocalDateTime.from(mdf.parse(startField.getText()));
	}
	
	public LocalDateTime getEnd(){
		return LocalDateTime.from(mdf.parse(endField.getText()));
	}
	
	public boolean isDurationMode(){
		return group.selectedToggleProperty().get().equals(durationButton);
	}
	
	public void setAbortAction(EventHandler<ActionEvent> eh){
		abortButton.setOnAction(eh);
	}
	
	public void setCreateAction(EventHandler<ActionEvent> eh){
		createButton.setOnAction(eh);
	}
	
	
}
