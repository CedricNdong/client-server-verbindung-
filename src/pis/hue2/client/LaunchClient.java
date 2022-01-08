package pis.hue2.client;
import pis.hue2.common.Instruction;


import java.net.*;
import java.io.*;


public class LaunchClient {
    public String serverAntwort;
    private Socket clientSocket;
    private int portNummer;
    PrintWriter stringOutput;
    BufferedReader stringInput;


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

    public void con() {
        try {
            stringOutput.println(Instruction.CON.toString());
            stringOutput.flush();
            System.out.println("con wurde geschickt");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void acknowledgement() {

        try {
            stringOutput.println(Instruction.ACK.toString());
            stringOutput.flush();
            System.out.println("ack wurde geschickt");;
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

    public void delete() {
        try {
            stringOutput.println(Instruction.DEL.toString());
            stringOutput.flush();
            System.out.println("ack wurde geschickt");
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
    public void download() {
        try {
            stringOutput.println(Instruction.GET.toString());
            stringOutput.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void upload() {
        try {
            stringOutput.println(Instruction.PUT.toString());
            stringOutput.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void data() {
        try {
            stringOutput.println(Instruction.DAT.toString() );
            stringOutput.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void list() {
        try {
            stringOutput.println(Instruction.LST.toString());
            stringOutput.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void uploadFile(String fileName) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        File file = new File("./ClientDateien/" + fileName);
        byte[] fileToUpload = new byte[(int) file.length()];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        bufferedInputStream.read(fileToUpload, 0, fileToUpload.length);
        outputStream.write(fileToUpload, 0, fileToUpload.length);
        outputStream.flush();


    }


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



