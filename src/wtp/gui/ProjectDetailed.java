package wtp.gui;

import java.util.Collection;

import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import wtp.model.Activity;

public class ProjectDetailed extends VBox {
	private Label description;
	private ProgressBar progress;
	private Label activityLabel;
	
	private ActivityTable table;
	
	private Button addButton, exportButton;
	
	public ProjectDetailed(String project){
		description = new Label(project);
		activityLabel = new Label("Activities");
		progress = new ProgressBar();
		progress.prefWidthProperty().bind(this.widthProperty());
		HBox.setHgrow(progress, Priority.ALWAYS);
		HBox pBox = new HBox(progress);
		table = new ActivityTable();
		
		addButton = new Button("Add Activity");
		exportButton = new Button("Export");
		Region spacer= new Region();
		HBox.setHgrow(spacer,Priority.ALWAYS);
		HBox buttons = new HBox(spacer,addButton,exportButton);
		Separator s = new Separator(Orientation.HORIZONTAL);
		this.getChildren().addAll(description,pBox,s,activityLabel,table,buttons);
		
	}
	
	public void updateTable(Collection<Activity> as){
		table.update(as);
	}
	
	public void bindProgressProperty(DoubleProperty dp){
		progress.progressProperty().bind(dp);
	}
	
	public void setAddAction(EventHandler<ActionEvent> eh){
		addButton.setOnAction(eh);
	}
	
	public void setExportAction(EventHandler<ActionEvent> eh){
		exportButton.setOnAction(eh);
	}
}
