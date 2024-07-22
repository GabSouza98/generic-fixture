package generic.fixture;

import java.util.HashMap;
import java.util.Map;

/**
    Class created to ensure type-safety within each key/value entry,
    but still allowing different types between entries.

    Valid usage examples:
     <pre>{@code
          map.put(Integer.class, 2);
          map.put(String.class, "2");
      }</pre>

    Invalid usage example:
      <pre>{@code
        map.put(String.class, 2);
      }</pre>
 */
public class CustomMap {

    private Map<Class<?>, Object> map;

    public CustomMap() {
        map = new HashMap<>();
    }

    public <T> void put(Class<T> key, T value) {
        map.put(key, value);
    }

    public <T> T get(Class<T> key) {
        // we know it's safe, but the compiler can't prove it
        return (T) map.get(key);
    }

    public Map<Class<?>, Object> getMap() {
        return map;
    }
}
