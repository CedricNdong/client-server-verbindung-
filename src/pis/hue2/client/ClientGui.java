package pis.hue2.client;

import pis.hue2.common.Instruction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Eine Klasse die uns als Teil "view" dient.
 * Damit haben wir das Aussehen aller in dem Server oder Client Klassen vorhandenen Methoden bei ein Interface.
 * Die Variablen "codec1" und "codec2" sind Klassenattributen mit denen man die Hauptmethoden
 * "Coder"(kodiere) und "decoder"(dekodiere) bedienbar machen werden.
 *
 * @author Constantin Nshuti und Cedrick
 */
public class ClientGui {

    private LaunchClient client1;


    public ClientGui() {


        JFrame jframeClient = new JFrame("Client-Server Verbindung");


        jframeClient.setSize(500, 550);
        jframeClient.setLayout(new BoxLayout(jframeClient.getContentPane(), BoxLayout.Y_AXIS));
        jframeClient.setLocationRelativeTo(null);
        jframeClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jpLed = new JPanel();
        jpLed.setBorder(new EmptyBorder(0, 0, 0, 390));

        JTextArea jbLed = new JTextArea();
        jbLed.setPreferredSize(new Dimension(35, 35));
        jbLed.setBackground(Color.green);
        jbLed.setEditable(false);
        jbLed.setVisible(false);
        jpLed.add(jbLed);

        JPanel jpTitel = new JPanel();
        jpTitel.setBorder(new EmptyBorder(0, 0, 0, 0));
        JLabel jlTitel = new JLabel("CLIENT");
        jlTitel.setFont(new Font("Arial", Font.BOLD, 30));
        jpTitel.add(jlTitel);

        JPanel jpButton1 = new JPanel();
        jpButton1.setBorder(new EmptyBorder(0, 0, 0, 0));

        JButton jbAufbauen = new JButton("Sitzungsaufbau");
        jbAufbauen.setPreferredSize(new Dimension(200, 60));
        jbAufbauen.setFont(new Font("Arial Narrow", Font.BOLD, 15));
        jbAufbauen.setBackground(new Color(0x15DC15));



        JButton jbAbbauen = new JButton("Sitzungsabbau");
        jbAbbauen.setPreferredSize(new Dimension(200, 60));
        jbAbbauen.setFont(new Font("Arial Narrow", Font.BOLD, 15));
        jbAbbauen.setBackground(new Color(0xEE0540));


        JPanel jpText1 = new JPanel();
        jpText1.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel jlTextClient = new JLabel("Vom Server  : ");
        jlTextClient.setFont(new Font("Arial", Font.BOLD, 20));

        JTextArea ergebnis = new JTextArea();
        ergebnis.setPreferredSize(new Dimension(220, 50));
        ergebnis.setLineWrap(true);
        ergebnis.setEditable(false);
        jpText1.add(jlTextClient);
        jpText1.add(ergebnis);



        JPanel jpText2 = new JPanel();
        jpText2.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel jlTextFileName = new JLabel("Datei Name : ");
        jlTextFileName.setFont(new Font("Arial", Font.BOLD, 20));

        JTextArea fileChoosed = new JTextArea();
        fileChoosed.setPreferredSize(new Dimension(220, 50));
        fileChoosed.setLineWrap(true);
        jpText2.add(jlTextFileName);
        jpText2.add(fileChoosed);

        ButtonGroup group1 = new ButtonGroup();
        jpButton1.add(jbAufbauen);
        jpButton1.add(jbAbbauen);
        group1.add(jbAufbauen);
        group1.add(jbAbbauen);


        JPanel jpButton2 = new JPanel();
        jpText1.setBorder(new EmptyBorder(0, 0, 40, 0));

        JButton jbList = new JButton("LST");
        jbList.setPreferredSize(new Dimension(80, 35));
        jbList.setFont(new Font("Arial", Font.BOLD, 10));

        JButton jbPut = new JButton("PUT");
        jbPut.setPreferredSize(new Dimension(80, 35));
        jbPut.setFont(new Font("Arial", Font.BOLD, 10));

        JButton jbDel = new JButton("DELETE");
        jbDel.setPreferredSize(new Dimension(80, 35));
        jbDel.setFont(new Font("Arial", Font.BOLD, 10));

        JButton jbGet = new JButton("GET");
        jbGet.setPreferredSize(new Dimension(80, 35));
        jbGet.setFont(new Font("Arial", Font.BOLD, 10));


        jpButton2.setVisible(false);
        jbAbbauen.setEnabled(false);
        jpText2.setVisible(false);


        jpButton2.add(jbList);
        jpButton2.add(jbPut);
        jpButton2.add(jbGet);
        jpButton2.add(jbDel);






        JPanel jpCopyRight = new JPanel();
        jpCopyRight.setBorder(new EmptyBorder(10, 0, 0, 0));
        JLabel jlCopyRight = new JLabel("\u00a9"+"Copyright Constantin & Cedric");
        jlCopyRight.setFont(new Font("SansSerif", Font.PLAIN, 15));
        jpCopyRight.add(jlCopyRight);


        jframeClient.add(jpLed);
        jframeClient.add(jpTitel);
        jframeClient.add(jpButton1);
        jframeClient.add(jpText1);
        jframeClient.add(jpText2);
        jframeClient.add(jpButton2);

        jframeClient.add(jpCopyRight);
        jframeClient.setVisible(true);





        jbAufbauen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                client1 = new LaunchClient(5678);
                if (jbAufbauen.isEnabled()) {
                    jbAufbauen.setEnabled(false);
                    try {
                    //Schick
                    client1.con();
                        client1.serverAntwort = client1.stringInput.readLine();

                        if (client1.serverAntwort.equals(Instruction.ACK.toString())) {

                            System.out.println(client1.serverAntwort);
                            ergebnis.append(client1.serverAntwort);

                            jbAbbauen.setEnabled(true);
                            jpButton2.setVisible(true);
                            jpText2.setVisible(true);
                            jbLed.setVisible(true);
                        } else {
                            System.out.println(client1.serverAntwort);
                            ergebnis.append(client1.serverAntwort);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        jbAbbauen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    //Schick DSC
                    client1.disconnect();

                    // Bekomme DSC
                    System.out.println("je suis off");
                    ergebnis.setText(client1.stringInput.readLine());
                    jbAbbauen.setEnabled(false);
                    jbDel.setEnabled(false);
                    jbGet.setEnabled(false);
                    jbPut.setEnabled(false);
                    jbList.setEnabled(false);
                    jbLed.setBackground(Color.red);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        jbList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    client1.list();
                    System.out.println("je tai send lst");
                    client1.serverAntwort = client1.stringInput.readLine();
                    if (client1.serverAntwort.equals(Instruction.ACK.toString())){
                        client1.acknowledgement();
                        System.out.println("je tai send ack");
                        if (client1.stringInput.readLine().equals(Instruction.DAT.toString())){
                            client1.acknowledgement();
                            System.out.println("je tai send ack 2 ");

                            String allFileTemp = client1.stringInput.readLine();
                            String[] allFileTemp2 = allFileTemp.split(" ");
                            String listAlleDatein = "";

                            for (String listFile : allFileTemp2) {
                                listAlleDatein += listFile + "\n";
                            }
                            System.out.println(listAlleDatein);

                            JOptionPane.showMessageDialog(null,"Folgen" + listAlleDatein);
                        }
                    }
                }catch (Exception e){

                }
            }
        });


