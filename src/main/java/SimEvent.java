public class SimEvent implements Comparable<SimEvent>{

    @Override
    public int compareTo(SimEvent o) {
        return 0;
    }
    public static enum eventType {AI,EI,AW,EW};  // AI - arrive at Inspector    EI - end inspect    AW - arrive at workstation   EW - depart workstation
    private eventType eType;
    private Integer eTime;           // Event time
    private Component component;   // which component is this event for
    private Product product;  // which product is this event for
    private Inspector inspector;

    public SimEvent (eventType eType, int eTime, Component componentID, int inspectorID) {
        this.eType = eType;
        this.eTime = eTime;
        this.component = component;
        this.inspector = inspector;
        this.product = product;
    }

    public eventType geteType() {
      return eType;
    }

    public void seteType(eventType eType) {
      this.eType = eType;
    }

    public Integer geteTime() {
      return eTime;
    }

    public void seteTime(int eTime) {
      this.eTime = eTime;
    }

    public Component getComponent() {
      return component;
    }

    public void setComponent(Component component) {
      this.component = component;
    }

    public Product getProduct() {
      return product;
    }

    public void setProduct(Product product) {
      this.product = product;
  }

    public Inspector getInspector() {
        return inspector;
    }
}
