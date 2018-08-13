package ua.nure.churkin.Hotel.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.churkin.Hotel.db.bean.ApartmentOrderBean;
import ua.nure.churkin.Hotel.db.bean.UserOrderBean;
import ua.nure.churkin.Hotel.db.entity.Apartments;
import ua.nure.churkin.Hotel.db.entity.Category;
import ua.nure.churkin.Hotel.db.entity.OrderSelection;
import ua.nure.churkin.Hotel.db.entity.OrdersMenu;
import ua.nure.churkin.Hotel.db.entity.Seats;
import ua.nure.churkin.Hotel.db.entity.User;
import ua.nure.churkin.Hotel.exception.DBException;
import ua.nure.churkin.Hotel.exception.Messages;

/**
 * DB manager. Works with MySQL 5.1 . Only the required DAO methods.
 * 
 */
public final class DBManager {

	private static final Logger LOG = Logger.getLogger(DBManager.class);
	private static DBManager instance;

	public static synchronized DBManager getInstance() throws DBException {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private DBManager() throws DBException {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/hoteldb_MYSQL");// hoteldb_MYSQL - the name of data source
			LOG.trace("Data source ==> " + ds);
		} catch (NamingException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
		}
	}

	private DataSource ds;

	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

	private static final String SQL_FIND_ALL_ORDERS = "SELECT * FROM orders_selection";

	private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

	private static final String SQL_FIND_ALL_MENU_ITEMS = "SELECT * FROM orders_selection";

	private static final String SQL_FIND_ALL_SEATS = "SELECT id, number FROM seats ";

	private static final String SQL_FIND_ALL_APARTMENTS = "SELECT * FROM apartments ";

	private static final String SQL_FIND_ALL_STATUSES = "SELECT * FROM order_statuses ";

	private static final String SQL_FIND_ORDERS_BY_STATUS_AND_USER = "SELECT * FROM orders_menu WHERE status_order=? AND user_id=?";

	private static final String SQL_FIND_APARTMENT_BY_ORDER = "select * from apartments where id in (select apartment_id from orders_menu where order_id=?)";

	private static final String SQL_FIND_ORDERS_BY_STATUS = "SELECT * FROM orders_menu WHERE status_order=?";

	private static final String SQL_FIND_ALL_CATEGORIES = "SELECT * FROM categories";

	private static final String SQL_UPDATE_USER = "UPDATE users SET password=?, first_name=?, last_name=?"
			+ "	WHERE id=?";

	private static final String SQL_GET_USER_ORDER_BEANS = "SELECT o.id, u.first_name, u.last_name, a.title, o.bill, s.name	FROM apartments a, users u, orders_menu o, order_statuses s WHERE o.user_id=u.id AND o.status_order=s.id AND o.apartment_id=a.id";

	private static final String SQL_GET_USER_APARTMENTS_BEANS = "SELECT a.id, a.title, s.number, c.category_name, a.bill, st.statuses_name FROM apartments a , seats s,categories c,statuses st WHERE a.status_id=st.id AND a.category_id=c.id AND a.seat_id=s.id ORDER BY a.bill ";
	private static final String SQL_GET_USER_ORDER_BILL_DEC = "SELECT o.id, u.first_name, u.last_name, a.title, o.bill, s.name	FROM apartments a, users u, orders_menu o, order_statuses s WHERE o.user_id=u.id AND o.status_order=s.id AND o.apartment_id=a.id ORDER BY o.bill DESC";
	// DESC
	private static final String SQL_GET_USER_ORDER_BILL_INC = "SELECT o.id, u.first_name, u.last_name, a.title, o.bill, s.name	FROM apartments a, users u, orders_menu o, order_statuses s WHERE o.user_id=u.id AND o.status_order=s.id AND o.apartment_id=a.id ORDER BY o.bill ";
	private static final String SQL_INCERT_INTO_ORDER_SELECTION = "INCERT INTO order_selection VALUES (id=DEFAULT, user_id=?, seat_id=?, category_id=?, arrival=?, departure=?) ";

	private static final String SQL_GET_USER_ORDER_APARTMENTS = "SELECT o.id, u.first_name, u.last_name, a.title, o.bill, s.name	FROM apartments a, users u, orders_menu o, order_statuses s WHERE o.user_id=u.id AND o.status_order=s.id AND o.apartment_id=a.id ORDER BY a.title";
	private static final String SQL_GET_USER_ORDER_APARTMENTS_DEC = "SELECT o.id, u.first_name, u.last_name, a.title, o.bill, s.name	FROM apartments a, users u, orders_menu o, order_statuses s WHERE o.user_id=u.id AND o.status_order=s.id AND o.apartment_id=a.id ORDER BY a.title DESC";

