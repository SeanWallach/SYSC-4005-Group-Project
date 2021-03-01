import java.util.*;

public class SimModel {
    // Declaration of Simulation Methods

    // Simulation Model Variables
    
    private static Queue<SimEvent> FEL;                                             // This is the FEL
    public static Queue<workstation> W1C1Q, W2C1Q, W2C2Q, W3C1Q, W3C3Q;             // Queue lines for the buffer unit
    private static boolean isW1C1QBusy, isW2C1QBusy, isW2C2QBusy, isW3C1QBusy, isW3C3QBusy;                            

    // Inputs
    public static double[][] C1 = {{},{},{}};                      
    public static double[][] C2 = {{},{}}};                              
    public static double[][] C3 = {{},{},{},{}};

   

    //
    //
    private static void Initialization() {
        
       

        // Creating workstations
        workstation W1 = new workstation(1);
        workstation W2 = new workstation(2);
        workstation W3 = new workstation(3);
       

        // set up any other initial settings here ;)
        

        // Creating the first event (if needed)
        //SimEvent first = new SimEvent(SimEvent.eventType.ALQ,getRandomTime())
    }


    //Main function
    //
    public static void main() {
       
        
        Initialization();

        

        GenerateReport();
    }

    // Process the components and send them to the corresponding buffers in workstation
    //
    private static void addComponet() {

    }
    
    
    // Generate the report
    //
    private static void GenerateReport() {
        
    }

 
