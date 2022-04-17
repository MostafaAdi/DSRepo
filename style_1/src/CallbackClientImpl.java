import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class CallbackClientImpl extends UnicastRemoteObject implements CallbackClientInterface {

    private String[] pathNames;

    public void setPathNames(String[] pathNames) {
        this.pathNames = pathNames;
    }

    public String[] getPathNames() {
        return pathNames;
    }

    public CallbackClientImpl(String filePath) throws RemoteException {
        super();
        File file = new File("E:/" + filePath);
        this.pathNames = file.list();

    }

    @Override
    public String notifyMe(String message){
        String returnMessage = "Call back received: " + message;
        System.out.println(returnMessage);
        return returnMessage;
    }

    @Override
    public String searchFile(String fileName) throws RemoteException {
        Predicate<String> predicate = x -> x.equals(fileName);
        try {
            return Arrays.stream(pathNames).filter(predicate).findFirst().get();

        } catch (NoSuchElementException e) {
            return "no such file";
        }
    }
}
