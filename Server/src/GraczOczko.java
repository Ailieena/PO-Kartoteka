
public class GraczOczko {

	int points;
	boolean isPlaying;
	OczkoClientInterface client;
	GraczOczko(OczkoClientInterface c)
	{
		client = c;
		points = 0;
		isPlaying = true;
	}
	int getPoints()
	{
		return points;
	}
	boolean isPlaying()
	{
		return isPlaying;
	}
	void addPoints(int p)
	{
		points+=p;
	}
}
