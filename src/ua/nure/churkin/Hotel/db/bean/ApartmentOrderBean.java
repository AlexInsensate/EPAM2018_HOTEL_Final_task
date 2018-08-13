package ua.nure.churkin.Hotel.db.bean;

import ua.nure.churkin.Hotel.db.entity.Entity;

public class ApartmentOrderBean  extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3316920413552832299L;
	private String title;
	private int seats;
	private String category_name;
	private int bill;
	private String statuses_name;
	
	
	
	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public int getSeats() {
		return seats;
	}



	public void setSeats(int seats) {
		this.seats = seats;
	}



	public String getCategory_name() {
		return category_name;
	}



	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}



	public int getBill() {
		return bill;
	}



	public void setBill(int bill) {
		this.bill = bill;
	}



	public String getStatuses_name() {
		return statuses_name;
	}



	public void setStatuses_name(String statuses_name) {
		this.statuses_name = statuses_name;
	}



	@Override
	public String toString() {
		return "ApartmentOrderBean [title=" + title + ", seats=" + seats + ", category_name=" + category_name
				+ ", bill=" + bill + ", statuses_name=" + statuses_name + "]";
	}


}
