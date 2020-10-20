import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * ReservationServer
 *
 * Creates a ReservationServer
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public class ReservationServer {
    private ServerSocket serverSocket;


    public ReservationServer() throws IOException {
        this.serverSocket = new ServerSocket(0);
    }

    public void serveClients() {
        Socket clientSocket;
        ClientHandler clientHandler;
        Thread handlerThread;

        JOptionPane.showMessageDialog(null, String.format("Your port number is: %d",
                this.serverSocket.getLocalPort()), "Port?", JOptionPane.INFORMATION_MESSAGE);

        while (true) {
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            clientHandler = new ClientHandler(clientSocket);
            handlerThread = new Thread(clientHandler);

            handlerThread.start();
        }
    }

}
/**
 * ClientHandler
 *
 * Creates a ClientHandler
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
class ClientHandler implements Runnable {
    private Socket serverSocket;

    private Delta deltaAirlines = new Delta();
    private Southwest southwestAirlines = new Southwest();
    private Alaska alaskanAirlines = new Alaska();

    public ClientHandler(Socket clientSocket) throws NullPointerException {
        try {
            if (clientSocket == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "The specified client socket is null",
                    "Ayer", JOptionPane.ERROR_MESSAGE);
        }

        this.serverSocket = clientSocket;
    }

    public void run() {
        ObjectOutputStream socketWriter = null;
        ObjectInputStream socketReader = null;

        File f = new File("airlines.txt");
        if (f.length() <= 3) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f, true))) {
                oos.writeObject(deltaAirlines);
                oos.writeObject(southwestAirlines);
                oos.writeObject(alaskanAirlines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                Object object;
                while ((object = ois.readObject()) != null) {
                    if (object instanceof Delta) {
                        deltaAirlines = (Delta) object;
                    } else if (object instanceof Southwest) {
                        southwestAirlines = (Southwest) object;
                    } else if (object instanceof Alaska) {
                        alaskanAirlines = (Alaska) object;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        deltaAirlines.addPassenger(new Passenger("Ira", "Einbinder", 18));
        southwestAirlines.addPassenger(new Passenger("Hayden", "Einbinder", 14));
        alaskanAirlines.addPassenger(new Passenger("Max", "Einbinder", 21));

        //--------------------------------------------------------------------------------------------------------------
        try {
            socketWriter = new ObjectOutputStream(serverSocket.getOutputStream());
            socketReader = new ObjectInputStream(serverSocket.getInputStream());

            socketWriter.flush();

            socketWriter.writeObject(deltaAirlines);
            socketWriter.flush();
            socketWriter.writeObject(southwestAirlines);
            socketWriter.flush();
            socketWriter.writeObject(alaskanAirlines);
            socketWriter.flush();

            if (socketReader.readUTF().equals("A")) {
                PrintWriter pw = new PrintWriter(f);
                pw.write("");
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f, true))) {
                    oos.writeObject(socketReader.readObject());
                    oos.writeObject(socketReader.readObject());
                    oos.writeObject(socketReader.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

