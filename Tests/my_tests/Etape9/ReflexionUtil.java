package my_tests.Etape9;

/**
 * TODO Put description here
 *
 * @author Georgios Fotiadis (271875)
 * @author Clement Charollais (264231)
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ReflexionUtil
{
    private ReflexionUtil()
    {
    }

    public static <T> T invokeMethod(Object object, String name, Class[] types, Object[] arguments)
    {
        final Method method;
        try
        {
            method = object.getClass().getDeclaredMethod(name, types);
            method.setAccessible(true);
            return (T) method.invoke(object, arguments);
        }
        catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
            throw new IllegalStateException(e);
        }
    }
}
