package snow;

public class Animal {
	public boolean alive;
	public int age; //ticks alive
	public int agemax;  //maximum ticks in which animal can be active
	public int timeWithoutFood; //ticks without having eaten
	public int reproductionTime;  //ticks needed to bear children
	public int timeWithoutReproduction;  //ticks without having children
	public int fertility;  //children born per reproduction
	public int posx;  //position in world-array
	public int posy;
	public int speed;  //distance which can be travelled; helps to hunt(fox) or to find food (rabbit)
	private int [] genome; //boni obtained by evolution
	public boolean hasEaten; //if animal has consumed food in his life
	public Animal(boolean a, int ag,int agmax, int twf,int twr, int rt, int f, int px, int py, int s, int[]g, boolean he){
		alive=a;
		age=ag;
		agemax=agmax;
		timeWithoutFood=twf;
		timeWithoutReproduction=twr;
		reproductionTime=rt;
		fertility=f;
		posx=px;
		posy=py;
		speed=s;
		genome=g;
		hasEaten=he;
	}


	public boolean getAlive(){
		return alive;
	}
	public void setAlive(boolean a){
		alive=a;
	}
	public boolean getHasEaten(){
		return hasEaten;
	}
	public void setHasEaten(boolean a){
		hasEaten=a;
	}
	public int getReproductionTime(){
		return reproductionTime+genome[2]/4;
	}
	public void setReproductionTime(int n){
		reproductionTime=n+genome[2]/4;
	}
	public int getTimeWithoutReproduction(){
		return timeWithoutReproduction;
	}
	public void setTimeWithoutReproduction(int n){
		timeWithoutReproduction=n;
	}
	public int getFertility(){
		return fertility+genome[3]/3;
	}
	public void setFertility(int n){
		fertility=n+genome[3]/3;
	}
	public int getPosx(){
		return posx;
	}
	public void setPosx(int n){
		posx=n;
	}
	public int getAge(){
		return age;
	}
	public void setAge(int n){
		age=n;
	}
	public int getAgemax(){
		return agemax;
	}
	public void setAgemax(int n){
		agemax=n;
	}
	public int getSpeed(){
		return speed+genome[4]/3;
	}
	public void setSpeed(int n){
		speed=n+genome[4]/3;
	}
	public int getTimeWithoutFood(){
		return timeWithoutFood;
	}
	public void setTimeWithoutFood(int n){
		timeWithoutFood=n;
	}
	public int[] getGenome(){
		return genome;
	}
	public void setGenome(int []a){
		genome=a;
	}
	public boolean die (){
		if((age>agemax+genome[0]/3)||(timeWithoutFood>3-genome[1]/3)){ //sets alive on false if certain criteria are given
			alive=false;
			return true;
		}
		return false;
	}
}