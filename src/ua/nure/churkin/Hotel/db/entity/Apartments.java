package ua.nure.churkin.Hotel.db.entity;

public class Apartments extends Entity {
	
	private static final long serialVersionUID = -6245473077904896126L;
	private String title;
	private int seatId;
	private int categoryId;
	private int bill;
	private int statusId;
	

	public String getName() {
		return title;
	}
	public void setName(String title) {
		this.title = title;
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
	public int getBill() {
		return bill;
	}
	public void setBill(int bill) {
		this.bill = bill;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	@Override
	public String toString() {
		return "Apartments [name=" + title + ", seatId=" + seatId + ", categoryId=" + categoryId + ", bill=" + bill
				+ ", statusId=" + statusId + ", getId()=" + getId() + "]";
	}

}
