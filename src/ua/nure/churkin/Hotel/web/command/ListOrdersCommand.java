package ua.nure.churkin.Hotel.web.command;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.churkin.Hotel.Path;
import ua.nure.churkin.Hotel.db.DBManager;
import ua.nure.churkin.Hotel.db.Fields;
import ua.nure.churkin.Hotel.db.bean.UserOrderBean;
import ua.nure.churkin.Hotel.exception.AppException;
import ua.nure.churkin.Hotel.exception.DBException;

/**
 * Lists orders.
 */
public class ListOrdersCommand extends Command {
	private static final String SQL_UPDATE_USER = "UPDATE orders_menu SET password=, first_name=?, last_name=?WHERE id=?";

	private static final long serialVersionUID = 1863978254689586513L;

	private static final Logger LOG = Logger.getLogger(ListOrdersCommand.class);

	/**
	 * Serializable comparator used with TreeMap container. When the servlet
	 * container tries to serialize the session it may fail because the session can
	 * contain TreeMap object with not serializable comparator.
	 */
	private static class CompareById implements Comparator<UserOrderBean>, Serializable {
		private static final long serialVersionUID = -1573481565177573283L;

		public int compare(UserOrderBean bean1, UserOrderBean bean2) {
			if (bean1.getId() > bean2.getId()) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	private static Comparator<UserOrderBean> compareById = new CompareById();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException, DBException {

		String forward = Path.PAGE_ERROR_PAGE;
		String type = request.getParameter("type");
		// List<UserOrderBean> userOrderBeanListFull =
		// DBManager.getInstance().getUserOrderBeans(type);

		if (type.equals("Sort by bill inc")) {
			List<UserOrderBean> userOrderBeanList = DBManager.getInstance().getUserOrderBeans(type);
			LOG.trace("Found in DB: userOrderBeanList --> " + userOrderBeanList);

			Collections.sort(userOrderBeanList, compareById);

			request.setAttribute("userOrderBeanList", userOrderBeanList);
			LOG.trace("Set the request attribute: userOrderBeanList --> " + userOrderBeanList);

			LOG.debug("Commands finished");
			forward = Path.PAGE_LIST_ORDERS;

		}
		if (type.equals("Sort by bill dec")) {
			List<UserOrderBean> userOrderBeanList = DBManager.getInstance().getUserOrderBeans(type);
			LOG.trace("Found in DB: userOrderBeanList --> " + userOrderBeanList);

			Collections.sort(userOrderBeanList, compareById);

			request.setAttribute("userOrderBeanList", userOrderBeanList);
			LOG.trace("Set the request attribute: userOrderBeanList --> " + userOrderBeanList);

			LOG.debug("Commands finished");
			forward = Path.PAGE_LIST_ORDERS;

		}
		if (type.equals("Sort by apartments")) {
			List<UserOrderBean> userOrderBeanList = DBManager.getInstance().getUserOrderBeans(type);
			LOG.trace("Found in DB: userOrderBeanList --> " + userOrderBeanList);

			Collections.sort(userOrderBeanList, compareById);

			request.setAttribute("userOrderBeanList", userOrderBeanList);
			LOG.trace("Set the request attribute: userOrderBeanList --> " + userOrderBeanList);

			LOG.debug("Commands finished");
			forward = Path.PAGE_LIST_ORDERS;

		}

		if (type.equals("Sort by apartments dec")) {
			List<UserOrderBean> userOrderBeanList = DBManager.getInstance().getUserOrderBeans(type);
			LOG.trace("Found in DB: userOrderBeanList --> " + userOrderBeanList);

			Collections.sort(userOrderBeanList, compareById);

			request.setAttribute("userOrderBeanList", userOrderBeanList);
			LOG.trace("Set the request attribute: userOrderBeanList --> " + userOrderBeanList);

			LOG.debug("Commands finished");
			forward = Path.PAGE_LIST_ORDERS;

		}

		if (type.equals("Update DB")) {
			String id = request.getParameter("status");
			DBManager manager = DBManager.getInstance();
			Connection con = manager.getConnection();
			Statement stmt ;
			ResultSet rs = null;
			

			try {
				stmt = con.createStatement();
				rs = stmt.executeQuery("UPDATE orders_menu SET status_order= 3 WHERE id = 3");
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			forward = Path.PAGE_LIST_ORDERS;
		}
		return Path.PAGE_LIST_ORDERS;
	}

}