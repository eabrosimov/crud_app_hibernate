package org.example.service;

import org.example.model.Specialty;
import org.example.repository.SpecialtyRepository;
import org.example.repository.hibernate.HibernateSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService() {
        specialtyRepository = new HibernateSpecialtyRepositoryImpl();
    }

    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public Specialty save(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public Specialty update(Specialty specialty) {
        return specialtyRepository.update(specialty);
    }

    public List<Specialty> getAll() {
        return specialtyRepository.getAll();
    }

    public Specialty getById(Integer integer) {
        return specialtyRepository.getById(integer);
    }

    public boolean deleteById(Integer integer) {
        return specialtyRepository.deleteById(integer);
    }
}
