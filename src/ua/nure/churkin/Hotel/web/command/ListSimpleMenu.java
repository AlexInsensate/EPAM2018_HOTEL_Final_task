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
import ua.nure.churkin.Hotel.db.bean.ApartmentOrderBean;
import ua.nure.churkin.Hotel.db.entity.Apartments;
import ua.nure.churkin.Hotel.db.entity.Category;
import ua.nure.churkin.Hotel.db.entity.Seats;
import ua.nure.churkin.Hotel.exception.AppException;
import ua.nure.churkin.Hotel.exception.DBException;
import ua.nure.churkin.Hotel.exception.Messages;


public class ListSimpleMenu extends Command {

	private static final long serialVersionUID = 1863978254689586513L;

	private static final Logger LOG = Logger.getLogger(ListOrdersCommand.class);

	/**
	 * Serializable comparator used with TreeMap container. When the servlet
	 * container tries to serialize the session it may fail because the session can
	 * contain TreeMap object with not serializable comparator.
	 */
	private static class CompareByIdApartments implements Comparator<ApartmentOrderBean>, Serializable {
		private static final long serialVersionUID = -1573481565177573283L;

		public int compare(ApartmentOrderBean ap1, ApartmentOrderBean ap2) {
			if (ap1.getId() > ap2.getId()) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	
	private static Comparator<ApartmentOrderBean> compareByIdApartments = new CompareByIdApartments();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Commands starts");
		String forward = Path.PAGE_ERROR_PAGE;
		String type = request.getParameter("itemId");

		List<ApartmentOrderBean> apartmentsList = DBManager.getInstance().getApartments();
		LOG.trace("Found in DB: apartmentsList --> " + apartmentsList);
		
		DBManager manager = DBManager.getInstance();
		Connection con = manager.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String query = "INSERT INTO orders_menu (id,user_id, order_id, apartment_id,  bill, status_order )"
				+ " values (DEFAULT,users.id,?,?,?,?)";
		PreparedStatement preparedStmt = null;

        	System.out.println(apartmentsList.get(Integer.parseInt(type)));
            for (int i = 0; i < apartmentsList.size(); i++) {
            	
            	
        }
			try {
				preparedStmt = con.prepareStatement(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				preparedStmt.setString(3, "");
				preparedStmt.setString(4, "");
				preparedStmt.setString(5, "");
				preparedStmt.setString(6, "");
				preparedStmt.execute();

				con.commit();
			} catch (SQLException ex) {

				LOG.error(Messages.ERR_CANNOT_OBTAIN_CATEGORIES, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_CATEGORIES, ex);
			} finally {
				close(con, stmt, rs);
			}

			return forward = Path.PAGE_LIST_SIMPLE_OK;
		}


	private void close(Connection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

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
	 * 
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

}
