class Event implements Comparable<Event> {
    private double time; //Time at which the event occurs
    private String type; // Type of the event (e.g., "CustomerArrival" or "CustomerDeparture")

    public Event(double time, String type) { //Event method
        this.time = time;
        this.type = type;
    }

    public double getTime() { //returns the time
        return time;
    }

    public String getType() { //Returns the type
        return type;
    }

    @Override
    public int compareTo(Event other) { // A method that compares the time to the previous time of arrival
        return Double.compare(this.time, other.time);
    }
}
