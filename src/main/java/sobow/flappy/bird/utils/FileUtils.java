package sobow.flappy.bird.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils
{
    private FileUtils() {}

    public static String loadAsString(String file)
    {
        StringBuilder stringBuilder = new StringBuilder();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String buffer;
            while ((buffer = reader.readLine()) != null)
            {
                stringBuilder.append(buffer);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
