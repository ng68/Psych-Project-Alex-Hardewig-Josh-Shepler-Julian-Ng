
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketTimeoutException;

/**
 * Program info
 *
 * @author Alex Hardewig, ahardewi@purdue.edu, lab sec G03
 * @version date
 */
public class View extends JFrame {
    //Main Panel
    JPanel mainPanel = new JPanel();
    CardLayout layout = new CardLayout();

    //Panel 1 LOGINWINDOW
    JPanel loginWindow = new JPanel(null);
    JButton login = new JButton("Login");
    JButton register = new JButton("Register");
    JTextField usernameF = new JTextField();
    JPasswordField passwordF = new JPasswordField();
    JLabel username = new JLabel("Username");
    JLabel password = new JLabel("Password");
    JLabel errorMessage = new JLabel("");


    //Panel 2 GAMECHOICE
    JPanel gameChoice = new JPanel(null);
    JButton start = new JButton("Start New Game");
    JButton join = new JButton("Join a Game");
    JLabel errorMessage2 = new JLabel("");
    JLabel userTitle = new JLabel("");

    //Panel 3a JOINGAME
    JPanel joinGame = new JPanel(null);
    JButton joinButton = new JButton("Join Game");
    JLabel joinKey = new JLabel("Enter a key to join a game");
    JTextField key = new JTextField();
    JLabel errorMessage4 = new JLabel("");
    JLabel userTitle3 = new JLabel("");

    //Panel 4 NEWGAME
    JPanel newGame = new JPanel(null);
    JTextArea participants = new JTextArea();
    JTextField newKey = new JTextField();
    JLabel keyMessage = new JLabel("Share this key with others so they can join");
    JLabel userTitle2 = new JLabel("");
    JLabel errorMessage3 = new JLabel("");
    JButton newGameButton = new JButton("Start Game");
    boolean isActivated = false;


    Controller controller;
    String cookie = "";
    String token = "";

    //Panel 5 HOST WAITING
    JPanel waiting = new JPanel(null);
    JLabel waitMessage = new JLabel("Waiting for leader...");

    //Panel 6 suggestion window
    JPanel game1 = new JPanel(null);
    JLabel userTitle4 = new JLabel("");
    JPanel description = new JPanel(null);
    JLabel errorMessage5 = new JLabel("");




    public View(Controller controller) {
        this.controller = controller;

    }

