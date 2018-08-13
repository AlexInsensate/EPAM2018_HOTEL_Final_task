package ua.nure.churkin.Hotel.db;

/**
 * Holder for fields names of DB tables and beans.
 */
public final class Fields {

	// entities
	public static final String ENTITY_ID = "id";

	public static final String USER_LOGIN = "login";
	public static final String USER_PASSWORD = "password";
	public static final String USER_FIRST_NAME = "first_name";
	public static final String USER_LAST_NAME = "last_name";
	public static final String USER_ROLE_ID = "role_id";

	public static final String ORDER_SELECTION_USER_ID = "user_id";
	public static final String ORDER_SELECTION_SEAT_ID = "seat_id";
	public static final String ORDER_SELECTION_CATEGORY_ID = "category_id";
	public static final String ORDER_SELECTION_ARRIVAL = "arrival";
	public static final String ORDER_SELECTION_DEPARTURE = "departure";
	public static final String ORDER_SELECTION_APARTMENT_ID = "apartment_id";
	
	public static final String CATEGORY_NAME = "category_name";

	public static final String STATUSE_NAME = "statuses_name";

	//public static final String ORDERS_STATUSE_NAME = "name";

	public static final String SEAT_NUMBER = "number";

	public static final String APARTMENT_NAME = "title";
	public static final String APARTMENT_SEAT_ID = "seat_id";
	public static final String APARTMENT_CATEGORY_ID = "category_id";
	public static final String APARTMENT_BILL = "bill";
	public static final String APARTMENT_STATUS_ID = "status_id";

	
	public static final String ORDERS_MENU_USER_ID = "user_id";
	public static final String ORDERS_MENU_ORDER_ID = "order_id";
	public static final String ORDERS_MENU_APARTMENT_ID = "apartment_id";
	public static final String ORDERS_MENU_BILL = "bill";
	public static final String ORDERS_MENU_ORDER_STATUS_ID = "status_order";
	
	// beans
	public static final String USER_ORDER_BEAN_ORDER_ID = "id";
	public static final String USER_ORDER_BEAN_USER_FIRST_NAME = "first_name";
	public static final String USER_ORDER_BEAN_USER_LAST_NAME = "last_name";
	public static final String USER_ORDER_BEAN_ORDER_APARTMENT_ID = "title";
	public static final String USER_ORDER_BEAN_BILL = "bill";
	public static final String USER_ORDER_BEAN_STATUS_ORDER = "name";

	
	public static final String APARTMENTS_ORDERS_MENU_USER_ID = "title";
	public static final String APARTMENTS_ORDERS_MENU_SEAT_ID = "number";
	public static final String APARTMENTS_ORDERS_MENU_CATEGORY = "category_name";
	public static final String APARTMENTS_ORDERS_MENU_BILL = "bill";
	public static final String APARTMENTS_ORDERS_MENU_ORDER_STATUS_ID = "statuses_name";
	
}