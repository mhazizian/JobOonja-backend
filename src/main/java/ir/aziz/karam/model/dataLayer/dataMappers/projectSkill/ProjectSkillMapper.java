package ir.aziz.karam.model.dataLayer.dataMappers.projectSkill;

import ir.aziz.karam.model.dataLayer.DBCPDBConnectionPool;
import ir.aziz.karam.model.dataLayer.dataMappers.Mapper;
import ir.aziz.karam.model.types.Project;
import ir.aziz.karam.model.types.ProjectSkill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProjectSkillMapper extends Mapper<ProjectSkill, String> implements IProjectSkillMapper {

    private static final String COLUMNS = " project_id, skill_id, req_point ";

    private String getSkillProjectByProjectIdStatement() {
        return "SELECT " + COLUMNS
                + " FROM ProjectSkill"
                + " WHERE project_id = ?";
    }

    private static ProjectSkillMapper instance;

    public static ProjectSkillMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProjectSkillMapper();
        }
        return instance;
    }

    private ProjectSkillMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st
                = con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "ProjectSkill" + " ("
                + "project_id VARCHAR, "
                + "skill_id VARCHAR, "
                + "req_point INTEGER, "
                + "PRIMARY KEY (project_id, skill_id), "
                + "FOREIGN KEY (project_id) REFERENCES Project, "
                + "FOREIGN KEY (skill_id) REFERENCES Skill "
                + ")");
        st.close();
        con.close();

    }

    public ProjectSkill find(String project_id, String skill_id) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement())) {
            st.setString(1, project_id);
            st.setString(2, skill_id);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query. ProjectSkillMapper \n" + ex.getMessage());
                throw ex;
            }
        }
    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS
                + " FROM ProjectSkill"
                + " WHERE "
                + " project_id = ? AND"
                + " skill_id = ?";
    }

    @Override
    protected ProjectSkill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new ProjectSkill(rs.getString(1), rs.getString(2), rs.getInt(3));
    }

    @Override
    protected String getAllStatement() {
        return "SELECT " + COLUMNS
                + " FROM ProjectSkill";
    }

    @Override
    protected void setInsertElementParameters(PreparedStatement st, ProjectSkill element, int baseIndex) throws SQLException {
        st.setString(baseIndex, element.getProject_id());
        st.setString(1 + baseIndex, element.getSkill_id());
        st.setInt(2 + baseIndex, element.getReqPoints());

    }

    @Override
    protected String getInsertStatement() {
        return "INSERT INTO ProjectSkill (project_id, skill_id, req_point) VALUES (?, ?, ?)";
    }

    @Override
    protected void setInsertOrUpdateElementParameters(PreparedStatement st, ProjectSkill element) throws SQLException {
        this.setInsertElementParameters(st, element, 1);
//        this.setInsertElementParameters(st, element, 4);
    }

    @Override
    protected String getInsertOrUpdateStatement() {
        return "INSERT OR IGNORE INTO ProjectSkill (project_id, skill_id, req_point) VALUES (?, ?, ?)\n";


//        return "INSERT INTO ProjectSkill (project_id, skill_id, req_point) VALUES (?, ?, ?)\n"
//                + "ON DUPLICATE KEY UPDATE\n"
//                + "project_id=?, skill_id=?, req_point=?";
    }

    public List<ProjectSkill> getSkillsOfProject(Project project) throws SQLException {
        List<ProjectSkill> skills = new ArrayList<>();

        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(this.getSkillProjectByProjectIdStatement())) {
            st.setString(1, project.getId());
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                while (resultSet.next()) {
                    skills.add(this.convertResultSetToDomainModel(resultSet));
                }

                return skills;
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query. ProjectSkillMapper\n" + ex.getMessage());
                throw ex;
            }
        }
    }

}
