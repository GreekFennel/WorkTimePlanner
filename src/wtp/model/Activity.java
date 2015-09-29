package wtp.model;

import java.time.Duration;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Activity {
	
	private String description;
	private ObjectProperty<Duration> duration;
	
	public Activity(String description, long minutes){
		this.description = description;
		duration = new SimpleObjectProperty<Duration>(this,"duration");
		duration.set(Duration.ofMinutes(minutes));
	}
	
	public long getDurationInMinutes(){
		return duration.get().toMinutes();
	}
	
	public ObjectProperty<Duration> durationProperty(){
		return duration;
	}
	
	public String toString(){
		return this.description+":"+duration.get().toMinutes();
	}
}
