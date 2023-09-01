package Server;

import java.util.ArrayList;

public class Team {
    public String name;
    public ArrayList<User> players = new ArrayList<>();

public Team(String name) {
        this.name = name;
    }
    public Team() {
    }
    public void addPlayer(User player) {
        players.add(player);
    }
}
