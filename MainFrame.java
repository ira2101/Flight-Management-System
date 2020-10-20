import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private WelcomePanel welcomePanel = new WelcomePanel();
    private BookingPromptPanel bookingPromptPanel = new BookingPromptPanel();
    private FlightSelectionPanel flightSelectionPanel = new FlightSelectionPanel();
    private ConfirmFlightPanel confirmFlightPanel = new ConfirmFlightPanel();
    private DemographicsPanel demographicsPanel = new DemographicsPanel();
    private FlightInformationPanel flightInformationPanel = new FlightInformationPanel();

    private CardLayout cardLayout = new CardLayout();;
    private JPanel container = new JPanel();

    private Delta deltaAirlines = new Delta();
    private Southwest southwestAirlines = new Southwest();
    private Alaska alaskanAirlines = new Alaska();

    private Passenger passenger = new Passenger();

    private FlightPopupFrame flightPopupFrame = new FlightPopupFrame();

    private KeyAdapter keyAdapter;

    private ObjectInputStream socketReader = null;
    private ObjectOutputStream socketWriter = null;
    private Socket clientSocket;

    public MainFrame(Socket clientSocket) {
        super("Purdue University Flight Reservation System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(container);

        container.setLayout(cardLayout);
        container.add(welcomePanel, "welcomePanel");
        container.add(bookingPromptPanel, "bookingPromptPanel");
        container.add(flightSelectionPanel, "flightSelectionPanel");
        container.add(confirmFlightPanel, "confirmFlightPanel");
        container.add(demographicsPanel, "demographicsPanel");
        container.add(flightInformationPanel, "flightInformationPanel");

        createWelcomePanel();

        cardLayout.show(container, "welcomePanel");
        setVisible(true);

        requestFocus();
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

        addKeyListener(keyAdapter);


        this.clientSocket = clientSocket;
        try {
            socketReader = new ObjectInputStream(clientSocket.getInputStream());
            socketWriter = new ObjectOutputStream(clientSocket.getOutputStream());

            Object object;
            while ((object = socketReader.readObject()) != null) {
                if (object instanceof Delta) {
                    deltaAirlines = (Delta) object;
                } else if (object instanceof Southwest) {
                    southwestAirlines = (Southwest) object;
                } else if (object instanceof  Alaska) {
                    alaskanAirlines = (Alaska) object;
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, deltaAirlines.toString());


    }

    private void createWelcomePanel() {
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
                dispose();
            }
        });

    } //End createWelcomePanel

    private void createBookingPromptPanel() {
        bookingPromptPanel.bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                createFlightSelectionPanel();
                cardLayout.show(container, "flightSelectionPanel");
            }
        });
        bookingPromptPanel.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                reservationsFile();
                dispose();
            }
        });
    } //createBookingPromptPanel

    private void createFlightSelectionPanel() {
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
                            "amazing snacks.<BR>In addition, we offer flights for much cheaper than other airlines.txt " +
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
                dispose();
            }
        }); //End exitButton

    } //End createFlightSelectionPanel

    private void createConfirmFlightPanel() {
        //NORTH - BOOKING FLIGHT LABEL ---------------------------------------------------------------------------------
        confirmFlightPanel.startingLabel.setText(String.format("<HTML><CENTER>Are you sure that you want to book a " +
                "flight on<BR> %s Airlines?", passenger.getAirline()));

        //SOUTH - EXIT BUTTON, SWITCH BUTTON, CONFIRM BUTTON------------------------------------------------------------
        confirmFlightPanel.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                reservationsFile();
                dispose();
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

    private void createDemographicsPanel() {
        demographicsPanel.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                reservationsFile();
                dispose();
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
                        "you entered correct?\nThe passenger's name is %s %s and their age is %d.\nIf all the " +
                                "information shown is correct, select the Yes button below, otherwise, " +
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

    private void createFlightInformationPanel() {
        removeKeyListener(keyAdapter);

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

                JOptionPane.showMessageDialog(null, "Thank you for using the Purdue " +
                        "University Airline Management System!", "Thank you!", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });

        flightInformationPanel.refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                flightInformationPanel.removeAll();
                createFlightInformationPanel();
                cardLayout.show(container, "flightInformationPanel");
            }
        });
    } //createFlightInformationPanel

    private void reservationsFile() {
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

        }
    }

    public ArrayList<Airline> getInfo() {
        ArrayList<Airline> airlines = new ArrayList<>();
        airlines.add(deltaAirlines);
        airlines.add(southwestAirlines);
        airlines.add(alaskanAirlines);

        return airlines;
    }
}
