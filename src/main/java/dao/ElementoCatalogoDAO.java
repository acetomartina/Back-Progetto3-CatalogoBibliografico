package dao;

import entities.ElementoCatalogo;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

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

    public ElementoCatalogo findById(Long id) {
        return em.find(ElementoCatalogo.class,id);
    }
}
