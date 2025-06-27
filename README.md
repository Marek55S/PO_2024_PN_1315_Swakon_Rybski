# PO_2024_PN_1315_Swakon_Rybski

Repozytorium z projektem na Programowanie Obiektowe  

## Projekt: Darwin World

### Cel projektu
Celem projektu było stworzenie symulacji ewolucji w świecie zwierząt. Świat ten składa się z prostokątnej mapy, na której zwierzęta poruszają się, jedzą rośliny, rozmnażają się i ewoluują. Symulacja pozwala obserwować, jak różne gatunki zwierząt przystosowują się do środowiska w długim okresie czasu.

### Opis świata
Świat gry składa się z:
- **Mapy**: Prostokątna siatka podzielona na pola. Większość mapy to stepy, ale istnieją obszary dżungli, gdzie rośliny rosną szybciej.
- **Roślin**: Rosną w losowych miejscach, z większą koncentracją w dżungli.
- **Zwierząt**: Poruszają się, jedzą rośliny i rozmnażają się.

### Anatomia zwierzaka
Każde zwierzę posiada:
- **Pozycję (x, y)**: Określa lokalizację na mapie.
- **Energię**: Maleje z każdym dniem, wzrasta po zjedzeniu rośliny.
- **Kierunek**: Zwierzę może być zwrócone w jednym z ośmiu kierunków.
- **Geny**: Lista liczb (0-7) określająca zachowanie zwierzęcia.

### Mechanizmy symulacji
1. **Konsumpcja**: Zwierzę zjada roślinę, gdy znajdzie się na jej polu.
2. **Rozmnażanie**: Dwa zwierzęta z wystarczającą energią mogą stworzyć potomka, który dziedziczy geny od rodziców z możliwością mutacji.
3. **Mutacje**: Geny potomka mogą ulec zmianie w zależności od wybranego wariantu mutacji.

### Etapy symulacji
1. Usunięcie martwych zwierząt.
2. Ruch zwierząt zgodnie z ich genami.
3. Konsumpcja roślin.
4. Rozmnażanie zwierząt.
5. Wzrost nowych roślin.

### Parametry symulacji
- Wymiary mapy.
- Liczba początkowych roślin i zwierząt.
- Energia z roślin i wymagana do rozmnażania.
- Liczba mutacji i długość genomu.
- Warianty mapy, mutacji i zachowań zwierząt.

### Warianty konfiguracji
#### Mapa
- **Kula ziemska**: Lewa i prawa krawędź mapy zapętlają się, a górna i dolna są biegunami.
- **Obszary wodne**: Zwierzęta nie mogą wejść na pola wodne, które zmieniają rozmiar cyklicznie.

#### Mutacje
- **Pełna losowość**: Gen zmienia się na dowolny inny.
- **Lekkie korekty**: Gen zmienia się o 1 w górę lub w dół.

#### Zachowanie zwierząt
- **Pełna predestynacja**: Zwierzęta wykonują geny w kolejności.
- **Nieco szaleństwa**: W 20% przypadków wybierany jest losowy gen.

### Wdrożone funkcjonalności
- Obsługa mapy z wodą (wariant C).
- Mutacje z lekkimi korektami (wariant 1).

### Instrukcja uruchomienia
1. Skonfiguruj parametry symulacji w pliku konfiguracyjnym lub interfejsie użytkownika.
2. Uruchom aplikację za pomocą `SimulationApp`.
3. Obserwuj przebieg symulacji na mapie.

### Technologie
- **Java**: Główny język programowania.
- **JavaFX**: Interfejs graficzny.
- **JUnit**: Testy jednostkowe.

### Autorzy
- Marek Swakoń
- Szymon Rybski
