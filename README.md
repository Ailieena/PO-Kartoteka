**Projekt PO – wersja pierwsza**

Potem możemy rozszerzyć projekt o:

    •	GUI
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


