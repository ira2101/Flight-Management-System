import java.io.Serializable;
/**
 * BoardingPass
 *
 * Creates a BoardingPass
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public class BoardingPass implements Serializable {
    public String flightNumber;
    private String airline;
    private String firstName;
    private String lastName;
    private int age;
    public String gate;

    public BoardingPass(String flightNumber, String airline, String firstName, String lastName, int age, String gate) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gate = gate;
    }

    public String toString() {
        return String.format("-----------------------------------------------------------------------------------" +
                        "----<BR>BOARDING PASS FOR FLIGHT %s WITH %s Airlines<BR>PASSENGER FIRST NAME: %s<BR>" +
                        "PASSENGER LAST NAME: %s<BR>PASSENGER AGE: %d<BR>You can now begin boarding at gate %s<BR>" +
                        "---------------------------------------------------------------------------------------",
                        flightNumber, airline, firstName, lastName, age, gate);
    }


}
