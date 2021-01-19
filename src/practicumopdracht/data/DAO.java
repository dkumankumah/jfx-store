package practicumopdracht.data;

import java.util.List;

public interface DAO<T> {
    public List<T> getAll();
    public void addOrUpdate(T object);
    public void remove(T object);
    public boolean save();
    public boolean load();
}
