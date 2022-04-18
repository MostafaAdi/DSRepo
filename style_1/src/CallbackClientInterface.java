import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface CallbackClientInterface extends Remote {

    public String notifyMe(String message) throws RemoteException;

    public boolean searchFile(String fileName) throws RemoteException;

    public void saveLink(String fileName, CallbackClientInterface link) throws RemoteException;

    public Set<String> showAvailableFiles() throws RemoteException;

    public CallbackClientInterface getDownloadLink(String fileName) throws RemoteException;

//TODO implement download
    public void downloadFile(String fileName, CallbackClientInterface link) throws RemoteException;

}
