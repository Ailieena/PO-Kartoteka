import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TaliaOczko
{
	Map<Integer, Integer> cards;
	Map<Integer, Integer> pik;
	Map<Integer, Integer> trefl;
	Map<Integer, Integer> kier;
	Map<Integer, Integer> karo;
	int size;
	TaliaOczko()
	{
		size = 52;
		pik = new HashMap<Integer, Integer>(13);
		trefl = new HashMap<Integer, Integer>(13);
		kier = new HashMap<Integer, Integer>(13);
		karo = new HashMap<Integer, Integer>(13);
		
		int j = 2;
		for(int i = 0; i < 13; i++)
			pik.put(i, j++);
		j=2;
		for(int i = 13; i < 26; i++)
			kier.put(i, j++);
		j=2;
		for(int i = 26; i < 39; i++)
			karo.put(i, j++);
		j=2;
		for(int i = 39; i < 52; i++)
			trefl.put(i, j++);
		
		cards = new HashMap<Integer, Integer>(52);
		cards.putAll(pik);
		cards.putAll(trefl);
		cards.putAll(kier);
		cards.putAll(karo);
	}
	Map<Integer, Integer> getCards()
	{
		//debugging function
		return cards;
	}
	int getCard()
	{
		if(size == 0)
		{
			return -1;
		}
		Random k = new Random();
		int i = k.nextInt(size);
		int val = cards.get(i);
		cards.remove(i);
		size--;
		return val;
	}
	
	
}
