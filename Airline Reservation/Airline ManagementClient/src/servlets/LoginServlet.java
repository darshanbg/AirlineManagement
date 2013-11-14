package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Person;

public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public LoginServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		System.out.println("Login1");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		String message = null;
		System.out.println("Inside Post Method");
		try
		{
			airlineSystem.AirlineServerProxy proxy = new airlineSystem.AirlineServerProxy();
			proxy.setEndpoint("http://localhost:8080/Airline_Management/services/AirlineServer?wsdl");

			if (request.getParameter("Login").equals("Register"))
			{
				String emailID = request.getParameter("emailID");
				String firstName = request.getParameter("firstName");
				String lastName = request.getParameter("lastName");
				String password = request.getParameter("password");
				String address = request.getParameter("address");
				String city = request.getParameter("city");
				String zipCode = request.getParameter("zipCode");
				String state = request.getParameter("selectedState");
				String dateOfBirth = request.getParameter("DOB");

				Person person = new Person();

				person.setEmailID(emailID);
				person.setFirstName(firstName);
				person.setLastName(lastName);
				person.setPassword(password);
				person.setAddress(address);
				person.setCity(city);
				person.setZipCode(zipCode);
				person.setState(state);
				person.setDateOfBirth(dateOfBirth);
				person.setRoleID(2);

				message = proxy.registerCustomer(person);

				request.setAttribute("message", message);

				request.getRequestDispatcher("Login.jsp").forward(request, response);

			}

			if (request.getParameter("Login").equals("Login"))
			{

				String userName = request.getParameter("userName");
				String password = request.getParameter("password");

				if (userName.length() == 0 || password.length() == 0)
				{
					message = "Invalid," + "Please enter User Name and Password";
				}
				else
				{
					message = proxy.login(userName, password);

					System.out.println(message);

					StringTokenizer tokenizer = new StringTokenizer(message, ",");
					String[] resultArray = new String[tokenizer.countTokens()];
					int count = 0;

					while (tokenizer.hasMoreTokens())
					{
						resultArray[count++] = tokenizer.nextToken();
					}

					if (resultArray[0].equalsIgnoreCase("Success"))
					{
						HttpSession session = request.getSession();

						String fName = resultArray[1];
						session.setAttribute("fName", fName);

						response.sendRedirect("Home.jsp");
					}
					else
					{
						message = "Incorrect Credentials";
						request.setAttribute("message", message);
						request.getRequestDispatcher("Login.jsp").forward(request, response);
					}
				}
			}

			if (request.getParameter("Login").equals("Remove"))
			{
				System.out.println("Remove");

				message = proxy.deleteCustomer("a@smtith.com");
				System.out.println(message);
			}

			if (request.getParameter("Login").equals("Add Employee"))
			{
				// Employee emp = new Employee();
				//
				// emp.setAddress("address");
				// emp.setCity("city");
				// emp.setDateOfBirth("10/10/2013");
				// emp.setEmailID("test12@email.com");
				// emp.setFirstName("firstName");
				// emp.setLastName("lastName");
				// emp.setHireDate("11/11/2013");
				// emp.setLastName("lastName");
				// emp.setPassword("password");
				// emp.setPosition("Pilot");
				// emp.setRoleID(1);
				// emp.setState("California");
				// emp.setWorkDescription("Co-pilot on Air Bus");
				// emp.setZipCode("31263");
				//
				// message = proxy.addEmployee(emp);
				// System.out.println(message);

			}

			if (request.getParameter("Login").equals("Search Employee"))
			{

				System.out.println("In Search");

				try
				{
					System.out.println("First");
					int empID = 0;
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Calendar cal = null;
					String strDate = request.getParameter("hireDate");
					if (null != strDate)
					{
						try
						{
							cal = Calendar.getInstance();
							cal.setTime(dateFormat.parse(strDate));
						}
						catch (Exception e)
						{
							cal = null;
						}
					}

					String strEmpID = request.getParameter("empID");
					System.out.println(strEmpID);
					if (strEmpID != null && !strEmpID.isEmpty())
					{
						empID = Integer.parseInt(strEmpID);
					}
					String desc = request.getParameter("description");

					if (empID == 0 && desc == null && cal == null)
					{
						request.getSession().setAttribute("emp", null);
						RequestDispatcher rd = request.getRequestDispatcher("/SearchEmployee.jsp");
						rd.forward(request, response);
					}

					System.out.println("Make a call");
					Object[] eArray = proxy.searchEmployeeForID(empID,
							request.getParameter("description"), cal);

					List eList = null;

					if (eArray != null)
					{
						eList = Arrays.asList(eArray);
						request.getSession().setAttribute("emp", eList);
					}
					else
					{
						request.getSession().setAttribute("emp", null);
					}

					// if (empID != 0) {
					// request.getSession().setAttribute("emp", eList);
					// } else {
					// request.getSession().setAttribute("emp", null);
					// }

					RequestDispatcher rd = request.getRequestDispatcher("/SearchEmployee.jsp");
					rd.forward(request, response);
				}

				catch (Exception e)
				{
					e.printStackTrace();
					RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
					rd.forward(request, response);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
