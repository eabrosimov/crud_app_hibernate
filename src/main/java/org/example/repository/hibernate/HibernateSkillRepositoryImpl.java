package org.example.repository.hibernate;

import org.example.model.Skill;
import org.example.repository.SkillRepository;
import org.example.utility.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class HibernateSkillRepositoryImpl implements SkillRepository {
    private final SessionFactory SESSION_FACTORY = HibernateUtils.getSessionFactory();

    @Override
    public Skill save(Skill skill) {
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            session.beginTransaction();
            session.persist(skill);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            return null;
        }

        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            session.beginTransaction();
            session.merge(skill);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            return null;
        }

        return skill;
    }

    @Override
    public List<Skill> getAll() {
        List<Skill> skills;
        String hql = "from Skill";
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            session.beginTransaction();
            skills = session.createQuery(hql, Skill.class).getResultList();
        }
        return skills;
    }

    @Override
    public Skill getById(Integer integer) {
        return getByIdInternal(integer);
    }

    @Override
    public boolean deleteById(Integer integer) {
        Skill skill = getByIdInternal(integer);
        if (skill != null) {
            try (Session session = SESSION_FACTORY.getCurrentSession()) {
                session.beginTransaction();
                session.remove(skill);
                session.getTransaction().commit();
            } catch (ConstraintViolationException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    private Skill getByIdInternal(Integer integer) {
        Skill skill;
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            session.beginTransaction();
            skill = session.find(Skill.class, integer);
            session.getTransaction().commit();
        }
        return skill;
    }
}
