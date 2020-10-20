import java.io.Serializable;
import java.util.Random;
/**
 * Gate
 *
 * Creates a Gate
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public class Gate implements Serializable {
    private String gate;

    public Gate() {
        Random random = new Random();

        char terminal = (char) (random.nextInt(6) + 65);
        int number = random.nextInt(16) + 1;

        gate = String.format("%s%d", terminal, number);
    }

    public String toString() {
        return gate;
    }
}
