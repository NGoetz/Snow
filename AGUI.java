package snow;

import java.awt.* ;
import java.awt.event.*;

import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JPanel;

import javax.swing.Timer;



public class AGUI extends Frame {//main class containing whole GUI
	Button start, stop, pause, unpause;
	static TextField fox;
	static TextField rabbit;
	static Label tick;
	Label help;
	Label help2;
	Label info;
	static JDialog meinJDialog;
	Timer timer;
	Arctic Game; //class containing calculations
	int[][]startwelt; //needed for starting sim
	Vector<Rabbit>rabbits;
	Vector<Fox>foxes;
	GridBagLayout grid = new GridBagLayout();
	Vector<Integer> rsave, fsave; //containing graphic data
	GridBagConstraints straints = new GridBagConstraints();//preparing Layout-Tools
	public AGUI(int w, int h) {
		
		startwelt= new int [250][250];//size of world
		rabbits=new Vector<Rabbit>();
		foxes=new Vector<Fox>();
		rsave=new Vector<Integer>();
		fsave=new Vector<Integer>();
		Game=new Arctic(startwelt,rabbits,foxes);
		meinJDialog = new JDialog();
		meinJDialog.setLayout(new BorderLayout());
		meinJDialog.setTitle("Arctic");
		meinJDialog.setSize(w, h);
		JPanel Controller = new JPanel();
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize(); //set default-size
		meinJDialog.setLocation((d.width-w)/2,(d.height-h)/2);
		meinJDialog.addWindowListener(new MyWindowListener());
		ActionListener action = new Buttonpress(); //introducing EventListener

		Controller.setLayout(grid);//building interface by GridBagLayout
		straints.gridx=straints.gridy=0;
		straints.gridheight=1;
		straints.gridwidth=20; straints.anchor=GridBagConstraints.FIRST_LINE_START;straints.fill=GridBagConstraints.NONE;
		fox=new TextField("number of foxes",30);
		grid.setConstraints(fox, straints);
		Controller.add(fox);
		straints.gridx=GridBagConstraints.RELATIVE;
		rabbit=new TextField("number of rabbits", 30);
		grid.setConstraints(rabbit, straints);
		Controller.add(rabbit);
		straints.gridx=GridBagConstraints.RELATIVE;
		start = new Button ("start");
		grid.setConstraints(start, straints);
		Controller.add(start);
		start.addActionListener(action);
		start.setActionCommand("start");
		straints.gridx=GridBagConstraints.RELATIVE;
		stop = new Button ("stop");
		grid.setConstraints(stop, straints);
		Controller.add(stop);
		stop.addActionListener(action);
		stop.setActionCommand("stop");
		straints.gridx=GridBagConstraints.RELATIVE;
		pause = new Button ("pause");
		grid.setConstraints(pause, straints);
		Controller.add(pause);
		pause.addActionListener(action);
		pause.setActionCommand("pause");
		straints.gridx=GridBagConstraints.RELATIVE;
		unpause = new Button ("unpause");
		Controller.add(unpause);
		unpause.addActionListener(action);
		unpause.setActionCommand("unpause");
		straints.gridy=1; straints.gridx=0; straints.gridwidth=60; straints.anchor=GridBagConstraints.LINE_START;
		straints.gridx=GridBagConstraints.RELATIVE;
		tick = new Label("Number of ticks: 0");
		grid.setConstraints(tick, straints);
		Controller.add(tick);
		straints.gridx=GridBagConstraints.LINE_END;
		info = new Label("Paused");
		grid.setConstraints(info, straints);
		Controller.add(info);

		straints.gridy=2; straints.gridx=0;straints.gridwidth=120;
		help = new Label("Predator-Prey-Sim. Start new sim by entering the desired amount of animals in the textfield above and push 'Start'. The current number of animals is shown there, too. ");
		grid.setConstraints(help, straints);
		Controller.add(help);
		straints.gridy=3;
		help2= new Label("Pause/Unpause/stop with the buttons. On the bottom, you can see the overall development. Grey=Rabbit, Red=Fox. By Niklas Götz");
		grid.setConstraints(help2, straints);
		Controller.add(help2);
		meinJDialog.add(Controller, BorderLayout.NORTH);
		Vis AVis = new Vis();//including 2D-map-space
		AVis.setPreferredSize(new Dimension(w,750));
		meinJDialog.add(AVis, BorderLayout.CENTER);
		AGraph AG = new AGraph();//including space for graphics
		AG.setPreferredSize(new Dimension(w,155));
		meinJDialog.add(AG, BorderLayout.SOUTH);
	
		
		pack();
		
		meinJDialog.setVisible(true);
		timer=new Timer(1000, new ActionListener (){//main timer, coordinating the animiation, firing every second once
			public void actionPerformed(ActionEvent ae){
				
				Game.nexttick();
				 AGUI.fox.setText("Foxes Alive:"+Game.getNumberf());
				AGUI.rabbit.setText("Rabbits Alive:"+Game.getNumberr());
				AGUI.tick.setText("Current tick: "+Game.getTick());
				
				rsave.addElement(Game.getNumberr());//saving number of animals for figures
				fsave.addElement(Game.getNumberf());
				AVis.setVisible(false); //hiding AVis because of loop-building
				meinJDialog.update(meinJDialog.getGraphics());
				AVis.setVisible(true);
				
			
				
				
			}
			});
		


	}
	
