package ChatApplication.Protocol;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by DaMasterHam on 23-02-2017.
 */
public class ChatProtocolParser
{
    // Protocol Syntax
    private static final int DELIMITER = (int)' ';
    private static final int START_PARA = (int)'{';
    private static final int END_PARA = (int)'}';

    // Server -> Client
    public static final String JOIN_OK = "J_OK";
    public static final String JOIN_ERR = "J_ERR";
    public static final String LIST = "LIST";

    // Client -> Server
    public static final String JOIN = "JOIN";
    public static final String ALIVE = "ALIVE";
    public static final String QUIT = "QUIT";

    // Both
    public static final String MSG = "MSG";

    // Other
    private static final String JOIN_PROTOCOL = JOIN + " {%s} {%s} {%s}";// JOIN {user_name} {server_ip} {server_port}
    private static final String MSG_PROTOCOL = MSG + " {%s} {%s}";// MSG {user_name} {message}
    private static final String LIST_PROTOCOL = LIST + " {%s}";

    private static int maxNrOfParameters = 10;
    private static boolean inParameter;
    private static StringReader reader;

    private static String[] shrinkArray(String[] array, int size)
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

    /**
     * Unpacks a string protocol with specifix syntax
     * Syntax:
     * ACTION {PARAMETER_1} {PARAMETER_2} ... {PARAMETER_N}
     * @param protocolStr a string containing a valid protocol to be parsed
     * @return an array of String containing the Action and the Parameters of the protocol
     */
    public static String[] unPack(String protocolStr)
    {
        String[] result = new String[maxNrOfParameters];
        int count = 0;

        inParameter = false;

        try
        {
            String buffer = "";
            int ch;
            reader = new StringReader(protocolStr);

            while (reader.ready())
            {
                ch = reader.read();

                if (ch == -1)
                    break;
                else if (ch == START_PARA) // Enter inside parameter
                    inParameter = true;
                else if (ch == END_PARA) // Exit parameter
                    inParameter = false;
                else if (ch == DELIMITER && !inParameter) // Split on space outside of parameter
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

            return shrinkArray(result, count);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    private static String pack(String action, String... parameters)
    {
        String result = action;

        for (String para : parameters)
        {
            result += " {" + para + "}";
        }

        return result;
    }

    public static String packJoin(String name, String ip, String port)
    {
        return pack(JOIN, name, ip, port);
    }

    public static String packJoinOk()
    {
        return JOIN_OK;
    }

    public static String packJoinError()
    {
        return JOIN_ERR;
    }

    public static String packMessage(String name, String message)
    {
        return pack(MSG, name, message);
    }

}