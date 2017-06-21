import java.io.File;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sahil Pattni on 17-Apr-17.
 */
public class Search {
    static List<Page> pageList;
    private String pageListFile;
    static List<Result> resultSet;
    static List<Word> wordList;
    private String wordListFile;
    private FileUtils fl;

    public Search(String wordListFile, String pageListFile) {
        this.wordListFile = wordListFile;
        this.pageListFile = pageListFile;
        fl = new FileUtils();
        resultSet = Collections.synchronizedList(new ArrayList<Result>()); //code from project page on sync
        setup(wordListFile, pageListFile);
    }

    public synchronized void setup(String wordListFile, String pageListFile) {
        try {
            pageList = fl.getPageList(pageListFile);
            wordList = fl.getWordList(wordListFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Result> executeQuery(String query) {
        String[] keywords = query.split(" ");
        Thread[] threads = new Thread[5];
        int totalSize = wordList.size();
        int divided = totalSize/5;
        int endPoint = totalSize/5;  //Variable endpoint for SearchThread constructor
        int startPoint = 0;         //Variable startpoint for SearchThread constructor
        int currentThread = 0;  //current thread position in array

        for (Thread thread : threads) {
            thread = new Thread(new SearchThread(startPoint, endPoint, keywords)); //Start thread
            threads[currentThread] = thread; //Assign thread to current position in array
            if (startPoint == 0)
                startPoint += divided + 1;
            else
                startPoint+=(totalSize/5);

            if (endPoint+divided < totalSize-5)
                endPoint += divided;
            else
                endPoint = totalSize -1;

            currentThread++;
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sort();

        return resultSet;
    }

    public void nullCheck() {
        if (pageList == null || wordList == null)
            setup(this.wordListFile, this.pageListFile);
    }
    public void sort() {
        Collections.sort(resultSet);
    }
}
