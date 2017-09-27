package ch.epfl.alpano.gui;

import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Converts a number to a string or the inverse
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public final class FixedPointStringConverter extends StringConverter<Integer>
{
    private int accuracy;

    /**
     * constructor of the class which instantiates the accuracy of the conversion
     *
     * @param accuracy the accuracy
     */
    public FixedPointStringConverter(int accuracy)
    {
        this.accuracy = accuracy;
    }


    @Override
    public String toString(Integer val)
    {

        if(val == null)
            return "";
        BigDecimal bigDecimal = new BigDecimal(val);

        return bigDecimal.movePointLeft(accuracy).toPlainString();
    }

    @Override
    public Integer fromString(String str)
    {
        if(str.equals(""))
            return 0;

        BigDecimal bigDecimal = new BigDecimal(str);

        return bigDecimal.movePointRight(accuracy).setScale(0, RoundingMode.HALF_UP).intValueExact();
    }
}
