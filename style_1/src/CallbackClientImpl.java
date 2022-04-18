import javax.swing.*;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.function.Predicate;

public class CallbackClientImpl extends UnicastRemoteObject implements CallbackClientInterface {

    private String[] pathNames;
    private Map<String, CallbackClientInterface> downloadLinks;

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
        downloadLinks = new HashMap<>();


    }

    @Override
    public Set<String> showAvailableFiles(){
        return downloadLinks.keySet();
    }

    @Override
    public CallbackClientInterface getDownloadLink(String fileName) throws RemoteException {
        return downloadLinks.get(fileName);
    }

    @Override
    public String notifyMe(String message){
        String returnMessage = "Call back received: " + message;
        System.out.println(returnMessage);
        return returnMessage;
    }

    @Override
    public boolean searchFile(String fileName) throws RemoteException {

        Predicate<String> predicate = x -> x.equals(fileName);
        return Arrays.stream(pathNames).anyMatch(predicate);

    }

    @Override
    public void saveLink(String fileName, CallbackClientInterface link) throws RemoteException {
        if (!downloadLinks.containsKey(fileName))
            downloadLinks.put(fileName, link);
    }


    //TODO implement download
    @Override
    public void downloadFile(String fileName, CallbackClientInterface link) throws RemoteException {

        JOptionPane.showMessageDialog(null, "download complete" + "\n");

    }
}
