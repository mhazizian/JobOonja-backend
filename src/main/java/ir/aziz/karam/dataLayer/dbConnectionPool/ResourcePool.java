package ir.aziz.karam.dataLayer.dbConnectionPool;

public interface ResourcePool<T> {

    T get();


    void release(T t);


    void terminate();

}
