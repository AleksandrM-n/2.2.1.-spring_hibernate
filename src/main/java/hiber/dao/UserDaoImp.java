package hiber.dao;

import hiber.model.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("FROM " + User.class.getSimpleName());
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public User getCarUser(String model, int series) {
      TypedQuery<User> queryUser = sessionFactory.getCurrentSession().createQuery("FROM " + User.class.getSimpleName()
              + " us join fetch us.car cr WHERE "
              + "cr.model = :model AND cr.series = :series");
      queryUser.setParameter("model", model);
      queryUser.setParameter("series", series);

      return queryUser.getSingleResult();
   }
}
