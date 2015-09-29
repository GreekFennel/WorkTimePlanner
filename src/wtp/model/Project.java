package wtp.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Project {
	private String name;
	private ObjectProperty<Duration> required;
	private DoubleProperty percentageDone;
	private Set<Activity> activities;

	public Project(String name, long hours) {
		this.name = name;
		this.percentageDone = new SimpleDoubleProperty();
		this.percentageDone.set(0.0);
		this.required = new SimpleObjectProperty<Duration>();
		this.required.set(Duration.ofHours(hours));
		this.activities = new TreeSet<Activity>(new Comparator<Activity>() {

			@Override
			public int compare(Activity arg0, Activity arg1) {
				if (arg0 == null || arg1 == null) {
					return 0;
				} else if (arg0.getClass().equals(DurationActivity.class)) {
					return -1;
				} else if (arg0.getClass().equals(DateActivity.class) && arg1.getClass().equals(DateActivity.class)) {
					return ((DateActivity) arg0).getStart().get()
							.compareTo(((DateActivity) arg1).getStart().get());
				} else if(arg1.getClass().equals(DurationActivity.class)){
					return 1;
				}
				return 0;
			}

		});
	}

	public List<Activity> getActivities() {
		return Collections.unmodifiableList(new ArrayList<Activity>(activities));
	}

	public void addActivity(Activity a) {
		double d = percentageDone.get() * required.get().toMinutes() + a.getDuration().get().toMinutes();
		percentageDone.set(d / required.get().toMinutes());
		activities.add(a);
	}

	public ObjectProperty<Duration> requiredProperty() {
		return required;
	}

	public DoubleProperty percentageProperty() {
		return percentageDone;
	}

	public String toString() {
		String s = this.name + "\nRequired(hrs):" + required.get().toHours();
		for (Activity a : activities) {
			s += "\n" + a.toString();
		}
		return s;
	}

	public String getName() {
		return name;
	}
}
