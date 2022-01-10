package pis.hue2.client;
import pis.hue2.common.Instruction;
import java.net.*;
import java.io.*;

/**
 * Die Client-Klasse wird dazu dienen, dass der Client sich mit Server verbinden kann und
 * verschiedenen Dateitransferaktion erlauben koennen.
 *
 * @author Constantin und Cedric
 */

public class LaunchClient {
    /**
     * Hier wird die Rueckmeldung von Server gespeichert
     */
    public String serverAntwort;
    /**
     * Der Socket, welcher zu Kommunikation mit dem Server bunutzt wird
     */
    private Socket clientSocket;
    /**
     * Der Client Port
     */
    private int portNummer;
    /**
     * Fuer das Senden der Nachrichten bzw. Data zu dem Server
     */
    PrintWriter stringOutput;
    /**
     * Fuer den Erhalt der Nachrichten bzw. Data von Server
     */
    BufferedReader stringInput;

    /**
     * Klassenkonstruktor wird eine Default Server-Adresse erstellen und den Port spezifizieren
     *Sie erstellt auch einen Socket, welcher mit der Adresse der ServerSocket verbinden wird
     * Ausserdem erstellt sie einen Printwriter und Bufferreader, ueber den die Nachrichten versendet
     * bzw. eingelesen werden koennen
     * @param portNummer spezifiziert Port
     */

    public LaunchClient(int portNummer) {
        this.portNummer = portNummer;

        try {
            clientSocket = new Socket("localhost", this.portNummer);

            //Schick
            stringOutput = new PrintWriter(clientSocket.getOutputStream(),true);


            //empfang
            stringInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //       serverAntwort = stringInput.readLine();
            //   System.out.println(serverAntwort);

        } catch (Exception connectException) {
            System.out.println("Verbindungsaufbau abgelehnt(Server erst Verbinden !!!)");
            System.exit(1);
        }

    }
/**
 * Diese Methode sendet eine Verbindungsanforderung mit CON
 */
    public void con() {
        try {
            stringOutput.println(Instruction.CON.toString());
            stringOutput.flush();
            System.out.println("con wurde geschickt");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    /**
     *Diese Methode sendet eine Rueckmeldung mit ACK
     */
    public void acknowledgement() {

        try {
            stringOutput.println(Instruction.ACK.toString());
            stringOutput.flush();
            System.out.println("ack wurde geschickt");;
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
     *Diese Methode sendet eine Anforderung mit DEL auf das Loeschen
     */
    public void delete() {
        try {
            stringOutput.println(Instruction.DEL.toString());
            stringOutput.flush();
            System.out.println("ack wurde geschickt");
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
     *Diese Methode sendet eine Anforderung mit GET auf das Herunterladen
     */
    public void download() {
        try {
            stringOutput.println(Instruction.GET.toString());
            stringOutput.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     *Diese Methode sendet eine Anforderung mit PUT auf das Hochladen
     */
    public void upload() {
        try {
            stringOutput.println(Instruction.PUT.toString());
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
            stringOutput.println(Instruction.DAT.toString() );
            stringOutput.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     *Diese Methode sendet eine Anforderung mit LIST auf Erhalt einer Dateiliste
     */
    public void list() {
        try {
            stringOutput.println(Instruction.LST.toString());
            stringOutput.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /**
     *Diese Methode ist fuer das Senden bzw. Hochladen einer Datei
     * @param fileName spezifiziert die Name der Datei
     */

    public void uploadFile(String fileName) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        File file = new File("./ClientDateien/" + fileName);
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
        FileOutputStream fileOutputStream = new FileOutputStream("./ClientDateien/" + fileName);
        byte[] fileToDownload = new byte[1024*8];
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        int bytesRead = inputStream.read(fileToDownload, 0, fileToDownload.length);
        bufferedOutputStream.write(fileToDownload, 0, fileToDownload.length);
        bufferedOutputStream.close();

    }

}



