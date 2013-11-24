package airlineSystem;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sun.misc.BASE64Encoder;
import beans.Employee;
import beans.FlightDetails;
import beans.MessageConstants;
import beans.Person;
import beans.Reservation;
import beans.Traveller;
import connection.EstablishConnection;

public class ModelController
{
	/*
	 * Added by Darshan -- Encrypts the input String (Password)
	 */
	public static synchronized String encrypt(String plaintext) throws Exception
	{
		MessageDigest msgDigest = null;
		String algorithm = "SHA";
		String encoding = "UTF-8";
		String hashValue = null;
		try
		{
			msgDigest = MessageDigest.getInstance(algorithm);
			msgDigest.update(plaintext.getBytes(encoding));
			byte rawByte[] = msgDigest.digest();
			hashValue = (new BASE64Encoder()).encode(rawByte);

		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println("No Such Algorithm Exists");
		}
		catch (UnsupportedEncodingException e)
		{
			System.out.println("The Encoding Is Not Supported");
		}
		return hashValue;
	}

	/*
	 * Added by Darshan -- Returns the list of valid states
	 */
	public String[] fetchStateList()
	{
		String[] stateList = null;
		EstablishConnection connectionClass = null;
		Connection connection = null;

		try
		{
			connectionClass = new EstablishConnection();
			connection = connectionClass.getConnection();
			connection.setAutoCommit(false);

			String query = "SELECT stateName from state_info";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			resultSet.last();
			int count = resultSet.getRow();
			resultSet.beforeFirst();

			if (count > 0)
			{
				stateList = new String[count];
				int i = 0;

				while (resultSet.next())
				{
					stateList[i++] = resultSet.getString(1);
				}

			}

			return stateList;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return stateList;
	}

	/*
	 * Added by Darshan -- Performs the Login operation
	 */
	public String login(String userName, String password)
	{
		String message = null;

		EstablishConnection connectionClass = null;
		Connection connection = null;

		try
		{
			connectionClass = new EstablishConnection();
			connection = connectionClass.getConnection();
			connection.setAutoCommit(false);

			String query = "SELECT PASSWORD, ROLEID, FIRSTNAME FROM PERSON WHERE EMAILID = ?";

			PreparedStatement prepStmt = connection.prepareStatement(query);

			prepStmt.setString(1, userName);
			ResultSet result = prepStmt.executeQuery();

			if (!result.next())
			{
				message = "Invalid Login";
				return message;
			}
			else
			{
				String encryptedPassword = encrypt(password);
				String savedPassword = result.getString("password");
				String firstName = result.getString("firstName");
				int roleID = result.getInt("roleID");

				if (encryptedPassword.equals(savedPassword))
				{
					message = "Success," + firstName + "," + roleID;
					connectionClass.endConnection(connection);
					System.out.println("message " + message);
					return message;
				}
				else
				{
					message = "Invalid Login";
					connectionClass.endConnection(connection);
					return message;
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return message;
	}

	/*
	 * Added by Darshan -- Registers a new customer
	 */
	public String registerCustomer(Person person)
	{
		String message = null;

		EstablishConnection connectionClass = null;
		Connection connection = null;

		try
		{
			connectionClass = new EstablishConnection();
			connection = connectionClass.getConnection();
			connection.setAutoCommit(false);

			String emailID = person.getEmailID();

			String query1 = "Select count(1) from person where emailID = ?";
			PreparedStatement preparedStmt = connection.prepareStatement(query1);

			preparedStmt.setString(1, emailID);
			ResultSet result1 = preparedStmt.executeQuery();

			while (result1.next())
			{
				if (result1.getInt(1) > 0)
				{
					message = "This email ID already exists. ";
					return message;
				}
			}

			String query2 = "Insert into person (firstName, lastName, address, city, zipCode, dateOfBirth, roleID, emailID, password, state) values (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStat2 = connection.prepareStatement(query2);

			preparedStat2.setString(1, person.getFirstName());
			preparedStat2.setString(2, person.getLastName());
			preparedStat2.setString(3, person.getAddress());
			preparedStat2.setString(4, person.getCity());
			preparedStat2.setString(5, person.getZipCode());
			preparedStat2.setString(6, person.getDateOfBirth());
			preparedStat2.setInt(7, person.getRoleID());

			preparedStat2.setString(8, person.getEmailID());
			preparedStat2.setString(9, encrypt(person.getPassword()));
			preparedStat2.setString(10, person.getState());

			preparedStat2.executeUpdate();
			message = "Successfully Registered!";
			connectionClass.endConnection(connection);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			message = "Some error while registering Customer!";
		}

		return message;
	}

	/*
	 * Added by Darshan -- Deletes an existing user
	 */
	public String deleteCustomer(String emailID)
	{
		String message = null;

		EstablishConnection connectionClass = null;
		Connection connection = null;

		try
		{
			connectionClass = new EstablishConnection();
			connection = connectionClass.getConnection();
			connection.setAutoCommit(false);

			String query = "delete from person a, traveller b where a.uniqueID = b.uniqueID and a.emailID = ?";

			PreparedStatement prepStmt = connection.prepareStatement(query);

			prepStmt.setString(1, emailID);

			int count = prepStmt.executeUpdate();

			if (count > 0)
				message = "Customer Deleted!!";
			else
				message = "Customer could not be deleted. Please try again.";
			connectionClass.endConnection(connection);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return message;
	}

	/*
	 * Code added by Pradyumna Get all reservation of USER OR THE SYSTEM
	 */
	public Reservation[] getAllReservations(String userId)
	{
		EstablishConnection myConnection = new EstablishConnection();
		Connection con = myConnection.getConnection();
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		Reservation[] tickets = null;

		String CountQuery = "select count(*) from reservation ";
		if (userId != null)
		{
			CountQuery += " where emailId=?;";
		}
		else
		{
			CountQuery += ";";
		}

		String qry = "select * from reservation ";
		if (userId != null)
		{
			qry += " where emailId=?;";
		}
		else
		{
			qry += ";";
		}
		try
		{
			pStmt = con.prepareStatement(CountQuery);
			if (userId != null)
			{
				pStmt.setString(1, userId);
			}
			rs = pStmt.executeQuery();
			if (rs.next())
			{
				tickets = new Reservation[rs.getInt(1)];
				// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
				pStmt = con.prepareStatement(qry);
				pStmt.setString(1, userId);
				rs = pStmt.executeQuery();
				int i = 0;
				while (rs.next())
				{
					Reservation ticket = new Reservation();
					ticket.setReservationId(rs.getInt(1));
					ticket.setFlightNumber(rs.getInt(2));
					ticket.setAirlineName(rs.getString(3));
					ticket.setSource(rs.getString(4));
					ticket.setDestination(rs.getString(5));
					ticket.setNumberOfSeats(rs.getInt(6));
					tickets[i] = ticket;
					i++;
				}
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		return tickets;
	}

	/*
	 * Added by Pradyumna-- Reserves the flight ticket
	 */
	public String reserveTicket(FlightDetails jDetails, String userID)
	{
		EstablishConnection myConnection = new EstablishConnection();
		Connection con = myConnection.getConnection();
		PreparedStatement pStmt = null;

		String sql = "insert into  reservation(flightNumber,airlineName,source,destination,noOfSeats,emailId) values(?,?,?,?,?,?);";
		try
		{
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, jDetails.getFlightNumber());
			pStmt.setString(2, jDetails.getAirlineName());
			pStmt.setString(3, jDetails.getSource());
			pStmt.setString(4, jDetails.getDestination());
			pStmt.setInt(5, jDetails.getNumberOfSeats());
			pStmt.setString(6, userID);
			pStmt.execute();
			con.close();
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		return MessageConstants.RESERVED_SUCCESSFULLY;
	}

	/**
	 * @Author : Pradyumna
	 * @param reservationId
	 * @return
	 */
	public String cancelTicket(String UserId, int reservationId)
	{
		EstablishConnection myConnection = new EstablishConnection();
		Connection con = myConnection.getConnection();
		PreparedStatement pStmt = null;
		String query = "delete from reservation where emailid=? and reservationid=?;";
		try
		{
			pStmt = con.prepareStatement(query);
			pStmt.setString(1, UserId);
			pStmt.setInt(2, reservationId);
			pStmt.executeUpdate();
		}
		catch (SQLException se)
		{
			se.printStackTrace();

		}

		return MessageConstants.CANCELED_SUCCESSFULLY;
	}

	/*
	 * Added by Pradyumna -- Retruns the available flights for source and
	 * destination
	 */
	public FlightDetails[] searchFlightForSourceAndDest(String source, String destination)
	{

		EstablishConnection myConnection = new EstablishConnection();
		Connection con = myConnection.getConnection();
		PreparedStatement pStmt = null;
		String flighSearchQry = null;
		String countQuery = null;
		ResultSet result = null;
		FlightDetails[] flights = null;

		if (source == null && destination == null)
		{
			countQuery = " select count(*) from flight_details;";
			flighSearchQry = "select * from flight_details;";
		}
		else
		{
			countQuery = " select count(*) from flight_details where source=? and destination=?;";
			flighSearchQry = "select * from flight_details where source=? and destination=?;";
		}

		try
		{

			pStmt = con.prepareStatement(countQuery);
			if (!(source == null && destination != null))
			{
				pStmt.setString(1, source);
				pStmt.setString(2, destination);
			}
			result = pStmt.executeQuery();
			if (result.next())
			{
				flights = new FlightDetails[result.getInt(1)];
				// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4

				pStmt = con.prepareStatement(flighSearchQry);
				pStmt.setString(1, source);
				pStmt.setString(2, destination);
				result = pStmt.executeQuery();
				int i = 0;
				while (result.next())
				{
					FlightDetails flight = new FlightDetails();
					flight.setFlightNumber(result.getInt(1));
					flight.setAirlineName(result.getString(2));
					flight.setSource(result.getString(3));
					flight.setDestination(result.getString(4));
					flight.setNumberOfSeats(result.getInt(5));
					flight.setCrewId(result.getInt(6));
					flights[i] = flight;
					i++;
				}
			}

		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		return flights;
	}

	/**
	 * @Author : Pradyumna
	 * @param reservationID
	 * @return
	 */
	public Reservation issueTicket(String userId, int reservationID)
	{
		EstablishConnection myConnection = new EstablishConnection();
		Connection con = myConnection.getConnection();
		PreparedStatement pStmt = null;
		ResultSet result = null;

		Reservation ticket = null;
		String qry = "select * from reservation where emailId=? and reservationID=?";
		try
		{
			pStmt = con.prepareStatement(qry);
			pStmt.setString(1, userId);
			pStmt.setInt(2, reservationID);
			result = pStmt.executeQuery();
			if (result.next())
			{
				ticket = new Reservation();
				ticket.setReservationId(result.getInt(1));
				ticket.setFlightNumber(result.getInt(2));
				ticket.setAirlineName(result.getString(3));
				ticket.setSource(result.getString(4));
				ticket.setDestination(result.getString(5));
				ticket.setNumberOfSeats(result.getInt(6));
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		return ticket;
	}

	/*
	 * Added by **** Returns the travellers matching the search criteria
	 */
	public Traveller[] searchTravelers(String travelerID)
	{
		EstablishConnection myConnection = new EstablishConnection();
		Connection con = myConnection.getConnection();
		PreparedStatement pStmt = null;
		ResultSet result = null;

		Traveller[] travelers = null;
		System.out.println("traveler id :" + travelerID);

		// Get the count of travellers
		String travelerCountQry = null;
		try
		{
			if (travelerID == null)
			{
				travelers = new Traveller[1];
			}
			else
			{
				travelerCountQry = "select count(*) from traveler";
				pStmt = con.prepareStatement(travelerCountQry);
				result = pStmt.executeQuery();
				if (result.next())
				{
					travelers = new Traveller[result.getInt(1)];
				}
			}

		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		// Traveler oount end

		String travelerSearchQry = "select * from traveler ";
		if (travelerID != null)
		{
			travelerSearchQry += " where travelerID=?;";
		}

		try
		{

			// #########################################################################################################################
			pStmt = con.prepareStatement(travelerSearchQry);
			if (travelerID != null)
			{
				pStmt.setString(1, travelerID);
			}
			result = pStmt.executeQuery();
			int count = 0;
			while (result.next())
			{
				Traveller t = new Traveller();
				t.setPassportNo(result.getString(1));
				t.setCustomerId(result.getString(2));
				t.setNationality(result.getString(3));

				travelers[count] = t;
				count++;
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		return travelers;
	}

	/*
	 * Added by Abinaya -- Returns the list of employees matching the search
	 * criteria
	 */
	public Employee[] searchEmployeeForID(int empID, String workDesc, String hireDate)
	{
		EstablishConnection myConnection = new EstablishConnection();
		Connection con = myConnection.getConnection();
		PreparedStatement pStmt = null;
		Employee[] empArray = new Employee[1];
		String employeeSearchQueryID;
		String employeeSearchQueryDesc;
		String employeeSearchQueryDate;

		try
		{
			if (empID != 0)
			{
				employeeSearchQueryID = "select * from employee where employeeID=?;";
				pStmt = con.prepareStatement(employeeSearchQueryID);
				pStmt.setInt(1, empID);

			}
			else if (null != workDesc)
			{
				employeeSearchQueryDesc = "select * from employee where workDescription=?;";
				pStmt = con.prepareStatement(employeeSearchQueryDesc);
				pStmt.setString(1, workDesc);
			}
			else if (null != hireDate)
			{
				employeeSearchQueryDate = "select * from employee where hireDate=?;";
				pStmt = con.prepareStatement(employeeSearchQueryDate);
				pStmt.setString(1, hireDate);
			}
			ResultSet result = null;

			result = pStmt.executeQuery();

			if (result.last())
			{
				empArray = new Employee[result.getRow()];
				result.beforeFirst();
			}

			int i = 0;
			while (result.next())
			{
				empArray[i] = new Employee();
				empArray[i].setEmployeeID(result.getInt(1));
				empArray[i].setUniqueID(result.getInt(2));
				empArray[i].setWorkDescription(result.getString(3));
				empArray[i].setPosition(result.getString(4));
				empArray[i].setHireDate(result.getString(5));
				i++;
			}

		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		return empArray;
	}

	/*
	 * Added by Darshan -- Adds a new employee to the system
	 */
	public String addEmployee(Employee employee)
	{
		String message = null;

		EstablishConnection connectionClass = null;
		Connection connection = null;

		try
		{
			connectionClass = new EstablishConnection();
			connection = connectionClass.getConnection();
			connection.setAutoCommit(false);

			Person person = new Person();
			person.setFirstName(employee.getFirstName());
			person.setLastName(employee.getLastName());
			person.setAddress(employee.getAddress());
			person.setCity(employee.getCity());
			person.setDateOfBirth(employee.getDateOfBirth());
			person.setEmailID(employee.getEmailID());
			person.setPassword(employee.getPassword());
			person.setState(employee.getState());
			person.setZipCode(employee.getZipCode());
			person.setRoleID(employee.getRoleID());
			String emailID = person.getEmailID();

			String query1 = "Select count(1) from person where emailID = ?";
			PreparedStatement preparedStmt = connection.prepareStatement(query1);

			preparedStmt.setString(1, emailID);
			ResultSet result1 = preparedStmt.executeQuery();

			while (result1.next())
			{
				if (result1.getInt(1) > 0)
				{
					message = "This email ID already exists. ";
					return message;
				}
			}

			String query2 = "Insert into person (firstName, lastName, address, city, zipCode, dateOfBirth, roleID, emailID, password, state) values (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStat2 = connection.prepareStatement(query2);

			preparedStat2.setString(1, person.getFirstName());
			preparedStat2.setString(2, person.getLastName());
			preparedStat2.setString(3, person.getAddress());
			preparedStat2.setString(4, person.getCity());
			preparedStat2.setString(5, person.getZipCode());
			preparedStat2.setString(6, person.getDateOfBirth());
			preparedStat2.setInt(7, person.getRoleID());

			preparedStat2.setString(8, person.getEmailID());
			preparedStat2.setString(9, encrypt(person.getPassword()));
			preparedStat2.setString(10, person.getState());

			preparedStat2.executeUpdate();

			try
			{
				String query = "update employee , person set workDescription = ? , position = ? , hiredate= ? where emailID = ? and employee.uniqueID = person.uniqueID";

				System.out.println("In MC " + employee.getWorkDescription());
				PreparedStatement prepStmt = connection.prepareStatement(query);
				prepStmt.setString(1, employee.getWorkDescription());
				prepStmt.setString(2, employee.getPosition());

				prepStmt.setString(3, "10/10/10");
				prepStmt.setString(4, employee.getEmailID());

				int count = prepStmt.executeUpdate();
				if (count > 0)
					message = "Employee added successfully to system";
				else
					message = "Error while adding a new employee!";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				connection.rollback();
				message = "Some error while registering Customer!";
			}

			connectionClass.endConnection(connection);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			message = "Some error while registering Customer!";
		}
		return message;
	}
}
