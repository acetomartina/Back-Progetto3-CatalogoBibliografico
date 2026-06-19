Il database è stato progettato utilizzando DrawSQL e segue una struttura relazionale basata sull'ereditarietà JPA tramite strategia JOINED.

Tabelle principali
- Utenti : 
la tabella  contiene le informazioni degli utenti registrati nella biblioteca.

Relazioni: 

un utente può avere più prestiti e può avere più elementi nella wishlist.

- Elementi Catalogo: rappresenta la superclasse astratta del catalogo bibliografico.

Relazioni: un elemento può essere presente in molti prestiti e può essere presente in molte wishlist.

- Libri: estende elementi_catalogo.
Eredita tutti gli attributi di elementi_catalogo.

Riviste: estende elementi_catalogo.
Eredita tutti gli attributi di elementi_catalogo.

Prestiti:gestisce i prestiti degli elementi del catalogo.

Relazioni: molti prestiti possono appartenere allo stesso utente e possono riferirsi allo stesso elemento catalogo.

Lista Desideri: rappresenta la wishlist personale degli utenti.

Relazioni: un utente può salvare più elementi e un elemento può essere presente nelle wishlist di più utenti.

Relazioni implementate
Utente ↔ Prestito
Utente (1) ------ (N) Prestito
Un utente può effettuare più prestiti.

ElementoCatalogo ↔ Prestito
ElementoCatalogo (1) ------ (N) Prestito
Un elemento può essere prestato più volte nel tempo.

Utente ↔ ListaDesideri
Utente (1) ------ (N) ListaDesideri
Ogni utente possiede una propria wishlist.

ElementoCatalogo ↔ ListaDesideri
ElementoCatalogo (1) ------ (N) ListaDesideri
Lo stesso elemento può essere salvato nelle wishlist di utenti diversi.


L'ereditarietà è stata implementata tramite strategia JPA:

@Inheritance(strategy = InheritanceType.JOINED)
in modo da mantenere separati gli attributi specifici di libri e riviste evitando colonne inutilizzate.


