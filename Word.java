import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piyush Juneja on 3/31/17.
 */
public class Word implements Serializable {
    String word;
    ArrayList<Integer> postings;
    public static final long serialVersionUID = -3696191086353573895L;
    int urlID;

    public Word(String word, int urlID) {
        postings = new ArrayList<>();
        this.word = word;
        this.postings.add(0, urlID);
    }

    public void addURLID(int urlID) {
        postings.add(urlID);
    }

    public String getWord() {
        return word;
    }

    public List<Integer> getList() {
        return postings;
    }

    public boolean equals(Object obj) {
        Word w = (Word) obj;
        return this.word.equals(w.getWord());
    }
}
