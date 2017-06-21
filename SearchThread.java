import java.io.Serializable;

/**
 * Created by Sahil Pattni on 17-Apr-17.
 */
public class SearchThread implements Serializable, Runnable {
    int start;
    int finish;
    String[] terms;

    public SearchThread(int start, int finish, String[] terms) {
        this.start = start;
        this.finish = finish;
        this.terms = terms;
    }

    public Word findTerm(String term) {
        for (Word word : Search.wordList) {
            if (word.getWord().equalsIgnoreCase(term)) {
                return word;
            }
        }
        return null;
    }

    public void run() {
        for (String term : terms) { //For each term searched
            for (int i = start; i <= finish; i++) {
                if (Search.wordList.get(i).getWord().equalsIgnoreCase(term)) { //if Word was found in Search.wordList  || CHANGELOG: replaced findTerm(term) != null
                    for (int ID : Search.wordList.get(i).getList()) { //Parse through link postings for given words
                        String url = Search.pageList.get(ID).getURL();
                        Result result = new Result(url , ID); //Store each link as a result

                        if (Search.resultSet.contains(result)) { //if array of results already contains current result
                            int index = Search.resultSet.indexOf(result);
                            Search.resultSet.get(index).incrementScore(); //Increment score of current result object
                        }
                        else
                            Search.resultSet.add(result); //if result doesn't exist in result array, add to array;
                    }
                }
            }
        }
    }
}
