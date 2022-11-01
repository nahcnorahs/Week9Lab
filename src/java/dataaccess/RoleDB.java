package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import models.Role;
import models.User;

public class RoleDB {

    public Role getRole(int roleID) throws Exception {
        

        try {
           
        } finally {
           
        }
        return null;
    }

    public List<Role> getAll() throws SQLException {
       

        try {
           
            }
        return null;
        
        } finally {
            
        }
    }
    
     public User get(String email) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
          User user = em.find(User.class, email);
            return user;
        } finally {
            em.close();
          
        }
}
}
