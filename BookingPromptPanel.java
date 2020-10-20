import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * BookingPromptPanel
 *
 * Creates a BookingPromptPanel
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public class BookingPromptPanel extends JPanel {
    public JLabel startingLabel;
    public JButton exitButton;
    public JButton bookButton;

    public BookingPromptPanel() {
        //NORTH - BOOKING FLIGHT LABEL ---------------------------------------------------------------------------------
        startingLabel = new JLabel("Do you want to book a flight today?", SwingConstants.CENTER);
        startingLabel.setFont(new Font("serif", Font.BOLD, 20));

        //SOUTH - EXIT BUTTON, BOOK BUTTON------------------------------------------------------------------------------
        JPanel bottomPanel = new JPanel();
        exitButton = new JButton("Exit");
        bookButton = new JButton("Yes, I want to book a flight.");
        bottomPanel.add(exitButton);
        bottomPanel.add(bookButton);

        //ASSEMBLY------------------------------------------------------------------------------------------------------
        exitButton.setFocusable(false);
        bookButton.setFocusable(false);

        setLayout(new BorderLayout());
        add(startingLabel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }


}
