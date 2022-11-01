package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Role;

public class RoleDB {

    public Role getRole(int roleID) throws Exception {
        Role role = null;

        ConnectionPool connectionPool = null;
        Connection connection = null;

        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            String preparedSQL = "SELECT role_id, role_name FROM role WHERE role_id=?";
            PreparedStatement ps = connection.prepareStatement(preparedSQL);
            ps.setInt(1, roleID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                role = new Role(rs.getInt(1), rs.getString(2));
            }
            return role;
        } finally {
            connectionPool.freeConnection(connection);
        }
    }

    public List<Role> getAll() throws SQLException {
        ArrayList<Role> roles = new ArrayList<>();
        ConnectionPool connectionPool = null;
        Connection connection = null;

        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();

            String preparedSQL = "SELECT role_id, role_name FROM role";
            PreparedStatement ps = connection.prepareStatement(preparedSQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int roleID = rs.getInt(1);
                String roleName = rs.getString(2);
                Role role = new Role(roleID, roleName);
                roles.add(role);
            }
            return roles;
        } finally {
            connectionPool.freeConnection(connection);
        }
    }
}
