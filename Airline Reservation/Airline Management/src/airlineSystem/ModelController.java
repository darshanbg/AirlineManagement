package airlineSystem;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import sun.misc.BASE64Encoder;
import beans.Employee;
import beans.Person;
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

			message = new ModelController().registerCustomer(person);

			if (message.equals("Successfully Registered!"))
			{
				String query = "update employee , person set workDescription = ? , position = ? , hiredate= ? where emailID = ? ";

				PreparedStatement prepStmt = connection.prepareStatement(query);
				prepStmt.setString(1, employee.getWorkDescription());
				prepStmt.setString(2, employee.getPosition());
				prepStmt.setString(3, employee.getHireDate());
				prepStmt.setString(4, employee.getEmailID());

				int count = prepStmt.executeUpdate();

				if (count > 0)
					message = "Employee added successfully to system";
				else
					message = "Error while adding a new employee!";

				connectionClass.endConnection(connection);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			message = "Error while adding a new Employee!!";
		}

		return message;
	}
}
