package ch.epfl.alpano.gui;

import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * converts a string to an integer based on its position in a list or the inverse
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */
public final class LabeledListStringConverter extends StringConverter<Integer>
{
    private final List<String> list;

    /**
     * constructor of the class that instantiates the list
     *
     * @param list the list of strings to be converted
     */
    public LabeledListStringConverter(String... list)
    {
        this.list = Collections.unmodifiableList(new ArrayList<>(asList(list)));
    }

    @Override
    public Integer fromString(String s)
    {
        return list.indexOf(s);
    }

    @Override
    public String toString(Integer index)
    {
        return (index != null ? list.get(index) : "");
    }
}
