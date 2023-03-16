package dbms.hibernate.user_type.ex1;

import org.hibernate.HibernateException;
import utils.ClassUtils;

/**
 *
 */
public class ExampleUtils {
    public static void throwIllegalPropertyNumber(Object type, Object component, int property, String method) throws HibernateException {
        String template = "%s#%s: %s is an invalid property index for class type %s";
        String message = String.format(template, method, ClassUtils.simpleName(type), property, ClassUtils.simpleName(component));
        throw new IllegalArgumentException(message);
    }
}
