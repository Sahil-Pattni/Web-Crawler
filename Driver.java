import sun.swing.FilePane;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Sahil Pattni on 17-Apr-17.
 */
public class Driver {

    private FileUtils fu;
    private List<Page> pageFile;
    private List<Word> wordFile;

    public void crawl() {
        Crawler cl = new Crawler("https://cs.purdue.edu/", "www.cs.purdue.edu", 15);
        cl.crawl();
    }

    public static void main(String[] args) {
        Driver d = new Driver();
        d.crawl();
        d.save();

        Scanner s = new Scanner(System.in);
        boolean doneSearching = false;

        while (!doneSearching) {
            System.out.println("Enter Query");
            String query = s.nextLine();
            d.search(query);
            System.out.println();
            System.out.println("Do you want to continue (yes/no)");
            String answer = s.nextLine();
            if (answer.equalsIgnoreCase("no"))
                doneSearching = true;

        }
    }

    public void save() {
        fu = new FileUtils();
        fu.savePageTable(Crawler.parsed,"C:\\Users\\Sahil Pattni\\Desktop\\IntelliJ\\HW 10\\parsed.txt");
        fu.saveWordTable(Crawler.words, "C:\\Users\\Sahil Pattni\\Desktop\\IntelliJ\\HW 10\\words.txt");
    }

    public void search(String query) {
        String pageLocation = "C:\\Users\\Sahil Pattni\\Desktop\\IntelliJ\\HW 10\\parsed.txt";
        String wordLocation = "C:\\Users\\Sahil Pattni\\Desktop\\IntelliJ\\HW 10\\words.txt";
        Search s = new Search(wordLocation, pageLocation);
        List<Result> results = s.executeQuery(query);
        Collections.sort(results);

        //Output
        int currentResult = 0;
        System.out.println("Query: " + query);
        for (Result result: results) {
            System.out.println("("+currentResult+")" + result.getURL() + " | " +
            "score: " + result.getScore());
        }
    }

}
