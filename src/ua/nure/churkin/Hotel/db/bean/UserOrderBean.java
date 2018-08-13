package ua.nure.churkin.Hotel.db.bean;

import ua.nure.churkin.Hotel.db.entity.Entity;

/**
 * Provide records for virtual table:
 */
public class UserOrderBean extends Entity {

	private static final long serialVersionUID = -5654982557199337483L;

	private int orderId;
	private String userFirstName;
	private String userLastName;
	private String apartments;
	private int bill;
	private String status_order;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getStatus_order() {
		return status_order;
	}

	public void setStatus_order(String status_order) {
		this.status_order = status_order;
	}

	public int getBill() {
		return bill;
	}

	public void setBill(int bill) {
		this.bill = bill;
	}

	public String getApartments() {
		return apartments;
	}

	public void setApartments(String apartments) {
		this.apartments = apartments;
	}

	@Override
	public String toString() {
		return "UserOrderBean [orderId=" + orderId + ", userFirstName=" + userFirstName + ", userLastName="
				+ userLastName + ", apartments=" + apartments + ", bill=" + bill + ", status_order=" + status_order
				+ "]";
	}

}
