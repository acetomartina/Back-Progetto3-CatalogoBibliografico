package acetomartina;

import dao.ElementoCatalogoDAO;
import dao.ListaDesideriDAO;
import dao.PrestitoDAO;
import dao.UtenteDAO;
import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CatalogoBibliografico");
        EntityManager em = emf.createEntityManager();

        ElementoCatalogoDAO catalogoDAO = new ElementoCatalogoDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);
        UtenteDAO utenteDAO = new UtenteDAO(em);
        ListaDesideriDAO wishlistDAO = new ListaDesideriDAO(em);

        Scanner scanner = new Scanner(System.in);

        int scelta = -1;

        while (scelta != 0) {
            System.out.println();
            System.out.println("===== BIBLIOTECA MARVEL =====");
            System.out.println("1. Catalogo");
            System.out.println("2. Aggiungi elemento");
            System.out.println("3. Prestiti");
            System.out.println("4. Login area personale");
            System.out.println("0. Esci");
            System.out.print("Scegli un'opzione: ");

            scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1 -> menuCatalogo(scanner, catalogoDAO);
                case 2 -> aggiungiElemento(scanner, catalogoDAO);
                case 3 -> menuPrestiti(scanner, prestitoDAO);
                case 4 -> login(scanner, utenteDAO, prestitoDAO, catalogoDAO, wishlistDAO);
                case 0 -> System.out.println("Chiusura programma...");
                default -> System.out.println("Scelta non valida.");
            }
        }

        scanner.close();
        em.close();
        emf.close();
    }

    public static void menuCatalogo(Scanner scanner, ElementoCatalogoDAO catalogoDAO) {
        int scelta = -1;

        while (scelta != 0) {
            System.out.println();
            System.out.println("===== MENU CATALOGO =====");
            System.out.println("1. Visualizza tutto il catalogo");
            System.out.println("2. Cerca per ISBN");
            System.out.println("3. Cerca per titolo");
            System.out.println("4. Cerca per autore");
            System.out.println("5. Cerca per anno pubblicazione");
            System.out.println("6. Rimuovi elemento per ISBN");
            System.out.println("0. Torna indietro");
            System.out.print("Scegli un'opzione: ");

            scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1 -> catalogoDAO.findAll().forEach(System.out::println);
                case 2 -> {
                    System.out.print("Inserisci codice ISBN: ");
                    String isbn = scanner.nextLine().trim().toUpperCase();
                    System.out.println(catalogoDAO.findByISBN(isbn));
                }
                case 3 -> {
                    System.out.print("Inserisci titolo o parte del titolo: ");
                    String titolo = scanner.nextLine();
                    catalogoDAO.findByTitolo(titolo).forEach(System.out::println);
                }
                case 4 -> {
                    System.out.print("Inserisci autore: ");
                    String autore = scanner.nextLine();
                    catalogoDAO.findByAutore(autore).forEach(System.out::println);
                }
                case 5 -> {
                    System.out.print("Inserisci anno: ");
                    int anno = Integer.parseInt(scanner.nextLine());
                    catalogoDAO.findByAnnoPubblicazione(anno).forEach(System.out::println);
                }
                case 6 -> {
                    System.out.print("Inserisci ISBN da rimuovere: ");
                    String isbn = scanner.nextLine().trim().toUpperCase();
                    catalogoDAO.deleteByISBN(isbn);
                }
                case 0 -> System.out.println("Torno al menu principale...");
                default -> System.out.println("Scelta non valida.");
            }
        }
    }

    public static void aggiungiElemento(Scanner scanner, ElementoCatalogoDAO catalogoDAO) {
        System.out.println();
        System.out.println("Che tipo di elemento vuoi aggiungere?");
        System.out.println("1. Libro");
        System.out.println("2. Rivista");
        System.out.println("0. Annulla");
        System.out.print("Scegli: ");

        int scelta = Integer.parseInt(scanner.nextLine());

        if (scelta == 0) {
            System.out.println("Operazione annullata.");
            return;
        }

        if (scelta != 1 && scelta != 2) {
            System.out.println("Scelta non valida.");
            return;
        }

        if (scelta == 1) {
            System.out.println("Per i libri usa il formato ISBN + numeri. Esempio: ISBN006");
        } else {
            System.out.println("Per le riviste usa il formato RIV + numeri. Esempio: RIV006");
        }

        System.out.print("Codice: ");
        String codiceISBN = scanner.nextLine().trim().toUpperCase();

        if (scelta == 1 && !codiceISBN.matches("ISBN\\d+")) {
            System.out.println("Errore: formato non valido. Per i libri usa ISBN seguito da numeri.");
            return;
        }

        if (scelta == 2 && !codiceISBN.matches("RIV\\d+")) {
            System.out.println("Errore: formato non valido. Per le riviste usa RIV seguito da numeri.");
            return;
        }

        try {
            catalogoDAO.findByISBN(codiceISBN);
            System.out.println("Errore: esiste già un elemento con questo codice.");
            return;
        } catch (Exception e) {
            // Codice libero
        }

        System.out.print("Titolo: ");
        String titolo = scanner.nextLine();

        System.out.print("Anno pubblicazione: ");
        int anno = Integer.parseInt(scanner.nextLine());

        System.out.print("Numero pagine: ");
        int numeroPagine = Integer.parseInt(scanner.nextLine());

        switch (scelta) {
            case 1 -> {
                System.out.print("Autore: ");
                String autore = scanner.nextLine();

                System.out.print("Genere: ");
                String genere = scanner.nextLine();

                Libro libro = new Libro(codiceISBN, titolo, anno, numeroPagine, autore, genere);
                catalogoDAO.save(libro);
                System.out.println("Libro aggiunto al catalogo.");
            }
            case 2 -> {
                System.out.println("Periodicità:");
                System.out.println("1. SETTIMANALE");
                System.out.println("2. MENSILE");
                System.out.println("3. SEMESTRALE");
                System.out.print("Scegli: ");

                int sceltaPeriodicita = Integer.parseInt(scanner.nextLine());

                Periodicita periodicita;

                switch (sceltaPeriodicita) {
                    case 1 -> periodicita = Periodicita.SETTIMANALE;
                    case 2 -> periodicita = Periodicita.MENSILE;
                    case 3 -> periodicita = Periodicita.SEMESTRALE;
                    default -> {
                        System.out.println("Periodicità non valida.");
                        return;
                    }
                }

                Rivista rivista = new Rivista(codiceISBN, titolo, anno, numeroPagine, periodicita);
                catalogoDAO.save(rivista);
                System.out.println("Rivista aggiunta al catalogo.");
            }
        }
    }

    public static void menuPrestiti(Scanner scanner, PrestitoDAO prestitoDAO) {
        int scelta = -1;

        while (scelta != 0) {
            System.out.println();
            System.out.println("===== MENU PRESTITI =====");
            System.out.println("1. Cerca prestiti attivi per numero tessera");
            System.out.println("2. Visualizza prestiti scaduti non restituiti");
            System.out.println("3. Restituisci prestito");
            System.out.println("0. Torna indietro");
            System.out.print("Scegli un'opzione: ");

            scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1 -> {
                    System.out.print("Inserisci numero tessera: ");
                    String numeroTessera = scanner.nextLine().trim().toUpperCase();
                    prestitoDAO.findPrestitiAttiviByNumeroTessera(numeroTessera)
                            .forEach(System.out::println);
                }
                case 2 -> prestitoDAO.findPrestitiScaduti().forEach(System.out::println);
                case 3 -> {
                    System.out.print("Inserisci ID prestito da restituire: ");
                    Long idPrestito = Long.parseLong(scanner.nextLine());
                    prestitoDAO.restituisciPrestito(idPrestito);
                }
                case 0 -> System.out.println("Torno al menu principale...");
                default -> System.out.println("Scelta non valida.");
            }
        }
    }

    public static void login(Scanner scanner,
                             UtenteDAO utenteDAO,
                             PrestitoDAO prestitoDAO,
                             ElementoCatalogoDAO catalogoDAO,
                             ListaDesideriDAO wishlistDAO) {

        System.out.print("Inserisci numero tessera: ");
        String numeroTessera = scanner.nextLine().trim().toUpperCase();

        try {
            Utente utente = utenteDAO.findByNumeroTessera(numeroTessera);

            System.out.println("Benvenuto " + utente.getNome() + " " + utente.getCognome() + "!");

            if (utente.getNumeroTessera().equals("ADMIN001")) {
                menuAdmin(scanner, catalogoDAO, prestitoDAO);
            } else {
                menuAreaPersonale(scanner, utente, prestitoDAO, catalogoDAO, wishlistDAO);
            }

        } catch (Exception e) {
            System.out.println("Numero tessera non trovato.");
        }
    }

    public static void menuAdmin(Scanner scanner,
                                 ElementoCatalogoDAO catalogoDAO,
                                 PrestitoDAO prestitoDAO) {

        int scelta = -1;

        while (scelta != 0) {
            System.out.println();
            System.out.println("===== AREA ADMIN =====");
            System.out.println("1. Cerca per ISBN");
            System.out.println("2. Cerca per titolo");
            System.out.println("3. Cerca per autore");
            System.out.println("4. Cerca per anno pubblicazione");
            System.out.println("5. Rimuovi elemento per ISBN");
            System.out.println("6. Visualizza prestiti scaduti");
            System.out.println("0. Logout");
            System.out.print("Scegli un'opzione: ");

            scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1 -> {
                    System.out.print("Inserisci codice ISBN: ");
                    String isbn = scanner.nextLine().trim().toUpperCase();
                    System.out.println(catalogoDAO.findByISBN(isbn));
                }
                case 2 -> {
                    System.out.print("Inserisci titolo o parte del titolo: ");
                    String titolo = scanner.nextLine();
                    catalogoDAO.findByTitolo(titolo).forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("Inserisci autore: ");
                    String autore = scanner.nextLine();
                    catalogoDAO.findByAutore(autore).forEach(System.out::println);
                }
                case 4 -> {
                    System.out.print("Inserisci anno: ");
                    int anno = Integer.parseInt(scanner.nextLine());
                    catalogoDAO.findByAnnoPubblicazione(anno).forEach(System.out::println);
                }
                case 5 -> {
                    System.out.print("Inserisci ISBN da rimuovere: ");
                    String isbn = scanner.nextLine().trim().toUpperCase();
                    catalogoDAO.deleteByISBN(isbn);
                }
                case 6 -> prestitoDAO.findPrestitiScaduti().forEach(System.out::println);
                case 0 -> System.out.println("Logout admin...");
                default -> System.out.println("Scelta non valida.");
            }
        }
    }

    public static void menuAreaPersonale(Scanner scanner,
                                         Utente utente,
                                         PrestitoDAO prestitoDAO,
                                         ElementoCatalogoDAO catalogoDAO,
                                         ListaDesideriDAO wishlistDAO) {

        int scelta = -1;

        while (scelta != 0) {
            System.out.println();
            System.out.println("===== AREA PERSONALE " + utente.getNome().toUpperCase() + " =====");
            System.out.println("1. Ultimi prestiti");
            System.out.println("2. Prestiti in corso");
            System.out.println("3. Da restituire");
            System.out.println("4. Wishlist");
            System.out.println("0. Logout");
            System.out.print("Scegli un'opzione: ");

            scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1 -> prestitoDAO.findUltimiPrestitiByNumeroTessera(utente.getNumeroTessera())
                        .forEach(System.out::println);
                case 2 -> prestitoDAO.findPrestitiAttiviByNumeroTessera(utente.getNumeroTessera())
                        .forEach(System.out::println);
                case 3 -> prestitoDAO.findPrestitiScaduti()
                        .forEach(System.out::println);
                case 4 -> menuWishlist(scanner, utente, wishlistDAO, catalogoDAO);
                case 0 -> System.out.println("Logout...");
                default -> System.out.println("Scelta non valida.");
            }
        }
    }

    public static void menuWishlist(Scanner scanner,
                                    Utente utente,
                                    ListaDesideriDAO wishlistDAO,
                                    ElementoCatalogoDAO catalogoDAO) {

        int scelta = -1;

        while (scelta != 0) {
            System.out.println();
            System.out.println("===== WISHLIST =====");
            System.out.println("1. Visualizza wishlist");
            System.out.println("2. Aggiungi elemento");
            System.out.println("3. Rimuovi elemento");
            System.out.println("0. Indietro");
            System.out.print("Scegli un'opzione: ");

            scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1 -> wishlistDAO.findByUtente(utente).forEach(System.out::println);

                case 2 -> {
                    System.out.print("Inserisci ISBN dell'elemento: ");
                    String isbn = scanner.nextLine().trim().toUpperCase();

                    try {
                        ElementoCatalogo elemento = catalogoDAO.findByISBN(isbn);
                        wishlistDAO.aggiungi(utente, elemento);
                    } catch (Exception e) {
                        System.out.println("Elemento non trovato.");
                    }
                }

                case 3 -> {
                    System.out.print("Inserisci ID wishlist da rimuovere: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    wishlistDAO.rimuovi(id);
                }

                case 0 -> System.out.println("Ritorno all'area personale...");
                default -> System.out.println("Scelta non valida.");
            }
        }
    }
}