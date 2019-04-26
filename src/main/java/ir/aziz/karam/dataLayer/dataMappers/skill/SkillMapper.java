package ir.aziz.karam.dataLayer.dataMappers.skill;

import ir.aziz.karam.dataLayer.DBCPDBConnectionPool;
import ir.aziz.karam.dataLayer.dataMappers.Mapper;
import ir.aziz.karam.model.types.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SkillMapper extends Mapper<Skill, String> implements ISkillMapper {

    private static final String COLUMNS = " name ";
    private static SkillMapper instance;

    public static SkillMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new SkillMapper();
        }
        return instance;
    }

    private SkillMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st
                = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Skill" + " ("
                + "name TEXT PRIMARY KEY"
                + ")");
        st.close();
        con.close();

    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS
                + " FROM SkillUser"
                + " WHERE name = ?";
    }

    @Override
    protected Skill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Skill(rs.getString(1));
    }

    @Override
    protected String getAllStatement() {
        return "SELECT " + COLUMNS
                + " FROM SkillUser";
    }

    @Override
    protected void setInsertElementParamters(PreparedStatement st, Skill element) throws SQLException {
        st.setString(1, element.getName());
    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO SkillUser (name ) VALUES (?)";
    }

}
