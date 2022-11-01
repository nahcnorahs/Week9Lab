package dataaccess;

import models.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Role;

public class UserDB {

    
    
    public List<User> getAll(String email) throws Exception {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Role roles = em.find(Role.class, email);
            return roles.getUserList();
            }
           finally {
            em.close();
        }
    }
    
    public User get(String email) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
          User user = em.find(User.class, email);
          //System.out.println("first name: " + user.getRole().getRoleName());
          //get all users as the same role as that user
          //List<User> users = user.getRole().getUserList();
            return user;
        } finally {
            em.close();
          
        }
        
    }
    
    public void insert(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Role role = user.getRole();
            role.getUserList().add(user);
            trans.begin();
            em.persist(user);
            em.merge(role);
            trans.commit();
        } catch (Exception ex){
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public void update(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception ex){
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public void delete(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Role role = user.getRole();
            role.getUserList().remove(user);
            trans.begin();
            em.remove(em.merge(user));
            em.merge(role);
            trans.commit();
        } catch (Exception ex){
            trans.rollback();
        } finally {
            em.close();
        }

}
   
}
