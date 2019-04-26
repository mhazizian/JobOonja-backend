package ir.aziz.karam.dataLayer.dataMappers.skillUser;

import ir.aziz.karam.dataLayer.DBCPDBConnectionPool;
import ir.aziz.karam.dataLayer.dataMappers.Mapper;
import ir.aziz.karam.dataLayer.dataMappers.project.IProjectMapper;
import ir.aziz.karam.model.types.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SkillUserMapper extends Mapper<Project, String> implements IProjectMapper {

    private static final String COLUMNS = " id, lastname, firstname, gpa ";
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
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Project" + " ("
                + "id TEXT PRIMARY KEY, "
                + "title TEXT, "
                + "description TEXT, "
                + "imageUrl TEXT, "
                + "budget INTEGER, "
                + "deadline BIGINT "
                + ")");
        st.close();
        con.close();

    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS
                + " FROM Project"
                + " WHERE id = ?";
    }

    @Override
    protected Project convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Project(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getLong(6));
    }

    @Override
    protected String getAllStatement() {
        return "SELECT " + COLUMNS
                + " FROM Project";
    }

    @Override
    protected void setInsertElementParamters(PreparedStatement st, Project element) throws SQLException {
        st.setString(1, element.getId());
        st.setString(2, element.getTitle());
        st.setString(3, element.getDescrption());
        st.setString(4, element.getImageURL());
        st.setInt(5, element.getBudget());
        st.setLong(6, element.getDeadline());
    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO Project (id, title, description, imageUrl, budget, deadline) VALUES (?, ?, ?, ?, ?, ?)";
    }

}
