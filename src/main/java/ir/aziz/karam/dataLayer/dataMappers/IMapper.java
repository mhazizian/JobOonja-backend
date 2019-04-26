package ir.aziz.karam.dataLayer.dataMappers;

import java.sql.SQLException;
import java.util.List;

public interface IMapper<T, I> {

    T find(I id) throws SQLException;

    List<T> getAll() throws SQLException;
}
