import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * DemographicsPanel
 *
 * Creates a DemographicsPanel
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public class DemographicsPanel extends JPanel {
    public JLabel startingLabel;
    public JTextArea firstNameText;
    public JTextArea lastNameText;
    public JTextArea ageText;
    public JButton exitButton;
    public JButton nextButton;

    public DemographicsPanel() {
        //NORTH - STARTING LABEL----------------------------------------------------------------------------------------
        startingLabel = new JLabel("Please input your information below.", SwingConstants.CENTER);
        startingLabel.setFont(new Font("serif", Font.PLAIN, 18));

        //CENTER - FIRST NAME JTEXTAREA, LAST NAME JTEXTAREA, AGE JTEXTAREA---------------------------------------------
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        firstNameText = new JTextArea();
        lastNameText = new JTextArea();
        ageText = new JTextArea();

        firstNameText.setLineWrap(true);
        lastNameText.setLineWrap(true);
        ageText.setLineWrap(true);
        firstNameText.setMaximumSize(new Dimension(500, 100));
        lastNameText.setMaximumSize(new Dimension(500, 100));
        ageText.setMaximumSize(new Dimension(500, 100));

        center.add(Box.createRigidArea(new Dimension(0, 5)));
        center.add(new JLabel("    What is your first name?", SwingConstants.LEFT));
        center.add(new JScrollPane(firstNameText));
        center.add(Box.createRigidArea(new Dimension(0, 10)));
        center.add(new JLabel("    What is your last name?", SwingConstants.LEFT));
        center.add(new JScrollPane(lastNameText));
        center.add(Box.createRigidArea(new Dimension(0, 10)));
        center.add(new JLabel("    What is your age?", SwingConstants.LEFT));
        center.add(new JScrollPane(ageText));

        //SOUTH - EXIT BUTTON, NEXT BUTTON------------------------------------------------------------------------------
        JPanel bottom = new JPanel();
        exitButton = new JButton("Exit");
        nextButton = new JButton("Next");
        bottom.add(exitButton);
        bottom.add(nextButton);

        //ASSEMBLY------------------------------------------------------------------------------------------------------
        exitButton.setFocusable(false);
        nextButton.setFocusable(false);

        setLayout(new BorderLayout());
        add(startingLabel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(new JPanel(), BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);
    }
}
