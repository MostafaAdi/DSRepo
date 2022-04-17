import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;

public class CallbackClient {


    public static void main(String[] args){

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

            System.out.println("Enter how many seconds to stay registered:");
            String timeDuration = br.readLine();
            int time = Integer.parseInt(timeDuration);

            String registryURL = "rmi://localhost:" + portNum + "/callback";

            CallbackServerInterface h = (CallbackServerInterface) Naming.lookup(registryURL);

            System.out.println("Lookup completed");
            System.out.println("Server said " + h.sayHello());

            //create directory of files

            CallbackClientInterface callbackObj = new CallbackClientImpl();
            h.registerForCallback(callbackObj);
            System.out.println("Registered for callback.");

            try {
                Thread.sleep(time * 1000);
            }
            catch (InterruptedException ex){ // sleep over
            }

            h.unregisterForCallback(callbackObj);
            System.out.println("Unregistered for callback.");


        } catch (Exception e) {

            System.out.println("Exception in CallbackClient: " + e);
        }

    }
}
