package pis.hue2.server;

import pis.hue2.common.Instruction;

import java.io.File;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LaunchServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int maxAnzahlClient = 3;
    private List<String> listeAlleDatein = new ArrayList<String>();

    //
    public void serverLaufen(int portNummer) {
        try {
            serverSocket = new ServerSocket(portNummer);
            System.out.println("Server erfolgreich verbunden.");

            // liste alle Dateien

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

                    else  {
                        System.out.println("jai recu disconect");
                        // ein Client ist weg
                        maxAnzahlClient--;
                        disconnect();
                        System.out.println("tu es deccete");
                        serverSocket.close();

                    }













            } catch (Exception exception) {
                System.out.println(exception);
                System.exit(1);
            }
        }


        public void acknowledgement() {

            try {
                stringOutput.println(Instruction.ACK.toString());
                stringOutput.flush();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public void denied() {

            try {
                stringOutput.println(Instruction.DND.toString());
                stringOutput.flush();

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public void disconnect() {
            try {
                stringOutput.println(Instruction.DSC.toString());
                stringOutput.flush();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public void data() {
            try {
                stringOutput.println(Instruction.DAT.toString());
                stringOutput.flush();
            } catch (Exception e) {
                System.out.println(e);
            }
        }




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


        public void uploadFile(String fileName) throws IOException {
            OutputStream outputStream = clientSocket.getOutputStream();
            File file = new File("./ServerDateien/" + fileName);
            byte[] fileToUpload = new byte[(int) file.length()];
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            bufferedInputStream.read(fileToUpload, 0, fileToUpload.length);
            outputStream.write(fileToUpload, 0, fileToUpload.length);
            outputStream.flush();


        }


        public void downloadFile(String fileName) throws IOException {
            InputStream inputStream = clientSocket.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream("./ServerDateien/" + fileName);
            byte[] fileToDownload = new byte[1024*8];
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            int bytesRead = inputStream.read(fileToDownload, 0, fileToDownload.length);
            bufferedOutputStream.write(fileToDownload, 0, fileToDownload.length);
            bufferedOutputStream.close();
            listeAlleDatein.add(fileName);
            // ajouter dans la Allliste le Nom de fichier
        }

    }
}