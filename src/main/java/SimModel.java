import java.util.*;

public class SimModel {
    // Declaration of Simulation Methods

    // Simulation Model Variables
    public static int Clock;
    public static Random RNGInspectionComponent1, RNGInspectionComponent2, RNGInspectionComponent3,RNGWorkstation1,RNGWorkstation2,RNGWorkstation3;      // Variables for random numbers generated for each event
    private static Queue<SimEvent> FEL;                                             // This is the FEL
    public static Queue<Component> W1C1Q, W2C1Q, W2C2Q, W3C1Q, W3C3Q,I1,I2;         // Queue lines for the buffer unit
    private static boolean isW1C1QBusy, isW2C1QBusy, isW2C2QBusy, isW3C1QBusy, isW3C3QBusy, isW1BUsy, isW2Busy, isW3Busy;
    private static boolean isI1Busy, isI2Busy;

    // Inputs
    public static double[][] servinp1 = {{5,0.35},{15,0.78},{25,0.93},{50,0.99},{80,1}};
    public static double[][] servinp2 = {{5,0.22},{15,0.61},{25,0.81},{50,0.7},{115,1}};
    public static double[][] servinp3 = {{5,0.19},{15,0.49},{25,0.71},{50,0.92},{105,1}};
    public static double[][] ws1 = {{3,0.49},{12,0.92},{30,1}};
    public static double[][] ws2 = {{3,0.26},{12,0.69},{30,0.91},{60,1}};
    public static double[][] ws3 = {{3,0.26},{12,0.75},{30,0.96},{54,1}};

    public SimModel() {
        Initialization();
    }

    // According to milestone2 frequency list, generate random numbers for input
    private static Integer getRandomTime(double TD[][], Random RNV) {
        Integer randomTime = -1;
        double temp = RNV.nextDouble();
        for (int i = 0; i < TD.length; i++){
            if (Double.compare(temp,TD[i][1]) < 0) {
                randomTime = ((int) TD[i][0]);
                break;
            }
        }
        return randomTime;
    }

    private static void Initialization() {
        // RNG initialization
        RNGInspectionComponent1 = new Random();
        RNGInspectionComponent2 = new Random();
        RNGInspectionComponent3 = new Random();
        RNGWorkstation1 = new Random();
        RNGWorkstation2 = new Random();
        RNGWorkstation3 = new Random();

        //Buffer initialization
        FEL = new PriorityQueue<SimEvent>();            // Initializing the FEL
        W1C1Q = new LinkedList<>();
        W2C1Q = new LinkedList<>();
        W2C2Q = new LinkedList<>();
        W3C1Q = new LinkedList<>();
        W3C3Q = new LinkedList<>();
        I1 = new LinkedList<>();
        I2 = new LinkedList<>();

        Clock = 0;

        // Creating workstations
        Workstation W1 = new Workstation(1);
        Workstation W2 = new Workstation(2);
        Workstation W3 = new Workstation(3);
        // Creating Inspectors
        Inspector INSPECTOR1 = new Inspector(1);
        Inspector INSPECTOR2 = new Inspector(2);
        // Creating Components
        Component Component1 = new Component(1);
        Component Component2 = new Component(2);
        Component Component3 = new Component(3);

        // set up any other initial settings here ;)
        System.out.print("\n-----------------------------------------------------------\n");
        System.out.print("Initial state of the simulation\n");
        System.out.print("Component1 at Inspector1, but no components in the buffers yet.\n");
        System.out.print("Component2 at Inspector2, but no components in the buffers yet.\n");

        Component1.setWhichService(Component.serviceType.INSPECTOR1);
        Component2.setWhichService(Component.serviceType.INSPECTOR2);


        // Creating the first event (if needed)
        //SimEvent first = new SimEvent(SimEvent.eventType.ALQ,getRandomTime())
    }


    //Main function
    //
    public static void main(String [] args) {
        Initialization();
        GenerateReport();
    }

    // Process the components and send them to the corresponding buffers in workstation
    //
    private static void addComponet() {

    }

    private static void ProcessAI(SimEvent evt){
        System.out.print(" event = Component " + evt.getComponent().getID() + "arrives at Inspector" + evt.getInspector().getID());
        // if component 1, sent to inspector 1; else, sent to inspector2

        if (evt.getComponent().getID() == 1){
            if ((I1.isEmpty()) && !isI1Busy) {
                isI1Busy = true;
                evt.getComponent().setWhichService(Component.serviceType.INSPECTOR1);
            } else if (isI1Busy){

            }
        } else{
            evt.getComponent().setWhichService(Component.serviceType.INSPECTOR2);
        }
        ScheduleEvent(SimEvent.eventType.AI,evt.getComponent());

    }

    private static void ProcessEI(SimEvent evt){
        System.out.print(" event = Component " + evt.getComponent().getID() + "leaves Inspector" + evt.getInspector().getID());
        //if ()
    }

    private static void ProcessEW(SimEvent imminentEVT) {
    }

    private static void ProcessAW(SimEvent imminentEVT) {
    }

    // Schedules events with their components
    private static void ScheduleEvent(SimEvent.eventType type, Component component) {
        Integer newRN = -1;
        switch (type) {
            case AI: // Arrive at Inspector
                newRN = getRandomTime(servinp1,RNGInspectionComponent1);   // These need to be changed
                break;
            case EI: // End Inspection
              //  newRN = getRandomTime(LTD,RNGloading); // These need to be changed
                break;
            case AW: // Arrive at Workstation
              //  newRN = getRandomTime(WTD,RNGscale);     // These need to be changed
                break;
            case EW: // End Workstation
                //newRN = getRandomTime();
        }
        checkSimDay(newRN);
        SimEvent newEVT = new SimEvent(type,Clock+newRN, component, null);
        System.out.print(" => new event = " + newEVT.geteType() + " time " + newEVT.geteTime() + " truck " + newEVT.getComponent().getID());
        FEL.offer(newEVT);
    }

    // Processes events
    private static void processSimEvent(SimEvent imminentEVT) {
        System.out.print("Processing events...");
        switch (imminentEVT.geteType()) {
            case AI:
                ProcessAI(imminentEVT);
                break;
            case EI:
                ProcessEI(imminentEVT);
                break;
            case AW:
                ProcessAW(imminentEVT);
                break;
            case EW:
                ProcessEW(imminentEVT);
        }
        System.out.print("\n");
    }

    // Not implemented yet
    private static void checkSimDay(Integer newRN) {
    }

    // Generate the report
    //
    private static void GenerateReport() {
        System.out.print("\n-----------------------------------------------------------\n");
        System.out.print("Statistics\n");
    }
}



 
