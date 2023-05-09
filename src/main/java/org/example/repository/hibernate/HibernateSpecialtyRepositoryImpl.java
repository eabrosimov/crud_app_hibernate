package org.example.repository.hibernate;

import org.example.model.Specialty;
import org.example.repository.SpecialtyRepository;
import org.example.utility.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateSpecialtyRepositoryImpl implements SpecialtyRepository {

    @Override
    public Specialty save(Specialty specialty) {
        try (Session session = HibernateUtils.getSession()) {
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
        try (Session session = HibernateUtils.getSession()) {
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
        try (Session session = HibernateUtils.getSession()) {
            specialties = session.createQuery(hql, Specialty.class).getResultList();
        }

        return specialties;
    }

    @Override
    public Specialty getById(Integer integer) {
        Specialty specialty;
        try (Session session = HibernateUtils.getSession()) {
            specialty = session.find(Specialty.class, integer);
        }

        return specialty;
    }

    @Override
    public boolean deleteById(Integer integer) {
        String hql = "delete from Specialty where id = :id";
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("id", integer);
            int status = query.executeUpdate();
            if(status > 0){
                return true;
            }
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            return false;
        }

        return false;
    }
}
