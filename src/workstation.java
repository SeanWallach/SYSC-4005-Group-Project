public class workstation {

    public enum serviceType {OFF,LOADER1,LOADER2,SCALE,TRAVEL,WAITING};

    public dumpTruck(int ID) {
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
