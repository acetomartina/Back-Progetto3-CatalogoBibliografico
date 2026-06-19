package dao;

import entities.ElementoCatalogo;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class ElementoCatalogoDAO {

    private final EntityManager em;


    public ElementoCatalogoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(ElementoCatalogo elemento){
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(elemento);
        transaction.commit();

        System.out.println("Elemento salvato correttamente!");
    }

    public ElementoCatalogo findByISBN(String codiceISBN) {
        TypedQuery<ElementoCatalogo> query = em.createQuery(
                "SELECT e FROM ElementoCatalogo e WHERE e.codiceISBN = :codiceISBN",
                ElementoCatalogo.class
        );

        query.setParameter("codiceISBN", codiceISBN);

        return query.getSingleResult();
    }
}
