package build.model;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class GradeLetters {

    private int id;
    private char letter;
    private double highest;
    private double lowest;

    public GradeLetters(){
        super();
    }

    public GradeLetters(char letter, double highest, double lowest) {
        this.letter = letter;
        this.highest = highest;
        this.lowest = lowest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public double getHighest() {
        return highest;
    }

    public void setHighest(double highest) {
        this.highest = highest;
    }

    public double getLowest() {
        return lowest;
    }

    public void setLowest(double lowest) {
        this.lowest = lowest;
    }
}
