import java.util.Set;

/**
 * Created by Alexander Worton on 28/12/2016.
 */
public class Validation {

    public static void validateIdPositive(int id) {
        if(id < 1)
            throw new IllegalArgumentException("Id must be greater than 0");
    }

    public static void validateObjectNotNull(Object obj) {
        validateObjectNotNull(obj, "Object");
    }

    public static void validateObjectNotNull(Object obj, String variableName) {
        if(obj == null)
            throw new NullPointerException(variableName + " supplied was null");
    }

    public static void validateSetPopulated(Set collection) {
        validateSetPopulated(collection, "Collection");
    }

    public static void validateSetPopulated(Set collection, String collectionName) {
        if(collection.size() < 1)
            throw new IllegalArgumentException(collectionName + " supplied is empty");
    }
}
