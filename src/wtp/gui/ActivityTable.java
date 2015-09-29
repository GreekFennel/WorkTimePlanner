package wtp.gui;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import wtp.model.Activity;

public class ActivityTable extends TableView<Activity> {

	DateTimeFormatter mdf= DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	
	private Activity[] activities;

	private TableColumn<Activity, String> descriptionColumn;
	private TableColumn<Activity, LocalDateTime> startColumn;
	private TableColumn<Activity, LocalDateTime> endColumn;
	private TableColumn<Activity, Duration> durationColumn;

	private void initColumns() {
		descriptionColumn = new TableColumn<Activity, String>("Description");
		startColumn = new TableColumn<Activity, LocalDateTime>("Started");
		endColumn = new TableColumn<Activity, LocalDateTime>("Finished");
		durationColumn = new TableColumn<Activity,Duration>("Duration");

		// Custom rendering of the table cell.
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescription());
		descriptionColumn.setCellFactory(column -> {
			return new TableCell<Activity, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					
					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						setText(item);
					}
				}
			};
		});

		// Custom rendering of the table cell.
		startColumn.setCellValueFactory(cellData -> cellData.getValue().getStart());
		startColumn.setCellFactory(column -> {
			return new TableCell<Activity, LocalDateTime>() {
				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);
					
					if(item == null || empty){
						setText("");
						setStyle("");
					} else{
						setText(mdf.format(item));
					}
				}
			};
		});
		
		endColumn.setCellValueFactory(cellData -> cellData.getValue().getEnd());
		endColumn.setCellFactory(column -> {
			return new TableCell<Activity, LocalDateTime>() {
				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);
					
					if(item == null || empty){
						setText("");
						setStyle("");
					} else{
						setText(mdf.format(item));
					}
				}
			};
		});
		

		durationColumn.setCellValueFactory(cellData -> cellData.getValue().getDuration());
		durationColumn.setCellFactory(column -> {
			return new TableCell<Activity, Duration>() {
				@Override
				protected void updateItem(Duration item, boolean empty) {
					super.updateItem(item, empty);
					
					if(item == null || empty){
						setText("");
						setStyle("");
					} else{
						setText(item.toHours()+":"+(item.toMinutes()%60));
					}
				}
			};
		});
		
		this.getColumns().addAll(descriptionColumn, startColumn, endColumn, durationColumn);
		this.setEditable(false);

	}

	public ActivityTable() {
		activities = new Activity[0];
		initColumns();
		updateTable();
	}

	public ActivityTable(Collection<Activity> as) {
		activities = (Activity[]) (as.toArray());
		initColumns();
		updateTable();
	}

	private void updateTable() {
		this.getItems().removeAll(this.getItems());
		ObservableList<Activity> data = FXCollections.observableArrayList(activities);
		this.getItems().addAll(data);
	}
	
	public void update(Collection<Activity> as){
		activities = new Activity[as.size()];
		int i = 0;
		for(Activity a : as){
			activities[i] = a;
			i++;
		}
		updateTable();
	}
	
	public Activity getSelected(){
		return this.getSelected();
	}

}
