package ua.nure.churkin.Hotel.web.command;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.churkin.Hotel.exception.AppException;

public abstract class Command implements Serializable {
	private static final long serialVersionUID = 8879403039606311780L;

	public abstract String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException;

	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}
}