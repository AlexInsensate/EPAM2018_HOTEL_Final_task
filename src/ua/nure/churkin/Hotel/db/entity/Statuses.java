package ua.nure.churkin.Hotel.db.entity;

public class Statuses extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5701923713911870473L;

	public String getStatuses_name() {
		return statuses_name;
	}

	public void setStatuses_name(String statuses_name) {
		this.statuses_name = statuses_name;
	}

	String statuses_name;

	@Override
	public String toString() {
		return "Statuses [statuses_name=" + statuses_name + ", getId()=" + getId() + "]";
	}

}
