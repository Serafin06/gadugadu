package GaduGadu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class clientThread extends Thread {
    private BufferedReader is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private final int maxClientsCount;
    private String name;

    public clientThread(Socket clientSocket, clientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
    }

    private void broadcastUserList () {
        StringBuilder userList = new StringBuilder("USERLIST");
        synchronized (threads) {
            for (clientThread thread : threads) {
                if (thread != null && thread.name != null) {
                    userList.append(" ").append(thread.name);
                }
            }
            for (clientThread thread : threads) {
                if (thread != null) {
                    thread.os.println(userList.toString());
                }
            }
        }
    }

    public void run() {
        try {
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());



            synchronized (threads) {
                do {
                    os.println("SUBMITNAME");
                    name = is.readLine().trim();
                    boolean nameExists = false;

                    for (clientThread t : threads) {
                        if (t != null && t != this && t.name.equals(name)) {
                            nameExists = true;
                            break;
                        }
                    }
                    if (!nameExists) {
                        this.name = name;
                        os.println("Akceptacja");
                        break;
                    } else {
                        os.println("BUSY");
                    }
                } while (true);
            }

            os.println("NAMEACCEPTED Czesc " + name + " na naszym chacie. Aby wyjsc wpisz EXIT");
            broadcastUserList();

            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i] != this) {
                    threads[i].os.println("MESSAGE *** Nowy uzytkownik " + name + " podlaczyl sie na chat ***");
                }
            }

            while (true) {
                String line = is.readLine();
                if (line.startsWith("EXIT") || line.equalsIgnoreCase("EXIT")) {
                    break;
                }
                if (line.startsWith("/")) {
                    String[] parts = line.split(" ", 2);
                    if (parts.length > 1) {
                        String recipient = parts[0].substring(1); //usuwanie "/"
                        String message = parts[1];

                        synchronized (threads) {
                            for (clientThread t : threads) {
                                if (t != null && t.name.equals(recipient)) {
                                    t.os.println("PRIVATE " + name + ": " + message);
                                    os.println("PRIVATE " + name + " -> " + recipient + ": " + message);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < maxClientsCount; i++) {
                        if (threads[i] != null) {
                            threads[i].os.println("MESSAGE <" + name + ">" + line);
                        }
                    }
                }
            }
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i] != this) {
                    threads[i].os.println("MESSAGE *** Uzytkownik " + name + " opuscil chat ***");
                }
            }
            os.println("MESSAGE *** See " + name + " later. Pa :) ***");
            this.os.close();
            this.is.close();
            this.clientSocket.close();

            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] == this) {
                    threads[i] = null;
                    break;
                }
            }
            broadcastUserList();

            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }

}
