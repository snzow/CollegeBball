package engine;

import main.Main;
import players.Player;
import teams.Team;

import java.util.ArrayList;

public class Game {
    public Team home;
    public Team away;

    public int homeScore;
    public int awayScore;

    public int timeLeft;
    public Team possession;
    public Team defense;

    public Game(Team home, Team away){
        this.home = home;
        this.away = away;
        this.homeScore = 0;
        this.awayScore = 0;
        this.timeLeft = 200;
        possession = null;
    }

    public Team playGame(){
        Boolean firstPossession = true;
        int counter = 0;
        while(timeLeft > 0){
            if(counter > 4){
                counter = 0;
            }
            if(firstPossession){
                double tip = Main.randomNumber(0,1);
                if(tip == 1){
                    possession = home;
                    defense = away;
                }
                else{
                    possession = away;
                    defense = home;

                }
                firstPossession = false;
            }
            double seed;
            while(true){
                seed = Main.randomNumber(0,6);
                if(seed == 6){
                    defense.playerList.get(counter).gameStats.steals++;
                    changePossession();
                    break;
                }
                else if(seed == 5){
                    counter++;
                    if(counter > 4){
                        counter = 0;
                    }
                }
                else if(seed > 2){
                    if(possession.playerList.get(counter).takeShot(defense.playerList.get(counter),3)){
                        if(possession.equals(home)){
                            homeScore += 3;
                        }
                        else{
                            awayScore += 3;
                        }
                    }
                    counter++;
                    changePossession();
                    break;
                }
                else{
                    if(possession.playerList.get(counter).takeShot(defense.playerList.get(counter),2)){
                        if(possession.equals(home)){
                            homeScore += 2;
                        }
                        else{
                            awayScore += 2;
                        }
                    }

                    changePossession();
                    counter++;
                    break;
                }
            }
            timeLeft--;

        }
        System.out.println("--Home--");
        for(int i = 0; i < 5; i++){
            System.out.println(home.playerList.get(i).gameStats.toString());
        }
        System.out.println("--Away--");
        for(int i = 0; i < 5; i++){
            System.out.println(away.playerList.get(i).gameStats.toString());
        }
        if(homeScore > awayScore){
            System.out.println(home.toString()+ " defeat the " + away.toString() + " " + homeScore + " - " + awayScore);
            return home;
        }
        else{
            System.out.println(away.toString()+ " defeat the " + home.toString() + " "  + awayScore + " - " + homeScore);
            return away;
        }


    }

    public void changePossession(){
        if(possession.equals(home)){
            possession = away;
            defense = home;
        }
        else{
            possession = home;
            defense = away;
        }
    }





}
