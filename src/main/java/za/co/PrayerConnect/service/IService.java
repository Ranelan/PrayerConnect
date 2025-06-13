package za.co.PrayerConnect.service;

public interface IService <T, ID> {

    T save(T entity);

    T findById(ID id);

    T update(T entity);

    void deleteById(ID id);
}
