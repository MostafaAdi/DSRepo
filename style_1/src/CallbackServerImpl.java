import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class CallbackServerImpl extends UnicastRemoteObject implements CallbackServerInterface {

    private Vector clientList;

    public CallbackServerImpl() throws RemoteException {
        super();
        clientList = new Vector();
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hello";
    }

    @Override
    public synchronized void registerForCallback(CallbackClientInterface callbackClientObj) throws RemoteException{

        if (!(clientList.contains(callbackClientObj))) {

            clientList.addElement(callbackClientObj);
            System.out.println("Registered new client ");
            doCallbacks();
        }

    }

    @Override
    public synchronized void unregisterForCallback(CallbackClientInterface callbackClientObject) throws RemoteException{

        if (clientList.removeElement(callbackClientObject)) {
            System.out.println("Unregistered client!");
        } else {
            System.out.println(
                    "unregister: client wasn't registered.");
        }
    }

    private synchronized void doCallbacks() throws RemoteException {
        System.out.println("**************************************\n"
                + "Callbacks initiated ---");

        for (int i = 0; i < clientList.size(); i++){
            System.out.println("callback number: " + i+1 + "\n");

            CallbackClientInterface nextClient = (CallbackClientInterface)clientList.elementAt(i);

            nextClient.notifyMe("Number of registered clients= " +  clientList.size());
        }
        System.out.println("********************************\n" +
                    "Server completed callbacks ---");



    }
}
