package pis.hue2.client;
import pis.hue2.common.Instruction;


import java.net.*;
import java.io.*;


public class LaunchClient {
    private Socket clientSocket;
    private int portNummer;
    PrintWriter stringOutput;
    BufferedReader stringInput;


    public LaunchClient(int portNummer) {
        this.portNummer = portNummer;

        try {
            clientSocket = new Socket("localhost", this.portNummer);

            //Schick
            stringOutput = new PrintWriter(clientSocket.getOutputStream());
            con();

            //empfang
            stringInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String serverAntwort = stringInput.readLine();
            System.out.println(serverAntwort);

        } catch (Exception connectException) {
            System.out.println("Verbindungsaufbau abgelehnt(Server erst Verbinden !!!)");
            System.exit(1);
        }

    }

    public void con() {
        try {
            stringOutput.println(Instruction.CON.toString());
            stringOutput.flush();
            // System.out.println("AAAA");
        }catch (Exception e){
            System.out.println(e);
        }
    }


    public static void main(String[] args) {
        LaunchClient client1 = new LaunchClient(5678);

    }


}



