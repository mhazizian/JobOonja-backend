package ir.aziz.karam.model.dataLayer.dataMappers;

import ir.aziz.karam.model.dataLayer.DBCPDBConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Mapper<T, I> implements IMapper<T, I> {


    abstract protected String getFindStatement();

    abstract protected String getAllStatement();

    abstract protected T convertResultSetToDomainModel(ResultSet rs) throws SQLException;

    protected abstract void setInsertElementParameters(final PreparedStatement st, T element, int baseIndex) throws SQLException;
    protected abstract void setInsertOrUpdateElementParameters(final PreparedStatement st, T element) throws SQLException;

    protected abstract String getInsertStatement();
    protected abstract String getInsertOrUpdateStatement();

    protected List<T> convertResultSetToDomainModelList(ResultSet rs) throws SQLException {
        List<T> results = new ArrayList<>();
        while (rs.next()) {
            results.add(this.convertResultSetToDomainModel(rs));
        }
        return results;

    }

    @Override
    public T find(I id) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
                PreparedStatement st = con.prepareStatement(getFindStatement())) {
            st.setString(1, id.toString());
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query. \n" + ex.getMessage());
                ex.printStackTrace();
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
                return convertResultSetToDomainModelList(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.getAll query.");
                ex.printStackTrace();
                throw ex;
            }
        }
    }

    @Override
    public void insert(T element) throws SQLException {
        String sql = getInsertStatement();
        Connection con = DBCPDBConnectionPool.getConnection();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            setInsertElementParameters(st, element, 1);
            st.executeUpdate();
        }
        con.close();
    }

    @Override
    public void insertOrUpdate(T element) throws SQLException {
        String sql = getInsertOrUpdateStatement();
        Connection con = DBCPDBConnectionPool.getConnection();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            setInsertOrUpdateElementParameters(st, element);
            st.executeUpdate();
        }
        con.close();
    }

}
