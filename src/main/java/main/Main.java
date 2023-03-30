package main;

import engine.Game;
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
    static HashMap<String,Player> playerMap;
    static HashMap<Team, Team> teamMap;
    static ArrayList<Team> teamList;

    public static void main(String[] args) throws IOException {

        initializePlayerGen();
        initializeTeams();

        teamList.get(0).addPlayer(new Player("Aodhan","Bower",90,90));

        teamList.get(0).addPlayer(new Player("Max","Ramstad"));
        teamList.get(0).addPlayer(new Player("Charlie","Dennis"));
        teamList.get(0).addPlayer(new Player("Malcolm","Bower"));
        teamList.get(0).addPlayer(new Player("Charlotte","Houston",95,50));

        for(int i = 1; i < teamList.size(); i++){
            Team temp = teamList.get(i);
            for(int j = 0; j < 5; j++){
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

    public static int randomNumber(int max, int min){
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
