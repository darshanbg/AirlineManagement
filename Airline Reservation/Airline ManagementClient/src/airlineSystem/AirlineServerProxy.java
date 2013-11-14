package airlineSystem;

public class AirlineServerProxy implements airlineSystem.AirlineServer {
  private String _endpoint = null;
  private airlineSystem.AirlineServer airlineServer = null;
  
  public AirlineServerProxy() {
    _initAirlineServerProxy();
  }
  
  public AirlineServerProxy(String endpoint) {
    _endpoint = endpoint;
    _initAirlineServerProxy();
  }
  
  private void _initAirlineServerProxy() {
    try {
      airlineServer = (new airlineSystem.AirlineServerServiceLocator()).getAirlineServer();
      if (airlineServer != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)airlineServer)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)airlineServer)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (airlineServer != null)
      ((javax.xml.rpc.Stub)airlineServer)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public airlineSystem.AirlineServer getAirlineServer() {
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer;
  }
  
  public java.lang.String login(java.lang.String userName, java.lang.String password) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.login(userName, password);
  }
  
  public beans.Reservation[] listAllReservation() throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.listAllReservation();
  }
  
  public beans.Traveller[] listAllCustomers() throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.listAllCustomers();
  }
  
  public beans.Employee[] listAllEmployees() throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.listAllEmployees();
  }
  
  public java.lang.String processPayement(java.lang.String cardID) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.processPayement(cardID);
  }
  
  public java.lang.String issueFlightTicket(beans.FlightDetails flightDetails) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.issueFlightTicket(flightDetails);
  }
  
  public java.lang.String cancelReservation(beans.Reservation reservation) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.cancelReservation(reservation);
  }
  
  public java.lang.String[] fetchStateList() throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.fetchStateList();
  }
  
  public java.lang.String registerCustomer(beans.Person person) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.registerCustomer(person);
  }
  
  public java.lang.String createNewReservation(beans.FlightDetails flightDetails) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.createNewReservation(flightDetails);
  }
  
  public java.lang.String deleteEmployee(java.lang.String emailID) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.deleteEmployee(emailID);
  }
  
  public java.lang.String deleteCustomer(java.lang.String emailID) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.deleteCustomer(emailID);
  }
  
  public beans.FlightDetails[] listAllFlights() throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.listAllFlights();
  }
  
  public java.lang.String updateTravellerInfo(beans.Traveller traveller) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.updateTravellerInfo(traveller);
  }
  
  public java.lang.String updateEmployeeInfo(beans.Employee emp) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.updateEmployeeInfo(emp);
  }
  
  public java.lang.String updateFlightDetails(beans.FlightDetails flight) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.updateFlightDetails(flight);
  }
  
  public beans.Employee[] searchEmployeeForID(int empID, java.lang.String workDesc, java.util.Calendar hireDate) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.searchEmployeeForID(empID, workDesc, hireDate);
  }
  
  public beans.Traveller[] findPassengersOnBoard(beans.FlightDetails flight) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.findPassengersOnBoard(flight);
  }
  
  public beans.FlightDetails findFlights(beans.FlightDetails flight) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.findFlights(flight);
  }
  
  public java.lang.String addEmployee(beans.Employee employee) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.addEmployee(employee);
  }
  
  
}