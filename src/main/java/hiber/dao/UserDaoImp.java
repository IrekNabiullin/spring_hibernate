package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

import static java.util.Objects.nonNull;

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
        String hql = "select u from User u";
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.getResultList();
    }

    @Override
    public User getUserByCar(String name, int series) {
//    Long id = Long.valueOf(series);
//        return sessionFactory.getCurrentSession().get(Car.class, id).getUser();
        ///////
        Long id = getIdByCar(name, series);
        return sessionFactory.getCurrentSession().createQuery("from User where id = '" + id + "'", User.class).getSingleResult();
    }

    public Long getIdByCar(String name, int series) {
        String hql = "SELECT car_id FROM Car WHERE name = 'toyota'";

        Long result = (Long) sessionFactory.getCurrentSession().createQuery(hql).getSingleResult();
  //      return (Long) sessionFactory.getCurrentSession().createQuery("select id from Car where " + //series вместо id
  //              "name = '" + name + "'and id = '" + series + "'").getSingleResult();             //id вместо series
        System.out.println("Car toyota has car_id + " + result);
        return result;
    }

    @Override
    public void deleteAllUsersFromTable() {
        String hql = "select u from User u";
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql);
        List<User> users = query.getResultList();
        for (User user : users) {
            sessionFactory.getCurrentSession().delete(user);
        }
    }

    @Override
    public void deleteAllCarsFromTable() {
        String hql = "select c from Car c";
        TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery(hql);
        List<Car> cars = query.getResultList();
        for (Car car : cars) {
            sessionFactory.getCurrentSession().delete(car);
        }
    }
}
