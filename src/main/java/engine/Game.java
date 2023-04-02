package engine;

import main.Main;
import players.Player;
import teams.Team;

import java.util.ArrayList;
import java.util.Comparator;

public class Game {
    public Team home;
    public Team away;

    public int homeScore;
    public int awayScore;

    public int timeLeft;
    public Team possession;
    public Team defense;

    int counter;

    public Game(Team home, Team away){
        this.home = home;
        this.away = away;
        this.homeScore = 0;
        this.awayScore = 0;
        this.timeLeft = 200;
        possession = null;
        counter = 0;
    }

    public Team playGame(){
        int homePos = 0;
        int awayPos = 0;
        Boolean firstPossession = true;
        counter = 0;
        while(timeLeft > 0){
            home.makeSubs();
            away.makeSubs();

            home.updateStamina();
            away.updateStamina();

            if(firstPossession){
                double tip = Main.randomNumber(0,1);
                if(tip == 1){
                    possession = home;
                    defense = away;
                    homePos ++;
                }
                else{
                    possession = away;
                    defense = home;
                    awayPos++;
                }
                firstPossession = false;
            }
            double seed;
            int reboundSeed;
            int reboundStat;
            Player assist = null;
            if(possession.equals(home)){
                homePos++;
            }
            else{
                awayPos++;
            }
            while(true){

                seed = Main.randomNumber(0,10);
                if(seed == 10){
                    defense.getPlayer(counter).gameStats.steals++;
                    changePossession();
                    break;
                }
                else if(seed >= 6){
                    assist = possession.getPlayer(counter);
                    incrementCounter();
                    if(counter > 4){
                        counter = 0;
                    }
                }
                else if(seed > 3){
                    reboundStat = getReboundMax(counter);
                    reboundSeed = Main.randomNumber(0,reboundStat);
                    if(possession.getPlayer(counter).takeShot(defense.getPlayer(counter),assist,3)){
                        if(assist != null){
                            assist.gameStats.assists++;
                        }
                        if(possession.equals(home)){
                            homeScore += 3;
                        }
                        else{
                            awayScore += 3;
                        }
                        break;
                    }
                    else{
                        if(simRebound(reboundStat,reboundSeed) == true){
                            break;
                        }
                    }

                }
                else{
                    reboundStat = getReboundMax(counter);
                    reboundSeed = Main.randomNumber(0,reboundStat);
                    if(possession.getPlayer(counter).takeShot(defense.getPlayer(counter),assist,2)){
                        if(assist != null){
                            assist.gameStats.assists++;
                        }
                        if(possession.equals(home)){
                            homeScore += 2;
                        }
                        else{
                            awayScore += 2;
                        }

                        break;
                    }
                    else{
                        if(simRebound(reboundStat,reboundSeed) == true){
                            break;
                        }
                    }
                }
            }
            timeLeft--;

        }
        home.playerList.sort(Comparator.comparingInt(Player :: getPoints).reversed());
        away.playerList.sort(Comparator.comparingInt(Player :: getPoints).reversed());
        System.out.println("--" + home.toString() + "--");
        for(int i = 0; i < home.maxRosterSize; i++){
            home.playerList.get(i).gameStats.stamina = 100;
            System.out.println(home.playerList.get(i).gameStats.toString());
            home.playerList.get(i).gameStats.resetStats();
        }
        System.out.println("--" + away.toString() + "--");
        for(int i = 0; i < away.maxRosterSize; i++){
            away.playerList.get(i).gameStats.stamina = 100;
            System.out.println(away.playerList.get(i).gameStats.toString());
            away.playerList.get(i).gameStats.resetStats();
        }
        System.out.println("Home pos: " + homePos + " away pos:" + awayPos);
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

    public int getReboundMax(int counter){
        int reboundStat;
        if(counter == 4){
            reboundStat = possession.getPlayer(counter).getEffectiveRebounding() + defense.getPlayer(counter).getEffectiveRebounding()
                    + defense.playerList.get(0).getEffectiveRebounding();
        }
        else{
            reboundStat = possession.getPlayer(counter).getEffectiveRebounding() + defense.getPlayer(counter).getEffectiveRebounding()
                    + defense.playerList.get(counter+1).getEffectiveRebounding();
        }
        return reboundStat;
    }

    public void incrementCounter(){
        counter++;
        if(counter == 5){
            counter = 0;
        }
    }

    public int nextCounter(){
        if(counter == 4){
            return 0;
        }
        return counter + 1;
    }

    public boolean simRebound(int reboundStat, int reboundSeed){
        if(reboundSeed > reboundStat - possession.getPlayer(counter).getEffectiveRebounding()){
            possession.getPlayer(counter).rebound();
            incrementCounter();
            return false;
        }
        else if(reboundSeed > defense.getPlayer(counter).getEffectiveRebounding()){
            incrementCounter();
            possession.getPlayer(counter).rebound();
            changePossession();
        }
        else{
            defense.getPlayer(counter).rebound();
            changePossession();
        }
        return true;
    }


    private class inGameTeam{
        Player pg;
        Player sg;
        Player sf;
        Player pf;
        Player c;
        ArrayList<Player> bench;
        Team team;

        int points;

        public inGameTeam(Team team){
            this.team = team;
            this.points = 0;
            this.bench = team.playerList;
            bench.sort(Comparator.comparing(Player :: getEffectiveShooting).reversed());
            sg = bench.remove(0);

        }

        public void setLineup(){


        }
    }

}
