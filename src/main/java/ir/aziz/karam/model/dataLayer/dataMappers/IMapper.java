package ir.aziz.karam.model.dataLayer.dataMappers;

import java.sql.SQLException;
import java.util.List;

public interface IMapper<T, I> {

    T find(I id) throws SQLException;

    List<T> getAll() throws SQLException;
    
    void insert(T element) throws SQLException;
}
