package observer;

import java.util.ArrayList;
import java.util.List;

public class InventorySubject implements Subject {

    private static InventorySubject instance;
    private final List<Observer> observers = new ArrayList<>();

    private InventorySubject() {}

    public static InventorySubject getInstance() {
        if (instance == null) {
            instance = new InventorySubject();
        }
        return instance;
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }

    // hazır mesajlar (istersen çoğaltırız)
    public void bookUpdated(String bookName) {
        notifyObservers("📚 '" + bookName + "' kitabı güncellendi.");
    }

    public void bookDeleted(String bookName) {
        notifyObservers("🗑️ '" + bookName + "' kitabı silindi.");
    }

    public void bookBorrowed(String bookName) {
        notifyObservers("✅ '" + bookName + "' ödünç alındı.");
    }

    public void bookReturned(String bookName) {
        notifyObservers("↩️ '" + bookName + "' iade edildi.");
    }
}
