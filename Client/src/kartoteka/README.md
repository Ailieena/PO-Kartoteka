## client.Card
`client.Card(int value, int color)` - podstawowy konstruktor
`int compareTo(client.Card c)` - zwraca liczbę dodatnią/ujemną/0 jeśli karta ma wartość odpowiednio większą/mniejszą/równą niż wartość karty `c

## Player
`Player(remoteinterface.remoteinterface.Game game)`- podstawowy konstruktor
`remoteinterface.remoteinterface.Game getGame()` - zwraca grę, w którą aktualnie gra gracz
`LinkedList<client.Card> getHand()` - zwraca listę kart gracza (klasa `client.Card`)
`int getHandSize()` - zwraca ilość kart w ręce gracza  
`void addCard(client.Card c)` - dodaje kartę `c` do ręki gracza
`boolean removeCard(client.Card c)` - usuwa jedną kartę z ręki gracza, która ma takią samą wartośc i kolor jak karta `c`
`client.Card removeCard(int index)` - usuwa karte o indeksie `index` z ręki gracza
`client.Card randomCard()` - zwraca losową kartę z ręki gracza

## Deck
`void addCard(client.Card c)` - dodaje kartę `c` do talii
`boolean removeCard(client.Card c)` - usuwa jedną kartę z talii, która ma taką samą wartość i kolor jak karta `c`
`boolean contains(client.Card c)` - - sprawdza czy w talii jest przynajmniej jedna karta `c`
`void shuffle()` - tasuje talie  
`client.Card takeCard()` - usuwa i zwraca wierzchnią kartę z talii
`boolean isEmpty()`  
