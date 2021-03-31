// Superclass of all components
public class Component {
  
  public enum serviceType {OFF,INSPECTOR1,INSPECTOR2,WORKSTATION1,WORKSTATION2,WORKSTATION3,BUFFER1,BUFFER2,WAITING};
  
  public Component(int ID) {
    this.ID = ID;
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