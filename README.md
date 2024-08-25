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

Filmweb Database to aplikacja webowa stworzona przy użyciu Spring Boot, która symuluje funkcjonalności popularnego serwisu Filmweb. Aplikacja pozwala na przeglądanie, wyszukiwanie i zarządzanie bazą danych filmów, aktorów i reżyserów.

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
2. Edytować istniejące filmy
3. Usuwać filmy
