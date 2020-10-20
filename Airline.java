import java.io.Serializable;
import java.util.ArrayList;
/**
 * Airline
 *
 * Creates an Airline
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public interface Airline extends Serializable {

    String getAirline();

    String getGate();

    String getFlightNumber();

    void addPassenger(Passenger passenger);

    ArrayList<Passenger> getPassengers();

    String getPassengerDemographics(Passenger passenger);

    int getPassengerCount();

}
