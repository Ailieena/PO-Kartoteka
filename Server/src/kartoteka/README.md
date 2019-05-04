## Card
`Card(int value, int color)` - podstawowy konstruktor  
`int compareTo(Card c)` - zwraca liczbę dodatnią/ujemną/0 jeśli karta ma wartość odpowiednio większą/mniejszą/równą niż wartość karty `c

## Player
`Player(Game game)`- podstawowy konstruktor  
`Game getGame()` - zwraca grę, w którą aktualnie gra gracz  
`LinkedList<Card> getHand()` - zwraca listę kart gracza (klasa `Card`)  
`int getHandSize()` - zwraca ilość kart w ręce gracza  
`void addCard(Card c)` - dodaje kartę `c` do ręki gracza  
`boolean removeCard(Card c)` - usuwa jedną kartę z ręki gracza, która ma takią samą wartośc i kolor jak karta `c`  
`Card removeCard(int index)` - usuwa karte o indeksie `index` z ręki gracza  
`Card randomCard()` - zwraca losową kartę z ręki gracza  

## Deck
`void addCard(Card c)` - dodaje kartę `c` do talii  
`boolean removeCard(Card c)` - usuwa jedną kartę z talii, która ma taką samą wartość i kolor jak karta `c`  
`boolean contains(Card c)` - - sprawdza czy w talii jest przynajmniej jedna karta `c`  
`void shuffle()` - tasuje talie  
`Card takeCard()` - usuwa i zwraca wierzchnią kartę z talii  
`boolean isEmpty()`  
