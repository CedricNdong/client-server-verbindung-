package pis.hue2.server;
import java.net.ServerSocket;
import java.net.Socket;


public class LaunchServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;

    private int maxAnzahlClient = 3;


    public void serverLaufen(int port) {

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server erfolgreich verbunden.\nServer wartet auf einen Client...");

            while (true) {
                if (this.maxAnzahlClient >= 0) {
                    clientSocket = serverSocket.accept();

                    System.out.println("Neue Verbindung mit Client CL00" + (-this.maxAnzahlClient + 4) + " bei " + "localhost"+ clientSocket.getLocalAddress());
                    System.out.println("Anzahl der Client : " + (-this.maxAnzahlClient + 4));

                    this.maxAnzahlClient--;
                } else {
                    System.out.println("!!! Client wurde abgelehnt !!!\nEs darf nur maximal 3 Clients");
                    break;
                }
            }
        }
        catch (Exception exception) {
            System.out.println(exception);
            System.exit(1);
        }
    }

    //Main
    public static void main(String[] args) {
        LaunchServer server = new LaunchServer();
        server.serverLaufen(7234);
    }

}





