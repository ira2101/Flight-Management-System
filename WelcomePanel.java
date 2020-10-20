import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * WelcomePanel
 *
 * Creates a WelcomePanel
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public class WelcomePanel extends JPanel {
    public JLabel welcomeLabel;
    public JButton exitButton;
    public JButton bookButton;

    public WelcomePanel() {
        //NORTH - WELCOME LABEL-----------------------------------------------------------------------------------------
        welcomeLabel = new JLabel("<HTML><CENTER>Welcome to the Purdue University Airline Reservation " +
                "<BR>Management System!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));

        //CENTER - PURDUE IMAGE-----------------------------------------------------------------------------------------
        ImageIcon image = new ImageIcon(new ImageIcon("Purdue.png").getImage()
                .getScaledInstance(450, 300, Image.SCALE_SMOOTH));

        //SOUTH - EXIT BUTTON, BOOK BUTTON------------------------------------------------------------------------------
        JPanel bottomPanel = new JPanel();
        exitButton = new JButton("Exit");
        bookButton = new JButton("Book a Flight");
        bottomPanel.add(exitButton);
        bottomPanel.add(bookButton);

        //ASSEMBLY------------------------------------------------------------------------------------------------------
        exitButton.setFocusable(false);
        bookButton.setFocusable(false);

        setLayout(new BorderLayout());
        add(welcomeLabel, BorderLayout.NORTH);
        add(new JLabel(image), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
