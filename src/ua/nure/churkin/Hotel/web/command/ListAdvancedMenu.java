package ua.nure.churkin.Hotel.web.command;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import ua.nure.churkin.Hotel.Path;
import ua.nure.churkin.Hotel.db.DBManager;
import ua.nure.churkin.Hotel.db.Fields;
import ua.nure.churkin.Hotel.exception.AppException;
import ua.nure.churkin.Hotel.exception.DBException;
import ua.nure.churkin.Hotel.exception.Messages;

public class ListAdvancedMenu extends Command {
	private static final String SQL_FIND_CATEGORY = "SELECT id FROM categories WHERE category_name=?";
	/**
	 * 
	 */
	

	private static final long serialVersionUID = -1431057527898855444L;
	private static final Logger LOG = Logger.getLogger(ListOrdersCommand.class);
	private Statement stmt;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;
		String type = request.getParameter("type");
		LOG.trace("Parametr type --> " + type);
		System.out.println(type);
		if (type.equals("Make a reservation")) {

			SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		
			String seat = request.getParameter("seat");
			System.out.println(seat);
			String category = request.getParameter("category");
			System.out.println(category);
			String arrival = request.getParameter("arrival");
			
				 Date date1 = null;
				try {
					date1 = (Date) format.parse(arrival);
					System.out.println(date1);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			
			String departure = request.getParameter("departure");

			DBManager manager = DBManager.getInstance();
			Connection con = manager.getConnection();
			stmt = null;
			ResultSet rs = null;
			String query = "INSERT INTO order_selection (id, user_id, seat_id,  category_id, arrival,departure)"
					+ " values (DEFAULT,2,?,?,?,?)";
			PreparedStatement preparedStmt = null;
			int cat =0;
			try {
				preparedStmt = con.prepareStatement(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
				try {
					stmt = con.createStatement();
					rs = stmt.executeQuery("SELECT id FROM categories WHERE category_name ="+category );
					 cat =	rs.getInt(Fields.ENTITY_ID);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			 System.out.println(cat);
			 
			
			try {
				preparedStmt.setInt(3, Integer.parseInt(seat));
				preparedStmt.setInt(4, cat);
				preparedStmt.setDate(5,date1);
				preparedStmt.setDate(6,date1 );
				preparedStmt.execute();

				con.commit();
			} catch (SQLException ex) {

				LOG.error(Messages.ERR_CANNOT_OBTAIN_CATEGORIES, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_CATEGORIES, ex);
			} finally {
				close(con, stmt, rs);
			}

			forward = Path.PAGE_LIST_ADVANCED_OK;
		}
		return forward;
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