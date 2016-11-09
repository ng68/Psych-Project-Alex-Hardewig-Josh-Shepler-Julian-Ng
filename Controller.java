
import java.io.*;
import java.net.*;

/**
 * Program info
 *
 * @author Alex Hardewig, ahardewi@purdue.edu, lab sec G03
 * @version date
 */
public class Controller {
    String serverIP = "localhost";
    int serverPort = 50001;
    private Model model;
    private View view;

    Socket s = new Socket(serverIP, serverPort);
    PrintWriter out = new PrintWriter(s.getOutputStream(), true);
    InputStreamReader isr = new InputStreamReader(s.getInputStream());
    BufferedReader in = new BufferedReader(isr);

    public Controller() throws IOException {
        model = new Model(this);
        view = new View(this);
        try {
            Socket s = new Socket(serverIP, serverPort);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            BufferedReader in = new BufferedReader(isr);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String input){
        out.println(input);
    }

    public String receiveMessage(){
        String incoming = "";
        try {
            incoming = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return incoming;
    }
    public void startNewGame(){

        view.makeLoginWindow();

    }
    
     public void closeStreams() {
        try {
            s.close();
            out.close();
            isr.close();
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Controller game = null;
        try {
            game = new Controller();
        } catch (IOException e) {
            e.printStackTrace();
        }
        game.startNewGame();

    }

}

