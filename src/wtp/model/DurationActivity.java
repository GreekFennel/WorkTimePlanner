package wtp.model;

import java.time.Duration;
import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DurationActivity implements Activity {
	
	private StringProperty description;
	private ObjectProperty<Duration> duration;
	
	public DurationActivity(String description, Duration d){
		this.description = new SimpleStringProperty(description);
		duration = new SimpleObjectProperty<Duration>(this,"duration");
		duration.set(d);
	}
	
	@Override
	public ObjectProperty<Duration> getDuration(){
		return duration;
	}
	
	public ObjectProperty<Duration> durationProperty(){
		return duration;
	}
	
	public String toString(){
		return this.description.get()+":"+duration.get().toMinutes();
	}
	
	public StringProperty getDescription(){
		return this.description;
	}

	@Override
	public ObjectProperty<LocalDateTime> getStart() {
		return null;
	}

	@Override
	public ObjectProperty<LocalDateTime> getEnd() {
		return null;
	}

}
