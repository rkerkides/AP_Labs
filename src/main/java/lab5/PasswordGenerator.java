package lab5;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PasswordGenerator extends Remote {
    // returns a randomly-generated password
    public String genPassword(int ID) throws RemoteException;
    // returns null if the password is incorrect / does not exist
    public byte[] downloadSource(int ID, String password) throws RemoteException;
}
