package ir.aziz.karam.model.dataLayer.dbConnectionPool;

public interface ResourcePool<T> {

    T get();


    void release(T t);


    void terminate();

}
