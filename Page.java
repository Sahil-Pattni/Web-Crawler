import java.io.Serializable;

/**
 * Created by piyushjuneja on 3/31/17.
 */
public class Page implements Comparable, Serializable {
    public static final long serialVersionUID = -1827677255104766839L;
    String url;
    private int urlID;


    public Page(String url, int urlID) {
        this.url = url;
        this.urlID = urlID;
    }


    public String getURL() {
        return this.url;
    }

    public int getURLID() {
        return this.urlID;
    }

    @Override
    public int compareTo(Object o) {
        Page candidate = (Page) o;
        if (candidate.getURLID() < this.getURLID()) return -1; // Less than this
        else if (candidate.getURLID() > this.getURLID()) return 1; // Greater than this
        else return 0; // equal to this
    }

    public boolean equals(Object obj) {
        Page candidate = (Page) obj;
        if (candidate.getURL().equals(this.getURL()) ||
                candidate.getURLID() == this.getURLID())
            return true;
        else
            return false;
    }
}
