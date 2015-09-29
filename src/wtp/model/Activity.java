package wtp.model;

import java.time.Duration;
import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface Activity {
	
	/**
	 * 
	 * @return duration of Activity in Minute
	 */
	public StringProperty getDescription();
	public ObjectProperty<Duration> getDuration();
	public ObjectProperty<LocalDateTime> getStart();
	public ObjectProperty<LocalDateTime> getEnd();
}
