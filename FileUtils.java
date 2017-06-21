import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sahil Pattni on 16-Apr-17.
 */
public class FileUtils {

    public boolean saveWordTable(List<Word> wordTable, String filePath)
    {
        if (wordTable == null || filePath == null)
            return false;
        try
        {
            FileOutputStream fos = new FileOutputStream(filePath);

            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(wordTable);

            oos.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean savePageTable(List<Page> pageTable, String filePath)
    {
        if (pageTable == null || filePath == null)
            return false;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try
        {
            fos = new FileOutputStream(filePath);

            oos = new ObjectOutputStream(fos);

            oos.writeObject(pageTable);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            try
            {
                fos.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public List<Page> getPageList(String filePath)
    {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        if (filePath == null)
            return null;
        try
        {
            fis = new FileInputStream(filePath);
            ois = new ObjectInputStream(fis);

            List<Page> pages = (ArrayList<Page>) ois.readObject();
            return pages;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                ois.close();
                fis.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Word> getWordList(String filePath)
    {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        if (filePath == null)
            return null;
        try
        {
            fis = new FileInputStream(filePath);
            ois = new ObjectInputStream(fis);

            List<Word> words = (ArrayList<Word>) ois.readObject();
            return words;
        }
        catch(Exception e)
        {
            return null;
        }
        /*finally
        {
            try
            {
                assert ois != null;
                ois.close();
                fis.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

}
