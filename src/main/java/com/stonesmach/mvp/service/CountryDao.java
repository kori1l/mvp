package com.stonesmach.mvp.service;

import com.stonesmach.mvp.entity.City;
import com.stonesmach.mvp.entity.Country;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CountryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Country> getAll() {
        return entityManager.createQuery("from Country c order by c.id desc", Country.class).getResultList();
    }

    public Country getById(int id) {
        return entityManager.find(Country.class, id);
    }

    public Country create(Country country) {
        for (City city : country.getCities()) {
            city.setCountry(country);
        }
        entityManager.persist(country);
        return country;
    }
}
