

import java.util.Comparator;

public class Event{
	int ID;
	int count;
	public Event(int ID, int count){
		this.ID = ID;
		this.count = count;
	}
	/**
	 * return a comparator for sort event with the ID.
	 * @return the comparator specified to sort event based on the ID in a increasing way
	 */
	public Comparator<Event> getComparator(){
		return new Comparator<Event>(){
			public int compare(Event e1, Event e2){
				return e1.ID - e2.ID;
			}
		};
	}
}