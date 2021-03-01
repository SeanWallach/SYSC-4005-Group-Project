// Superclass of all products
public class Product {
  
  public enum serviceType {OFF,TRAVEL,WAITING};
  
  public Product(int ID) {
    this.ID = ID
    this.whichService = serviceType.OFF;
  }
  
  private int ID;
  private serviceType whichService;
  
  public int getID() {
        return ID;
  }

  public void setID(int ID) {
      this.ID = ID;
  }

  public serviceType getWhichService() {
      return whichService;
  }

  public void setWhichService(serviceType whichService) {
      this.whichService = whichService;
  }
}
