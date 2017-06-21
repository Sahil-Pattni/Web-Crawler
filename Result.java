import java.io.Serializable;

/**
 * Created by Sahil Pattni on 17-Apr-17.
 */
public class Result implements Serializable, Comparable<Result> {
    public int score;
    public static final long serialVersionUID = -938761094876384658L;
    public String url;
    public int urlID;

    public Result(String url, int urlID) {
        this.url = url;
        this.urlID = urlID;
        score = 1;
    }

    public void updateScore(int score) {this.score += score;} //TODO: FIX: PARAM IS ALWAYS 783

    public void incrementScore() {score++;}

    public int getScore() {return score;}

    public String getURL() {return url;}

    public int getURLID() {return urlID;}

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Result) {
            Result result = (Result) obj;
            if (this.url.equals(result.url) || this.urlID == result.urlID) {
                return true;
            }
        }
        return false;
    }

    public int compareTo(Result candidate) {
        if (this.score > candidate.score)
            return -1;
        else if (this.score < candidate.score)
            return 1;
        else
            return 0;
    }


}
