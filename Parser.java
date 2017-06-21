import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by SahilMPattni on 3/31/2017.
 */
public class Parser {
    public Parser() {}

    public Document getDocument(String url) throws ParseException {
        Document d = null;

        //Custom Exceptions
        if (url == null)
            throw new ParseException("getDocument() failed. String url is null.");
        else if (url.equals(""))
            throw new ParseException("getDocument() failed. String url is empty.");

            //Try-Catch
        else {
            try {
                System.out.println(url);
                d = Jsoup.connect(url).timeout(3000).get();
            } catch (Exception e) {
                throw new ParseException("getDocument() failed. Connection failed.");
            }
            if (d == null)
                throw new ParseException("getDocument() failed. Docoument is null.");
        }
        return d;
    }

    public Elements getLinks(Document doc) throws ParseException {
        if (doc == null)
            throw new ParseException("getLinks() failed. Document parameter is null.");
        else
            return doc.select("a[href]");
    }

    public String getBody(Document doc) throws ParseException {
        if (doc == null)
            throw new ParseException("getBody() failed. Document parameter is null.");

        Element content = doc.body();

        if (content != null)
            return content.text();
        else
            return "";
    }

    public static void main(String[] args) throws ParseException {
        Parser p = new Parser();
        Document doc = p.getDocument("http://www.cs.purdue.edu");
        Element body = doc.body();
        String content = body.text();
        System.out.println(content);
        //for (Element e : links)
            //System.out.println(e.attr("abs:href"));
    }
}
