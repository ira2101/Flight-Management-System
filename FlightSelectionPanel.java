import javax.swing.*;
import java.awt.*;
/**
 * FlightSelectionPanel
 *
 * Creates a FlightSelectionPanel
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public class FlightSelectionPanel extends JPanel {
    public JLabel startingLabel;
    public JComboBox airlineDropdown;
    public JLabel airlineDescription;
    public JButton exitButton;
    public JButton bookButton;

    public FlightSelectionPanel() {
        //NORTH - CHOOSE LABEL, AIRLINE JCOMBOBOX | CENTER - DESCRIPTION LABEL------------------------------------------
        JPanel north = new JPanel();
        north.setLayout(new BorderLayout());
        bookButton = new JButton("Choose this flight"); //Belongs in South -->

        startingLabel = new JLabel("Choose a flight from the drop down menu.", SwingConstants.CENTER);
        startingLabel.setFont(new Font("serif", Font.BOLD, 20));

        airlineDropdown = new JComboBox();
        JPanel holder = new JPanel();
        holder.add(airlineDropdown);

        north.add(startingLabel, BorderLayout.NORTH);
        north.add(holder, BorderLayout.SOUTH);

        //CENTER - AIRLINE DESCRIPTION LABEL----------------------------------------------------------------------------
        airlineDescription = new JLabel("", SwingConstants.CENTER);

        //SOUTH - EXIT BUTTON, BOOK BUTTON------------------------------------------------------------------------------
        JPanel bottomPanel = new JPanel();
        exitButton = new JButton("Exit");
        bookButton.setEnabled(false);
        bottomPanel.add(exitButton);
        bottomPanel.add(bookButton);

        //ASSEMBLY------------------------------------------------------------------------------------------------------
        airlineDropdown.setFocusable(false);
        exitButton.setFocusable(false);
        bookButton.setFocusable(false);

        setLayout(new BorderLayout());
        add(north, BorderLayout.NORTH);
        add(airlineDescription, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
