package ChatApplication.Protocol;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by DaMasterHam on 23-02-2017.
 */
public class ProtocolStringParser
{
    private static int maxNrOfParameters = 10;
    private static boolean inParameter;
    private static StringReader reader;

    private static String[] resizeArray(String[] array, int size)
    {
        if (array.length > size)
        {
            String[] result = new String[size];

            for (int i = 0; i < size; i++)
            {
                result[i] = array[i];
            }

            return result;
        }
        else
            return array;
    }

    public static String[] getParameters(String message)
    {
        String[] result = new String[maxNrOfParameters];
        int count = 0;

        inParameter = false;

        try
        {
            String buffer = "";
            int ch;
            reader = new StringReader(message);

            while (reader.ready())
            {
                ch = reader.read();

                if (ch == -1)
                    break;
                else if (ch == (int)'{') // Enter inside parameter
                    inParameter = true;
                else if (ch == (int)'}') // Exit parameter
                    inParameter = false;
                else if (ch == (int)' ' && !inParameter) // Split on space outside of parameter
                {
                    result[count] = buffer; // Add buffer to array of paramters
                    buffer = "";
                    count++;
                }
                else // Add character to buffer
                    buffer += (char)ch;
            }

            // Adds final parameter
            if (buffer != "" || buffer != null)
            {
                result[count] = buffer;
                count++;
            }

            return resizeArray(result, count);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

}
