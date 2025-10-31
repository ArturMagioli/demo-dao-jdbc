package model.dao.impl;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

    Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                "INSERT INTO department "
                + "(Name) "
                + "VALUES "
                + "(?)", PreparedStatement.RETURN_GENERATED_KEYS);

            st.setString(1, department.getName());
    
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    department.setId(rs.getInt(1));
                }
                DB.closeResultSet(rs);
            }
        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Department department) {
        
        PreparedStatement st = null;

        try {

            st = conn.prepareStatement("UPDATE department SET Name = ? WHERE id = ?");

            st.setString(1, department.getName());
            st.setInt(2, department.getId());

            st.executeUpdate();
        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {

            st = conn.prepareStatement("DELETE FROM department WHERE id = ?");

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Department findById(Integer id) {
        
        Department department = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT * FROM department WHERE id = ?");

            st.setInt(1, id);

            rs = st.executeQuery();
            if (rs.next()) {
                department = new Department(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return department;
    }

    @Override
    public List<Department> findAll() {
        
        List<Department> departments = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();

            rs = st.executeQuery("SELECT * FROM department");
            while (rs.next()) {
                departments.add(new Department(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException exception) {
            throw new DbException(exception.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return departments;
    }
}
