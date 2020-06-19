public class KeyPad {
    private StringBuilder keys;

    public KeyPad() {
        keys = new StringBuilder();
    }

    public void addKey(String key) {
        keys.append(key);
    }

    public void resetKeys() {
        keys = new StringBuilder();
    }

    public String getKeys() {
        return keys.toString();
    }
}
