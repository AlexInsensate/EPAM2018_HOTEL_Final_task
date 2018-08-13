package ua.nure.churkin.Hotel.web.command;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.churkin.Hotel.Path;
import ua.nure.churkin.Hotel.db.DBManager;
import ua.nure.churkin.Hotel.db.Role;
import ua.nure.churkin.Hotel.db.bean.UserOrderBean;
import ua.nure.churkin.Hotel.db.entity.User;
import ua.nure.churkin.Hotel.exception.AppException;


/**
 * Login command.
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);
	
	private static class CompareById implements Comparator<UserOrderBean>, Serializable {
	

		/**
		 * 
		 */
		private static final long serialVersionUID = -3204290302060453561L;

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
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		HttpSession session = request.getSession();

		// obtain login and password from a request
		DBManager manager = DBManager.getInstance();
		String login = request.getParameter("login");
		LOG.trace("Request parameter: loging --> " + login);

		String password = request.getParameter("password");
		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			throw new AppException("Login/password cannot be empty");
		}

		User user = manager.findUserByLogin(login);
		LOG.trace("Found in DB: user --> " + user);

		if (user == null || !password.equals(user.getPassword())) {
			throw new AppException("Cannot find user with such login/password");
		}
		String s = "native";
		Role userRole = Role.getRole(user);
		LOG.trace("userRole --> " + userRole);
		
		String forward = Path.PAGE_ERROR_PAGE;

		if (userRole == Role.ADMIN) {
			
			
			List<UserOrderBean> userOrderBeanList = DBManager.getInstance().getUserOrderBeans(s);
			LOG.trace("Found in DB: userOrderBeanList --> " + userOrderBeanList);
			
			Collections.sort(userOrderBeanList, compareById);

			request.setAttribute("userOrderBeanList", userOrderBeanList);		
			LOG.trace("Set the request attribute: userOrderBeanList --> " + userOrderBeanList);
			
			LOG.debug("Commands finished");
			
			
			forward = Path.PAGE_LIST_ORDERS;
		}

		if (userRole == Role.CLIENT) {
			forward = Path.PAGE_LIST_MENU;
		}

		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userRole", userRole);
		LOG.trace("Set the session attribute: userRole --> " + userRole);

		LOG.info("User " + user + " logged as " + userRole.toString().toLowerCase());

		LOG.debug("Command finished");
		return forward;
	}

}