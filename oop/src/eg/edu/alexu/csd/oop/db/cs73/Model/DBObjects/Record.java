package eg.edu.alexu.csd.oop.db.cs73.Model.DBObjects;

public class Record<T> { // Could be String or Integer

    T value;

    public Record(T value){
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