	class AGraph extends JPanel //creates long-term-figures (in standard size >1000 ticks)
	{
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setColor(Color.DARK_GRAY);
			for(int i=0; i<rsave.size()-1;i++){//drawing graph showing amount of rabbits
			g.drawLine(i, getHeight()-(int)(rsave.elementAt(i)/(double)425), (i+1), getHeight()-(int)(rsave.elementAt(i+1)/(double)(425)));}
			g.setColor(Color.RED);
			for(int i=0; i<fsave.size()-1;i++){//drawing graph showing amount of foxes
			g.drawLine(i, getHeight()-(int)(fsave.elementAt(i)/(double)425), (i+1), getHeight()-(int)(fsave.elementAt(i+1)/(double)(425)));}
			
		}
	}
	 class Vis extends JPanel //shows a 2D map of all animals
	  {
	    public void paint(Graphics g)
	    {	
	    	int width=3;
	    	int height=3;
	      
	      
		
			int [][]data=Arctic.getWorld();//get data about the position of all animals alive
			for(int i=0; i<250; i++){
				for(int k=0; k<250; k++){
					if(data[i][k]==0)//draw it
						g.setColor(Color.WHITE);
					else if (data [i][k]==1)
						g.setColor(Color.DARK_GRAY);
					else if (data[i][k]==-2)
						g.setColor(Color.RED);
					g.fillRect(k*width, i*height, width, height);
					g.drawRect(k*width, i*height, width, height);
				}
			}
			
			
	     
	    }
	  } 
	class MyWindowListener extends WindowAdapter{
		public void windowClosing(WindowEvent event){//enable window-closing
			
			System.exit(0);
		}
	}
	class Buttonpress implements ActionListener{//next action decided by which button was pressed
		public void actionPerformed (ActionEvent e){
			String cmd = e.getActionCommand();
			if(cmd.equals("start")){//initialize new game, killing the old on
				
				startwelt= new int [250][250];
				rabbits=new Vector<Rabbit>();
				foxes=new Vector<Fox>();
				Arctic.setWorld(startwelt);
				Arctic.setRabbits(rabbits);
				Arctic.setFoxes(foxes);
				Arctic.setTick(0);
				rsave=new Vector<Integer>();
				fsave=new Vector<Integer>();
				Game.initialize(Integer.parseInt(AGUI.rabbit.getText()),Integer.parseInt(AGUI.fox.getText()));
				info.setText("Active");
				timer.start();

			}
			if(cmd.equals("stop")||cmd.equals("pause")){//both just pausing. Stop is there for "feel-good"
				
				info.setText("Paused");
				timer.stop();
				
			}
			if(cmd.equals("unpause")){//continuing old game
				info.setText("Active");
				timer.start();
				
			}
			

		}
	}
	public static void main (String[] args){//just open window.
		AGUI a= new AGUI(1000,1025);
}

}

