// Superclass of all components
public class Component {
  
  public enum serviceType {OFF,INSPECTOR1,INSPECTOR2,WORKSTATION1,WORKSTATION2,WORKSTATION3,BUFFER11,BUFFER21,BUFFER22,BUFFER31,BUFFER33,WAITING};
  private int type;  // Component type. 1 for 1, 2 for 2.. so on.

  public Component(int type) {
    this.type = type;
    this.whichService = serviceType.OFF;
  }

  private serviceType whichService;
  
  public int getType() {
      return type;
  }

  public void setType(int type) {
      this.type = type;
  }

  public serviceType getWhichService() {
      return whichService;
  }

  public void setWhichService(serviceType whichService) {
      this.whichService = whichService;
  }
}
