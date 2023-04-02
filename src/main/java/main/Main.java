package main;

import engine.Game;
import engine.Position;
import players.Player;
import teams.Team;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static ArrayList<String> firstNames;
    static ArrayList<String> lastNames;
    public static HashMap<String,Player> playerMap;
    public static HashMap<Team, Team> teamMap;
    public static ArrayList<Team> teamList;

    public static HashMap<String,Position> positions;

    public static ArrayList<Position> positionList;

    public static Position POINT_GUARD;

    public static Position SHOOTING_GUARD;
    public static Position SMALL_FORWARD;
    public static Position POWER_FORWARD;
    public static Position CENTER;

    public static void main(String[] args) throws IOException {
        initializePositions();
        initializePlayerGen();
        initializeTeams();


        teamList.get(0).addPlayer(new Player("Aodhan","Bower",100,90,90,90));

        teamList.get(0).addPlayer(new Player("Max","Ramstad",65,95,90,100));
        teamList.get(0).addPlayer(new Player("Charlie","Dennis",40,100,80,75));
        teamList.get(0).addPlayer(new Player("Malcolm","Bower",80,85,85,100));
        teamList.get(0).addPlayer(new Player("Charlotte","Houston",110,30,85,65));
        teamList.get(0).addPlayer(generatePlayer());
        teamList.get(0).addPlayer(generatePlayer());
        teamList.get(0).addPlayer(generatePlayer());
        teamList.get(0).addPlayer(generatePlayer());
        teamList.get(0).addPlayer(generatePlayer());

        for(int i = 1; i < teamList.size(); i++){
            Team temp = teamList.get(i);
            for(int j = 0; j < temp.maxRosterSize; j++){
                temp.addPlayer(generatePlayer());
            }
        }







        Game game = new Game(teamList.get(0),teamList.get(1));
        Game game2 = new Game(teamList.get(2),teamList.get(3));

        Team g1w = game.playGame();
        Team g2w = game2.playGame();
        Game finals = new Game(g1w,g2w);

        finals.playGame();
    }

    public static int randomNumber(int min, int max){
        return (int)Math.floor(Math.random() *(max - min + 1) + min);
    }

    public static void initializePlayerGen() throws IOException {
        firstNames = new ArrayList<>();
        lastNames = new ArrayList<>();
        playerMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader("src/firstNames.txt"));
        String name;
        while((name = br.readLine()) != null){
            firstNames.add(name);
        }
        br = new BufferedReader(new FileReader("src/lastNames.txt"));
        while((name = br.readLine()) != null){
            lastNames.add(name);
        }
    }

    public static void initializeTeams() throws IOException {
        teamMap = new HashMap<>();
        teamList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader("src/teams.txt"));
        String city;
        while((city = br.readLine()) != null){
            String mascot = br.readLine();
            Team temp = new Team(city,mascot);
            teamMap.put(temp,temp);
            teamList.add(temp);
        }
    }

    public static void initializePositions(){
        positionList = new ArrayList<>();
        positions = new HashMap<>();
        positionList.add(new Position("Point Guard","PG",4,7,1));
        positionList.add(new Position("Shooting Guard","SG",4,3,1));
        positionList.add(new Position("Small Forward","SF",3,3,2));
        positionList.add(new Position("Power Forward","PF",2,4,6));
        positionList.add(new Position("Center","C",1,2,9));
        POINT_GUARD = positionList.get(0);
        SHOOTING_GUARD = positionList.get(1);
        SMALL_FORWARD = positionList.get(2);
        POWER_FORWARD = positionList.get(3);
        CENTER = positionList.get(4);
        for(int i = 0; i < 5; i++){
            positions.put(positionList.get(i).getShortName(),positionList.get(i));
        }
    }

    public static Player generatePlayer(){
        int max = firstNames.size()*lastNames.size();
        int loops = 0;
        while(true){
            int seed = randomNumber(0, firstNames.size()-1);
            int seed2 = randomNumber(0, lastNames.size() -1);
            String firstName = firstNames.get(seed);
            String lastName = lastNames.get(seed2);
            if(playerMap.get(firstName + lastName) == null){
                Player toGenerate = new Player(firstName,lastName);
                playerMap.put(firstName+lastName,toGenerate);
                return toGenerate;
            }
            loops++;
            if(loops >= max){
                return null;
            }
        }




    }
}
