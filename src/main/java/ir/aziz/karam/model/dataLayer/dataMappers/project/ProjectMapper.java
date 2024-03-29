package ir.aziz.karam.model.dataLayer.dataMappers.project;

import ir.aziz.karam.model.dataLayer.DBCPDBConnectionPool;
import ir.aziz.karam.model.dataLayer.dataMappers.Mapper;
import ir.aziz.karam.model.types.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ProjectMapper extends Mapper<Project, String> implements IProjectMapper {

    private static final String COLUMNS = " id, title, description, imageUrl, budget, deadline, creationDate, winnerUSer";
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
                + "id VARCHAR(200) PRIMARY KEY, "
                + "title TEXT, "
                + "description TEXT, "
                + "imageUrl TEXT, "
                + "budget INTEGER, "
                + "deadline BIGINT, "
                + "creationDate BIGINT, "
                + "winnerUser INTEGER, "
                + "FOREIGN KEY (winnerUser) REFERENCES User(id) "
                + ")");
        st.close();
        con.close();

    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS
                + " FROM Project"
                + " WHERE id = ? ";
    }

    @Override
    protected Project convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Project(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getInt(5),
                rs.getLong(6),
                rs.getLong(7),
                rs.getInt(8)
        );
    }

    @Override
    protected String getAllStatement() {
        return "SELECT " + COLUMNS
                + " FROM Project ORDER by  Project.creationDate DESC LIMIT 30";
    }

    protected String getProjectsSearchedByNameStatement() {
        return "SELECT " + COLUMNS
                + " FROM Project"
                + " WHERE title LIKE ? OR"
                + " description LIKE ? ORDER by  Project.creationDate DESC LIMIT 30";
    }

    protected String getProjectsToAuctStatement() {
        return "SELECT " + COLUMNS
                + " FROM Project"
                + " WHERE winnerUser IS NULL AND"
                + " deadline < ? ";
    }

    @Override
    protected void setInsertElementParameters(PreparedStatement st, Project element, int baseIndex) throws SQLException {
        st.setString(baseIndex, element.getId());
        st.setString(1 + baseIndex, element.getTitle());
        st.setString(2 + baseIndex, element.getDescrption());
        st.setString(3 + baseIndex, element.getImageURL());
        st.setInt(4 + baseIndex, element.getBudget());
        st.setLong(5 + baseIndex, element.getDeadline());
        st.setLong(6 + baseIndex, element.getCreationDate());
    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO Project (id, title, description, imageUrl, budget, deadline, creationDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    protected String getUpdateStatement() {
        return "UPDATE Project "
                + " SET title = ?, description = ?, imageUrl = ?, budget = ?, deadline = ?, creationDate = ?, winnerUser = ?"
                + "WHERE id = ?";
    }

    @Override
    protected void setInsertOrUpdateElementParameters(PreparedStatement st, Project element) throws SQLException {
        this.setInsertElementParameters(st, element, 1);
//        this.setInsertElementParameters(st, element, 7);
    }

    protected void setUpdateElementParameters(PreparedStatement st, Project element) throws SQLException {
        st.setString(1, element.getTitle());
        st.setString(2, element.getDescrption());
        st.setString(3, element.getImageURL());
        st.setInt(4, element.getBudget());
        st.setLong(5, element.getDeadline());
        st.setLong(6, element.getCreationDate());
        st.setInt(7, element.getWinnerId());

        st.setString(8, element.getId());
    }

    @Override
    protected String getInsertOrUpdateStatement() {
//        return "INSERT INTO Project (id, title, description, imageUrl, budget, deadline) VALUES (?, ?, ?, ?, ?, ?)\n"
//                + "ON DUPLICATE KEY UPDATE\n"
//                + "    id=?, title=?, description=?, imageUrl=?, budget=?, deadline=?";
        return "INSERT IGNORE INTO Project (id, title, description, imageUrl, budget, deadline, creationDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    public List<Project> getProjectsSearchedByName(String name) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
                PreparedStatement st = con.prepareStatement(getProjectsSearchedByNameStatement())) {
            st.setString(1, "%" + name + "%");
            st.setString(2, "%" + name + "%");
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


    public List<Project> getProjectsToAuct() throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getProjectsToAuctStatement())) {
            st.setLong(1, System.currentTimeMillis());

            System.out.println("Time: " + System.currentTimeMillis());

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


    public void update(Project project) throws SQLException {
        String sql = getUpdateStatement();
        Connection con = DBCPDBConnectionPool.getConnection();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            setUpdateElementParameters(st, project);
            st.executeUpdate();
        }
        con.close();
    }
}
