package ua.nure.churkin.Hotel.db.entity;

public class Seats extends Entity {

	private static final long serialVersionUID = 3593951391011249104L;

	private int number;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Seats [number=" + number + ", getId()=" + getId() + "]";

	}

}
