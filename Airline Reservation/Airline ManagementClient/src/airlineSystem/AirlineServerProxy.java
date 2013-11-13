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
  
  public java.lang.String deleteCustomer(java.lang.String emailID) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.deleteCustomer(emailID);
  }
  
  public java.lang.String addEmployee(beans.Employee employee) throws java.rmi.RemoteException{
    if (airlineServer == null)
      _initAirlineServerProxy();
    return airlineServer.addEmployee(employee);
  }
  
  
}