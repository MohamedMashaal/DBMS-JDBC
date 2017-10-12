package eg.edu.alexu.csd.oop.calculator;

/**
 *
 * @author youssefali
 */
public interface Calculator {

    /* return the current formula */
    public String current();

    /* Take input from user */
    public void input(String s);

    /* Load from file the saved operations */
    public void load();

    /*
     *  return the next operation in String format, or Null if no more history
     * available
     */
    public String next();

    /*
     *  return the last operation in String format, or Null if no more history
     * available
     */
    public String prev();

    /* Save in file the last 5 done Operations */
    public void save();

    /*
     *  Return the result of the current operations or throws a runtime
     * exception
     */
    public String getResult();
}


//~ Formatted by Jindent --- http://www.jindent.com
