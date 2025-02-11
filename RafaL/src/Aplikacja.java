import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Aplikacja {
    private JPanel mainPanel;
    private JLabel lab1;
    private JTextField text1;
    private JButton b1;
    private JTextField nrIP;
    private JLabel lab2;

    public Aplikacja() {
        JFrame frame = new JFrame("IP");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setVisible(true);

        b1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hostName = lab1.getText().trim();
                if (hostName.isEmpty()) {
                    nrIP.setText("Wpisz nazwe hosta");
                    return;
                }
                try {
                    InetAddress site = InetAddress.getByName(hostName);
                    nrIP.setText(site.getHostAddress());
                } catch (UnknownHostException ex) {
                    nrIP.setText("Nie znaleziono hosta");
                }
            }
        });


    }
}

