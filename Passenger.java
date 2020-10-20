import java.io.Serializable;
/**
 * Passenger
 *
 * Creates a Passenger
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public class Passenger implements Serializable {
    private String firstName;
    private String lastName;
    private String airline;
    private int age;
    private BoardingPass boardingPass;

    public Passenger() {
        firstName = "";
        lastName = "";
        airline = "";
        age = 0;
    }

    public Passenger(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        airline  = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getAirline() {
        return this.airline;
    }

    public void createBoardingPass(BoardingPass boardingPas) {
        this.boardingPass = boardingPas;
    }

    public BoardingPass getBoardingPass() {
        return boardingPass;
    }

}
