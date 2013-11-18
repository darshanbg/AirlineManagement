package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import airlineSystem.AirlineServerProxy;
import beans.FlightDetails;

/**
 * Servlet implementation class TravelServlet THIS SERVLET HANDLES REQUEST
 * RELATED TO TRAVEL SUCH AS RESERVING TICKET,ISSUING,SEARCHING TRAVELLERS AND
 * RESERVATIONS
 */
public class TravelServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TravelServlet()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String method = request.getParameter("method");
		AirlineServerProxy airlnproxy = new AirlineServerProxy();
		if ("1".equals(method))
		{
			String source = request.getParameter("source");
			String destination = request.getParameter("destination");
			FlightDetails[] flights = airlnproxy.listAllFlights(source, destination);
			request.setAttribute("flights", flights);
			RequestDispatcher dispatch = request.getRequestDispatcher("Reserve.jsp?method=2");
			dispatch.forward(request, response);
		}
	}

}
