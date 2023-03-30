package teams;

import players.Player;

import java.util.ArrayList;

public class Team {
    public ArrayList<Integer> teamList;
    public ArrayList<Player> playerList;
    public String city;
    public String mascot;
    public int maxRosterSize;

    public Team(String city, String mascot){
        playerList = new ArrayList<>();
        this.city = city;
        this.mascot = mascot;
        this.maxRosterSize = 5;

    }

    public void addPlayer(Player player){
        if(playerList.size() == maxRosterSize){
            return;
        }
        playerList.add(player);
    }

    public String toString(){
        return city + " " + mascot;
    }

}
