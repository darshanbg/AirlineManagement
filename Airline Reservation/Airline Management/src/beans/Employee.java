package beans;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable
{

	int employeeID;
	int uniqueID;
	String workDescription;
	String position;
	Date hireDate;

	public int getEmployeeID()
	{
		return employeeID;
	}

	public void setEmployeeID(int employeeID)
	{
		this.employeeID = employeeID;
	}

	public int getUniqueID()
	{
		return uniqueID;
	}

	public void setUniqueID(int uniqueID)
	{
		this.uniqueID = uniqueID;
	}

	public String getWorkDescription()
	{
		return workDescription;
	}

	public void setWorkDescription(String workDescription)
	{
		this.workDescription = workDescription;
	}

	public String getPosition()
	{
		return position;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public Date getHireDate()
	{
		return hireDate;
	}

	public void setHireDate(Date hireDate)
	{
		this.hireDate = hireDate;
	}

}
