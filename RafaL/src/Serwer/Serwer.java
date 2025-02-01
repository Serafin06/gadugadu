package Serwer;

import java.io.*;
import java.net.*;

public class Serwer {
    public static final int PORT = 50010;

    public static void main(String args[]) throws IOException {
        //tworzenie gniazda serwerowego
        ServerSocket serv;
        serv = new ServerSocket(PORT);

        //oczekiwanie na polaczenie i tworzenie gniazda sieciowego
        System.out.println("Nasluchuje: " + serv);
        Socket sock;
        sock = serv.accept();
        System.out.println("Jest polaczenie: " + sock);

        boolean war = true;
        BufferedReader inp;
        inp = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        BufferedReader klaw;
        klaw=new BufferedReader(new InputStreamReader(System.in));
        PrintWriter outp;
        outp=new PrintWriter(sock.getOutputStream());

        //tworzenie strumienia dganych pobieranych z gniazda sieciowego
        while(war) {

            //komunikacja - czytanie danych ze strumienia
            String str;
            str = inp.readLine();
            if (str.equals("/0")){
                war = false;
            }
            System.out.println("<Odebrano:> " + str);

            System.out.print("<Wysylamy:> ");
            str=klaw.readLine();
            outp.println(str);
            outp.flush();

        }

        System.out.println("Kończę działanie serwera");

        //zamykanie polaczenia
        klaw.close();
        inp.close();
        sock.close();
        serv.close();
    }
}
