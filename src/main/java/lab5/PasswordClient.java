package lab5;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.rmi.Naming;

public class PasswordClient {
    private static String host = "//stlinux02.dcs.gla.ac.uk/"; // "//localhost" "//stlinux02.dcs.gla.ac.uk/"
    private static String serviceName = "UnsecurePasswordServer";

    public static void writeBytesToFile(byte[] bytes, OutputStream out){
		try {
			out.write(bytes);
		} catch(Exception e) {
			System.err.println("😱 Client exception while writing to file: " + e);
			e.printStackTrace();
		}
   	}

    public static void main(String[] args){
        try {
            // TODO: create stub
            PasswordGenerator stub = (PasswordGenerator) Naming.lookup(host + serviceName);

            int myID = 2923219; // TODO: insert your GUID
            // TODO: use stub to generate password
            String returnPassword = stub.genPassword(myID);

            OutputStream output = null;
            String outputFilename = "sauce.java";
            try {
                output = new FileOutputStream(outputFilename);
            } catch (FileNotFoundException e) {
                System.err.println("😱 Client exception while trying to create file: " + e.toString());
                e.printStackTrace();
            }
            byte[] bytes = stub.downloadSource(myID, returnPassword); // TODO: use stub to download source file
            writeBytesToFile(bytes, output);
            System.out.println("Written file @ " + outputFilename + " 😏");
        } catch (Exception e) {
            System.err.println("😱 Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
