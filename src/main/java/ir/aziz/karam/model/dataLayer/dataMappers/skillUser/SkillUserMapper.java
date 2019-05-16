package ir.aziz.karam.model.dataLayer.dataMappers.skillUser;

import ir.aziz.karam.model.dataLayer.DBCPDBConnectionPool;
import ir.aziz.karam.model.dataLayer.dataMappers.Mapper;
import ir.aziz.karam.model.types.SkillUser;
import ir.aziz.karam.model.types.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SkillUserMapper extends Mapper<SkillUser, String> implements ISkillUserMapper {

    private static final String COLUMNS = "skill_name, user_id, point";
    private static SkillUserMapper instance;

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO SkillUser (skill_name, user_id, point) VALUES (?, ?, ?)";
    }

    private String getDeleteSkillUserStatement() {
        return "DELETE "
                + " FROM SkillUser"
                + " WHERE user_id = ? AND skill_name = ?";
    }

    private String getSkillUserByUserIdStatement() {
        return "SELECT " + COLUMNS
                + " FROM SkillUser"
                + " WHERE user_id = ?";
    }


    public static SkillUserMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new SkillUserMapper();
        }
        return instance;
    }

    private SkillUserMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st
                = con.createStatement();

        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "SkillUser" + " ("
                + "skill_name VARCHAR(200) NOT NULL, "
                + "user_id VARCHAR(200) NOT NULL, "
                + "point INTEGER, "
                + "PRIMARY KEY (skill_name, user_id), "
                + "FOREIGN KEY (user_id) REFERENCES User(id), "
                + "FOREIGN KEY (skill_name) REFERENCES Skill(name)"
                + ")");
        st.close();
        con.close();

    }

    public SkillUser find(String userId, String skillId) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement())) {
            st.setString(1, skillId);
            st.setString(2, userId);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
//                System.out.println("error in Mapper.findByID query. skillUserMapper\n" + ex.getMessage());
//                ex.printStackTrace();
                throw ex;
            }
        }
    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS
                + " FROM SkillUser"
                + " WHERE skill_name = ? AND user_id = ?";
    }

    @Override
    protected SkillUser convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new SkillUser(
                rs.getString(1),
                rs.getInt(3)
        );
    }

    @Override
    protected String getAllStatement() {
        return "SELECT " + COLUMNS
                + " FROM SkillUser";
    }

    @Override
    protected void setInsertElementParameters(PreparedStatement st, SkillUser element, int baseIndex) throws SQLException {
        st.setString(baseIndex, element.getName());
        st.setString(1 + baseIndex, element.getUserId());
        st.setInt(2 + baseIndex, element.getPoints());

    }


    public List<SkillUser> getSkillOfUser(User user) throws SQLException {
        List<SkillUser> skills = new ArrayList<>();

        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(this.getSkillUserByUserIdStatement())) {
            st.setString(1, user.getId());
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next()) {
                    skills.add(this.convertResultSetToDomainModel(resultSet));
                }

                return skills;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query. SkillUser");
                throw ex;
            }
        }
    }

    public void deleteSkillUser(String skillName, String userId) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(this.getDeleteSkillUserStatement())) {
            st.setString(1, userId);
            st.setString(2, skillName);
            try {
                st.execute();
            } catch (SQLException ex) {
                System.out.println("error in Mapper.Delete query.");
                throw ex;
            }
        }
    }

    @Override
    protected void setInsertOrUpdateElementParameters(PreparedStatement st, SkillUser element) throws SQLException {
        this.setInsertElementParameters(st, element, 1
        );

//        st.setString(4, element.getName());
//        st.setString(5, element.getUserId());
//        this.setInsertElementParameters(st, element, 3);
//        st.setString(6, element.getName());
//        st.setString(7, element.getUserId());
//        this.setInsertElementParameters(st, element, 8);
    }

    @Override
    protected String getInsertOrUpdateStatement() {
        return "INSERT OR IGNORE INTO SkillUser (skill_name, user_id, point) VALUES (?, ?, ?)\n";
//        return "INSERT INTO SkillUser (skill_name, user_id, point) VALUES (?, ?, ?)\n"
//                + "ON DUPLICATE KEY UPDATE\n"
//                + "skill_name=?, user_id=?, point=?";

//        return "IF EXISTS (SELECT * FROM SkillUser WHERE skill_name=? AND user_id=?)"
//                + "    UPDATE SkillUser SET (skill_name=?, user_id=?, point=?) WHERE skill_name=? AND user_id=?"
//                + "ELSE"
//                + "    INSERT INTO SkillUser (skill_name, user_id, point) VALUES (?, ?, ?)";
    }

}
