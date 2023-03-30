package teams;

import players.Player;

import java.util.ArrayList;

public class Team {
    public ArrayList<Integer> teamList;
    public ArrayList<Player> playerList;

    public Team(ArrayList<Player> playerList){
        this.playerList = playerList;
    }

}
