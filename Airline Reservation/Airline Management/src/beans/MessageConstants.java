package beans;

import java.io.Serializable;

@SuppressWarnings("serial")
// error or success messages to be displayed
public class MessageConstants implements Serializable
{
	// Ex: No of seats exceeds the available:
	public static final String FLIGHT_SEAT_EXCEED = "Sorry customer,Numbers of seats exceeds the availability ";
	public static final String INVALID_LOGIN = "Invalid Credentials!!";
	public static final String REGESTERED_SUCCESSFULLY = "Registeration Successfull. Please login to continue.";
	public static final String RESERVED_SUCCESSFULLY = "BOOKED FLIGHT TICKET SUCCESSFULLY.";

}
