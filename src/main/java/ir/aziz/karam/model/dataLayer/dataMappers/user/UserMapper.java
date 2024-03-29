package ir.aziz.karam.model.dataLayer.dataMappers.user;

import ir.aziz.karam.model.dataLayer.DBCPDBConnectionPool;
import ir.aziz.karam.model.dataLayer.dataMappers.Mapper;
import ir.aziz.karam.model.types.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserMapper extends Mapper<User, Integer> implements IUserMapper {

    private static final String COLUMNS = " id, firstName, lastName, jobTitle, pictureUrl, bio, username";
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
                + "id INTEGER PRIMARY KEY AUTO_INCREMENT, "
                + "firstName TEXT, "
                + "lastName TEXT, "
                + "jobTitle TEXT, "
                + "pictureUrl TEXT, "
                + "bio TEXT,"
                + "username VARCHAR(200) UNIQUE,"
                + "password TEXT"
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

    public List<User> getAllUserWithoutCurrent(int userId) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
                PreparedStatement st = con.prepareStatement(getAllUserWithoutCurrentStatement())) {
            st.setInt(1, userId);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
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
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7)
        );
    }

    @Override
    protected String getAllStatement() {
        return "SELECT " + COLUMNS
                + " FROM User";
    }

    @Override
    protected void setInsertElementParameters(PreparedStatement st, User element, int baseIndex) throws SQLException {
//        st.setInt(baseIndex, element.getId());
        st.setString(baseIndex, element.getFirstName());
        st.setString(1 + baseIndex, element.getLastName());
        st.setString(2 + baseIndex, element.getJobTitle());
        st.setString(3 + baseIndex, element.getPictureUrl());
        st.setString(4 + baseIndex, element.getBio());
        st.setString(5 + baseIndex, element.getUsername());
        st.setString(6 + baseIndex, element.getPassword());
    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO User (firstName, lastName, jobTitle, pictureUrl, bio, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected void setInsertOrUpdateElementParameters(PreparedStatement st, User element) throws SQLException {
        this.setInsertElementParameters(st, element, 1);
//        this.setInsertElementParameters(st, element, 7);
    }

    protected String getUsersSearchedByNameStatement() {
        return "SELECT " + COLUMNS
                + " FROM User"
                + " WHERE (firstName LIKE ? " + "OR\n"
                + " lastName LIKE ?) AND id <> ?";
    }

    protected String getUserByUsernameAndPasswordStatement() {
        return "SELECT " + COLUMNS
                + " FROM User"
                + " WHERE username = ? " + "AND\n"
                + " password = ?";
    }

    @Override
    protected String getInsertOrUpdateStatement() {

        return "INSERT IGNORE INTO User (firstName, lastName, jobTitle, pictureUrl, bio, username, password) VALUES (?, ?, ?, ?, ?, ?, ?) \n";

//        return "INSERT INTO User (id, firstName, lastName, jobTitle, pictureUrl, bio) VALUES (?, ?, ?, ?, ?, ?) \n"
//                + "ON CONFLICT DO UPDATE SET\n"
//                + "id=?, firstName=?, lastName=?, jobTitle=?, pictureUrl=?, bio=?"
//                ;
//        return "IF EXISTS (SELECT * FROM User WHERE id=?)"
//               + "    UPDATE User SET (id=?, firstName=?, lastName=?, jobTitle=?, pictureUrl=?, bio=?) WHERE id=?"
//               + "ELSE"
//               + "    INSERT INTO User (id, firstName, lastName, jobTitle, pictureUrl, bio) VALUES (?, ?, ?, ?, ?, ?)"
//        ;
    }

    public List<User> getUsersSearchedByName(String name, int currentUser) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
                PreparedStatement st = con.prepareStatement(getUsersSearchedByNameStatement())) {
            st.setString(1, "%" + name + "%");
            st.setString(2, "%" + name + "%");
            st.setInt(3, currentUser);
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

    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
                PreparedStatement st = con.prepareStatement(getUserByUsernameAndPasswordStatement())) {
            st.setString(1, username);
            st.setString(2, password);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.getUserByUsernameAndPassword query.");
//                ex.printStackTrace();
                throw ex;
            }
        }
    }
}
