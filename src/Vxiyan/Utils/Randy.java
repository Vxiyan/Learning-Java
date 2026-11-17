package Vxiyan.Utils;
import Vxiyan.CritClasses.Event.type;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
public class Randy {
    public static double randRange(double min, double max) {
        return min + Math.random() * (max - min);
    }

    public static type randEventType() {
        type[] values = type.values();
        return values[ThreadLocalRandom.current().nextInt(values.length)];
    }

    public static <K, V> Map.Entry<K, V> randomEntry(Map<K, V> map) {
        if (map.isEmpty()) throw new NoSuchElementException("Map is empty");
        int target = ThreadLocalRandom.current().nextInt(map.size());
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        for (int i = 0; i < target; i++) it.next();
        return it.next();
    }
}