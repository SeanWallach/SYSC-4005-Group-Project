public class Workstation {

    public enum serviceType {IDLE,BUSY,OFF};

    public Workstation(int ID) {
        this.ID = ID;
        this.whichService = serviceType.OFF;
    }
    
    private int ID;
    private serviceType whichService;

    public void assembleProduct(Component1 c1) {
    }
    
    public void assembleProduct(Component1 c1, Component2 c2){
    }
    
    public void assembleProduct(Component1 c1, Component3 c3){
    }    
    
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
