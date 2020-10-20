import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * FlightPopupFrame
 *
 * Creates a FlightPopupFrame
 *
 *
 *
 * @author Ira Einbinder & Adam Cook, lab sec G23
 * @version 12-03-19
 *
 */
public class FlightPopupFrame extends JFrame {
    public JLabel airlineName;
    public JLabel numPassengers;
    public JPanel passengerListPanel;
    public JButton exitButton;

    public FlightPopupFrame() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(200, 150);

        airlineName = new JLabel("", SwingConstants.LEFT);
        numPassengers = new JLabel("");
        airlineName.setFont(new Font("serif", Font.BOLD, 20));
        numPassengers.setFont(new Font("serif", Font.PLAIN, 18));

        JPanel top = new JPanel();
        top.add(airlineName);
        top.add(numPassengers);
        //End top

        passengerListPanel = new JPanel();
        passengerListPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passengerListPanel.setLayout(new BoxLayout(passengerListPanel, BoxLayout.PAGE_AXIS));
        //End center

        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        });
        exitButton.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setVisible(false);
                }
            }
        });

        JPanel hold = new JPanel();
        hold.add(exitButton);
        //End bottom

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(passengerListPanel), BorderLayout.CENTER);
        add(hold, BorderLayout.SOUTH);
    }
}
