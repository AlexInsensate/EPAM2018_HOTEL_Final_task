package ua.nure.churkin.Hotel.db.entity;

import java.sql.Date;

public class OrderSelection extends Entity {

	private static final long serialVersionUID = 5692708766041889396L;

	private int userId;
	private int seatId;
	private int categoryId;
	private Date arrival;
	private Date departure;
	private int apartmentId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Date getArrival() {
		return arrival;
	}

	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}

	public Date getDeparture() {
		return departure;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public int getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(int apartmentId) {
		this.apartmentId = apartmentId;
	}

	@Override
	public String toString() {
		return "OrderSelection [userId=" + userId + ", seatId=" + seatId + ", categoryId=" + categoryId + ", arrival="
				+ arrival + ", departure=" + departure + ", apartmentId=" + apartmentId + ", getId()=" + getId() + "]";
	}

}
