package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("YourPersistenceUnitName");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        ApartmentCriteria apartmentCriteria = new ApartmentCriteria(entityManager);
        List<Object[]> result = apartmentCriteria.findApartmentsWithoutAccess();

        for (Object[] row : result) {
            System.out.println(
                    "Full Name: " + row[0] +
                            ", Email: " + row[1] +
                            ", Address: " + row[2] +
                            ", Apartment Number: " + row[3] +
                            ", Area: " + row[4]
            );
        }

        entityManager.close();
        entityManagerFactory.close();
    }
}