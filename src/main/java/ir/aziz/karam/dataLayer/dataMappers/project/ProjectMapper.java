package ir.aziz.karam.dataLayer.dataMappers.project;

import ir.aziz.karam.dataLayer.DBCPDBConnectionPool;
import ir.aziz.karam.dataLayer.dataMappers.Mapper;
import ir.aziz.karam.model.types.Project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProjectMapper extends Mapper<Project, String> implements IProjectMapper {

    private static final String COLUMNS = " id, lastname, firstname, gpa ";
    private static ProjectMapper instance;

    public static ProjectMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProjectMapper();
        }
        return instance;
    }

    private ProjectMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st
                = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Project" + " ("
                + "id TEXT PRIMARY KEY, "
                + "title TEXT, "
                + "description TEXT, "
                + "imageUrl TEXT, "
                + "budget INTEGER, "
                + "deadline BIGINT, "
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

}
