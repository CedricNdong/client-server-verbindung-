package pis.hue2.client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ClientGui {

    public static void main(String[] args) {

        final File[] fileToSend= new File [1];

        JFrame jframe = new JFrame("GuiClient!!");

        jframe.setSize(500,500);
        jframe.setLayout(new BoxLayout(jframe.getContentPane(),BoxLayout.Y_AXIS));
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel jlTitel = new JLabel("Client - Server Connection!!");
        jlTitel.setFont(new Font("Arial",Font.BOLD,25));
        jlTitel.setBorder(new EmptyBorder(20,0,10,0));
        jlTitel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jpButton1 = new JPanel();
        jpButton1.setBorder(new EmptyBorder(75,0,10,0));

        JButton jbAufbauen = new JButton("Sitzungsaufbau");
        jbAufbauen.setPreferredSize(new Dimension(150,30));
        jbAufbauen.setFont(new Font("Arial Narrow",Font.BOLD,15));


        JButton jbAbbauen = new JButton("Sitzungsabbau");
        jbAbbauen.setPreferredSize(new Dimension(150,30));
        jbAbbauen.setFont(new Font("Arial Narrow",Font.BOLD,15));

        ButtonGroup group1 = new ButtonGroup();
        jpButton1.add(jbAbbauen);
        jpButton1.add(jbAufbauen);
        group1.add(jbAbbauen);
        group1.add(jbAufbauen);



        JPanel jpButton2 = new JPanel();
        jpButton1.setBorder(new EmptyBorder(75,0,10,0));

        JButton jbList = new JButton("LST");
        jbList.setPreferredSize(new Dimension(80,35));
        jbList.setFont(new Font("Arial",Font.BOLD,10));

        JButton jbPut = new JButton("PUT");
        jbPut.setPreferredSize(new Dimension(80,35));
        jbPut.setFont(new Font("Arial",Font.BOLD,10));

        JButton jbGet = new JButton("GET");
        jbGet.setPreferredSize(new Dimension(80,35));
        jbGet.setFont(new Font("Arial",Font.BOLD,10));

        JButton jbDel = new JButton("Delete");
        jbDel.setPreferredSize(new Dimension(80,35));
        jbDel.setFont(new Font("Arial",Font.BOLD,10));

        jpButton2.add(jbList);
        jpButton2.add(jbPut);
        jpButton2.add(jbGet);
        jpButton2.add(jbDel);

        jbGet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = new JFrame("Get File!!");

                frame.setSize(300,300);
                frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));

                JLabel jTitel = new JLabel("Choose a file to download!!");
                jTitel.setFont(new Font("Arial",Font.BOLD,20));
                jTitel.setBorder(new EmptyBorder(18,0,8,0));
                jTitel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JPanel jPanel = new JPanel();
                jPanel.setBorder(new EmptyBorder(75,0,10,0));

                JButton jList = new JButton("YES");
                jList.setPreferredSize(new Dimension(80,35));
                jList.setFont(new Font("Arial",Font.BOLD,10));

                JButton jList1 = new JButton("NO");
                jList1.setPreferredSize(new Dimension(80,35));
                jList1.setFont(new Font("Arial",Font.BOLD,10));

                jPanel.add(jList);
                jPanel.add(jList1);

                frame.add(jTitel);
                frame.add(jPanel);
                frame.setVisible(true);







                /** JFileChooser jFileChooser = new JFileChooser();
                 jFileChooser.setDialogTitle("Choose a file to download!!");

                 if(jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                 fileToSend[0] = jFileChooser.getSelectedFile();
                 jlTitel.setText("The file you want to download is: " + fileToSend[0].getName());
                 }*/
            }
        });

        jbList.addActionListener(e -> {
            JFrame listframe = new JFrame("Liste of Files!!");

            listframe.setSize(300,300);
            listframe.setLayout(new BoxLayout(listframe.getContentPane(),BoxLayout.Y_AXIS));

            JLabel jlistTitel = new JLabel("Liste of all Files!!");
            jlistTitel.setFont(new Font("Arial",Font.BOLD,20));
            jlistTitel.setBorder(new EmptyBorder(18,0,8,0));
            jlistTitel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel jPanel = new JPanel();
            jPanel.setBorder(new EmptyBorder(75,0,10,0));

            JButton jList = new JButton("BACK");
            jList.setPreferredSize(new Dimension(80,35));
            jList.setFont(new Font("Arial",Font.BOLD,10));

            jPanel.add(jList);

            listframe.add(jlistTitel);
            listframe.add(jPanel);
            listframe.setVisible(true);


        });

        jbPut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame putframe = new JFrame("Add a File!!");

                putframe.setSize(300,300);
                putframe.setLayout(new BoxLayout(putframe.getContentPane(),BoxLayout.Y_AXIS));

                JLabel jputTitel = new JLabel("Give the name of the File");
                jputTitel.setFont(new Font("Arial",Font.BOLD,20));
                jputTitel.setBorder(new EmptyBorder(18,0,8,0));
                jputTitel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JPanel jPanel = new JPanel();
                jPanel.setBorder(new EmptyBorder(75,0,10,0));

                JButton jList = new JButton("ADD");
                jList.setPreferredSize(new Dimension(80,35));
                jList.setFont(new Font("Arial",Font.BOLD,10));

                jPanel.add(jList);

                putframe.add(jputTitel);
                putframe.add(jPanel);
                putframe.setVisible(true);
            }
        });

        jbDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame delframe = new JFrame("Delete a File!!");

                delframe.setSize(300,300);
                delframe.setLayout(new BoxLayout(delframe.getContentPane(),BoxLayout.Y_AXIS));

                JLabel jdelTitel = new JLabel("Give the name of the File");
                jdelTitel.setFont(new Font("Arial",Font.BOLD,20));
                jdelTitel.setBorder(new EmptyBorder(18,0,8,0));
                jdelTitel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JPanel jPanel = new JPanel();
                jPanel.setBorder(new EmptyBorder(75,0,10,0));

                JButton jList = new JButton("ADD");
                jList.setPreferredSize(new Dimension(80,35));
                jList.setFont(new Font("Arial",Font.BOLD,10));

                jPanel.add(jList);

                delframe.add(jdelTitel);
                delframe.add(jPanel);
                delframe.setVisible(true);
            }
        });

        JPanel jpCopyRight = new JPanel();
        JLabel nichtsLabel = new JLabel("@Copyright Constantin & Cedric" ,JLabel.CENTER);

        jpCopyRight.add(nichtsLabel);
        jframe.add(jlTitel);
        jframe.add(jpButton1);
        jframe.add(jpButton2);
        jframe.add(jpCopyRight);
        jframe.setVisible(true);








    }
}
