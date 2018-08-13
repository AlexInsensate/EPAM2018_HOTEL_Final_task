package ua.nure.churkin.Hotel.db.entity;

public class Category extends Entity {

	private static final long serialVersionUID = 2386302708905518585L;

	private String category_name;



	public String getCategory_name() {
		return category_name;
	}



	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}



	@Override
	public String toString() {
		return "Category [category_name=" + category_name + ", getId()=" + getId() + "]";
	}

}
