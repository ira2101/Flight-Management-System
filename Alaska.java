import java.util.ArrayList;
/**
 * Alaska
 *
 * Creates an Alaska
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public class Alaska implements Airline {
    private String airline;
    private Gate gate;
    private String flightNumber;
    private int passengerCount;
    public final int flightCapacity = 100;
    private ArrayList<Passenger> passengers = new ArrayList<>();

    public Alaska() {
        airline = "Alaska";
        gate = new Gate();
        flightNumber = "18000";
    }

    public String getAirline() {
        return airline;
    }

    public String getGate() {
        return gate.toString();
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void addPassenger(Passenger passenger) {
        if (passengerCount < flightCapacity) {
            passengerCount++;
            passengers.add(passenger);
        }
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public String getPassengerDemographics(Passenger passenger) {
        String firstLetter = passenger.getFirstName().substring(0, 1).toUpperCase() + ".";

        return String.format("%s %s, %d", firstLetter, passenger.getLastName().toUpperCase(), passenger.getAge());
    }

    public int getPassengerCount() {
        return passengerCount;
    }

}
