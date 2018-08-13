package ua.nure.churkin.Hotel;

public final class Path {

	// pages
	public static final String PAGE_LOGIN = "/login.jsp";
	public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";

	public static final String PAGE_LIST_ADVANCED_OK = "/WEB-INF/jsp/client/advancedOk.jsp";
	public static final String PAGE_LIST_ADVANCED_MENU = "/WEB-INF/jsp/client/advanced_registration.jsp";
	public static final String PAGE_LIST_SIMPLE_MENU = "/WEB-INF/jsp/client/simple_registration.jsp";
	public static final String PAGE_LIST_SIMPLE_OK = "/WEB-INF/jsp/client/simpleOk.jsp";
	public static final String PAGE_LIST_MENU = "/WEB-INF/jsp/client/list_menu.jsp";

	public static final String PAGE_LIST_ORDERS = "/WEB-INF/jsp/admin/list_orders.jsp";
	public static final String PAGE_SETTINGS = "/WEB-INF/jsp/settings.jsp";

	// commands
	public static final String COMMAND_LIST_ORDERS = "/controller?command=listOrders";
	public static final String COMMAND_LIST_SIMPLE = "/controller?command=simple";
	public static final String COMMAND_LIST_MENU = "/controller?command=listMenu";

}