	/**
	 * Returns a DB connection from the Pool Connections. Before using this method
	 * you must configure the Date Source and the Connections Pool in your
	 * WEB_APP_ROOT/META-INF/context.xml file.
	 */
	public Connection getConnection() throws DBException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
		}
		return con;
	}

	/**
	 * Admin panel
	 * 
	 * @return List of category entities.
	 */
	public List<UserOrderBean> getUserOrderBeans(String s) throws DBException {
		List<UserOrderBean> orderUserBeanList = new ArrayList<UserOrderBean>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();

			if (s.equals("native")) {
				rs = stmt.executeQuery(SQL_GET_USER_ORDER_BEANS);
			}
			if (s.equals("Sort by bill dec")) {
				rs = stmt.executeQuery(SQL_GET_USER_ORDER_BILL_DEC);
			}
			if (s.equals("Sort by bill inc")) {
				rs = stmt.executeQuery(SQL_GET_USER_ORDER_BILL_INC);
			}
			if (s.equals("Sort by apartments")) {
				rs = stmt.executeQuery(SQL_GET_USER_ORDER_APARTMENTS);
			}
			if (s.equals("Sort by apartments dec")) {
				rs = stmt.executeQuery(SQL_GET_USER_ORDER_APARTMENTS_DEC);
			}

			while (rs.next()) {
				orderUserBeanList.add(extractUserOrderBean(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_ORDER_BEANS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_ORDER_BEANS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return orderUserBeanList;
	}

	/**
	 * @return UserOrderBean object
	 */

	private UserOrderBean extractUserOrderBean(ResultSet rs) throws SQLException {
		UserOrderBean bean = new UserOrderBean();
		bean.setStatus_order(rs.getString(Fields.USER_ORDER_BEAN_STATUS_ORDER));
		bean.setOrderId(rs.getInt(Fields.USER_ORDER_BEAN_ORDER_ID));
		bean.setUserFirstName(rs.getString(Fields.USER_ORDER_BEAN_USER_FIRST_NAME));
		bean.setUserLastName(rs.getString(Fields.USER_ORDER_BEAN_USER_LAST_NAME));
		bean.setApartments(rs.getString(Fields.USER_ORDER_BEAN_ORDER_APARTMENT_ID));
		bean.setBill(rs.getInt(Fields.USER_ORDER_BEAN_BILL));
		return bean;
	}

	/**
	 * client advanced panel apartments
	 * 
	 * @return List of category entities.
	 */
	public List<ApartmentOrderBean> getApartments() throws DBException {
		List<ApartmentOrderBean> apartmentsList = new ArrayList<ApartmentOrderBean>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_USER_APARTMENTS_BEANS);
			while (rs.next()) {
				apartmentsList.add(extractApartments(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_ORDER_BEANS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_ORDER_BEANS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return apartmentsList;
	}

	/**
	 * @return Apartments object
	 */

	private ApartmentOrderBean extractApartments(ResultSet rs) throws SQLException {
		ApartmentOrderBean apartments = new ApartmentOrderBean();
		apartments.setId(rs.getInt(Fields.ENTITY_ID));
		apartments.setTitle(rs.getString(Fields.APARTMENTS_ORDERS_MENU_USER_ID));
		apartments.setSeats(rs.getInt(Fields.APARTMENTS_ORDERS_MENU_SEAT_ID));
		apartments.setCategory_name(rs.getString(Fields.APARTMENTS_ORDERS_MENU_CATEGORY));
		apartments.setStatuses_name(rs.getString(Fields.APARTMENTS_ORDERS_MENU_ORDER_STATUS_ID));
		apartments.setBill(rs.getInt(Fields.APARTMENTS_ORDERS_MENU_BILL));
		return apartments;
	}

	/**
	 * @return Seats object
	 */

	public List<Seats> findSeats() throws DBException {
		List<Seats> seatsList = new ArrayList<Seats>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_SEATS);
			while (rs.next()) {
				seatsList.add(extractSeats(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CATEGORIES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CATEGORIES, ex);
		} finally {
			close(con, stmt, rs);
		}
		return seatsList;
	}

	private Seats extractSeats(ResultSet rs) throws SQLException {
		Seats seats = new Seats();
		seats.setId(rs.getInt(Fields.ENTITY_ID));
		seats.setNumber(rs.getInt(Fields.SEAT_NUMBER));
		return seats;
	}

	/**
	 * Returns all categories.
	 * 
	 * @return List of category entities.
	 */
	public List<Category> findCategories() throws DBException {
		List<Category> categoriesList = new ArrayList<Category>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_CATEGORIES);
			while (rs.next()) {
				categoriesList.add(extractCategory(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CATEGORIES, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CATEGORIES, ex);
		} finally {
			close(con, stmt, rs);
		}
		return categoriesList;
	}

	/**
	 * Extracts a category entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a category entity will be extracted.
	 * @return Category entity.
	 */
	private Category extractCategory(ResultSet rs) throws SQLException {
		Category category = new Category();
		category.setId(rs.getInt(Fields.ENTITY_ID));
		category.setCategory_name(rs.getString(Fields.CATEGORY_NAME));
		return category;
	}

	/**
	 * Returns all menu items.
	 * 
	 * @return List of menu item entities.
	 */
	public List<OrderSelection> findMenuItems() throws DBException {
		List<OrderSelection> menuItemsList = new ArrayList<OrderSelection>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_MENU_ITEMS);
			while (rs.next()) {
				menuItemsList.add(extractOrderSelectionMenu(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_MENU_ITEMS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_MENU_ITEMS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return menuItemsList;
	}

	private OrderSelection extractOrderSelectionMenu(ResultSet rs) throws SQLException {
		OrderSelection menuItem = new OrderSelection();
		menuItem.setId(rs.getInt(Fields.ENTITY_ID));
		menuItem.setUserId(rs.getInt(Fields.ORDER_SELECTION_USER_ID));
		menuItem.setSeatId(rs.getInt(Fields.ORDER_SELECTION_SEAT_ID));
		menuItem.setCategoryId(rs.getInt(Fields.ORDER_SELECTION_CATEGORY_ID));
		menuItem.setArrival(rs.getDate(Fields.ORDER_SELECTION_ARRIVAL));
		menuItem.setDeparture(rs.getDate(Fields.ORDER_SELECTION_DEPARTURE));
		menuItem.setApartmentId(rs.getInt(Fields.ORDER_SELECTION_APARTMENT_ID));
		return menuItem;
	}

	/**
	 * Returns menu items of the given order.
	 */
	public List<OrderSelection> insertOrderSelection() throws DBException {
		List<OrderSelection> menuItemsList = new ArrayList<OrderSelection>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_INCERT_INTO_ORDER_SELECTION);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				menuItemsList.add(extractOrderSelectionMenu(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_MENU_ITEMS_BY_ORDER, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_MENU_ITEMS_BY_ORDER, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return menuItemsList;
	}

	/**
	 * Returns menu items with given identifiers.
	 * 
	 * @param ids
	 *            Identifiers of menu items.
	 * @return List of menu item entities.
	 */
	public List<OrderSelection> findMenuItems(String[] ids) throws DBException {
		List<OrderSelection> menuItemsList = new ArrayList<OrderSelection>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();

			// create SQL query like "... id IN (1, 2, 7)"
			StringBuilder query = new StringBuilder("SELECT * FROM orders_menu WHERE id IN (");
			for (String idAsString : ids) {
				query.append(idAsString).append(',');
			}
			query.deleteCharAt(query.length() - 1);
			query.append(')');

			stmt = con.createStatement();
			rs = stmt.executeQuery(query.toString());
			while (rs.next()) {
				menuItemsList.add(extractOrderSelectionMenu(rs));
			}
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_MENU_ITEMS_BY_IDENTIFIERS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return menuItemsList;
	}

	/**
	 * Returns all orders.
	 * 
	 * @return List of order entities.
	 */
	public List<OrdersMenu> findOrders() throws DBException {
		List<OrdersMenu> ordersList = new ArrayList<OrdersMenu>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_ORDERS);
			while (rs.next()) {
				ordersList.add(extractOrder(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ORDERS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return ordersList;
	}

	/**
	 * Returns orders with the given status.
	 * 
	 * @param statusId
	 *            Status identifier.
	 * @return List of order entities.
	 */
	public List<OrdersMenu> findOrders(int statusId) throws DBException {
		List<OrdersMenu> ordersList = new ArrayList<OrdersMenu>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_ORDERS_BY_STATUS);
			pstmt.setInt(1, statusId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ordersList.add(extractOrder(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ORDERS_BY_STATUS_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return ordersList;
	}

	/**
	 * Returns orders with given identifiers.
	 * 
	 * @param ids
	 *            Orders identifiers.
	 * @return List of order entities.
	 */
	public List<OrdersMenu> findOrders(String[] ids) throws DBException {
		List<OrdersMenu> ordersList = new ArrayList<OrdersMenu>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();

			// create SQL query like "... id IN (1, 2, 7)"
			StringBuilder query = new StringBuilder("SELECT * FROM orders_menu WHERE id IN (");
			for (String idAsString : ids) {
				query.append(idAsString).append(',');
			}
			query.deleteCharAt(query.length() - 1);
			query.append(')');

			stmt = con.createStatement();
			rs = stmt.executeQuery(query.toString());
			while (rs.next()) {
				ordersList.add(extractOrder(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ORDERS_BY_IDENTIFIERS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return ordersList;
	}

	/**
	 * Returns orders of the given user and status
	 * 
	 * @param user
	 *            User entity.
	 * @param statusId
	 *            Status identifier.
	 * @return List of order entities.
	 * @throws DBException
	 */
	public List<OrdersMenu> findOrders(User user, int statusId) throws DBException {
		List<OrdersMenu> ordersList = new ArrayList<OrdersMenu>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_ORDERS_BY_STATUS_AND_USER);
			pstmt.setInt(1, statusId);
			pstmt.setLong(2, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ordersList.add(extractOrder(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ORDERS_BY_USER_AND_STATUS_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return ordersList;
	}

	/**
	 * Returns a user with the given identifier.
	 * 
	 * @param id
	 *            User identifier.
	 * @return User entity.
	 * @throws DBException
	 */
	public User findUser(long id) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Returns a user with the given login.
	 * 
	 * @param login
	 *            User login.
	 * @return User entity.
	 * @throws DBException
	 */
	public User findUserByLogin(String login) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Update user.
	 * 
	 * @param user
	 *            user to update.
	 * @throws DBException
	 */
	public void updateUser(User user) throws DBException {
		Connection con = null;
		try {
			con = getConnection();
			updateUser(con, user);
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con);
		}
	}

	/**
	 * Update user.
	 * 
	 * @param user
	 *            user to update.
	 * @throws SQLException
	 */
	private void updateUser(Connection con, User user) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_UPDATE_USER);
			int k = 1;
			pstmt.setString(k++, user.getPassword());
			pstmt.setString(k++, user.getFirstName());
			pstmt.setString(k++, user.getLastName());
			pstmt.setLong(k, user.getId());
			pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
	}

	// DB util methods
	/**
	 * Closes a connection.
	 * 
	 * @param con
	 *            Connection to be closed.
	 */
	private void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
			}
		}
	}

	/**
	 * Closes a statement object.
	 */
	private void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
			}
		}
	}

	/**
	 * Closes a result set object.
	 */
	private void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
			}
		}
	}

	/**
	 * Closes resources.
	 */
	private void close(Connection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

	/**
	 * Rollbacks a connection.
	 * 
	 * @param con
	 */
	private void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error("Cannot rollback transaction", ex);
			}
		}
	}

	// //////////////////////////////////////////////////////////
	// Other methods
	// //////////////////////////////////////////////////////////

	/**
	 * Extracts a user entity from the result set.
	 * 
	 * @param rs
	 * @return User entity
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt(Fields.ENTITY_ID));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
		user.setLastName(rs.getString(Fields.USER_LAST_NAME));
		user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
		return user;
	}

	/**
	 * Extracts a menu item from the result set.
	 * 
	 * @param rs
	 * @return Menu item entity.
	 */

	private OrdersMenu extractOrder(ResultSet rs) throws SQLException {
		OrdersMenu order = new OrdersMenu();
		order.setId(rs.getInt(Fields.ENTITY_ID));
		order.setUserId(rs.getInt(Fields.USER_ORDER_BEAN_ORDER_ID));
		order.setOrderId(rs.getInt(Fields.ORDERS_MENU_ORDER_ID));
		order.setBill(rs.getInt(Fields.ORDERS_MENU_BILL));
		order.setStatusOrder(rs.getInt(Fields.ORDERS_MENU_ORDER_STATUS_ID));
		return order;
	}

}