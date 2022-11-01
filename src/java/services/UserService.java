package services;

import dataaccess.RoleDB;
import models.User;
import java.util.List;
import dataaccess.UserDB;
import models.Role;


public class UserService {
    

    public User get(String email) throws Exception {
        UserDB db = new UserDB();
        User user = db.get(email);
        return user;
    }

    public List<User> getAll() throws Exception {
        UserDB db = new UserDB();
        List<User> users = db.getAll();
        
        return users;
    }

    public void update(String email, String first_name, String last_name, String password, int roleId) throws Exception {
        UserDB db = new UserDB();
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.getRole(roleId);
        User user = new User(email, first_name, last_name, password, role);
        db.update(user);
    }

    public void delete(String email) throws Exception {
        UserDB db = new UserDB();
        User user = get(email);
        db.delete(user);;
    }

    public void insert(String email, String first_name, String last_name, String password, int roleId) throws Exception {
        UserDB db = new UserDB();
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.getRole(roleId);
        User user = new User(email, first_name, last_name, password, role);
        db.insert(user);
    }

}
