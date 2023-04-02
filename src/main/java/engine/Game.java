package engine;

import main.Main;
import players.Player;
import players.PlayerTendencies;
import teams.Team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Game {
    public Team homeTeam;
    public Team awayTeam;

    public InGameTeam home;
    public InGameTeam away;

    public static Position POINT_GUARD = Main.POINT_GUARD;

    public static Position SHOOTING_GUARD = Main.SHOOTING_GUARD;
    public static Position SMALL_FORWARD = Main.SMALL_FORWARD;
    public static Position POWER_FORWARD = Main.POWER_FORWARD;
    public static Position CENTER = Main.CENTER;


    public int timeLeft;
    public InGameTeam possession;
    public InGameTeam defense;
    Player ballCarrier;


    public Game(Team home, Team away){
        this.homeTeam = home;
        this.awayTeam = away;
        this.home = new InGameTeam(homeTeam);
        this.away = new InGameTeam(awayTeam);
        this.timeLeft = 200;
        possession = null;
        ballCarrier = null;
    }

    public Team playGame(){
        int homePos = 0;
        int awayPos = 0;
        int turnovers = 0;

        Boolean firstPossession = true;
        while(timeLeft > 0){
            if(timeLeft % 20 == 0){
                home.sub();
                away.sub();
            }

            if(firstPossession){
                int max = home.courtMap.get(CENTER).rebounding +  away.courtMap.get(CENTER).rebounding;
                double tip = Main.randomNumber(0,max);
                if(tip <= home.courtMap.get(CENTER).rebounding){
                    possession = home;
                    defense = away;
                    ballCarrier = home.getPlayer(POINT_GUARD);
                    homePos ++;
                }
                else{
                    possession = away;
                    defense = home;
                    ballCarrier = away.getPlayer(POINT_GUARD);
                    awayPos++;
                }
                firstPossession = false;
            }
            double seed;
            Player assist = null;
            if(possession.equals(home)){
                homePos++;
            }
            else{
                awayPos++;
            }
            ballCarrier = possession.getPlayer(POINT_GUARD);
            while(true){
                PlayerTendencies ballTend = ballCarrier.tendencies;
                seed = Main.randomNumber(1,20);
                if(seed > ballTend.getShotTendency()){
                    int rand = Main.randomNumber(0,10);
                    if(rand > 2){
                        assist = ballCarrier;
                    }
                    else{
                        assist = null;
                    }
                    rand = Main.randomNumber(0,100);
                    if(rand <= ballCarrier.getEffectivePassing() + 20){
                        ballCarrier = possession.pass(ballCarrier);
                    }
                    else{
                        turnovers++;
                        changePossession();
                        break;
                    }
                }
                else{
                    seed = Main.randomNumber(1,10);
                    boolean andOne = false;
                    int type = 2;
                    if(seed <= ballTend.getThreeRatio()){
                        type = 3;
                    }
                    Player defender = defense.getPlayer(ballCarrier.getPositionForTeam());
                    seed = Main.randomNumber(1,defender.defense);
                    if(seed >= defender.defense - 10){
                        for(int i = 0; i < type; i++){
                            if(ballCarrier.takeShot(defender,null,1)){
                                possession.score(1);
                            }
                        }
                        changePossession();
                        break;

                    }
                    else if(seed >= defender.defense -15){
                        andOne = true;
                    }
                    else if(ballCarrier.takeShot(defense.getPlayer(ballCarrier.positionForTeam),assist,type)){
                        possession.score(type);
                        if(andOne){
                            if(ballCarrier.takeShot(defender,null,1)){
                                possession.score(1);
                            }
                        }
                        changePossession();
                        break;
                    }
                    else{
                        seed = Main.randomNumber(1,5);
                        if(seed < 3){
                            ballCarrier = possession.getRebounder();
                        }
                        else{
                            ballCarrier = defense.getRebounder();
                            changePossession();

                        }
                        ballCarrier.rebound();
                        if(seed > 2){
                            break;
                        }

                    }

                }

            }
            home.updateStamina();
            away.updateStamina();
            if(timeLeft == 1 && home.getPoints() == away.getPoints()){
                timeLeft = 20;
            }
            timeLeft--;

        }
        homeTeam.playerList.sort(Comparator.comparingInt(Player :: getPoints).reversed());
        awayTeam.playerList.sort(Comparator.comparingInt(Player :: getPoints).reversed());
        if(home.getPoints() > away.getPoints()){
            printTeamStats(homeTeam);
            printTeamStats(awayTeam);
        }
        else{
            printTeamStats(awayTeam);
            printTeamStats(homeTeam);
        }

        System.out.println("Home pos: " + homePos + " away pos:" + awayPos + " " + turnovers + "total TO");
        if(home.getPoints() > away.getPoints()){
            System.out.println(homeTeam.toString()+ " defeat the " + awayTeam.toString() + " " + home.getPoints() + " - " + away.getPoints());
            return homeTeam;
        }
        else{
            System.out.println(awayTeam.toString()+ " defeat the " + homeTeam.toString() + " "  + away.getPoints() + " - " + home.getPoints());
            return awayTeam;
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

    public void printTeamStats(Team team){
        System.out.println("--" + team.toString() + "--");
        for(int i = 0; i < team.playerList.size(); i++){
            team.playerList.get(i).gameStats.stamina = 100;
            System.out.println(team.playerList.get(i).gameStats.toString());
            team.playerList.get(i).gameStats.resetStats();
        }
    }

    private class InGameTeam{
        Position pg;
        Position sg;
        Position sf;
        Position pf;
        Position c;

        HashMap<Position, Player> courtMap;
        ArrayList<Player> bench;
        Team team;

        int points;

        public InGameTeam(Team team){
            this.team = team;
            this.points = 0;
            this.bench = (ArrayList<Player>)team.playerList.clone();
            team.setDepthCharts();
            courtMap = new HashMap<>();
            pg = Main.positions.get("PG");
            sg = Main.positions.get("SG");
            sf = Main.positions.get("SF");
            pf = Main.positions.get("PF");
            c = Main.positions.get("C");
            courtMap.put(pg,null);
            courtMap.put(sg,null);
            courtMap.put(sf,null);
            courtMap.put(pf,null);
            courtMap.put(c,null);
            sub();
        }

        public void sub(){
            team.setDepthCharts();
            subPosition(pg);
            subPosition(sg);
            subPosition(sf);
            subPosition(pf);
            subPosition(c);
        }

        private void subPosition(Position position) {
            Player prevOnCourt = courtMap.get(position);
            if(prevOnCourt != null){
                bench.add(prevOnCourt);
            }
            courtMap.put(position,null);
            List<Player> toEvalList;
            if(team.getPositionDepthChart(position).size() == 0){
                toEvalList = bench;
            }
            else{
                toEvalList = team.getPositionDepthChart(position);
            }
            int cur = 0;
            while(courtMap.get(position) == null){
                if(cur >= toEvalList.size()){
                    cur = 0;
                    toEvalList = bench;
                }
                Player toEval = toEvalList.get(cur);
                if(bench.contains(toEval)){
                    bench.remove(toEval);
                    courtMap.put(position,toEval);
                    toEval.positionForTeam = position;
                    break;
                }
                cur++;
            }

        }

        public Player getRebounder(){
            int total = 0;
            int[] maxes = new int[5];
            for(int i = 0; i < 5; i++){
                Player toEval = courtMap.get(Main.positionList.get(i));
                if(toEval != null){
                    total += toEval.tendencies.getRbTendency();
                    maxes[i] = total;
                }
            }
            int seed = Main.randomNumber(0,total);
            for(int i = 0; i < 5; i++){
                if(seed <= maxes[i]){
                    return courtMap.get(Main.positionList.get(i));
                }
            }
            return null;
        }
        public void updateStamina(){
            for(int i = 0; i < 5; i++){
                courtMap.get(Main.positionList.get(i)).gameStats.stamina--;
            }
            for(int i = 0; i < bench.size(); i++){
                Player p = bench.get(i);
                if(p.gameStats.stamina < 100){
                    p.gameStats.stamina += 2;
                }
            }
        }

        public Player getPlayer(Position position){
            return courtMap.get(position);
        }

        public Player pass(Player carrier){
            while(true){
                int seed = Main.randomNumber(0,4);
                Position p = Main.positionList.get(seed);
                if(!p.equals(carrier.position)){
                    return getPlayer(p);
                }
            }
        }

        public void score(int score){
            points += score;
        }

        public int getPoints(){
            return points;
        }
    }





}
