package ir.aziz.karam.model.dataLayer.dataMappers.skillUser;

import ir.aziz.karam.model.dataLayer.DBCPDBConnectionPool;
import ir.aziz.karam.model.dataLayer.dataMappers.Mapper;
import ir.aziz.karam.model.dataLayer.dataMappers.project.IProjectMapper;
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

    private static final String COLUMNS = "skill_name, user_id";
    private static SkillUserMapper instance;

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO SkillUser (skill_name, user_id, point) VALUES (?, ?, ?)";
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
                + "skill_name TEXT, "
                + "user_id TEXT, "
                + "point INTEGER, "
                + "PRIMARY KEY (skill_name, user_id), "
                + "FOREIGN KEY skill_name REFERENCES Skill, "
                + "FOREIGN KEY user_id REFERENCES User"
                + ")"
        );
        st.close();
        con.close();

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
    protected void setInsertElementParamters(PreparedStatement st, SkillUser element) throws SQLException {
        st.setString(1, element.getName());
        st.setString(2, element.getUserId());
        st.setInt(3, element.getPoints());

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
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }

}
