package wtp.model;

import java.time.Duration;
import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class DateActivity implements Activity {

	private StringProperty description;
	private ObjectProperty<LocalDateTime> start;
	private ObjectProperty<LocalDateTime> end;
	private ObjectProperty<Duration> duration;
	
	public DateActivity(String d, LocalDateTime s, LocalDateTime e) throws Exception{
		if(d==null || s==null || e == null){
			throw new Exception("Argument must not be null");
		}
		
		if(e.isBefore(s)){
			throw new Exception("Activity must end after it starts");
		}
		
		description = new SimpleStringProperty(d);
		start = new SimpleObjectProperty<LocalDateTime>(s);
		end = new SimpleObjectProperty<LocalDateTime>(e);
		
		duration = new SimpleObjectProperty<Duration>();
		duration.set(Duration.between(start.get(), end.get()));
		
		start.addListener(new ChangeListener<LocalDateTime>(){

			@Override
			public void changed(ObservableValue<? extends LocalDateTime> arg0, LocalDateTime arg1, LocalDateTime arg2) {
				Duration d = Duration.between(arg0.getValue(), end.get());
				duration.set(d);
				
			}
			
		});
		
		end.addListener(new ChangeListener<LocalDateTime>(){

			@Override
			public void changed(ObservableValue<? extends LocalDateTime> arg0, LocalDateTime arg1, LocalDateTime arg2) {
				Duration d = Duration.between(start.get(),arg0.getValue());
				duration.set(d);
				
			}
			
		});
	}
	
	@Override
	public ObjectProperty<Duration> getDuration() {

		return duration;
	}
	
	public ObjectProperty<LocalDateTime> getStart(){
		return start;
	}
	
	public ObjectProperty<LocalDateTime> getEnd(){
		return end;
	}
	
	public StringProperty getDescription(){
		return description;
	}
	
	

}
