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
      long carId;
      Car car;

      TypedQuery<Car> queryCar = sessionFactory.getCurrentSession().createQuery("FROM " + Car.class.getSimpleName()
              + " WHERE model = :model AND series = :series");
      queryCar.setParameter("model", model);
      queryCar.setParameter("series", series);
      car = queryCar.getSingleResult();
      carId = car.getId();

      TypedQuery<User> queryUser = sessionFactory.getCurrentSession().createQuery("FROM " +  User.class.getSimpleName()
              + " WHERE id = :id");
      queryUser.setParameter("id", carId);

      return queryUser.getSingleResult();
   }
}
