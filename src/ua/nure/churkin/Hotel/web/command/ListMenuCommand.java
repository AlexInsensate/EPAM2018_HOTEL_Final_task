package ua.nure.churkin.Hotel.web.command;

import java.io.IOException;
import java.io.Serializable;
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


/**
 * Lists menu items.
 */
public class ListMenuCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger LOG = Logger.getLogger(ListMenuCommand.class);
	
	
	
	private static class CompareByIdSeats implements Comparator<Seats>, Serializable {
		private static final long serialVersionUID = -1573481565177573283L;

		public int compare(Seats seat1, Seats seat2) {
			if (seat1.getId() > seat2.getId()) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	private static class CompareByIdCategory implements Comparator<Category>, Serializable {
		private static final long serialVersionUID = -1573481565177573283L;

		public int compare(Category c1, Category c2) {
			if (c1.getId() > c2.getId()) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	private static Comparator<Seats> compareByIdSeast = new CompareByIdSeats();
	private static Comparator<Category> compareByIdCategory = new CompareByIdCategory();
	private static Comparator<ApartmentOrderBean> compareByIdApartments = new CompareByIdApartments();
	
	private static class CompareByIdApartments implements Comparator<ApartmentOrderBean>, Serializable {
		private static final long serialVersionUID = -1573481565177573283L;
		
		public int compare(ApartmentOrderBean bean1, ApartmentOrderBean bean2) {
			if (bean1.getId() > bean2.getId()) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");
		String type = request.getParameter("type");
		LOG.trace("Parametr type --> " + type);
		String forward = Path.PAGE_ERROR_PAGE;

		if (type.equals("Simple")) {
			
			List<ApartmentOrderBean> apartmentsList = DBManager.getInstance().getApartments();
			LOG.trace("Found in DB: apartmentsList --> " + apartmentsList);
			
			Collections.sort(apartmentsList, compareByIdApartments);

			request.setAttribute("apartmentsList", apartmentsList);		
			LOG.trace("Set the request attribute: userOrderBeanList --> " + apartmentsList);
	
			
			forward = Path.PAGE_LIST_SIMPLE_MENU;
		}

		if (type.equals("Advanced")) {
			
			List<Seats> seatsList = DBManager.getInstance().findSeats();
			LOG.trace("Found in DB: seatsList --> " + seatsList);
			Collections.sort(seatsList, compareByIdSeast);
			request.setAttribute("seatsList", seatsList);
			LOG.trace("Set the request attribute: seatsList --> " + seatsList);

			List<Category> categoryList = DBManager.getInstance().findCategories();
			LOG.trace("Found in DB: categoryList --> " + categoryList);
			Collections.sort(categoryList, compareByIdCategory);
			request.setAttribute("categoryList", categoryList);
			LOG.trace("Set the request attribute: categoryList --> " + categoryList);

		
			forward = Path.PAGE_LIST_ADVANCED_MENU;
		}
		LOG.debug("Command finished");
		return forward;
	}

}