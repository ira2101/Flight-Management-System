import java.io.IOException;
/**
 * ReservationRunner
 *
 * Creates a ReservationRunner
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public class ReservationRunner {
    public static void main(String[] args) {
        ReservationServer reservationServer;

        try {
            reservationServer = new ReservationServer();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        reservationServer.serveClients();
    }

}

