package snow;

import java.util.Vector;

public class Example {

	public static void main (String[] args){
		int[][]startwelt= new int [80][80];
		Vector<Rabbit>rabbits=new Vector<Rabbit>();
		Vector<Fox>foxes=new Vector<Fox>();
		Arctic Game=new Arctic(startwelt,rabbits,foxes);
		Game.initate();
		Game.nexttick();
		Game.nexttick();
		Game.nexttick();
		Game.printout();
		Game.nexttick();
		Game.nexttick();
		Game.nexttick();
		Game.printout();
		Game.nexttick();
		Game.nexttick();
		Game.nexttick();
		Game.printout();
		Game.nexttick();
		Game.nexttick();
		Game.nexttick();
		Game.printout();
		Game.nexttick();
		Game.nexttick();
		Game.nexttick();
		Game.printout();
		Game.nexttick();
		Game.nexttick();
		Game.nexttick();
		Game.printout();
	}

}
