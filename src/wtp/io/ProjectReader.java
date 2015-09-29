package wtp.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import wtp.model.Activity;
import wtp.model.DurationActivity;
import wtp.model.Project;

public class ProjectReader {

	public static Project read(File f){
		Project p = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String s;
			int state = 0;
			String name = null;
			long required = 0;
			List<Activity> al = new LinkedList<Activity>();
			while((s = br.readLine())!=null){
				switch(state){
				case 0:
					name = s;
					state++;
					break;
				case 1:
					try {
						required = Long.parseLong(s.split(":")[1]);
						state++;
					} catch (Exception e) {
						e.printStackTrace();
						state = 3;
					}
					break;
				case 2:
					try {
						String de = s.split(":")[0];
						long d = Long.parseLong(s.split(":")[1]);
						al.add(new DurationActivity(de,Duration.ofMinutes(d)));
					} catch (Exception e) {
						e.printStackTrace();
						state = 3;
					}
				}
			}
			p = new Project(name, required);
			for(Activity a : al){
				p.addActivity(a);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
}
