package snow;
//movement->food&hunt
import java.util.Random;
import java.util.Vector;

public class Arctic {
	private int lengthx; //measures of world
	private int lengthy;
	private int tick; //age of world
	private static int [][] world;
	private static Vector<Rabbit> rabbits; //all rabbits/foxes
	private static Vector<Fox> foxes;
	int numberr;
	int numberf;
	public Arctic(int [] [] startwelt, Vector<Rabbit> r, Vector<Fox>f) { //startwelt should contain only zeros!
		lengthx= startwelt.length;
		lengthy=startwelt[0].length;
		tick=0;
		world=startwelt;
		rabbits=r;
		foxes=f;
		numberr=0;
		numberf=0;
	}

	public static int [][] getWorld (){
		return world;
	}
	public static void setWorld (int [][]w){
		world=w;
	}
	public Vector<Rabbit> getRabbits(){
		return rabbits;
	}
	public static void setRabbits(Vector<Rabbit> r){
		rabbits=r;
	}
	public Vector<Fox> getFoxes(){
		return foxes;
	}
	public static void setFoxes(Vector<Fox> f){
		foxes=f;
	}
	public int getLengthx(){
		return lengthx;
	}
	public void setLengthx(int n){
		lengthx=n;
	}
	public int getLengthy(){
		return lengthy;
	}
	public void setLengthy(int n){
		lengthy=n;
	}
	public int getNumberr(){
		return numberr;
	}
	public void setNumberr(int n){
		numberr=n;
	}
	public int getNumberf(){
		return numberf;
	}
	public void setNumberf(int n){
		numberf=n;
	}public int getTick(){
		return tick;
	}
	public void setTick(int n){
		tick=n;
	}

