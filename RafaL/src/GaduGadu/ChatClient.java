package GaduGadu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.*;

public class ChatClient {
    String serverAddress;
    Scanner in;
    PrintWriter out;
    JFrame frame = new JFrame("Gadu-GaduNew");
    JTextField textField = new JTextField(50);
    JTextArea messageArea = new JTextArea(16, 50);
    private JList<String> userList;
    private DefaultListModel<String> userListModel;



    public ChatClient(String serverAddress) {
        this.serverAddress = serverAddress;
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.pack();
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        JScrollPane userScrollPane = new JScrollPane(userList);
        frame.getContentPane().add(userScrollPane, BorderLayout.EAST);

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = textField.getText();
                out.println(message);
                textField.setText("");
            }
        });
    }

    private String getName() {
        return JOptionPane.showInputDialog(
                frame,
                "Wybierz swoje imie/pseudonim:",
                "Wybor loginu",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private void run() throws IOException {
        try {
            Socket socket = new Socket(serverAddress, 2222);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.startsWith("Serwer zajety, sproboj pozniej!")) {
                    messageArea.append(line.substring(5) + "\n");
                }else if (line.startsWith("BUSY")){
                    JOptionPane.showMessageDialog(frame,"Ta nazwa jest zajeta. Wprowadz inna.","Blad", JOptionPane.ERROR_MESSAGE);

                } else if (line.startsWith("SUBMITNAME")) {
                    out.println(getName());
                } else if (line.startsWith("NAMEACCEPTED")) {
                    messageArea.append(line.substring(13) + "\n");
                    textField.setEditable(true);
                } else if (line.startsWith("PRIVATE")) {
                    messageArea.append("(PRIV) " + line.substring(8) + "\n");
                } else if (line.startsWith("MESSAGE")) {
                    messageArea.append(line.substring(8) + "\n");
                }else if (line.startsWith("USERLIST")){
                    SwingUtilities.invokeLater(()->{
                        userListModel.clear();
                        String[] users = line.substring(9).split(" ");
                        for(String u : users){
                            if (!u.isEmpty()){
                                userListModel.addElement(u);
                            }
                        }
                    });
                }
            }
        } finally {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            in.close();
            out.close();
            frame.setVisible(false);
            frame.dispose();
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        ChatClient client = new ChatClient(args[0]);
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
}
