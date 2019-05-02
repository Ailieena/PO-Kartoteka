**Projekt PO – wersja druga**


**GUI -**
Przydatne linki:

[Design Patterns in Game Programming](https://www.gamasutra.com/blogs/MichaelHaney/20110920/90250/Design_Patterns_in_Game_Programming.php?fbclid=IwAR3U8xj0kkm1s_zLLwW_Rep8W3T-1gujNPuHyOCLGDNCRXjMwpNjkLGkZk4)

[State](https://www.gameprogrammingpatterns.com/state.html?fbclid=IwAR326fHs-0wjTJDHKxuFs0mDCUmHGt5IQigDDAQ61Qe1BjwITOjsWO6Vj9o)

Jak ja to widzę:

   * Prośba o login -> przejście do menu
   * Po wejściu do menu są 2 opcje:
   
    1) Dołącz do gry
    2) Stwórz nową grę

   * Po kliknięciu 1) dostajesz listę otwartych pokoi ( wraz z ich numerami ) oraz tego jakie gry będą rozgrywane w danym pokoju i oczywiście można dany pokój wybrać i wejść do gry
   * Po kliknięciu 2) dostajemy listę możliwych typów gry -> wybieramy jedną -> wrzuca nas do tej gry
    
    
**Sama gra:**

   * Gdzieś w rogu podany numer gry który można podać np. jak chcesz dołączyć do gry do kolegi
   * Nie możemy przesuwać kartami które posiadamy, mają ustalone miejsce gdzieś na dole ekranu
   * Karta którą dostajemy pojawia się na środku ekranu - dostajemy wtedy dwie opcje - pass albo wzięcie karty (wydaje mi się że dwa guziki w zupełności wystarczą) 
   * Miło by było żeby był gdzieś licznik tego ile klient ma punktów
    



Potem możemy rozszerzyć projekt o:

    •	Prywatne pokoje
    •	Rejestrowanie użytkowników
    •	Ranking
    •	Przywracanie użytkownika do gry po przerwanym połączeniu
    •	Dodatkowe gry

Serwer wystawia metody do dołączania/tworzenia pokoi
Całość opiera się na RMI

Pokoje:

    •	Publiczne
    •	Rodzaj gry
    •	Ilość graczy


Aplikacja listuje pokoje publiczne
Dołączenie do pokoju przez podanie id (np. Od znajomego)

Gracze wybierają sobie nick ale nie jest on nigdzie rejestrowany/
Gracz który nie ma wybranego nicku dostaje losowy.

Po wybraniu/stworzeniu pokoju zaczyna się gra.

Tworzony jest nowy wątek który otwiera komunikację w nowym porcie. (?)

Oczekiwanie na graczy.

W pierwszej wersji proponuję żeby ilość graczy była stała.
Potem możemy zrobić np. tak że po osiągnięciu minimalnej ilości graczy gracze dostają informacje o tym czy chcą czekać na innych albo czekać przez minutę. (Mix tych dwóch?)

Klienci dołączający do gry dostają informację od serwera gdzie powinni przesyłać swoją dalszą komunikację (port).


Klienci komunikują się z wątkami na ustalonym wcześniej porcie. Każdy wątek implementuje jakąś grę. Komunikacja przy użyciu RMI.


**Oczko**

https://pl.wikipedia.org/wiki/Oczko_(gra_karciana)

Tak jak ustaliliśmy zaczęłabym od tego.  Gra jest dla 2-5 osób ale na początek ustalmy że będziemy pisać na 3 osoby. 


Rozpoczęcie gry 

    •	Gracz dostaje po 1 karcie.
Rozgrywka

    •	W turach z tokenem gracz decyduje czy chce kolejną kartę czy pasuje
    •	Jeżeli pasuje to do końca rozgrywki nie dostanie tokena.
    •	Jeżeli gracz dostał >= 21 punkty to automatycznie pasuje.
Koniec gry:

    •	Gra kończy się w momencie kiedy wszyscy gracze spasowali. 


Moja propozycja drugiej gry:

**Oszust**

https://pl.wikipedia.org/wiki/Oszust_(gra)

2-5 graczy. Jak będziemy zaczynać pisać drugą grę to chciałabym żeby już działała opcja „elastycznej” ilości graczy.
Rozpoczęcie gry: 

    •	Jedna karta jest dodana na środek
    •	Gracze dostają swoje karty
Rozgrywka:

    •	Gracz z tokenem wybiera kartę do dołożenia na stos lub opcję sprawdzenia ostatnio rzuconej karty.
    •	Zebranie stosu: gracz który rzucił złą kartę lub niepoprawnie sprawdził przeciwnika wybiera kartę do rzucenia 
Koniec gry:

    •	Jeżeli gracz pozbędzie się wszystkich kart - wygrywa. Gra się kończy


To jest prototyp który można by pokazać na pierwszej prezentacji:

Serwer tylko loguje informacje na konsole

Klient działa z konsoli.

    1.	Serwer na polecenie klienta otwiera wątek i przekierowuje klienta do danego portu wraz z informacja jakie pokój ma id.
    2.	Inny klient może dołączyć do danego pokoju po id (serwer powinien podać port z którym należy się komunikować)
    3.	Klient po podłączeniu się do pokoju powinien być w stanie (z konsoli) poprosić o podanie karty.
    4.	Jeżeli karty się skończą serwer zwraca informację, że nie ma więcej kart.
    5.	Gracz może poprosić o stan pokoju tzn. Informację ile kto ma jakich kart.
    6.	Wątek powinien się sam zakończyć po tym jak skończą się karty (wtedy nie da się już poprosić o stan pokoju)
    

Jak to się uda zrobić to można też pomyśleć nad podstawową wersją gry w oczko


