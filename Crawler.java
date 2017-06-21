import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.print.Doc;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Sahil Pattni on 04-Apr-17.
 */
public class Crawler {
    static String seedURL;
    static int currentID;
    static String domain;
    static int limit;
    MyQueue toParse;
    static Parser parser;

    static int totalURLs;
    static List<Page> parsed  = new ArrayList<>();
    static List<String> visited = new ArrayList<>();
    static List<Word> words = new ArrayList<>();

    public Crawler(String seed, String domain, int limit) {
        currentID = 0;
        totalURLs = 0;
        Crawler.seedURL = seed;
        Crawler.domain = domain;
        Crawler.limit = limit;

        parser = new Parser();
        toParse = new MyQueue();

        toParse.add(seed);
    }

    public void crawl(){
        while (!toParse.isEmpty() && currentID < limit) {
            if (toParse.peek().getData() != null) { //if next node to be parsed is not null
                String link = (String) toParse.remove().getData(); //remove node from queue and parse
                if (isValidURL(link)) {
                    if (!visited.contains(link)) { //if link has not been previously visited
                        Page p = new Page(link, currentID);
                        if (!pageExists(p)) { //custom method to test whether page has already been created
                            try {
                                Document d = parser.getDocument(link);
                                if (parse(d, currentID)) {
                                    currentID++;
                                    addPageToList(p); //Add page to list of parsed pages
                                }
                            }
                            catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        visited.add(link); //Add links to visited regardless of whether they have been parsed
                    }
                }
            }
            else {
                break;
            }
        }
    }

    public boolean parse(Document doc, int id) {
        boolean returner = false;
        try {
            parseLinks(doc);
            parseText(doc, id);
            returner = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returner;
    }

    public boolean pageExists (Page p) {
        for (Page page : parsed) {
            if (page.equals(p))
                return true;
        }

        return false;
    }

    public static void main(String[] args) throws ParseException {
        Crawler cl = new Crawler("https://www.cs.purdue.edu/homes/bxd/", "www.cs.purdue.edu", 50);
        Parser p = new Parser();
        Document d = p.getDocument("http://www.cs.purdue.edu/homes/cs177");
        cl.parse(d, currentID);
    }

    public void parseLinks(Document doc) throws ParseException{
        Elements links = parser.getLinks(doc);
        boolean exists = false;
        for (Element element : links) {
            for (String string : visited) {
                if (element.hasText()) {
                    if (element.equals(string))
                        exists = true;
                }

            }
            if (!exists)
                toParse.add(element.attr("abs:href"));
        }
    }
    public void parseText(Document doc, int id) {
        try {
            String text = parser.getBody(doc);  //Gather text
            if (!text.equals("")) { //see getBody() in Parser class
                String[] splitUp = text.split(" "); //Split up into array

                for (String string : splitUp) {  //parse through words
                    boolean isInArray = false;  // boolean to check if current word is in array
                    for (Word word : words) { // parse through Words array
                        if (word.getWord().equals(string)) { // if the word exists
                            isInArray = true;
                            word.addURLID(id);
                        }
                    }
                    if (!isInArray) {
                        addWordToList(string.toLowerCase(), id);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void addWordToList(String word, int id) {
        Word wordle = new Word(word, id);
        words.add(wordle);
    }
    public void addToQueue(String url) {
        boolean duplicateURL = false;

        for (Page page : parsed) {
            if (page.getURL().equals(url))
                duplicateURL = true;
        }

        if (!duplicateURL) {
            toParse.add(url);
            totalURLs++;
        }

    }

    public void addPageToList(Page p) {
        boolean exists = false;

        for (Page page : parsed) {
            if (page.equals(p))
                exists = true;
        }

        if (!exists)
            parsed.add(p);
    }

    public boolean isInDomain(String url) {
        if (url.contains(domain))
            return true;
        return false;
    }
    public boolean isValidURL(String url) {
        boolean valid = false;
        if ((url.startsWith("https://") || url.startsWith("http://"))) {
            valid = true;
        }
        else
            valid = false;

        return valid;
    }
}
