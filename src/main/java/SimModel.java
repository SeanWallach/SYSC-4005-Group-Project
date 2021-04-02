import java.util.*;

public class SimModel {
    // Declaration of Simulation Methods

    // Simulation Model Variables
    public static int Clock, workdayHours, currentDay, simDays;
    public static Random RNGInspectionComponent1, RNGInspectionComponent2, RNGInspectionComponent3,RNGWorkstation1,RNGWorkstation2,RNGWorkstation3;      // Variables for random numbers generated for each event
    public static int LastI1IdleTime, LastI2IdleTime;
    private static Queue<SimEvent> FEL;                                             // This is the FEL
    public static Queue<Component> W1C1Q, W2C1Q, W2C2Q, W3C1Q, W3C3Q,I1Q,I2Q;         // Queue lines for the buffer unit
    private static boolean isW1C1QBusy, isW2C1QBusy, isW2C2QBusy, isW3C1QBusy, isW3C3QBusy, isW1BUsy, isW2Busy, isW3Busy;
    private static boolean isI1Busy, isI2Busy;

    // Inputs
    public static double[][] servinp1 = {{5,0.35},{15,0.78},{25,0.93},{50,0.99},{80,1}};
    public static double[][] servinp2 = {{5,0.22},{15,0.61},{25,0.81},{50,0.7},{115,1}};
    public static double[][] servinp3 = {{5,0.19},{15,0.49},{25,0.71},{50,0.92},{105,1}};
    public static double[][] ws1 = {{3,0.49},{12,0.92},{30,1}};
    public static double[][] ws2 = {{3,0.26},{12,0.69},{30,0.91},{60,1}};
    public static double[][] ws3 = {{3,0.26},{12,0.75},{30,0.96},{54,1}};

    private static double BI, BW;                                           // Total busy time for the two inspectors and the workstations, respectively
    private static double UI, UW;                                           // Utilization for the two inspectors and the workstations, respectively

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

        Clock = 0;
        currentDay = 1;
        workdayHours = 8;
        LastI1IdleTime = 0;
        LastI2IdleTime = 0;
        simDays = 10; // For now

        BI = 0.0;
        BW = 0.0;
        UI = 0.0;
        UW = 0.0;

        // Initializing the FEL and waiting queues
        FEL = new PriorityQueue<>();
        W1C1Q = new LinkedList<>();
        W2C1Q = new LinkedList<>();
        W2C2Q = new LinkedList<>();
        W3C1Q = new LinkedList<>();
        W3C3Q = new LinkedList<>();
        I1Q = new LinkedList<>();
        I2Q = new LinkedList<>();
      
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
        System.out.print("\n-----------------------------------------------------------\n");
        System.out.print("Day " + currentDay +"\n");
        while ((currentDay <= simDays) && !(FEL.isEmpty())) {
            SimEvent imminentEVT = FEL.poll();
            if (imminentEVT != null) {
                Clock = imminentEVT.geteTime();
                System.out.print("Clock = " + Clock);
                ProcessSimEvent(imminentEVT);
            }
        }
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
            if ((I1Q.isEmpty()) && !isI1Busy) {
                if (isI1Busy){
                    evt.getComponent().setWhichService(Component.serviceType.WAITING);
                    I1Q.offer(evt.getComponent())  ;
                }else {
                    isI1Busy = true;
                    evt.getComponent().setWhichService(Component.serviceType.INSPECTOR1);
                    LastI1IdleTime = Clock;
                    ScheduleEvent(SimEvent.eventType.EI,evt.getComponent());
                }
            }
        } else {
            if ((I2Q.isEmpty()) && !isI2Busy) {
                if (isI2Busy) {
                    evt.getComponent().setWhichService(Component.serviceType.WAITING);
                    I1Q.offer(evt.getComponent());
                } else {
                    isI2Busy = true;
                    evt.getComponent().setWhichService(Component.serviceType.INSPECTOR2);
                    LastI2IdleTime = Clock;
                    ScheduleEvent(SimEvent.eventType.EI, evt.getComponent());
                }
            }
        }
    }

    private static void ProcessEI(SimEvent evt){
        System.out.print(" event = Component " + evt.getComponent().getID() + "leaves Inspector" + evt.getInspector().getID());

        if (evt.getComponent().getID() == 1) {
            if (W1C1Q.size() < 2){
                    W1C1Q.offer(evt.getComponent());
                    evt.getComponent().setWhichService(Component.serviceType.BUFFER11);
            } else if (W1C1Q.size() == 2 && W2C1Q.size() < 2) {
                W2C1Q.offer(evt.getComponent());
                evt.getComponent().setWhichService(Component.serviceType.BUFFER21);
            } else if (W1C1Q.size() == 2 && W2C1Q.size() == 2 && W3C1Q.size() < 2) {
                W3C1Q.offer(evt.getComponent());
                evt.getComponent().setWhichService(Component.serviceType.BUFFER31);
            } else {
                evt.getComponent().setWhichService(Component.serviceType.WAITING);
            }

        } else if (evt.getComponent().getID() == 2) {
            if (W2C2Q.size() < 2){
                W2C2Q.offer(evt.getComponent());
                evt.getComponent().setWhichService(Component.serviceType.BUFFER22);
            } else {
                evt.getComponent().setWhichService(Component.serviceType.WAITING);
            }
        } else {
            if (W2C2Q.size() < 2){
                W2C2Q.offer(evt.getComponent());
                evt.getComponent().setWhichService(Component.serviceType.BUFFER22);
            } else {
                evt.getComponent().setWhichService(Component.serviceType.WAITING);
            }

        }

        // Moving the next component from In to the idle loader, or set the loader status to idle if the queue is empty.
        Component.serviceType currentService = evt.getComponent().getWhichService();
        Component movingI1Q = I1Q.poll();
        Component movingI2Q = I2Q.poll();
        if (currentService == Component.serviceType.INSPECTOR1){
            if (!I1Q.isEmpty()) {
                isI1Busy = true;
                movingI1Q.setWhichService(Component.serviceType.INSPECTOR1);
            }  else {
                BI += Clock - LastI1IdleTime;
                isI1Busy=false;
            }
        } else if (currentService == Component.serviceType.INSPECTOR2){
            if (!I2Q.isEmpty()) {
                isI2Busy = true;
                movingI2Q.setWhichService(Component.serviceType.INSPECTOR2);
            } else {
                BI += Clock - LastI2IdleTime;
                isI2Busy=false;
            }
        } else {
           // break;            // break only works if you are in a loop
        }
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
    private static void ProcessSimEvent(SimEvent imminentEVT) {
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
        UI = (BI/Clock)*100;
        UW = (BW/Clock)*100;

        System.out.print("\n-----------------------------------------------------------\n");
        System.out.print("Statistics\n");
        System.out.print("Inspectors Utilization = " + UI + "\n");
        System.out.print("Workstation Utilization = " + UW + "\n");
    }
}



 
