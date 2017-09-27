package ch.epfl.alpano;

/**
 * Check if some conditions are satisfied
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charllais (264231)
 */
public interface Preconditions
{
    /**
     * checks the value of the parameter
     *
     * @param b true if the argument is correct, false otherwise
     * @throws IllegalArgumentException if the argument is incorrect
     */
    static void checkArgument(boolean b) throws IllegalArgumentException
    {
        if(!b)
            throw new IllegalArgumentException();
    }

    /**
     * checks the value of the boolean parameter and prints a message if it throws an exception
     *
     * @param b       true if the argument is correct, false otherwise
     * @param message the message to be printed if an exception is thrown
     * @throws IllegalArgumentException if the argument is incorrect
     */
    static void checkArgument(boolean b, String message) throws IllegalArgumentException
    {
        if(!b)
            throw new IllegalArgumentException(message);
    }
}
