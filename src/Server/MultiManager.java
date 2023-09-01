package Server;

import java.io.*;
import java.util.ArrayList;
import java.net.Socket;

public class MultiManager {
public static ArrayList<User> players = new ArrayList<>();
public static ArrayList<Game> games = new ArrayList<>();
Game game = new Game();
Socket clientSocket;
User player ;
PrintWriter out = null;
BufferedReader in = null;
public MultiManager(Socket userSocket, User player ) throws FileNotFoundException {
players.add(player);
this.clientSocket = userSocket;
this.player = player;
}

public void start() throws IOException {
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    out.println("1. Create Game");
    out.println("2. Join Game");
    out.println("needreply");
    String choice = in.readLine();
    if (choice.equals("1")) {
        out.println("Enter a game code");
        while(true) {
            out.println("needreply");
            String gameName = in.readLine();
            if(game.checkGame(gameName)) {
                out.println("Game code already exists");
                out.println("Enter a new game code or join other game");
            } else {
                createGame(gameName, this.player);
                //games.add(gameName);
                out.println("Game created");
                break;
            }

            }

        }
    else if(choice.equals("2")){
        while(true) {
            out.println("Enter a game code");
            out.println("needreply");
            String code = in.readLine();
            if (game.checkGame(code)) {
                if(!checkTeam(code)){
                    out.println("Game is full");
                    out.println("Enter another game");
                }
                else{
                    joinGame(code, this.player);
                    out.println("Game joined");
                }

            } else {
                out.println("Game code does not exist");
            }
        }
    }

}
public void createGame(String Code, User player) throws IOException {
    Game game= new Game(Code);
    game.addGame(Code);
    games.add(game);
    game.team.addPlayer(player);
    game.start(player,this.clientSocket);

}
public boolean checkTeam(String Code){
    for(int i = 0; i < games.size(); i++) {
        if(games.get(i).equals(Code)) {
                System.out.println(games.get(i).team.players.get(i).username);
        }
    }
    boolean flag = false;
    for(int i = 0; i < games.size(); i++) {
        if(games.get(i).equals(Code)) {
            if(games.get(i).team.players.size() == 3) {
                flag = false;
            }
        }
    }
    return flag;
}
public void joinGame(String Code, User player) throws IOException {
    for(int i = 0; i < games.size(); i++) {
        if (games.get(i).equals(Code)) {
            games.get(i).team.addPlayer(player);
            games.get(i).start(player,this.clientSocket);
        }
    }
}

}




