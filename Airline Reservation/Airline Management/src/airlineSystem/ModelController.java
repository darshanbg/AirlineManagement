package airlineSystem;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import sun.misc.BASE64Encoder;
import beans.Employee;
import beans.FlightDetails;
import beans.MessageConstants;
import beans.Person;
import beans.Traveller;
import connection.EstablishConnection;

public class ModelController
{

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

			String query = "SELECT PASSWORD, FIRSTNAME FROM PERSON WHERE EMAILID = ?";

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

				if (encryptedPassword.equals(savedPassword))
				{
					message = "Success," + firstName;
					connectionClass.endConnection(connection);
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
		}

		return message;
	}

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

			String query = "delete a,b from person a, traveller b where a.uniqueID = b.uniqueID and a.emailID = ?";

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

	public String addEmployee(Employee employee)
	{
		String message = null;

		EstablishConnection connectionClass = null;
		Connection connection = null;

		// try
		// {
		// connectionClass = new EstablishConnection();
		// connection = connectionClass.getConnection();
		// connection.setAutoCommit(false);
		//
		// Person person = new Person();
		// // person.setFirstName(employee.getFirstName());
		// // person.setLastName(employee.getLastName());
		// // person.setAddress(employee.getAddress());
		// // person.setCity(employee.getCity());
		// // person.setDateOfBirth(employee.getDateOfBirth());
		// // person.setEmailID(employee.getEmailID());
		// // person.setPassword(employee.getPassword());
		// // person.setState(employee.getState());
		// // person.setZipCode(employee.getZipCode());
		// // person.setRoleID(employee.getRoleID());
		//
		// message = new ModelController().registerCustomer(person);
		//
		// if (message.equals("Successfully Registered!"))
		// {
		// String query =
		// "update employee , person set workDescription = ? , position = ? , hiredate= ? where emailID = ? ";
		//
		// PreparedStatement prepStmt = connection.prepareStatement(query);
		// prepStmt.setString(1, employee.getWorkDescription());
		// prepStmt.setString(2, employee.getPosition());
		// prepStmt.setString(3, employee.getHireDate());
		// prepStmt.setString(4, employee.getEmailID());
		//
		// int count = prepStmt.executeUpdate();
		//
		// if (count > 0)
		// message = "Employee added successfully to system";
		// else
		// message = "Error while adding a new employee!";
		//
		// connectionClass.endConnection(connection);
		// }
		// }
		// catch (Exception e)
		// {
		// e.printStackTrace();
		// message = "Error while adding a new Employee!!";
		// }

		return message;
	}

	/*
	 * Get all reservation of USER OR THE SYSTEM
	 */
	public FlightDetails[] getAllReservations(String userId)
	{
		EstablishConnection myConnection = new EstablishConnection();
		Connection con = myConnection.getConnection();
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		FlightDetails[] tickets = null;

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
			CountQuery += " where emailId=?;";
		}
		else
		{
			CountQuery += ";";
		}
		try
		{
			pStmt = con.prepareStatement(CountQuery);
			pStmt.setString(1, userId);
			rs = pStmt.executeQuery();
			if (rs.next())
			{
				tickets = new FlightDetails[rs.getInt(1)];
				// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
				pStmt = con.prepareStatement(qry);
				pStmt.setString(1, userId);
				rs = pStmt.executeQuery();
				int i = 0;
				while (rs.next())
				{
					FlightDetails ticket = new FlightDetails();
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

	public String reserveTicket(FlightDetails jDetails, String userID)
	{
		EstablishConnection myConnection = new EstablishConnection();
		Connection con = myConnection.getConnection();
		PreparedStatement pStmt = null;

		String sql = "insert into  reservation values(?,?,?,?,?,?);";
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

	public FlightDetails issueTicket(String userId, int flightNo)
	{
		EstablishConnection myConnection = new EstablishConnection();
		Connection con = myConnection.getConnection();
		PreparedStatement pStmt = null;
		ResultSet result = null;

		FlightDetails ticket = null;
		String qry = "select * from reservation where emailId=? and flightNumber=?";
		try
		{
			pStmt = con.prepareStatement(qry);
			pStmt.setString(1, userId);
			pStmt.setInt(2, flightNo);
			result = pStmt.executeQuery();
			if (result.next())
			{
				ticket = new FlightDetails();
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

	public Employee[] searchEmployeeForID(int empID, String workDesc, Calendar hireDate)
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
				employeeSearchQueryID = "select * from emp where employeeID=?;";
				pStmt = con.prepareStatement(employeeSearchQueryID);
				pStmt.setInt(1, empID);

			}
			else if (null != workDesc)
			{
				employeeSearchQueryDesc = "select * from emp where workDescription=?;";
				pStmt = con.prepareStatement(employeeSearchQueryDesc);
				pStmt.setString(1, workDesc);
			}
			else if (null != hireDate)
			{
				employeeSearchQueryDate = "select * from emp where hireDate=?;";
				pStmt = con.prepareStatement(employeeSearchQueryDate);
				pStmt.setDate(1, (Date) hireDate.getTime());
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
				empArray[i].setHireDate(result.getDate(5));
				i++;
			}

		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		return empArray;
	}
}
