package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import models.User;
import java.util.List;
import models.Role;

public class UserDB {

    
    public List<User> getAll() throws Exception {
        ConnectionPool cp = null;
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            cp = ConnectionPool.getInstance();
            connection = cp.getConnection();
            User user;
            ArrayList<User> users = new ArrayList<>();

            String preparedSQL = "SELECT email, first_name, last_name, role FROM user";
            ps = connection.prepareStatement(preparedSQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                String email = rs.getString(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);
                int roleID = rs.getInt(4);
                RoleDB roleDB = new RoleDB();
                Role role = roleDB.getRole(roleID);

                user = new User(email, firstName, lastName, role);
                users.add(user);
            }
            return users;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(connection);
        }
    }
    
    public User get(String email) throws Exception {

        ConnectionPool cp = null;
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try {
            cp = ConnectionPool.getInstance();
            connection = cp.getConnection();

            User user = new User();
            String preparedSQL = "SELECT first_name, last_name, password, role FROM user WHERE email = ?";
            ps = connection.prepareStatement(preparedSQL);
            ps.setString(1, email);
            rs = ps.executeQuery();

            while (rs.next()) {
                String firstName = rs.getString(1);
                String lastName = rs.getString(2);
                String password = rs.getString(3);
                int roleID = rs.getInt(4);
                RoleDB roleDB = new RoleDB();
                Role role = roleDB.getRole(roleID);

                user = new User(email, firstName, lastName, password, role);

            }
            return user;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(connection);
        }

    }
    
    public void insert(User user) throws Exception {

        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        PreparedStatement ps = null;
        
        int rows = 0;
        try {
            String preparedQuery
                    = "INSERT INTO user "
                    + "(email, first_name, last_name, password, role) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)";

            ps = connection.prepareStatement(preparedQuery);

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole().getRoleId());

            rows = ps.executeUpdate();
            ps.close();

        }  finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(connection);
        }
    }

    public void update(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        PreparedStatement ps = null;
        
        String UPDATE_STATEMENT = "UPDATE user set first_name=?, last_name=?, password=?, role=? where email=?";
        int successCount = 0;
        try {
            ps = connection.prepareStatement(UPDATE_STATEMENT);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRole().getRoleId());
            ps.setString(5, user.getEmail());

            successCount = ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(connection);
        }
    }

    public void delete(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection connection = cp.getConnection();
        PreparedStatement ps = null;
        try {
            String DELETE_STMT = "DELETE FROM user where email = ?";
            ps = connection.prepareStatement(DELETE_STMT);
            ps.setString(1, user.getEmail());

            int rowCount = ps.executeUpdate();
            ps.close();

        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(connection);
        }
    }

}
