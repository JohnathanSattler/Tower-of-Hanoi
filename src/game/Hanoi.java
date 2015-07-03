package game;

public class Hanoi implements Runnable {
	
	private Game game;
	private int rings;
	
	public Hanoi(int rings, Game game)
	{
		this.rings = rings;
		this.game = game;
	}
	
	public void run()
	{
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("test");
		}
		
		moveNRings(rings, 0, 1, 2);
	}
	
	private void moveNRings(int rings, int start, int buffer, int finish)
	{
		if(rings == 1)
		{
			moveOneRing(start, finish);
		} else {
			moveNRings(rings - 1, start, finish, buffer);
			moveOneRing(start, finish);
			moveNRings(rings - 1, buffer, start, finish);
		}
	}
	
	private void moveOneRing(int start, int finish)
	{
		Long before, after, sleep;
		
		before = System.currentTimeMillis();
		
		Game.setStatus("Disk Moved From Tower " + (start + 1) + " to Tower " + (finish + 1));
		game.getTowers().moveRing(start, finish);
		game.getTowers().addMoves();
		
		after = System.currentTimeMillis() - before;
		sleep = 250 - after;
		
		if(sleep < 1)
		{
			sleep = 1L;
		}
		
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
