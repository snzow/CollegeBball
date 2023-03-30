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

    public static void main(String[] args) throws IOException {

        initializePlayerGen();


        ArrayList<Player> teamOne = new ArrayList<Player>();
        ArrayList<Player> teamTwo = new ArrayList<>();
        teamOne.add(new Player("Aodhan","Bower",90,90));
        teamOne.add(new Player("Max","Ramstad"));
        teamOne.add(new Player("Charlie","Dennis"));
        teamOne.add(new Player("Malcolm","Bower"));
        teamOne.add(new Player("Charlotte","Houston",95,50));

        teamTwo.add(generatePlayer());
        teamTwo.add(generatePlayer());
        teamTwo.add(generatePlayer());
        teamTwo.add(generatePlayer());
        teamTwo.add(generatePlayer());


        Team teamA = new Team(teamOne);
        Team teamB = new Team(teamTwo);

        Game game = new Game(teamA,teamB);

        game.playGame();
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
