package nebula.io;

import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<>();
    int maxEntries;

    public Cache(int maxEntries) {
        this.maxEntries = maxEntries;
    }


    String get(String key) {
        return hashMap.get(key);
    }

    void put(String key, String value) {
        while (hashMap.size() > maxEntries) {
            String firstKey = hashMap.keys().nextElement();
            hashMap.remove(firstKey);
        }
        hashMap.put(key, value);
    }

}
