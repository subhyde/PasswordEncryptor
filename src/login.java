import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class login extends JFrame {

    private JButton submit;
    private JTextField username;
    private JTextField password;
    private JPanel panel1;
    private JButton logIn;
    private JTextArea PLEASEENTERAPASSWORDTextArea;
    private JButton encrypt;



    public login() {
        //creating the file
        File newTextFile = new File("Database");
        //setting the size of the window
        add(panel1);
        //version number
        setTitle("Password Encryptor 0.0.1");
        setSize(400, 500);

        //hiding our encrypted and decrypted buttons until logged in
        encrypt.setVisible(false);


//action listener for the signup button
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if one of the fields is blank an error will appear
                if (username.getText().equals("") || password.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "You have left one of the fields blank",
                            "error",
                            JOptionPane.WARNING_MESSAGE);
                    //error if username or password contains a space
                } else if (username.getText().contains(" ") || password.getText().contains(" ")) {

                    JOptionPane.showMessageDialog(null,
                            "You can NOT have spaces in your username or password",
                            "error",
                            JOptionPane.WARNING_MESSAGE);
                }
                //error if username is too long/short
                else if (username.getText().length() > 15 || username.getText().length() < 7) {
                    JOptionPane.showMessageDialog(null,
                            "Make sure your username is in between 7 and 15 characters",
                            "error",
                            JOptionPane.WARNING_MESSAGE);

                } else {
//after signup is successful their username and info will be stored in the database in plain text
                    try {
                        //this appends the text to the textfile
                        //will change possibly to create unique ids to find combination easier
                        FileWriter fw = new FileWriter(newTextFile, true);
                        fw.write("username: " + username.getText() + " || " + "Password: " + password.getText() + "\n");
                        fw.close();

                    } catch (IOException iox) {
                        iox.printStackTrace();
                    }
                    //message prompt saying data has successfully been stored
                    JOptionPane.showMessageDialog(null,
                            "\tYour Data Has Been Stored",
                            "Success",
                            JOptionPane.PLAIN_MESSAGE);
                }


            }


        });
        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //below writes the entire data of the textfile to a string
                try {
                    String content = new String(Files.readAllBytes(Paths.get("Database")), "UTF-8");

                    //checks the content of the string to see if the combination is there

                    if (content.contains(password.getText()) && content.contains(username.getText())) {
                        encrypt.setVisible(true); //shows the encrypt and decrypt buttons


                        JOptionPane.showMessageDialog(null, //displays success message with encryption access
                                "Welcome " + username.getText() + " you have encryption access now",
                                "login success",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                    //if the combination isnt present in the file, an error will occour
                    else {
                        JOptionPane.showMessageDialog(null,
                                "Invalid combination",
                                "Error",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } catch (IOException ie) {
                    ie.printStackTrace();
                }


            }


        });
        encrypt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "\tData Has Been Encrypted",
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);


                try{

                    KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
                    SecretKey myDesKey = keygenerator.generateKey();

                    Cipher desCipher;
                    desCipher = Cipher.getInstance("DES");

                    String content = new String(Files.readAllBytes(Paths.get("Database")), "UTF-8");
                    byte[] text = content.getBytes("UTF8");


                    desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
                    byte[] textEncrypted = desCipher.doFinal(text);

                    String s = new String(textEncrypted);
                    FileWriter fw = new FileWriter(newTextFile, false);
                    fw.write(s);
                    fw.close();
                    //decrypt
/*
desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
                    byte[] textDecrypted = desCipher.doFinal(textEncrypted);

                    s = new String(textDecrypted);
                    System.out.println(s);
*/


                }catch(Exception f)
                {
                    System.out.println("Exception");
                }
            }





        });


    }
}

