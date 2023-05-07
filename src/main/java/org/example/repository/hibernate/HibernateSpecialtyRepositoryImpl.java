package org.example.repository.hibernate;

import org.example.model.Specialty;
import org.example.repository.SpecialtyRepository;
import org.example.utility.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class HibernateSpecialtyRepositoryImpl implements SpecialtyRepository {
    private final SessionFactory SESSION_FACTORY = HibernateUtils.getSessionFactory();

    @Override
    public Specialty save(Specialty specialty) {
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            session.beginTransaction();
            session.persist(specialty);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            return null;
        }

        return specialty;
    }

    @Override
    public Specialty update(Specialty specialty) {
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            session.beginTransaction();
            session.merge(specialty);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            return null;
        }

        return specialty;
    }

    @Override
    public List<Specialty> getAll() {
        List<Specialty> specialties;
        String hql = "from Specialty";
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            session.beginTransaction();
            specialties = session.createQuery(hql, Specialty.class).getResultList();
        }
        return specialties;
    }

    @Override
    public Specialty getById(Integer integer) {
        return getByIdInternal(integer);
    }

    @Override
    public boolean deleteById(Integer integer) {
        Specialty specialty = getByIdInternal(integer);
        if (specialty != null) {
            try (Session session = SESSION_FACTORY.getCurrentSession()) {
                session.beginTransaction();
                session.remove(specialty);
                session.getTransaction().commit();
            } catch (ConstraintViolationException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    private Specialty getByIdInternal(Integer integer) {
        Specialty specialty;
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            session.beginTransaction();
            specialty = session.find(Specialty.class, integer);
            session.getTransaction().commit();
        }
        return specialty;
    }
}
