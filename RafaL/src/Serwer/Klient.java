package Serwer;

import java.io.*;
import java.net.*;

public class Klient {
    public static final int PORT = 50010;
    public static final String HOST = "192.168.0.111";

    public static void main(String[] args) throws IOException {
        //nawiazanie polaczenia z serwerem
        Socket sock;
        sock = new Socket(HOST, PORT);
        System.out.println("Nawiazalem polaczenie: " + sock);

        BufferedReader klaw;
        klaw = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter outp;
        outp = new PrintWriter(sock.getOutputStream());
        boolean war = true;
        BufferedReader inp;
        inp = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        while (war) {

            String str;

            System.out.print("<Wysylamy:> ");
            str = klaw.readLine();
            outp.println(str);
            outp.flush();
            if (str.equals("/0")) {
                war = false;
                System.out.println("<Odebrano:> zamykam");
            }

            str = inp.readLine();
            if (str.equals("/0")) {
                war = false;
                System.out.println("<Odebrano:> zamykam");
            }
            System.out.println("<Odebrano:> " + str);


        }

        System.out.println("Kończę działanie klienta");

        inp.close();
        klaw.close();
        outp.close();
        sock.close();
    }

}
