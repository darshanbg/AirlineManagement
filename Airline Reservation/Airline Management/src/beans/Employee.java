package beans;

import java.io.Serializable;

public class Employee extends Person implements Serializable
{
	int employeeID;
	String workDescription;
	String position;
	String hireDate;

	public int getEmployeeID()
	{
		return employeeID;
	}

	public void setEmployeeID(int employeeID)
	{
		this.employeeID = employeeID;
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

	public String getHireDate()
	{
		return hireDate;
	}

	public void setHireDate(String hireDate)
	{
		this.hireDate = hireDate;
	}

}