        jbPut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    client1.upload();
                    System.out.println("jai envoyer PUT");
                    if (client1.stringInput.readLine().equals(Instruction.ACK.toString())){
                        System.out.println("jai recu ack, je te send alors le fichier ci ?");
                        client1.data();


                        System.out.println("oooüüep");
                        if (client1.stringInput.readLine().equals(Instruction.ACK.toString())){
                            System.out.println("tu as deja ca??? "+fileChoosed.getText());
                            client1.stringOutput.println(fileChoosed.getText());

                            if (client1.stringInput.readLine().equals(Instruction.DND.toString())){
                                ergebnis.setText(client1.stringInput.readLine());
                                System.out.println("tu as barat nor,je nai pas pu de send");
                            }
                            else{
                                System.out.println(" voila ca ooo");
                                client1.uploadFile(fileChoosed.getText());
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });



        jbGet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                try {
                    client1.download();
                    System.out.println("jai envoyer get");

                    if (client1.stringInput.readLine().equals(Instruction.ACK.toString())){
                        client1.acknowledgement();

                        if (client1.stringInput.readLine().equals(Instruction.DAT.toString())){
                            client1.acknowledgement();
                            client1.stringOutput.println(fileChoosed.getText());


                            if (client1.stringInput.readLine().equals(Instruction.DND.toString())){

                                ergebnis.setText(Instruction.DND.toString());
                            }
                            else {
                                ergebnis.setText(Instruction.DAT.toString());
                                client1.downloadFile(fileChoosed.getText());

                            }


                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        jbDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ergebnis.setText(Instruction.DEL.toString());
            }
        });

    }



    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        ClientGui guiClient = new ClientGui();

    }
}
