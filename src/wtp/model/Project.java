package wtp.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Project {
	private String name;
	private ObjectProperty<Duration> required;
	private DoubleProperty percentageDone;
	private List<Activity> activities;
	
	
	public Project(String name, long hours){
		this.name = name;
		this.percentageDone = new SimpleDoubleProperty();
		this.percentageDone.set(0.0);
		this.required = new SimpleObjectProperty<Duration>();
		this.required.set(Duration.ofHours(hours));
		this.activities = new ArrayList<Activity>();
	}
	
	public List<Activity> getActivities(){
		return Collections.unmodifiableList(activities);
	}

	
	public void addActivity(Activity a){
		double d = percentageDone.get() * required.get().toMinutes()+a.getDurationInMinutes();
		percentageDone.set(d/required.get().toMinutes());
		activities.add(a);
	}
	
	public ObjectProperty<Duration> requiredProperty(){
		return required;
	}

	public DoubleProperty percentageProperty(){
		return percentageDone;
	}
	
	public String toString(){
		String s = this.name + "\nRequired(hrs):"+required.get().toHours();
		for(Activity a : activities){
			s+="\n"+a.toString();
		}
		return s;
	}
}
