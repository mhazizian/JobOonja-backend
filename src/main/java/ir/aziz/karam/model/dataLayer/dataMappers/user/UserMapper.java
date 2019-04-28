package ir.aziz.karam.model.dataLayer.dataMappers.user;

import ir.aziz.karam.model.dataLayer.DBCPDBConnectionPool;
import ir.aziz.karam.model.dataLayer.dataMappers.Mapper;
import ir.aziz.karam.model.types.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserMapper extends Mapper<User, String> implements IUserMapper {

    private static final String COLUMNS = " id, fistName, lastName, jobTitle, pictureUrl, bio";
    private static UserMapper instance;

    public static UserMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserMapper();
        }
        return instance;
    }

    private UserMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st
                = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "User" + " ("
                + "id VARCHAR(200) PRIMARY KEY, "
                + "firstName TEXT, "
                + "lastName TEXT, "
                + "jobTitle TEXT, "
                + "pictureUrl TEXT, "
                + "bio TEXT"
                + ")");
        st.close();
        con.close();

    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS
                + " FROM User"
                + " WHERE id = ?";
    }

    protected String getAllUserWithoutCurrentStatement() {
        return "SELECT " + COLUMNS
                + " FROM User"
                + " WHERE id <> ?";
    }

    public List<User> getAllUserWithoutCurrent(String userId) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
                PreparedStatement st = con.prepareStatement(getAllUserWithoutCurrentStatement())) {
            st.setString(1, userId);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return super.convertResultSetToDomainModelList(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.getAll query.");
                throw ex;
            }
        }
    }

    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new User(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6)
        );
    }

    @Override
    protected String getAllStatement() {
        return "SELECT " + COLUMNS
                + " FROM User";
    }

    @Override
    protected void setInsertElementParamters(PreparedStatement st, User element) throws SQLException {
        st.setString(1, element.getId());
        st.setString(2, element.getFirstName());
        st.setString(3, element.getLastName());
        st.setString(4, element.getJobTitle());
        st.setString(5, element.getPictureUrl());
        st.setString(6, element.getBio());
    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO User (id, firstName, lastName, jobTitle, pictureUrl, bio) VALUES (?, ?, ?, ?, ?, ?)";
    }

}
