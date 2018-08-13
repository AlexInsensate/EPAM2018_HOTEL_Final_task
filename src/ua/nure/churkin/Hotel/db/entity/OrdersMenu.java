package ua.nure.churkin.Hotel.db.entity;

public class OrdersMenu extends Entity {

	private static final long serialVersionUID = 586048288290682295L;

	private int userId;
	private int orderId;
	private int bill;
	private int statusOrder;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getStatusOrder() {
		return statusOrder;
	}

	public void setStatusOrder(int statusOrder) {
		this.statusOrder = statusOrder;
	}

	public int getBill() {
		return bill;
	}

	public void setBill(int bill) {
		this.bill = bill;
	}

	@Override
	public String toString() {
		return "OrdersMenu [userId=" + userId + ", orderId=" + orderId + ", bill="
				+ bill + ", statusOrder=" + statusOrder + ", getId()=" + getId() + "]";
	}

}
