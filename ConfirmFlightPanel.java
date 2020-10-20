import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * ConfirmFlightPanel
 *
 * Creates a ConfirmFlightPanel
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public class ConfirmFlightPanel extends JPanel {
    public JLabel startingLabel;
    public JButton exitButton;
    public JButton switchFlightButton;
    public JButton confirmFlightButton;

    public ConfirmFlightPanel() {
        //NORTH - BOOKING FLIGHT LABEL ---------------------------------------------------------------------------------
        startingLabel = new JLabel("", SwingConstants.CENTER);
        startingLabel.setFont(new Font("serif", Font.PLAIN, 18));

        //SOUTH - EXIT BUTTON, SWITCH BUTTON, CONFIRM BUTTON------------------------------------------------------------
        exitButton = new JButton("Exit");
        switchFlightButton = new JButton("No, I want a different flight.");
        confirmFlightButton = new JButton("Yes, I want this flight.");
        confirmFlightButton.requestFocus();

        JPanel bottom = new JPanel();
        bottom.add(exitButton);
        bottom.add(switchFlightButton);
        bottom.add(confirmFlightButton);

        //ASSEMBLY------------------------------------------------------------------------------------------------------
        exitButton.setFocusable(false);
        switchFlightButton.setFocusable(false);
        confirmFlightButton.setFocusable(false);

        setLayout(new BorderLayout());
        add(startingLabel, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);
    }

}
