package org.example;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class ApartmentCriteria {
    private final EntityManager entityManager;

    public ApartmentCriteria(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Object[]> findApartmentsWithoutAccess() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Apartment> apartmentRoot = query.from(Apartment.class);
        Join<Apartment, Owner> ownerJoin = apartmentRoot.join("owner");
        Join<Apartment, Building> buildingJoin = apartmentRoot.join("building");

        query.multiselect(
                ownerJoin.get("fullName"),
                ownerJoin.get("email"),
                buildingJoin.get("address"),
                apartmentRoot.get("number"),
                apartmentRoot.get("area")
        );

        query.where(
                cb.and(
                        cb.equal(apartmentRoot.get("resident"), false),
                        cb.equal(apartmentRoot.get("owner"), ownerJoin),
                        cb.lt(cb.size(ownerJoin.get("apartments")), 2)
                )
        );

        return entityManager.createQuery(query).getResultList();
    }
}