    public void makeLoginWindow() {
        loginWindow.add(login);
        loginWindow.add(register);
        loginWindow.add(username);
        loginWindow.add(usernameF);
        loginWindow.add(password);
        loginWindow.add(passwordF);
        loginWindow.add(errorMessage);

        login.setBounds(30, 300, 150, 30);
        register.setBounds(220, 300, 150, 30);
        username.setBounds(30, 100, 150, 30);
        usernameF.setBounds(200, 100, 150, 30);
        password.setBounds(30, 130, 150, 30);
        passwordF.setBounds(200, 130, 150, 30);
        errorMessage.setBounds(20, 440, 350, 30);


        gameChoice.add(start);
        gameChoice.add(join);
        gameChoice.add(errorMessage2);
        gameChoice.add(userTitle);

        start.setBounds(30, 300, 150, 30);
        join.setBounds(220, 300, 150, 30);
        errorMessage2.setBounds(20, 440, 350, 30);
        userTitle.setBounds(5, 0, 150, 50);


        joinGame.add(joinKey);
        joinGame.add(key);
        joinGame.add(joinButton);
        joinGame.add(userTitle3);
        joinGame.add(errorMessage4);

        joinKey.setBounds(120, 180, 350, 30);
        key.setBounds(170, 210, 50, 30);
        joinButton.setBounds(150, 250, 110, 30);
        userTitle3.setBounds(5, 0, 150, 50);
        errorMessage4.setBounds(20, 440, 350, 30);


        newGame.add(keyMessage);
        newGame.add(errorMessage3);
        newGame.add(userTitle2);
        newGame.add(newKey);
        newGame.add(participants);
        newGame.add(newGameButton);

        keyMessage.setBounds(80, 70, 350, 30);
        errorMessage3.setBounds(20, 440, 350, 30);
        userTitle2.setBounds(5, 0, 150, 50);
        newKey.setBounds(170, 100, 50, 30);
        newKey.setEditable(false);
        participants.setBounds(100, 140, 190, 190);
        participants.setEditable(false);
        participants.setBorder(BorderFactory.createTitledBorder("Participants"));
        newGameButton.setBounds(140, 340, 130, 30);
        newGameButton.setEnabled(isActivated);

        waiting.add(waitMessage);
        waiting.add(userTitle3);

        mainPanel.setLayout(layout);
        mainPanel.add(loginWindow, "1");
        mainPanel.add(gameChoice, "2");
        mainPanel.add(joinGame, "3");
        mainPanel.add(newGame, "4");
        mainPanel.add(waiting, "5");


        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.sendMessage("LOGIN" + "--" + usernameF.getText() + "--" + passwordF.getText());
                String incoming = controller.receiveMessage();
                if (incoming.contains("UNKNOWNUSER")) {
                    errorMessage.setText("Unknown User");
                }
                if (incoming.contains("INVALIDUSERPASSWORD")) {
                    errorMessage.setText("Wrong Password");
                }
                if (incoming.contains("USERALREADYLOGGEDIN")) {
                    errorMessage.setText("User already logged in");
                }
                if (incoming.contains("SUCCESS")) {
                    errorMessage.setText("Success!");
                    int index = incoming.lastIndexOf("-");
                    cookie = incoming.substring(index + 1);

                    layout.show(mainPanel, "2");
                    errorMessage2.setText("Welcome!");
                    userTitle.setText("Player: " + usernameF.getText());

                }


            }
        });

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.sendMessage("CREATENEWUSER" + "--" + usernameF.getText() + "--" + passwordF.getText());
                String incoming = controller.receiveMessage();
                if (usernameF.getText().length() <= 0 && passwordF.getText().length() <= 0) {
                    errorMessage.setText("Username length Error and Password length Error");
                } else if (usernameF.getText().length() <= 0) {
                    errorMessage.setText("Username length Error");
                } else if (passwordF.getText().length() <= 0) {
                    errorMessage.setText("Password Length Error");
                } else if (incoming.contains("USERALREADYEXISTS")) {
                    errorMessage.setText("User already exists");
                } else
                    errorMessage.setText("Success!");


            }
        });

        //Action Listeners for Panel 2
        join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                layout.show(mainPanel, "3");
                userTitle3.setText("Player: " + usernameF.getText());
                errorMessage4.setText("Welcome!");
            }
        });

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.sendMessage("JOINGAME" + "--" + cookie + "--" + key.getText());
                String incoming = controller.receiveMessage();
                if (incoming.contains("USERNOTLOGGEDIN")) {
                    errorMessage4.setText("User not logged in");
                }
                if (incoming.contains("GAMEKEYNOTFOUND")) {
                    errorMessage4.setText("Invalid game key");
                }
                if (incoming.contains("FAILURE")) {
                    errorMessage4.setText("User already playing game");
                }
                if (incoming.contains("SUCCESS")) {

                    System.out.println("got it");
                    participants.append("banana");
                }
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int counter = 0;
                controller.sendMessage("STARTNEWGAME" + "--" + cookie);

                String incoming = controller.receiveMessage();
                if (incoming.contains("USERNOTLOGGEDIN")) {
                    errorMessage3.setText("User not logged in");
                }
                if (incoming.contains("FAILURE")) {
                    errorMessage3.setText("Failure");
                }
                if (incoming.contains("SUCCESS")) {
                    int index = incoming.lastIndexOf("-");
                    token += incoming.substring(index + 1);
                    layout.show(mainPanel, "4");
                    userTitle2.setText("Player: " + usernameF.getText());
                    errorMessage3.setText("Game Started. You are the leader!");
                    newKey.setText(token);



                }
            }
        });


        add(mainPanel);
        setTitle("Foilmaker!");
        setResizable(false);

        setLocation(550, 200);
        setMinimumSize(new Dimension(400, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        layout.show(mainPanel, "1");
        setVisible(true);


    }
}
