import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
    JTextArea participants = new JTextArea(" ");
    JTextField newKey = new JTextField();
    JLabel keyMessage = new JLabel("Share this key with others so they can join");
    JLabel userTitle2 = new JLabel("");
    JLabel errorMessage3 = new JLabel("");
    JButton newGameButton = new JButton("Start Game");
    boolean isActivated = false;


    Controller controller;
    String cookie = "";
    String token = "";

    int playerCounter = 0;
    ArrayList<String> nameList = new ArrayList<String>();


    //Panel 5 HOST WAITING
    JPanel waiting = new JPanel(null);
    JLabel waitMessage = new JLabel("Waiting for leader...");
    JLabel errorMessage5 = new JLabel("Joined game: Waiting for leader");

    //Panel 6 suggestion window
    JPanel game1 = new JPanel(null);
    JLabel userTitle4 = new JLabel("");
    JTextArea description = new JTextArea("");
    JLabel errorMessage6 = new JLabel("");
    JTextField suggestion = new JTextField();
    JButton submitSuggestion = new JButton("Submit Suggestion");
    JLabel question = new JLabel("What is the word for");

    //PANEL 7 optionWindow
    JPanel optionPanel = new JPanel(null);
    JLabel userTitle5 = new JLabel("");
    JLabel pickMessageOption = new JLabel("Pick your option below");
    JRadioButton choice1 = new JRadioButton();
    JRadioButton choice2 = new JRadioButton();
    JRadioButton choice3 = new JRadioButton();
    JButton lockInAnswer = new JButton("Submit option");
    JLabel errorMessage7 = new JLabel("");

    String choice1String;
    String choice2String;
    String choice3String;



    String askedQuestion = "";


    public View(Controller controller) {
        this.controller = controller;

    }

    SwingWorker worker = new SwingWorker<String, Object>() {

        @Override
        public String doInBackground() {

                String response = "";
                response = controller.receiveMessage();
                playerCounter++;
                return response;

        }


        @Override
        public void done() {


            try {
                String response = get();
                int place = response.lastIndexOf("-");
                String name = response.substring(16, place - 1);
                playerCounter++;
                participants.append(name);
                errorMessage3.setText("Press <Start Game> to start game");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    SwingWorker worker2 = new SwingWorker<String, Object>() {

        @Override
        public String doInBackground() {

            String response = "";
            response = controller.receiveMessage();
            return response;

        }


        @Override
        public void done() {


            try {
                String response = get();
                if (response.contains("NEWGAMEWORD")){
                    int firstDash = response.indexOf("-");
                    int lastDash = response.lastIndexOf("-");
                    String askedQuestion = response.substring(firstDash + 2, lastDash - 1  );
                    description.append(askedQuestion);
                    layout.show(mainPanel, "6");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };










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
        waitMessage.setBounds(100,150,350,30);
        waiting.add(errorMessage5);
        errorMessage5.setBounds(20, 440, 350, 30);
//        JPanel game1 = new JPanel(null);
//        JLabel userTitle4 = new JLabel("");
//        JPanel description = new JPanel(null);
//        JLabel errorMessage6 = new JLabel("");
//        JTextField suggestion = new JTextField();

        game1.add(userTitle4);
        game1.add(description);
        game1.add(errorMessage6);
        game1.add(suggestion);
        game1.add(submitSuggestion);
        game1.add(question);
        userTitle4.setBounds(5, 0, 150, 50);
        description.setBounds(100,100,190,190);
        description.setEditable(false);
        suggestion.setBounds(100, 300, 200, 20);
        suggestion.setText("Enter your suggestion");
        errorMessage6.setBounds(20, 440, 350, 30);
        errorMessage6.setText("Enter your suggestion");
        submitSuggestion.setBounds(100, 350, 200, 20);
        question.setBounds(10,30,150,50);

//        JPanel optionPanel = new JPanel(null);
//        JLabel userTitle5 = new JLabel("");
//        JLabel pickMessageOption = new JLabel("Pick your option below");
//        JRadioButton correctChoice = new JRadioButton();
//        JRadioButton hostChoice = new JRadioButton();
//        JRadioButton participantsChoice = new JRadioButton();
//        JButton lockInAnswer = new JButton("Submit option");
//        JLabel errorMessage7 = new JLabel("");

        optionPanel.add(userTitle5);
        optionPanel.add(pickMessageOption);
        optionPanel.add(choice1);
        optionPanel.add(choice2);
        optionPanel.add(choice3);
        optionPanel.add(lockInAnswer);
        optionPanel.add(errorMessage7);
        userTitle5.setBounds(5, 0, 150, 50);
        pickMessageOption.setBounds(100, 20, 150, 50);
        choice1.setBounds(100, 50, 150, 50);
        choice2.setBounds(100, 80, 150, 50);
        choice3.setBounds(100, 110, 150, 50);
        lockInAnswer.setBounds(100, 350, 150, 20);
        ButtonGroup b = new ButtonGroup();
        b.add(choice1);
        b.add(choice2);
        b.add(choice3);
        errorMessage7.setBounds(20, 440, 350, 30);
        errorMessage7.setText("Choose an option");







        mainPanel.setLayout(layout);
        mainPanel.add(loginWindow, "1");
        mainPanel.add(gameChoice, "2");
        mainPanel.add(joinGame, "3");
        mainPanel.add(newGame, "4");
        mainPanel.add(waiting, "5");
        mainPanel.add(game1, "6");
        mainPanel.add(optionPanel, "7");


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
                token = key.getText();
                controller.sendMessage("JOINGAME" + "--" + cookie + "--" + token);

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
                    layout.show(mainPanel, "5");
                    worker2.execute();





                }
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
                    worker.execute();
                    newGameButton.setEnabled(true);

                }
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.sendMessage("ALLPARTICIPANTSHAVEJOINED--" + cookie + "--" + token);
                String incoming = controller.receiveMessage();
                if (incoming.contains("USERNOTLOGGEDIN")){
                    errorMessage4.setText("User not logged in");
                }
                if (incoming.contains("INVALIDGAMETOKEN")){
                    errorMessage4.setText("Invalid game token");
                }
                if (incoming.contains("USERNOTGAMELEADER")){
                    errorMessage4.setText("User not game leader");
                }
                if (incoming.contains("NEWGAMEWORD")){
                int firstDash = incoming.indexOf("-");
                int lastDash = incoming.lastIndexOf("-");
                String askedQuestion = incoming.substring(firstDash + 2, lastDash - 1  );
                description.append(askedQuestion);

                    layout.show(mainPanel, "6");
                }

            }
        });

        submitSuggestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userSuggestion = suggestion.getText();
                controller.sendMessage("PLAYERSUGGESTION--" + cookie + "--" + token + "--" + userSuggestion);
                submitSuggestion.setEnabled(false);
                String incoming = controller.receiveMessage();
                if (incoming.contains("USERNOTLOGGEDIN")){
                    errorMessage6.setText("Invalid user token");
                    submitSuggestion.setEnabled(true);
                }
                if (incoming.contains("INVALIDGAMETOKEN")){
                    errorMessage6.setText("Invalid game token");
                    submitSuggestion.setEnabled(true);
                }
                if (incoming.contains("UNEXPECTEDMESSAGEFORMAT")){
                    errorMessage6.setText("Unexpected Message");
                    submitSuggestion.setEnabled(true);
                }
                if (incoming.contains("INVALIDMESSAGEFORMAT")){
                    errorMessage6.setText("Invalid message format");
                    submitSuggestion.setEnabled(true);
                }
                if (incoming.contains("ROUNDOPTIONS")){
                    int lastDash = incoming.lastIndexOf("-");
                     choice1String = incoming.substring(lastDash + 1);
                    String remainingIncoming = incoming.substring(0, lastDash - 1);
                    int firstDash = remainingIncoming.indexOf("-");
                    int lastDashRemainingIncoming = remainingIncoming.lastIndexOf("-");
                     choice2String = remainingIncoming.substring(firstDash + 2, lastDashRemainingIncoming -  1);
                    int lastDash2 = remainingIncoming.lastIndexOf("-");
                    choice3String = remainingIncoming.substring(lastDash2 + 1);
                    choice1.setText(choice1String);
                    choice2.setText(choice2String);
                    choice3.setText(choice3String);
                    layout.show(mainPanel, "7");

                }


            }
        });

        lockInAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choice1.isSelected()) {
                    controller.sendMessage("PLAYERCHOICE--" + cookie + "--" + token + "--" + choice1String);
                }
                if (choice2.isSelected()){
                    controller.sendMessage("PLAYERCHOICE--" + cookie + "--" + token + "--" + choice2String);
                }
                if (choice3.isSelected()){
                    controller.sendMessage("PLAYERCHOICE--" + cookie + "--" + token + "--" + choice3String);
                }
                String incoming = controller.receiveMessage();
                if (incoming.contains("USERNOTLOGGEDIN")){
                    errorMessage7.setText("Invalid user token");
                }
                if (incoming.contains("INVALIDGAMETOKEN")){
                    errorMessage7.setText("Invalid game token");
                }
                if (incoming.contains("UNEXPECTEDMESSAGETYPE")){
                    errorMessage7.setText("Unexpected Message type");
                }
                if (incoming.contains("INVALIDMESSAGEFORMAT")){
                    errorMessage7.setText("Incorrect message format");
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
