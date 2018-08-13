package ua.nure.churkin.Hotel.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.churkin.Hotel.Path;
import ua.nure.churkin.Hotel.exception.AppException;
import ua.nure.churkin.Hotel.web.command.Command;
import ua.nure.churkin.Hotel.web.command.CommandContainer;

/**
 * Main servlet controller.
 */

public class Controller extends HttpServlet {

	private static final long serialVersionUID = 2423353715955164816L;
	private static final Logger LOG = Logger.getLogger(Controller.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * Main method of this controller.
	 */
	private void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		LOG.debug("Controller starts");

		String commandName = request.getParameter("command");
		LOG.trace("Request parameter: command --> " + commandName);
		Command command = CommandContainer.get(commandName);
		LOG.trace("Obtained command --> " + command);

		String forward = Path.PAGE_ERROR_PAGE;
		try {
			forward = command.execute(request, response);
		} catch (AppException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
		}
		LOG.trace("Forward address --> " + forward);

		LOG.debug("Controller finished, now go to forward address --> " + forward);

		request.getRequestDispatcher(forward).forward(request, response);
	}

}