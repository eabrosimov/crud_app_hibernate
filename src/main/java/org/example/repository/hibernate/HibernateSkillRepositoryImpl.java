package org.example.repository.hibernate;

import org.example.model.Skill;
import org.example.repository.SkillRepository;
import org.example.utility.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateSkillRepositoryImpl implements SkillRepository {

    @Override
    public Skill save(Skill skill) {
        try (Session session = HibernateUtils.getSession()) {
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
        try (Session session = HibernateUtils.getSession()) {
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
        try (Session session = HibernateUtils.getSession()) {
            skills = session.createQuery(hql, Skill.class).getResultList();
        }

        return skills;
    }

    @Override
    public Skill getById(Integer integer) {
        Skill skill;
        try (Session session = HibernateUtils.getSession()) {
            skill = session.find(Skill.class, integer);
        }

        return skill;
    }

    @Override
    public boolean deleteById(Integer integer) {
        String hql = "delete from Skill where id = :id";
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
