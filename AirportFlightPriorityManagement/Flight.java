
public class Flight {
    private String flightId;
    private String airline;
    private String destination;
    private int priority;
    private String reason;
    private long arrivalOrder;

    public Flight(String flightId, String airline,
                  String destination, int priority,
                  String reason, long arrivalOrder) {
        this.flightId = flightId;
        this.airline = airline;
        this.destination = destination;
        this.priority = priority;
        this.reason = reason;
        this.arrivalOrder = arrivalOrder;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getAirline() {
        return airline;
    }

    public String getDestination() {
        return destination;
    }

    public int getPriority() {
        return priority;
    }

    public String getReason() {
        return reason;
    }

    public long getArrivalOrder() {
        return arrivalOrder;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.format(
            "Flight ID: %-10s | Airline: %-15s | " +
            "Destination: %-15s | Priority: %d | Reason: %s",
            flightId, airline, destination, priority, reason
        );
    }
}
