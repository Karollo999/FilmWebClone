# FilmWebClone - Baza danych filmów

## Spis treści
1. [Wprowadzenie](#wprowadzenie)
2. [Funkcjonalności](#funkcjonalności)
3. [Wymagania systemowe](#wymagania-systemowe)
4. [Instalacja](#instalacja)
5. [Uruchomienie aplikacji](#uruchomienie-aplikacji)
6. [Korzystanie z aplikacji](#korzystanie-z-aplikacji)
   - [Strona główna](#strona-główna)
   - [Wyszukiwanie](#wyszukiwanie)
   - [Szczegóły filmu](#szczegóły-filmu)
   - [Szczegóły aktora](#szczegóły-aktora)
   - [Szczegóły reżysera](#szczegóły-reżysera)
   - [Panel administratora](#panel-administratora)
   
## Wprowadzenie

FilmWebClone to aplikacja webowa stworzona przy użyciu Spring Boot, która symuluje funkcjonalności popularnego serwisu Filmweb. Aplikacja pozwala na przeglądanie, wyszukiwanie i zarządzanie bazą danych filmów, aktorów i reżyserów.

## Funkcjonalności

- Przeglądanie listy wszystkich filmów
- Wyszukiwanie filmów, aktorów i reżyserów
- Wyświetlanie szczegółowych informacji o filmach, aktorach i reżyserach
- Dodawanie nowych filmów (panel administratora)
- Edycja istniejących filmów (panel administratora)
- Usuwanie filmów (panel administratora)

## Wymagania systemowe

- Java Development Kit (JDK) 17 lub nowszy
- Maven 3.6 lub nowszy
- MySQL 8.0 lub nowszy

## Instalacja

1. Sklonuj repozytorium:
   ```
   git clone https://github.com/Karollo999/FilmWebClone.git
   ```
2. Przejdź do katalogu projektu:
   ```
   cd FilmWebClone
   ```
3. Skonfiguruj połączenie z bazą danych w pliku `src/main/resources/application.properties`:
   MySQL databaza jest wymagana.
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/filmweb_db
   spring.datasource.username=twoj_username
   spring.datasource.password=twoje_haslo
   ```
4. Zbuduj projekt używając Maven:
   ```
   mvn clean install
   ```

## Uruchomienie aplikacji
1. Uruchom aplikację za pomocą Maven:
   ```
   mvn spring-boot:run
   ```
2. Otwórz przeglądarkę i przejdź pod adres `http://localhost:8080`

## Korzystanie z aplikacji

### Strona główna

Strona główna wyświetla listę wszystkich filmów w bazie danych. Możesz kliknąć na tytuł filmu, aby zobaczyć jego szczegóły.

### Wyszukiwanie

1. Kliknij przycisk "Wyszukaj" na stronie głównej.
2. Wprowadź kryteria wyszukiwania (np. tytuł filmu, nazwa aktora lub reżysera).
3. Kliknij "Szukaj", aby wyświetlić wyniki.

### Szczegóły filmu

Na stronie szczegółów filmu znajdziesz informacje takie jak:
- Tytuł
- Reżyser (z linkiem do jego profilu)
- Lista aktorów (z linkami do ich profili)
- Gatunek
- Długość filmu

### Szczegóły aktora

Strona aktora zawiera:
- Imię i nazwisko
- Datę urodzenia
- Listę filmów, w których wystąpił (z linkami do stron filmów)

### Szczegóły reżysera

Strona reżysera zawiera:
- Imię i nazwisko
- Datę urodzenia
- Listę wyreżyserowanych filmów (z linkami do stron filmów)

### Panel administratora

Dostęp do panelu administratora: `http://localhost:8080/admin`

W panelu administratora możesz:
1. Dodawać nowe filmy
2. Edytować istniejące filmy wraz z Aktorami i reżyserami. 
3. Usuwać filmy

## Lista Endpointów

### Publiczne Endpointy

- `GET /` - Strona główna z listą filmów
- `GET /search` - Strona wyszukiwania
- `POST /search` - Wykonanie wyszukiwania
- `GET /movie/{id}` - Szczegóły filmu
- `GET /actor/{id}` - Szczegóły aktora
- `GET /director/{id}` - Szczegóły reżysera

### Endpointy Administratora

- `GET /admin` - Panel administratora
- `POST /admin/add-movie` - Dodanie nowego filmu
- `GET /admin/edit-movie/{id}` - Formularz edycji filmu
- `POST /admin/edit-movie/{id}` - Zapisanie zmian w filmie
- `GET /admin/delete-movie/{id}` - Usunięcie filmu
- `GET /admin/actors` - Lista aktorów
- `POST /admin/add-actor` - Dodanie nowego aktora
- `GET /admin/directors` - Lista reżyserów
- `POST /admin/add-director` - Dodanie nowego reżysera
- `GET /admin/movie-types` - Lista typów filmów
- `POST /admin/add-movie-type` - Dodanie nowego typu filmu

## Przykłady Zapytań cURL

### Wyszukiwanie Filmów

```bash
curl -X POST http://localhost:8080/search \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "query=Inception&minLength=120&maxLength=180&genre=Sci-Fi"
```

### Dodawanie Nowego Filmu (Panel Administratora)

```bash
curl -X POST http://localhost:8080/admin/add-movie \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "title=Inception&length=148&directorName=Christopher Nolan&actorNames=Leonardo DiCaprio,Ellen Page&movieTypeId=1"
```

### Edycja Filmu (Panel Administratora)

```bash
curl -X POST http://localhost:8080/admin/edit-movie/1 \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "title=Inception (Extended)&length=170&directorName=Christopher Nolan&actorNames=Leonardo DiCaprio,Ellen Page,Tom Hardy&movieTypeId=1"
```

### Usuwanie Filmu (Panel Administratora)

```bash
curl -X GET http://localhost:8080/admin/delete-movie/1
```

### Dodawanie Nowego Aktora (Panel Administratora)

```bash
curl -X POST http://localhost:8080/admin/add-actor \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "firstName=Tom&lastName=Hardy&birthDate=1977-09-15"
```

### Dodawanie Nowego Reżysera (Panel Administratora)

```bash
curl -X POST http://localhost:8080/admin/add-director \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "firstName=Quentin&lastName=Tarantino&birthDate=1963-03-27"
```

### Dodawanie Nowego Typu Filmu (Panel Administratora)

```bash
curl -X POST http://localhost:8080/admin/add-movie-type \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "typeName=Thriller"
```