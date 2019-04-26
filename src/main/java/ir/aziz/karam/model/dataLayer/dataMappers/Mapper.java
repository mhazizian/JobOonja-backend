package ir.aziz.karam.model.dataLayer.dataMappers;

import ir.aziz.karam.model.dataLayer.DBCPDBConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Mapper<T, I> implements IMapper<T, I> {

    protected Map<I, T> loadedMap = new HashMap<>();

    abstract protected String getFindStatement();

    abstract protected String getAllStatement();

    abstract protected T convertResultSetToDomainModel(ResultSet rs) throws SQLException;

    protected abstract void setInsertElementParamters(final PreparedStatement st, T element) throws SQLException;

    protected abstract String getInsertStatement();

    protected List<T> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        rs.next();
        List<T> results = new ArrayList<>();
        while (rs.next()) {
            results.add(this.convertResultSetToDomainModel(rs));
        }
        return results;

    }

    @Override
    public T find(I id) throws SQLException {
        T result = loadedMap.get(id);
        if (result != null) {
            return result;
        }

        try (Connection con = DBCPDBConnectionPool.getConnection();
                PreparedStatement st = con.prepareStatement(getFindStatement())) {
            st.setString(1, id.toString());
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }

    @Override
    public List<T> getAll() throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
                PreparedStatement st = con.prepareStatement(getAllStatement())) {

            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToDomainModelList(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.getAll query.");
                throw ex;
            }
        }
    }

    @Override
    public void insert(T element) throws SQLException {
        String sql = getInsertStatement();
        Connection con = DBCPDBConnectionPool.getConnection();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            setInsertElementParamters(st, element);
            st.executeUpdate();
        }
    }

}
