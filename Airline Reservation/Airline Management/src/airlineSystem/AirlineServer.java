package airlineSystem;

import javax.jws.WebService;

import beans.Employee;
import beans.FlightDetails;
import beans.Person;
import beans.Reservation;
import beans.Traveller;

@WebService
public class AirlineServer
{
	public String[] fetchStateList()
	{
		String[] stateArray = null;
		ModelController controller = new ModelController();
		stateArray = controller.fetchStateList();

		return stateArray;
	}

	public String registerCustomer(Person person)
	{
		String message = null;
		ModelController controller = new ModelController();
		message = controller.registerCustomer(person);

		return message;
	}

	public String deleteCustomer(String emailID)
	{
		String message = null;
		ModelController controller = new ModelController();
		message = controller.deleteCustomer(emailID);
		return message;
	}

	public String login(String userName, String password)
	{
		String message = null;
		ModelController controller = new ModelController();
		message = controller.login(userName, password);

		return message;
	}

	public String addEmployee(Employee employee)
	{
		String message = null;
		ModelController controller = new ModelController();
		message = controller.addEmployee(employee);

		return message;
	}

	public String deleteEmployee(String emailID)
	{
		String message = null;

		return message;
	}

	// Added by Pradyumna
	public String createNewReservation(FlightDetails journeyDetails, String userID)
	{
		ModelController controller = new ModelController();
		String message = controller.reserveTicket(journeyDetails, userID);
		return message;
	}

	public String cancelReservation(String userID, int reservationID)
	{
		String message = null;
		ModelController controller = new ModelController();
		return controller.cancelTicket(userID, reservationID);
	}

	public Reservation issueTicket(String userId, int reservationID)
	{
		ModelController controller = new ModelController();
		return controller.issueTicket(userId, reservationID);
	}

	public Reservation[] getBookedTickets(String userID)
	{
		ModelController controller = new ModelController();
		Reservation[] jDetials = controller.getAllReservations(userID);
		;
		return jDetials;
	}

	// End of code added by Pradyumna

	public String processPayement(String cardID)
	{
		String message = null;

		return message;
	}

	public Employee[] listAllEmployees()
	{
		Employee[] employeeArray = null;

		return employeeArray;
	}

	// GEt 1 or many traveller information
	public Traveller[] listAllCustomers(String travelerID)
	{
		ModelController controller = new ModelController();
		Traveller[] travellerArray = controller.searchTravelers(travelerID);

		return travellerArray;
	}

	public Reservation[] listAllReservation()
	{
		Reservation[] reservationList = null;
		return reservationList;
	}

	// Start: Code added by Pradyumna
	public FlightDetails[] listAllFlights(String source, String destination)
	{
		ModelController controller = new ModelController();
		FlightDetails[] flightArray = controller.searchFlightForSourceAndDest(source, destination);

		return flightArray;
	}

	public String updateTravellerInfo(Traveller traveller)
	{
		String message = null;
		return message;
	}

	// End of code added by Pradyumna
	public String updateEmployeeInfo(Employee emp)
	{
		String message = null;
		return message;
	}

	public String updateFlightDetails(FlightDetails flight)
	{
		String message = null;
		return message;
	}

	public Employee[] searchEmployeeForID(int empID, String workDesc, String hireDate)
	{
		Employee[] employeeArray = null;

		ModelController controller = new ModelController();
		employeeArray = controller.searchEmployeeForID(empID, workDesc, hireDate);
		return employeeArray;
	}

	public Employee findEmployee(Employee emp)
	{
		Employee employee = null;
		return employee;
	}

	public FlightDetails findFlights(FlightDetails flight)
	{
		FlightDetails flightDetails = null;
		return flightDetails;
	}

	public Traveller[] findPassengersOnBoard(FlightDetails flight)
	{
		Traveller[] travellerArray = null;

		return travellerArray;
	}
}
