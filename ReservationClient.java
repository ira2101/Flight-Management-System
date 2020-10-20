import com.sun.tools.javac.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.EventListener;
/**
 * ReservationClient
 *
 * Creates a ReservationClient
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public class ReservationClient {
    private static JFrame mainFrame = new JFrame();

    private static WelcomePanel welcomePanel = new WelcomePanel();
    private static BookingPromptPanel bookingPromptPanel = new BookingPromptPanel();
    private static FlightSelectionPanel flightSelectionPanel = new FlightSelectionPanel();
    private static ConfirmFlightPanel confirmFlightPanel = new ConfirmFlightPanel();
    private static DemographicsPanel demographicsPanel = new DemographicsPanel();
    private static FlightInformationPanel flightInformationPanel = new FlightInformationPanel();

    private static CardLayout cardLayout = new CardLayout();
    private static JPanel container = new JPanel();

    private static Delta deltaAirlines;
    private static Southwest southwestAirlines;
    private static Alaska alaskanAirlines;

    private static Passenger passenger = new Passenger();

    private static FlightPopupFrame flightPopupFrame = new FlightPopupFrame();

    private static KeyAdapter keyAdapter;

    private static ObjectInputStream socketReader = null;
    private static ObjectOutputStream socketWriter = null;
    private static Socket clientSocket;


    private static void createWelcomePanel() {
        welcomePanel.bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                createBookingPromptPanel();
                cardLayout.show(container, "bookingPromptPanel");
                bookingPromptPanel.bookButton.requestFocus();
            }
        });
        welcomePanel.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                reservationsFile();
                mainFrame.dispose();
            }
        });

    } //End createWelcomePanel

    private static void createBookingPromptPanel() {
        bookingPromptPanel.bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                createFlightSelectionPanel();
                cardLayout.show(container, "flightSelectionPanel");
            }
        });
        bookingPromptPanel.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                reservationsFile();
                mainFrame.dispose();
            }
        });
    } //createBookingPromptPanel

    private static void createFlightSelectionPanel() {
        //NORTH - AIRLINE JCOMBOBOX FEATURES----------------------------------------------------------------------------
        if (deltaAirlines.getPassengerCount() <= deltaAirlines.flightCapacity) {
            flightSelectionPanel.airlineDropdown.addItem("Delta");
        }
        if (southwestAirlines.getPassengerCount() <= southwestAirlines.flightCapacity) {
            flightSelectionPanel.airlineDropdown.addItem("Southwest");
        }
        if (alaskanAirlines.getPassengerCount() <= alaskanAirlines.flightCapacity) {
            flightSelectionPanel.airlineDropdown.addItem("Alaska");
        }

        flightSelectionPanel.airlineDropdown.setSelectedIndex(-1);
        flightSelectionPanel.airlineDropdown.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                flightSelectionPanel.bookButton.setEnabled(true);
                flightPopupFrame.passengerListPanel.removeAll();

                String option = (String) flightSelectionPanel.airlineDropdown.getSelectedItem();
                if (option.equals("Delta")) {
                    passenger.setAirline("Delta");
                    flightSelectionPanel.airlineDescription.setText("<HTML><CENTER>Delta Airlines is proud to be one " +
                            "of the five premier Airlines at Purdue University.<BR>We offer exceptional services, " +
                            "with free limited WiFi for all customers.<BR>Passengers who use T-Mobile as a cell " +
                            "phone carrier get additional benefits.<BR>We are also happy to offer power outlets in " +
                            "each seat for passenger use.<BR>We hope you choose to fly Delta as your next Airline.");

                    //For The Popup Frame -->
                    flightPopupFrame.airlineName.setText("Delta Airlines.");
                    flightPopupFrame.numPassengers.setText(
                            String.format("%d : %d", deltaAirlines.getPassengerCount(), deltaAirlines.flightCapacity));
                    for (Passenger i: deltaAirlines.getPassengers()) {
                        JLabel label = new JLabel(deltaAirlines.getPassengerDemographics(i));
                        label.setAlignmentX(Component.CENTER_ALIGNMENT);
                        flightPopupFrame.passengerListPanel.add(label);
                    }
                } else if (option.equals("Southwest")) {
                    passenger.setAirline("Southwest");
                    flightSelectionPanel.airlineDescription.setText("<HTML><CENTER>Southwest is proud to offer flight" +
                            "s to Purdue University.<BR>We are happy to offer free in flight WiFi, as well as our " +
                            "amazing snacks.<BR>In addition, we offer flights for much cheaper than other airlines" +
                            "and offer two free checked bags.<BR>We hope you choose Southwest for your next flight.");

                    //For The Popup Frame -->
                    flightPopupFrame.airlineName.setText("Southwest Airlines.");
                    flightPopupFrame.numPassengers.setText(
                            String.format("%d : %d", southwestAirlines.getPassengerCount(),
                                    southwestAirlines.flightCapacity));
                    for (Passenger i: southwestAirlines.getPassengers()) {
                        JLabel label = new JLabel(southwestAirlines.getPassengerDemographics(i));
                        label.setAlignmentX(Component.CENTER_ALIGNMENT);
                        flightPopupFrame.passengerListPanel.add(label);
                    }
                } else if (option.equals("Alaska")) {
                    passenger.setAirline("Alaskan");
                    flightSelectionPanel.airlineDescription.setText("<HTML><CENTER>Alaska Airlines is proud to serve " +
                            "the strong and knowledgeable Boilermakers from Purdue University.<BR>We primarily fly " +
                            "westward and often have stops in Alaska and California.<BR>We have first class " +
                            "amenities, even in coach class.<BR>We provide fun snacks, such as pretzels and " +
                            "goldfish.<BR>We also have comfortable seats and free WiFi.<BR>We hope you choose Alaska " +
                            "Airlines for your next itinerary!");

                    //For The Popup Frame -->
                    flightPopupFrame.airlineName.setText("Alaskan Airlines.");
                    flightPopupFrame.numPassengers.setText(
                            String.format("%d : %d", alaskanAirlines.getPassengerCount(),
                                    alaskanAirlines.flightCapacity));
                    for (Passenger i: alaskanAirlines.getPassengers()) {
                        JLabel label = new JLabel(alaskanAirlines.getPassengerDemographics(i));
                        label.setAlignmentX(Component.CENTER_ALIGNMENT);
                        flightPopupFrame.passengerListPanel.add(label);
                    }
                }
            }
        });

        //SOUTH - EXIT BUTTON, BOOK BUTTON------------------------------------------------------------------------------
        flightSelectionPanel.bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                createConfirmFlightPanel();
                cardLayout.show(container, "confirmFlightPanel");
            }
        }); //End bookButton
        flightSelectionPanel.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                reservationsFile();
                mainFrame.dispose();
            }
        }); //End exitButton

    } //End createFlightSelectionPanel

    private static void createConfirmFlightPanel() {
        //NORTH - BOOKING FLIGHT LABEL ---------------------------------------------------------------------------------
        confirmFlightPanel.startingLabel.setText(String.format("<HTML><CENTER>Are you sure that you want to book a " +
                "flight on<BR> %s Airlines?", passenger.getAirline()));

        //SOUTH - EXIT BUTTON, SWITCH BUTTON, CONFIRM BUTTON------------------------------------------------------------
        confirmFlightPanel.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                reservationsFile();
                mainFrame.dispose();
            }
        });

        confirmFlightPanel.switchFlightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                cardLayout.show(container, "flightSelectionPanel");
            }
        });

        confirmFlightPanel.confirmFlightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                createDemographicsPanel();
                cardLayout.show(container, "demographicsPanel");
            }
        });
    } //createConfirmFlightPanel

    private static void createDemographicsPanel() {
        demographicsPanel.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                reservationsFile();
                mainFrame.dispose();
            }
        });

        demographicsPanel.nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                //Error Checking -->
                if (demographicsPanel.firstNameText.getText().isBlank() ||
                        demographicsPanel.lastNameText.getText().isBlank() ||
                        demographicsPanel.ageText.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Please fill out all text areas",
                            "Personal Information", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                passenger.setFirstName(demographicsPanel.firstNameText.getText());
                passenger.setLastName(demographicsPanel.lastNameText.getText());
                try {
                    int num = Integer.parseInt(demographicsPanel.ageText.getText());
                    if (num < 0) {
                        throw new NumberFormatException();
                    }

                    passenger.setAge(num);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid age",
                            "Personal Information", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // <--

                int option = JOptionPane.showConfirmDialog(null, String.format("Are all the details " +
                                "you entered correct?\nThe passenger's name is %s %s and their age is %d.\nIf all " +
                                "the information shown is correct, select the Yes button below, otherwise, " +
                                "select the No button.",
                        passenger.getFirstName(), passenger.getLastName(), passenger.getAge()),
                        "Confirm info", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    switch (passenger.getAirline()) {
                        case "Delta":
                            deltaAirlines.addPassenger(passenger);
                            passenger.createBoardingPass(new BoardingPass(deltaAirlines.getFlightNumber(),
                                    passenger.getAirline(), passenger.getFirstName(), passenger.getLastName(),
                                    passenger.getAge(), deltaAirlines.getGate()));
                            break;
                        case "Southwest":
                            southwestAirlines.addPassenger(passenger);
                            passenger.createBoardingPass(new BoardingPass(southwestAirlines.getFlightNumber(),
                                    passenger.getAirline(), passenger.getFirstName(), passenger.getLastName(),
                                    passenger.getAge(), southwestAirlines.getGate()));
                            break;
                        case "Alaskan":
                            alaskanAirlines.addPassenger(passenger);
                            passenger.createBoardingPass(new BoardingPass(alaskanAirlines.getFlightNumber(),
                                    passenger.getAirline(), passenger.getFirstName(), passenger.getLastName(),
                                    passenger.getAge(), alaskanAirlines.getGate()));
                            break;
                    }

                    createFlightInformationPanel();
                    cardLayout.show(container, "flightInformationPanel");
                }
            }
        });
    } //createDemographicsPanel

    private static void createFlightInformationPanel() {
        mainFrame.removeKeyListener(keyAdapter);

        //NORTH - STARTING LABEL----------------------------------------------------------------------------------------
        flightInformationPanel.startingLabel.setText(String.format("<HTML><CENTER>Flight data displaying for " +
                        "%s Airlines<BR>Enjoy your flight!<BR>Flight is now boarding at Gate %s",
                passenger.getAirline(), passenger.getBoardingPass().gate));

        //CENTER - PASSENGER LIST PANEL---------------------------------------------------------------------------------
        if (passenger.getAirline().equals("Delta")) {
            JLabel l = new JLabel(String.format("%d : %d",
                    deltaAirlines.getPassengerCount(), deltaAirlines.flightCapacity), SwingConstants.CENTER);
            flightInformationPanel.passengerListPanel.add(l);

            for (Passenger i: deltaAirlines.getPassengers()) {
                JLabel label = new JLabel(deltaAirlines.getPassengerDemographics(i));
                flightInformationPanel.passengerListPanel.add(label);
            }
        } else if (passenger.getAirline().equals("Southwest")) {
            JLabel l = new JLabel(String.format("%d : %d",
                    southwestAirlines.getPassengerCount(), southwestAirlines.flightCapacity), SwingConstants.CENTER);
            flightInformationPanel.passengerListPanel.add(l);

            for (Passenger i: southwestAirlines.getPassengers()) {
                JLabel label = new JLabel(southwestAirlines.getPassengerDemographics(i));
                flightInformationPanel.passengerListPanel.add(label);
            }
        } else if (passenger.getAirline().equals("Alaskan")) {
            JLabel l = new JLabel(String.format("%d : %d",
                    alaskanAirlines.getPassengerCount(), alaskanAirlines.flightCapacity), SwingConstants.CENTER);
            flightInformationPanel.passengerListPanel.add(l);

            for (Passenger i: alaskanAirlines.getPassengers()) {
                JLabel label = new JLabel(alaskanAirlines.getPassengerDemographics(i));
                flightInformationPanel.passengerListPanel.add(label);
            }
        }

        for (int i = 0; i < 20; i++) {
            flightInformationPanel.passengerListPanel.add(new JLabel());
        }

        //SOUTH - BOARDING INFORMATION LABEL, EXIT BUTTON, REFRESH BUTTON-----------------------------------------------
        flightInformationPanel.boardingInfoLabel.setText("<HTML>" + passenger.getBoardingPass().toString());

        flightInformationPanel.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                reservationsFile();

                try {
                    socketWriter.writeUTF("A");
                    socketWriter.flush();
                    socketWriter.writeObject(deltaAirlines);
                    socketWriter.flush();
                    socketWriter.writeObject(southwestAirlines);
                    socketWriter.flush();
                    socketWriter.writeObject(alaskanAirlines);
                    socketWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Thank you for using the Purdue " +
                        "University Airline Management System!", "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                mainFrame.dispose();
            }
        });

        flightInformationPanel.refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                cardLayout.show(container, "flightInformationPanel");
            }
        });
    } //createFlightInformationPanel

    private static void reservationsFile() {
        try (PrintWriter pw = new PrintWriter(new File("reservations.txt"))) {
            pw.println("ALASKA");
            pw.println(String.format("%d/%d", alaskanAirlines.getPassengerCount(), alaskanAirlines.flightCapacity));
            pw.println("Alaska passenger list");
            pw.println();
            for (Passenger i: alaskanAirlines.getPassengers()) {
                pw.println(alaskanAirlines.getPassengerDemographics(i));
                pw.println("---------------------ALASKA");
            }
            pw.println();
            pw.println();

            pw.println("DELTA");
            pw.println(String.format("%d/%d", deltaAirlines.getPassengerCount(), deltaAirlines.flightCapacity));
            pw.println("Delta passenger list");
            pw.println();
            for (Passenger i: deltaAirlines.getPassengers()) {
                pw.println(deltaAirlines.getPassengerDemographics(i));
                pw.println("---------------------DELTA");
            }
            pw.println();
            pw.println();

            pw.println("SOUTHWEST");
            pw.println(String.format("%d/%d",
                    southwestAirlines.getPassengerCount(), southwestAirlines.flightCapacity));
            pw.println("Southwest passenger list");
            pw.println();
            for (Passenger i: southwestAirlines.getPassengers()) {
                pw.println(southwestAirlines.getPassengerDemographics(i));
                pw.println("---------------------SOUTHWEST");
            }

            pw.println();
            pw.println("EOF");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String hostName;
        String portString = "";
        int portInt = 0;

        try {
            hostName = JOptionPane.showInputDialog(null, "What is the host name you " +
                    "would like to connect to?", "Hostname?", JOptionPane.QUESTION_MESSAGE);

            if (!hostName.toLowerCase().equals("localhost")) {
                JOptionPane.showMessageDialog(null, "That is not a valid host name!",
                        "Ayer", JOptionPane.ERROR_MESSAGE);
                return;
            }


            portString = JOptionPane.showInputDialog(null, "What is the port you would like " +
                            "to connect to?",
                    "Port?", JOptionPane.QUESTION_MESSAGE);

            if (portString == null) {
                JOptionPane.showMessageDialog(null, "That is not a valid port number!",
                        "Ayer", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                try {
                    portInt = Integer.parseInt(portString);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "That is not a valid port number!",
                            "Ayer", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try {
                clientSocket = new Socket(hostName, portInt);
            } catch (ConnectException e) {
                JOptionPane.showMessageDialog(null, "That is not a valid port number!",
                        "Ayer", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //This is the client. --------------------------------------------------------------------------------------
            mainFrame.setTitle("Purdue University Flight Reservation System");
            mainFrame.setSize(600, 500);
            mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainFrame.setContentPane(container);

            container.setLayout(cardLayout);
            container.add(welcomePanel, "welcomePanel");
            container.add(bookingPromptPanel, "bookingPromptPanel");
            container.add(flightSelectionPanel, "flightSelectionPanel");
            container.add(confirmFlightPanel, "confirmFlightPanel");
            container.add(demographicsPanel, "demographicsPanel");
            container.add(flightInformationPanel, "flightInformationPanel");

            createWelcomePanel();

            cardLayout.show(container, "welcomePanel");
            mainFrame.setVisible(true);

            mainFrame.requestFocus();
            keyAdapter = new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
                        if (flightSelectionPanel.airlineDropdown.getSelectedIndex() == -1) {
                            return;
                        }

                        if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
                            flightPopupFrame.setVisible(true);
                            flightPopupFrame.exitButton.requestFocus();
                        }
                    }
                }
            };

            mainFrame.addKeyListener(keyAdapter);

            try {
                socketWriter = new ObjectOutputStream(clientSocket.getOutputStream());
                socketWriter.flush();
                socketReader = new ObjectInputStream(clientSocket.getInputStream());

                Object object;
                while ((object = socketReader.readObject()) != null) {
                    if (object instanceof Delta) {
                        deltaAirlines = (Delta) object;
                    } else if (object instanceof Southwest) {
                        southwestAirlines = (Southwest) object;
                    } else if (object instanceof  Alaska) {
                        alaskanAirlines = (Alaska) object;
                        break;
                    }

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/**
 * ResponseListener
 *
 * Creates a ResponseListener
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
class ResponseListener implements Runnable {
    private Socket clientSocket;
    private MainFrame mainFrame;

    public ResponseListener(Socket clientSocket) {
        try {
            if (clientSocket == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "The specified client socket is null",
                    "Ayer", JOptionPane.ERROR_MESSAGE);
        }

        this.clientSocket = clientSocket;
    }

    public void run() {
    }
}