	public void initialize (int numberr, int numberf){ 
		Random r=new Random();
		int posx,posy, counter;
		boolean taken =false;
		for(int i=0; i<numberr;i++){
			counter=0;
			do{
				counter++;//prevent failure if requested animals are more than space
				taken=false;
				posx=r.nextInt(lengthx);
				posy=r.nextInt(lengthy);
				if (world[posx][posy]==1){
						taken=true;}
				
			}while(taken==true&&counter<20);
			if(!taken&&counter<20){
			Rabbit rnew= new Rabbit (true,0,posx,posy);
			rabbits.addElement(rnew);
			world[posx][posy]=1;
			}
		}

		for(int i=0; i<numberf;i++){
			counter=0;
			do{
				counter++;;
				taken=false;
				posx=r.nextInt(lengthx);
				posy=r.nextInt(lengthy);
				if (world[posx][posy]!=0){
							taken=true;
						}
				}while(taken==true&&counter<20);
				if(!taken&&counter<20){
				Fox fnew= new Fox (true,0,posx,posy);
				foxes.addElement(fnew);
				world[posx][posy]=-2;
				}
			}

		}
	

	
	public void printout(){//visualization
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("The biotop after "+tick+" steps.");
		System.out.println(" ");
		for(int i=0; i<lengthx; i++){
			for(int j=0; j<lengthy;j++){
				if(world[i][j]==0)
					System.out.print(" ");
				else if(world[i][j]==1)
					System.out.print("R");
				else if(world[i][j]==-2)
					System.out.print("F");
				else
					System.out.print("X");
			}
			System.out.println(" ");
		}
	}
	public void nexttick(){//central functions, controls and initates all changements
		tick=tick+1;
		Random r=new Random();
		for(int i=0; i<rabbits.size();i++){//Age and time without food and reproduction is refreshed for rabbits - they may die
			rabbits.elementAt(i).setAge(rabbits.elementAt(i).getAge()+1);
			rabbits.elementAt(i).setTimeWithoutFood(rabbits.elementAt(i).getTimeWithoutFood()+1);
			rabbits.elementAt(i).setTimeWithoutReproduction(rabbits.elementAt(i).getTimeWithoutReproduction()+1);
			if(rabbits.elementAt(i).die()){//animal died, place in world is free and it is removed from the list
				world[rabbits.elementAt(i).posx][rabbits.elementAt(i).posy]=0;
				rabbits.removeElementAt(i);
				i=i-1;
			}
		}
		for(int i=0; i<foxes.size();i++){//Age and time without food and reproduction is refreshed for foxes - they may die
			foxes.elementAt(i).setAge(foxes.elementAt(i).getAge()+1);
			foxes.elementAt(i).setTimeWithoutFood(foxes.elementAt(i).getTimeWithoutFood()+1);
			foxes.elementAt(i).setTimeWithoutReproduction(foxes.elementAt(i).getTimeWithoutReproduction()+1);
			if(foxes.elementAt(i).die()){//animal died, place in world is free and it is removed from the list
				world[foxes.elementAt(i).posx][foxes.elementAt(i).posy]=0;
				foxes.removeElementAt(i);
				i=i-1;
			}
		}
		for(int i=0;i<rabbits.size();i++){//rabbits may make babies
			if(rabbits.elementAt(i).getReproductionTime()==rabbits.elementAt(i).getTimeWithoutReproduction()){
				rabbits.elementAt(i).setTimeWithoutReproduction(0);
				born(true, rabbits.elementAt(i),rabbits.elementAt(0).getFertility());
			}
		}
		for(int i=0;i<foxes.size();i++){//foxes make babies
			if(foxes.elementAt(i).getReproductionTime()==foxes.elementAt(i).getTimeWithoutReproduction()){
				foxes.elementAt(i).setTimeWithoutReproduction(0);
				born(false, foxes.elementAt(i),foxes.elementAt(0).getFertility());
			}
			movehunt(foxes.elementAt(i));//foxes go hunting and move
		}
		for(int i=0;i<rabbits.size();i++){//rabbits move. They find food with a chance of 20%
			int s=r.nextInt(5);
			if(s==1)
				rabbits.elementAt(i).setTimeWithoutFood(0);
			move(false,rabbits.elementAt(i),rabbits.elementAt(i).posx,rabbits.elementAt(i).posy);
		}
		numberr=rabbits.size();
		numberf=foxes.size();


	}
	public void born(boolean rabbit, Animal a, int f){//function for creating sweet animal babies
		boolean taken =false; //place has no space for another animal
		Random r= new Random();
		int posx=0;
		int posy=0;
		int bailout=0; //counts tries to find a new place
		boolean check=true; //if true, place in world isn't out of bounds
		for(int s=0;s<f;s++){
			do{
				taken=false;
				int dir=r.nextInt(4);//different directions (up, down, left, right, etc.)
				bailout++;
				if(dir==0){
					posx=a.posx+r.nextInt(4);
					posy=a.posy+r.nextInt(4);
					if(posx>=lengthx||posy>=lengthy){//out of bounds!
						taken=true;
						check=false;
						continue;
					}

				}
				if(dir==1){
					posx=a.posx-r.nextInt(4);
					posy=a.posy-r.nextInt(4);
					if(posx<0||posy<0){
						taken=true;
						check=false;
						continue;
					}
				}
				if(dir==2){
					posy=a.posy+r.nextInt(4);
					posx=a.posx-r.nextInt(4);
					if(posy>=lengthy||posx<0){
						taken=true;
						check=false;
						continue;
					}

				}
				if(dir==3){
					posy=a.posy-r.nextInt(4);
					posx=a.posx+r.nextInt(4);
					if(posx>=lengthx||posy<0){
						taken=true;
						check=false;
						continue;
					}
				}
				if(check){//not out of bounds, lets look if there is already someone
					if (world[posx][posy]!=0){
						taken=true;
						continue;
					}
				}

			}while(taken==true&&bailout<10);
			if(bailout>10)
				continue;
			if(rabbit&&check&&world[posx][posy]==0&&bailout<10){
				Rabbit rnew= new Rabbit (true,0,posx,posy);//create a new sweet animal and place it in its space
				rabbits.addElement(rnew);
				world[posx][posy]=1;
			}else if (!rabbit&&check&&world[posx][posy]==0&&bailout<10){
				Fox fnew= new Fox (true,0,posx,posy);//create a new sweet animal and place it in its space
				foxes.addElement(fnew);
				world[posx][posy]=-2;
				}
		}
	}
	public void movehunt(Fox f){//searches first for food around the fox, otherwise moves him
		world [f.posx][f.posy]=0;
		int posx=f.posx;
		int posy=f.posy;
		if(posx>1){
			if(world[posx-1][posy]==1){//is the area around in bounds and is there food?
				killRabbit(posx-1, posy);//then move there, kill the rabbit and reset time without food
				world[posx-1][posy]=-2;
				f.posx=f.posx-1;
				f.timeWithoutFood=0;
				return; //fox moved - no further orders needed
			}

		}
		else if (posx<lengthx-1){
			if(world[posx+1][posy]==1){
				killRabbit(posx+1, posy);
				world[posx+1][posy]=-2;
				f.posx=f.posx+1;
				f.timeWithoutFood=0;
				return;
			}
		}
		else if(posy>1){
			if(world[posx][posy-1]==1){
				killRabbit(posx, posy-1);
				world[posx][posy-1]=-2;
				f.posy=f.posy-1;
				f.timeWithoutFood=0;
				return;
			}

		}
		else if (posy<lengthy-1){
			if(world[posx][posy+1]==1){
				killRabbit(posx, posy+1);
				world[posx][posy+1]=-2;
				f.posy=f.posy+1;
				f.timeWithoutFood=0;
				return;
			}
		}
		if(posx>2){
			if(world[posx-2][posy]==1){
				killRabbit(posx, posy+1);
				world[posx-2][posy]=-2;
				f.posx=f.posx-2;
				f.timeWithoutFood=0;
				return; 
			}

		}
		else if (posx<lengthx-2){
			if(world[posx+2][posy]==1){
				killRabbit(posx+2, posy);
				world[posx+2][posy]=-2;
				f.posx=f.posx+2;
				f.timeWithoutFood=0;
				return;
			}
		}
		else if(posy>2){
			if(world[posx][posy-2]==1){
				killRabbit(posx, posy-2);
				world[posx][posy-2]=-2;
				f.posy=f.posy-2;
				f.timeWithoutFood=0;
				return;
			}

		}
		else if (posy<lengthy-2){
			if(world[posx][posy+2]==1){
				killRabbit(posx, posy+2);
				world[posx][posy+2]=-2;
				f.posy=f.posy+2;
				f.timeWithoutFood=0;
				return;
			}
		}
		move(true,f, posx, posy); // no rabbit around, move without hunting

	}
	public void move(boolean f,Animal a, int x, int y){
		boolean taken=false,check=true;
		int posx=x, posy=y;
		int bailout=0;
		world[x][y]=0;
		Random r=new Random();
		do{  //try to find a place to move
			taken=false;
			check=true;
			int dir=r.nextInt(4);
			bailout++;
			if(dir==0){ //random distance, limited by speed. has to be in bounds
				posx=a.posx+r.nextInt(a.speed);
				posy=a.posy+r.nextInt(a.speed);
				if(posx>=lengthx||posy>=lengthy){
					taken=true;
					check=false;
				}

			}
			if(dir==1){
				posx=a.posx-r.nextInt(a.speed);
				posy=a.posy-r.nextInt(a.speed);
				if(posx<0||posy<0){
					taken=true;
					check=false;
				}
			}
			if(dir==2){
				posy=a.posy+r.nextInt(a.speed);
				posx=a.posx-r.nextInt(a.speed);
				if(posy>=lengthy||posx<0){
					taken=true;
					check=false;
				}

			}
			if(dir==3){
				posy=a.posy-r.nextInt(a.speed);
				posx=a.posx+r.nextInt(a.speed);
				if(posx>=lengthx||posy<0){
					taken=true;
					check=false;
				}
			}
			if(check){
				if (world[posx][posy]!=0&&f==false){ //rabbits can't move to spaces with other animals
					taken=true;
				}else if(world[posx][posy]==1&&f==true){ //fox can move to rabbits and kill them
					killRabbit(posx, posy);
					world[posx][posy]=0;
					a.posy=posy;
					a.posx=posx;
					a.timeWithoutFood=0;
					return;
				}else if(world[posx][posy]==-2&&f==true){
					taken=true;
				}
			}

		}while((taken==true&&bailout<10)||check==false);
		if(world[posx][posy]==0){
		a.posx=posx; //position of the animal is a successful changed one or the one from the beginning
		a.posy=posy;
		
		if(f==true){
			world[posx][posy]=-2;
		}else{
			world[posx][posy]=1;
		}
		}else{
			a.posx=x;
			a.posy=y;
			if(f==true){
				world[x][y]=-2;
			}else{
				world[x][y]=1;
			}
			
		}

	}

	public void killRabbit(int x, int y){//finds a rabbit in the list by its place
		for(int i=0;i<rabbits.size();i++){
			if(rabbits.elementAt(i).posx==x&&rabbits.elementAt(i).posy==y){
				rabbits.removeElementAt(i);
				world[x][y]=0;
			}
		}
	}

}
