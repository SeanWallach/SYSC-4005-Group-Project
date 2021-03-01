public class Inspector {
  private enum status {off, BUSY}        // Can only ever be off or busy inspecting
  private int ID;                        // Can only have 2 inspectors
  
  public Inspector(int ID) {
    this.ID = ID;
  }
  
  // Inspects the component and returns it
  public Component inspect(Component c) {
    return c;
  }
}
