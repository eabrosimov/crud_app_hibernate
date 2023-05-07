package org.example.repository.hibernate;

import org.example.model.Developer;
import org.example.model.Status;
import org.example.repository.DeveloperRepository;
import org.example.utility.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateDeveloperRepositoryImpl implements DeveloperRepository {
    private final SessionFactory SESSION_FACTORY = HibernateUtils.getSessionFactory();

    @Override
    public Developer save(Developer developer) {
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            session.beginTransaction();
            session.persist(developer);
            session.getTransaction().commit();
        }

        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
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
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            session.beginTransaction();
            developers = session.createQuery(hql, Developer.class).getResultList();
            session.getTransaction().commit();
        }
        return developers;
    }

    @Override
    public Developer getById(Integer integer) {
        return getByIdInternal(integer);
    }

    @Override
    public boolean deleteById(Integer integer) {
        Developer developer = getByIdInternal(integer);
        if (developer != null) {
            try (Session session = SESSION_FACTORY.getCurrentSession()) {
                session.beginTransaction();
                developer.setStatus(Status.DELETED);
                developer.setSpecialty(null);
                developer.setSkills(null);
                session.merge(developer);
                session.getTransaction().commit();
            }
            return true;
        }
        return false;
    }

    private Developer getByIdInternal(Integer integer) {
        Developer developer;
        String hql = "from Developer where id = :id and status like 'ACTIVE'";
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            session.beginTransaction();
            Query<Developer> query = session.createQuery(hql, Developer.class);
            query.setParameter("id", integer);
            developer = query.getSingleResultOrNull();
            session.getTransaction().commit();
        }
        return developer;
    }
}
