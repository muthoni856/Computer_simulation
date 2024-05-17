import java.util.PriorityQueue;
import java.util.Queue;

public class DiscreteEventSimulation {
    public static void main(String[] args) {
        Queue<Event> eventQueue = new PriorityQueue<>(); //Priority queue for managing events
        double currentTime = 0; //Current simulation time
        int queueLength = 0; // Length of the queue
        boolean serverBusy = false; // Flag to indicate if the server is busy
        double serviceTimeMean = 5.0;
        double simulationTime = 100;// Total simulation time

        // Add initial events
        eventQueue.add(new Event(currentTime, "CustomerArrival"));

        while (currentTime < simulationTime) {
            Event event = eventQueue.poll(); // .poll()- Retrieves and removes the head of this queue, or returns null if this queue is empty
            currentTime = event.getTime();

            if (event.getType().equals("CustomerArrival")) {
                //Handling customer arrival event
                System.out.println("Customer arrived at time " + currentTime);
                queueLength++;
                if (!serverBusy) {
                    // Server is idle, start service
                    serverBusy = true;
                    double serviceTime = serviceTimeMean - Math.log(Math.random()) * serviceTimeMean;
