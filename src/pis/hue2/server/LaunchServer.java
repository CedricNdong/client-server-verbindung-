package pis.hue2.server;

import pis.hue2.common.Instruction;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LaunchServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    public static int maxAnzahlClient = 3;

    //
    public void serverLaufen(int portNummer) {
        try {
            serverSocket = new ServerSocket(portNummer);
            System.out.println("Server erfolgreich verbunden.");

            while (true) {
                if (this.maxAnzahlClient >= 0) {
                    clientSocket = serverSocket.accept();
                    new ClientThread(clientSocket, (-this.maxAnzahlClient + 4)).start();

                    System.out.println("Neue Verbindung mit Client CL00" + (-this.maxAnzahlClient + 4) + " bei " + "localhost" + clientSocket.getLocalAddress());

                    this.maxAnzahlClient--;
                } else {
                    System.out.println("!!! Client wurde abgelehnt !!!\nEs darf nur maximal 3 Clients");
                    break;
                }
            }
        } catch (Exception exception) {
            System.out.println(exception);
            System.exit(1);
        }
    }

    //Main
    public static void main(String[] args) {
        LaunchServer server = new LaunchServer();
        server.serverLaufen(5678);
    }

    public class ClientThread extends Thread {

        private Socket clientSocket;
        PrintWriter stringOutput;
        BufferedReader stringInput;
        private int clientID;

        public ClientThread(Socket clientSocket, int clientID) {
            this.clientID = clientID;
            this.clientSocket = clientSocket;

        }


        @Override
        public void run() {
            try {
                stringInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String clientInstruction = stringInput.readLine();
                System.out.println(clientInstruction);

                stringOutput = new PrintWriter(clientSocket.getOutputStream());
                if (clientInstruction.equals(Instruction.CON.toString())) {
                    acknowledgement();
                }


            } catch (Exception exception) {
                System.out.println("KKK");
                System.exit(1);
            }
        }

        public void acknowledgement() {

            try {
                stringOutput.println(Instruction.ACK.toString());
                stringOutput.flush();
                System.out.println("dddd");
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

}