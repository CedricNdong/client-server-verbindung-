package pis.hue2.server;

import pis.hue2.common.Instruction;

import java.io.File;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Diese Server-Klasse is in der Lage, auf alle Anforderungen einer Client zu reagieren bzw. zu erfuellen
 * Sie ist dafuer zustaendig, durch Unterthreads die Anzahl aller erlaubten Client zu entscheiden,
 * wobei hier nur 3 Clients erlaubt sind
 * Als Parameter wird der Port des Servers gefordert
 *
 * @author Constantin Nshuti und Cedrick
 */
public class LaunchServer {
    /**
     * Server socket, ueber welcher die Verbindung laeuft
     */
    private ServerSocket serverSocket;
    /**
     * Der Socket, an welchen der Client sich verbinden wird
     */
    private Socket clientSocket;
    /**
     * Maximale Anzahl der erlaubten Clients
     */
    private int maxAnzahlClient = 3;
    /**
     * List von allen vorhanden Dateien in dem Server
     */
    private List<String> listeAlleDatein = new ArrayList<String>();

    /**
     * @param portNummer Port des Servers
     */
    public void serverLaufen(int portNummer) {
        try {
            serverSocket = new ServerSocket(portNummer);
            System.out.println("Server erfolgreich verbunden.");

            File[] dataein = new File("./ServerDateien/").listFiles();
            for (File data : dataein) {
                if (data.isFile()) {
                    listeAlleDatein.add(data.getName());
                }
            }

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

    /**
     * Der ClientThread-Klasse pusht einen Client in die neue SocketThread
     * und warten auf eine Socket sich zu verbinden
     */
    public class ClientThread extends Thread {

        private Socket clientSocket;
        PrintWriter stringOutput;
        BufferedReader stringInput;
        private int clientID;

        public ClientThread(Socket clientSocket, int clientID) {
            this.clientID = clientID;
            this.clientSocket = clientSocket;

        }
        /**
         * Server starten und warten auf den Client
         * solange der Client sich verbindet ist, ueberprueft der Server der Client zulaessig ist,
         * falls ja, gibt ihn eine Rueckmeldung mit CON ansonsten mit DND
         */

        @Override
        public void run() {
            try {
                //empfangen
                stringInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //schicken
                stringOutput = new PrintWriter(clientSocket.getOutputStream(), true);

                    String clientInstruction = stringInput.readLine();
                    if (clientInstruction.equals(Instruction.CON.toString())){
                        System.out.println("con received");
                        if (maxAnzahlClient < 0){
                            denied();
                            clientSocket.close();
                        }
                        else {
                            acknowledgement();
                        }
                    }

                    else if (clientInstruction.equals(Instruction.GET.toString())){
                        System.out.println("get received");
                        acknowledgement();
                        if (stringInput.readLine().equals(Instruction.ACK.toString())){
                            System.out.println("ack1 received");
                            data();
                            if (stringInput.readLine().equals(Instruction.ACK.toString())){
                                System.out.println("ack2 received");
                                String dateiName = stringInput.readLine();
                                System.out.println(dateiName);

                                if (listeAlleDatein.contains(dateiName)){

                                    uploadFile(dateiName);
                                    System.out.println("get work");
                                }
                                else{
                                    denied();
                                    System.out.println("get never work");
                                }
                            }



                        }


                    }
                    else if (clientInstruction.equals(Instruction.PUT.toString())){
                        System.out.println("put received");
                        acknowledgement();


                        if (stringInput.readLine().equals(Instruction.DAT.toString())) {
                            System.out.println("dat received");
                            acknowledgement();
                            System.out.println("icciiiiii");

                            String dateiName = stringInput.readLine();
                            System.out.println(dateiName);
                            if (listeAlleDatein.contains(dateiName)) {
                                denied();
                                System.out.println("dat never work");

                            }
                            else {
                                stringOutput.println("");
                                downloadFile(dateiName);

                                System.out.println("put work");
                            }


                        }






                    }
                    else if (clientInstruction.equals(Instruction.DEL.toString())){


                    }
                    else if (clientInstruction.equals(Instruction.LST.toString())){
                        acknowledgement();
                        System.out.println("je suis serveur,tai send ack");
                        if (stringInput.readLine().equals(Instruction.ACK.toString())){
                            data();
                            System.out.println("je tai send dat");
                            if (stringInput.readLine().equals(Instruction.ACK.toString())){
                                System.out.println("je tai send les fichier suivant :");
                                String stringAlleDatein = "";

                                for (String lisD : listeAlleDatein) {
                                    stringAlleDatein += lisD +" ";

                                }

                                System.out.println(stringAlleDatein);


                                stringOutput.println(listeAlleDatein);
                                stringOutput.flush();

                                stringOutput.println(stringAlleDatein);
                                stringOutput.flush();
                            }
                        }
                    }



                    else if (clientInstruction.equals(Instruction.DSC.toString())){
                        System.out.println("jai recu disconect");
                        disconnect();
                        System.out.println("tu es deccete");
                        serverSocket.close();
                    }

            } catch (Exception exception) {
                System.out.println(exception);
                //System.exit(1);
            }
        }

        /**
         *Diese Methode sendet eine Rueckmeldung mit ACK
         */
        public void acknowledgement() {

            try {
                stringOutput.println(Instruction.ACK.toString());
                stringOutput.flush();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        /**
         *Diese Methode sendet eine Ablehnung mit DND
         */
        public void denied() {

            try {
                stringOutput.println(Instruction.DND.toString());
                stringOutput.flush();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        /**
         *Diese Methode sendet eine Anforderung mit DSC auf das Beenden von Verbindung
         */
        public void disconnect() {
            try {
                stringOutput.println(Instruction.DSC.toString());
                stringOutput.flush();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        /**
         *Diese Methode sendet Rueckmeldung mit DAT
         */
        public void data() {
            try {
                stringOutput.println(Instruction.DAT.toString());
                stringOutput.flush();
            } catch (Exception e) {
                System.out.println(e);
            }
        }


        /**
         *Diese Methode ist fuer das Loeschen einer Datei
         * @param file_name  spezifiziert die Name der Datei
         */

        public void delete(String file_name){
            try {
                File fileName = new File("./ServerDateien/" + file_name);
                fileName.delete();
                listeAlleDatein.removeAll(Collections.singleton(file_name));
                //retire le file du  listeAlleDatein.
                System.out.println( fileName.delete() + "wurde erfolgreich geloescht");
            }catch (Exception e){
                System.out.println(e);
            }
        }

        /**
         *Diese Methode ist fuer das Senden bzw. Hochladen einer Datei
         * @param fileName  spezifiziert die Name der Datei
         */
        public void uploadFile(String fileName) throws IOException {
            OutputStream outputStream = clientSocket.getOutputStream();
            File file = new File("./ServerDateien/" + fileName);
            byte[] fileToUpload = new byte[(int) file.length()];
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            bufferedInputStream.read(fileToUpload, 0, fileToUpload.length);
            outputStream.write(fileToUpload, 0, fileToUpload.length);
            outputStream.flush();


        }

        /**
         *Diese Methode ist fuer den Erhalt bzw. Herunterladen einer Datei
         * @param fileName spezifiziert die Name der Datei
         */
        public void downloadFile(String fileName) throws IOException {
            InputStream inputStream = clientSocket.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream("./ServerDateien/" + fileName);
            byte[] fileToDownload = new byte[1024*8];
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            int bytesRead = inputStream.read(fileToDownload, 0, fileToDownload.length);
            bufferedOutputStream.write(fileToDownload, 0, fileToDownload.length);
            bufferedOutputStream.close();
            listeAlleDatein.add(fileName);
        }
    }
}