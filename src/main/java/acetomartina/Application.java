package acetomartina;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("CatalogoBibliografico");

        EntityManager em = emf.createEntityManager();

        System.out.println("Siamo connessi!");




    }
}
