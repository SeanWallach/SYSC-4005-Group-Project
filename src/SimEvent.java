public class SimEvent implements Comparable<SimEvent>{

  public static enum eventType {AI,EI,AW,EW};  // AI - arrive at Inspector    EI - end inspect    AW - arrive at workstation   EW - depart workstation
  private eventType eType;
  private Integer eTime;           // Event time
  private Component componentID;   // which component is this event for
  private Product productID;  // which product is this event for
  
  public SimEvent () {
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

  public Component getComponentID() {
      return component;
  }

  public void setComponentID(Component component) {
      this.componentID = component;
  }
  
  public Product getProductID() {
      return product;
  }

  public void setProductID(Product product) {
      this.productID = product;
  }
}
