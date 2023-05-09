package org.example.repository.hibernate;

import org.example.model.Developer;
import org.example.repository.DeveloperRepository;
import org.example.utility.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateDeveloperRepositoryImpl implements DeveloperRepository {

    @Override
    public Developer save(Developer developer) {
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            session.persist(developer);
            session.getTransaction().commit();
        }

        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            session.merge(developer);
            session.getTransaction().commit();
        }

        return developer;
    }

    @Override
    public List<Developer> getAll() {
        List<Developer> developers;
        String hql = "from Developer where status like 'ACTIVE'";
        try (Session session = HibernateUtils.getSession()) {
            developers = session.createQuery(hql, Developer.class).getResultList();
        }

        return developers;
    }

    @Override
    public Developer getById(Integer integer) {
        Developer developer;
        String hql = "from Developer where id = :id and status like 'ACTIVE'";
        try (Session session = HibernateUtils.getSession()) {
            Query<Developer> query = session.createQuery(hql, Developer.class);
            query.setParameter("id", integer);
            developer = query.getSingleResultOrNull();
        }

        return developer;
    }

    @Override
    public boolean deleteById(Integer integer) {
        String hql = "update Developer set status = 'DELETED', specialty = null where id = :id and status like 'ACTIVE'";
        try(Session session = HibernateUtils.getSession()){
            session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("id", integer);
            int status = query.executeUpdate();
            if(status > 0){
                String sql = "delete from skill_set where developer_id = ?";
                NativeQuery query1 = session.createNativeQuery(sql);
                query1.setParameter(1, integer);
                query1.executeUpdate();
                return true;
            }
            session.getTransaction().commit();
        }

        return false;
    }
}
