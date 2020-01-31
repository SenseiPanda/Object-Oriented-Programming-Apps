

public class Team {
    private String mascot;
    private int score;

    public Team(String name){
        this.mascot = name;
        this.score = 0;
    }
    public String GetMascot(){
        return mascot;
    }
    public int GetScore(){
        return score;
    }
    public void Score(){
        score = score +2;
    }

    public static void main(String[] args) {
         Team team2 = new Team("Crimson Tide");
         Team team1 = new Team("LSU Tigers");
         team1.Score();
         team2.Score();
         team2.Score();
         if (team1.score>team2.score){
             System.out.println("The " + team1.mascot +" have won the game!");
         }
         else {
             System.out.println(team2.mascot +"has won the game!");
         }

    }
}
