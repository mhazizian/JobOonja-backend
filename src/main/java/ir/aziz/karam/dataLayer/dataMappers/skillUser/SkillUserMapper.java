package ir.aziz.karam.dataLayer.dataMappers.skillUser;

import ir.aziz.karam.dataLayer.DBCPDBConnectionPool;
import ir.aziz.karam.dataLayer.dataMappers.Mapper;
import ir.aziz.karam.dataLayer.dataMappers.project.IProjectMapper;
import ir.aziz.karam.model.types.Project;
import ir.aziz.karam.model.types.SkillUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SkillUserMapper extends Mapper<SkillUser, String> implements IProjectMapper {

    private static final String COLUMNS = "skill_name, user_id";
    private static SkillUserMapper instance;

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

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO SkillUser (skill_name, user_id, point) VALUES (?, ?, ?)";
    }

}
