import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Aplikacja();
            }
        });

        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Nie mozna uzyskac IP");
            System.exit(0);
        }
        String ip = inetAddress.getHostAddress();
        System.out.println("IP tego kompa to: " + ip);

        String hostName = inetAddress.getHostName();
        System.out.println("Nazwa: "+hostName);


        if(args.length<1){
            System.out.println("Wywolanie");
            System.exit(0);
        }


        String wp = "wp.pl";
        String gog = "google.com";
        String polsl = "polsl.pl";
        InetAddress wp1 = null;
        InetAddress gog1 = null;
        InetAddress polsl1 = null;

        try {
            wp1 = InetAddress.getByName(wp);
            gog1 = InetAddress.getByName(gog);
            polsl1 = InetAddress.getByName(polsl);
        } catch (UnknownHostException e) {
            System.out.println("Nie mozna uzyskac IP");
            System.exit(0);
        }
        String ipw = wp1.getHostAddress();
        String ipg = gog1.getHostAddress();
        String ipp = polsl1.getHostAddress();

        System.out.println(ipw+"\n"+ipg+"\n"+ipp);


    }
}