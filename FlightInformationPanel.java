import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * FlightInformationPanel
 *
 * Creates a FlightInformationPanel
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public class FlightInformationPanel extends JPanel {
    public JLabel startingLabel;
    public JPanel passengerListPanel;
    public JLabel boardingInfoLabel;
    public JButton exitButton;
    public JButton refreshButton;

    public FlightInformationPanel() {
        //NORTH - STARTING LABEL----------------------------------------------------------------------------------------
        startingLabel = new JLabel("", SwingConstants.CENTER);
        startingLabel.setFont(new Font("serif", Font.BOLD, 20));

        //CENTER - PASSENGER LIST PANEL---------------------------------------------------------------------------------
        passengerListPanel = new JPanel();
        passengerListPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passengerListPanel.setLayout(new GridLayout(0, 1));

        //SOUTH - BOARDING INFORMATION LABEL, EXIT BUTTON, REFRESH BUTTON-----------------------------------------------
        boardingInfoLabel = new JLabel("");

        JPanel buttonPanel = new JPanel();
        exitButton = new JButton("Exit");
        refreshButton = new JButton("Refresh Flight Status");
        buttonPanel.add(exitButton);
        buttonPanel.add(refreshButton);

        JPanel bottom = new JPanel(new GridLayout(0, 1));
        bottom.add(boardingInfoLabel);
        bottom.add(buttonPanel);

        //ASSEMBLY------------------------------------------------------------------------------------------------------
        setLayout(new BorderLayout());
        add(startingLabel, BorderLayout.NORTH);
        add(new JScrollPane(passengerListPanel), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }
}
