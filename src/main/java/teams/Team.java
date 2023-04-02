package teams;

import engine.Position;
import main.Main;
import players.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Team {
    public ArrayList<Integer> teamList;
    public ArrayList<Player> playerList;
    private ArrayList<ArrayList<Player>> depthChartList;

    public ArrayList<Player> pgDepthChart;
    public ArrayList<Player> sgDepthChart;
    public ArrayList<Player> sfDepthChart;
    public ArrayList<Player> pfDepthChart;
    public ArrayList<Player> cDepthChart;
    private HashMap<Position,ArrayList<Player>> depthMap;


    public String city;
    public String mascot;
    public int maxRosterSize;

    public Team(String city, String mascot){
        playerList = new ArrayList<>();
        this.city = city;
        this.mascot = mascot;
        this.maxRosterSize = 10;
        pgDepthChart = new ArrayList<>();
        sgDepthChart = new ArrayList<>();
        sfDepthChart = new ArrayList<>();
        pfDepthChart = new ArrayList<>();
        cDepthChart = new ArrayList<>();
        depthMap = new HashMap<>();
        depthMap.put(Main.positions.get("PG"),pgDepthChart);
        depthMap.put(Main.positions.get("SG"),sgDepthChart);
        depthMap.put(Main.positions.get("SF"),sfDepthChart);
        depthMap.put(Main.positions.get("PF"),pfDepthChart);
        depthMap.put(Main.positions.get("C"),cDepthChart);
        this.depthChartList = new ArrayList<>();
        depthChartList.add(pgDepthChart);
        depthChartList.add(sgDepthChart);
        depthChartList.add(sfDepthChart);
        depthChartList.add(pfDepthChart);
        depthChartList.add(cDepthChart);

    }

    public void addPlayer(Player player){
        if(playerList.size() == maxRosterSize){
            return;
        }
        depthMap.get(player.position).add(player);
        depthMap.get(player.secondaryPosition).add(player);
        playerList.add(player);

    }

    public void setDepthCharts(){
        for(int i = 0; i < 5; i++){
            depthChartList.get(i).sort(Comparator.comparing(Player :: getEffectiveOvr));
        }
    }

    public Player getPlayer(int i){
        return playerList.get(i);
    }




    public ArrayList<Player> getPositionDepthChart(Position position){
        depthMap.get(position).sort(Comparator.comparing(Player :: getEffectiveOvr).reversed());
        return depthMap.get(position);
    }

    public String toString(){
        return city + " " + mascot;
    }

}
