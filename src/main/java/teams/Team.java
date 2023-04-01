package teams;

import players.Player;

import java.util.ArrayList;
import java.util.Comparator;

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
        this.maxRosterSize = 10;

    }

    public void addPlayer(Player player){
        if(playerList.size() == maxRosterSize){
            return;
        }
        playerList.add(player);
    }
    public Player getPlayer(int i){
        return playerList.get(i);
    }
    public void makeSubs(){
        playerList.sort(Comparator.comparing(Player :: getEffectiveOvr).reversed());
    }

    public void updateStamina(){
        for(int i = 0; i < 5; i++){
            playerList.get(i).gameStats.stamina--;
        }
        for(int i = 5; i < maxRosterSize; i++){
            Player p = playerList.get(i);
            if(p.gameStats.stamina < 100){
                p.gameStats.stamina += 2;
            }
        }
    }

    public String toString(){
        return city + " " + mascot;
    }

}
