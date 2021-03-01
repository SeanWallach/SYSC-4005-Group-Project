public class Workstation {

    public enum serviceType {IDLE,BUSY,OFF};
    // a buffer of capacity 2 is needed here, for Component objects waiting in queue
    
    public Workstation(int ID) {
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
