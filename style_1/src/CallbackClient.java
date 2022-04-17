import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.NoSuchElementException;

public class CallbackClient {


    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {

        try {

            int RMIport;
            String hostName;
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);

//            System.out.println("enter rmi registry host name: ");
//            hostName = br.readLine();

            System.out.println("enter rmi registry port number");
            String portNum = br.readLine();
            RMIport = Integer.parseInt(portNum);

//            System.out.println("Enter how many seconds to stay registered:");
//            String timeDuration = br.readLine();
//            int time = Integer.parseInt(timeDuration);

            String registryURL = "rmi://localhost:" + portNum + "/callback";

            CallbackServerInterface h = (CallbackServerInterface) Naming.lookup(registryURL);

            System.out.println("Lookup completed");
            System.out.println("Server said " + h.sayHello());

            System.out.println("enter folder name you want to share, Please?(Folder must be in E drive)");
            String path = br.readLine();
            CallbackClientInterface callbackObj = new CallbackClientImpl(path);
            h.registerForCallback(callbackObj);
            System.out.println("Registered for callback.");
//            System.out.println("enter file name you want to search for");
//            String fileToSearch = br.readLine();
//            String downloadLink = h.searchFile(fileToSearch);
//
//            System.out.println(downloadLink);

            boolean findMore;
            do {
                String[] options = {"Find File", "Exit"};
                int choice = JOptionPane.showOptionDialog(null, "Choose an action", "Option dialog", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                switch(choice){

                    case 0:
                        String fileName = JOptionPane.showInputDialog("Type the name of the file you want to find.");
                        try{
                            String response = h.searchFile(fileName);
                            JOptionPane.showMessageDialog(null,  response + "\n", "Found", JOptionPane.INFORMATION_MESSAGE);
                        }catch(NoSuchElementException ex){
                            JOptionPane.showMessageDialog(null, "Not found");
                        }
                        break;

                    default:
                        h.unregisterForCallback(callbackObj);
                        System.out.println("Unregistered for callback.");
                        System.exit(0);
                        break;
                }
                findMore = (JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION);
                if (!findMore) {
                    h.unregisterForCallback(callbackObj);
                    System.out.println("Unregistered for callback.");
                    System.exit(0);
                }
            } while (findMore);

        } catch (Exception e) {

            System.out.println("Exception in CallbackClient: " + e);
        }



    }
}
