/**
 * AirlineServer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package airlineSystem;

public interface AirlineServer extends java.rmi.Remote {
    public java.lang.String login(java.lang.String userName, java.lang.String password) throws java.rmi.RemoteException;
    public java.lang.String[] fetchStateList() throws java.rmi.RemoteException;
    public java.lang.String registerCustomer(beans.Person person) throws java.rmi.RemoteException;
    public java.lang.String deleteCustomer(java.lang.String emailID) throws java.rmi.RemoteException;
    public java.lang.String addEmployee(beans.Employee employee) throws java.rmi.RemoteException;
}
