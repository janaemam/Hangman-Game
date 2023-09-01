package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Hangman  {

    Socket userSocket;
    User player;

    PrintWriter out;
    BufferedReader in;


    Hangman(Socket socket,User user) throws IOException {
        this.userSocket = socket;
        this.player=user;
        run();
    }
    public void run() throws IOException {
        out= new PrintWriter(userSocket.getOutputStream(),true);
        in = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
        while(true) {
            out.println("Welcome " + player.username);

            out.println("1.Single Player\n2.Multiplayer\n3.History and Rules\n4.Log out");
            out.println("needreply");
            String choice = in.readLine();
            if (choice.equals("1")) {
                SinglePlayer singlePlayer = new SinglePlayer(this.userSocket,this.player);
                singlePlayer.start();

            } else if (choice.equals("2")) {
                out.println("Multiplayer is not available yet");
                MultiManager multi = new MultiManager(this.userSocket,this.player);
                multi.start();
            } else if (choice.equals("3")) {
                player.uploadScore();
                out.println("You have won "+player.wins+" games");
                out.println("You have lost "+player.losses+" games");
                out.println("Rules:");
                out.println("--You have 6 chances before you lose ");
                out.println("--Multiplayer teams have a minimum of 2 players and a maximum of 4 players");

            }
            else if (choice.equals("4")) {
                out.println("Logging out");
                break;
            }
            else {
                out.println("Invalid choice");
            }

        }

}
}
