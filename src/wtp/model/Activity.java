package wtp.model;

import java.time.Duration;
import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Activity{
	
	private String description;
	private ObjectProperty<Duration> duration;
	private LocalDate date;
	
	public Activity(String description, long minutes, LocalDate date){
		this.description = description;
		duration = new SimpleObjectProperty<Duration>(this,"duration");
		duration.set(Duration.ofMinutes(minutes));
		this.date = date;
	}
	
	public long getDurationInMinutes(){
		return duration.get().toMinutes();
	}
	
	public ObjectProperty<Duration> durationProperty(){
		return duration;
	}
	
	public String toString(){
		return this.date.toString()+":"+this.description+":"+duration.get().toMinutes();
	}
    public LocalDate getDate(){
        return this.date;
    }
}
