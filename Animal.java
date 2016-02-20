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
	public int speed;  //distance which can be travelled

	public Animal(boolean a, int ag,int agmax, int twf,int twr, int rt, int f, int px, int py, int s){
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
	}


	public boolean getAlive(){
		return alive;
	}
	public void setAlive(boolean a){
		alive=a;
	}
	public int getReproductionTime(){
		return reproductionTime;
	}
	public void setReproductionTime(int n){
		reproductionTime=n;
	}
	public int getTimeWithoutReproduction(){
		return timeWithoutReproduction;
	}
	public void setTimeWithoutReproduction(int n){
		timeWithoutReproduction=n;
	}
	public int getFertility(){
		return fertility;
	}
	public void setFertility(int n){
		fertility=n;
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
		return speed;
	}
	public void setSpeed(int n){
		speed=n;
	}
	public int getTimeWithoutFood(){
		return timeWithoutFood;
	}
	public void setTimeWithoutFood(int n){
		timeWithoutFood=n;
	}
	public boolean die (){
		if((age>agemax)||(timeWithoutFood>4)){ //sets alive on false if certain criteria are given
			alive=false;
			return true;
		}
		return false;
	}
}