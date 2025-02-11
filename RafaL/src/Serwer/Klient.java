package Serwer;

import java.io.*;
import java.net.*;

public class Klient {
    public static final int PORT=50010;
    public static final String HOST = "192.168.27.3";

    public static void main(String[] args) throws IOException
    {
        //nawiazanie polaczenia z serwerem
        Socket sock;
        sock=new Socket(HOST,PORT);
        System.out.println("Nawiazalem polaczenie: "+sock);

        //tworzenie strumieni danych pobieranych z klawiatury i dostarczanych do socketu
        BufferedReader klaw;
        klaw=new BufferedReader(new InputStreamReader(System.in));
        PrintWriter outp;
        outp=new PrintWriter(sock.getOutputStream());

        //komunikacja - czytanie danych z klawiatury i przekazywanie ich do strumienia
        System.out.print("<Wysylamy:> ");
        String str=klaw.readLine();
        outp.println(str);
        outp.flush();

        System.out.println("Kończę działanie klienta");
        //zamykanie polaczenia
        klaw.close();
        outp.close();
        sock.close();
    }

}
