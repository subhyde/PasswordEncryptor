import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public class login extends JFrame  {

    private JButton submit;
    private JTextField username;
    private JTextField password;
    private JPanel panel1;
    private JButton logIn;
    private JTextArea PLEASEENTERAPASSWORDTextArea;
    private JButton encrypt;
    private JButton decrypt;


    public login() {
        //creating the file
        File newTextFile = new File("Database");
        //creates the scanner to find a match
        Scanner scanner=new Scanner("Database");
        //setting the size of the window
        add(panel1);
        setTitle("Password Encrypter 0.0.1");
        setSize(400, 500);

        //hiding our encrypted and decrypted buttons until logged in
        encrypt.setVisible(false);
        decrypt.setVisible(false);


        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//after signup is successful their username and info will be stored in the database in plain text
                if (username.getText().equals("") || password.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "You have left one of the fields blank",
                            "error",
                            JOptionPane.WARNING_MESSAGE);

                } else if (username.getText().contains(" ") || password.getText().contains(" ")) {

                    JOptionPane.showMessageDialog(null,
                            "You can NOT have spaces in your username or password",
                            "error",
                            JOptionPane.WARNING_MESSAGE);
                }


                else if(username.getText().length() >15 || username.getText().length() <7){
                    JOptionPane.showMessageDialog(null,
                            "Make sure your username is in between 7 and 15 characters",
                            "error",
                            JOptionPane.WARNING_MESSAGE);

                }
                else {

                    try {

                        FileWriter fw = new FileWriter(newTextFile, true);
                        fw.write("username: " + username.getText() + " || " + "Password: " + password.getText() + "\n");
                        fw.close();

                    } catch (IOException iox) {
                        //do stuff with exception
                        iox.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null,
                            "\tYour Data Has Been Stored",
                            "Success",
                            JOptionPane.PLAIN_MESSAGE);
                }


            }


        });
        logIn.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e){

try{
                String content = new String(Files.readAllBytes(Paths.get("Database")), "UTF-8");


                if (content.contains(password.getText()) && content.contains(username.getText())) {
                    encrypt.setVisible(true);
                    decrypt.setVisible(true);
                    JOptionPane.showMessageDialog(null,
                            "Welcome " + username.getText() + " you have encryption access now",
                            "login success",
                            JOptionPane.PLAIN_MESSAGE);
                }

                         else {
                    JOptionPane.showMessageDialog(null,
                            "Invalid combination",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
                }
             catch(IOException ie) {
                 ie.printStackTrace();
             }




}


        });
    }
}
