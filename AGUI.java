package snow;

import java.awt.* ;
import java.awt.event.*;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;



public class AGUI extends JFrame {//main class containing whole GUI
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton start, stop, pause, unpause, mutator;
	static TextField fox;
	static TextField rabbit;
	static JLabel tick;
	JLabel help;
	JLabel help2;
	JLabel info;
	static JLabel mut1;
	static JLabel mut2;
	static int height;
	JDialog meinJDialog;
	Timer timer;
	Arctic Game; //class containing calculations
	int[][]startwelt; //needed for starting sim
	Vector<Rabbit>rabbits;
	Vector<Fox>foxes;
	GridBagLayout grid = new GridBagLayout();
	Vector<Integer> rsave, fsave; //containing graphic data
	GridBagConstraints straints = new GridBagConstraints();//preparing Layout-Tools
	public AGUI(int w, int h) {
		height=h;
		startwelt= new int [215][200];//size of world
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
		meinJDialog.setLocation((d.width-w)/2,(d.height-h)/2-25);
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
		start = new JButton ("start");
		grid.setConstraints(start, straints);
		Controller.add(start);
		start.addActionListener(action);
		start.setActionCommand("start");
		straints.gridx=GridBagConstraints.RELATIVE;
		stop = new JButton ("stop");
		grid.setConstraints(stop, straints);
		Controller.add(stop);
		stop.addActionListener(action);
		stop.setActionCommand("stop");
		straints.gridx=GridBagConstraints.RELATIVE;
		pause = new JButton ("pause");
		grid.setConstraints(pause, straints);
		Controller.add(pause);
		pause.addActionListener(action);
		pause.setActionCommand("pause");
		straints.gridx=GridBagConstraints.RELATIVE;
		unpause = new JButton ("unpause");
		Controller.add(unpause);
		unpause.addActionListener(action);
		unpause.setActionCommand("unpause");
		/*straints.gridy=1; straints.gridx=0; straints.gridwidth=120;*/ straints.anchor=GridBagConstraints.LINE_START;
		straints.gridx=GridBagConstraints.RELATIVE;
		tick = new JLabel("Number of ticks: 0");
		grid.setConstraints(tick, straints);
		Controller.add(tick);

		info = new JLabel("  Paused");
		grid.setConstraints(info, straints);
		Controller.add(info);

		straints.gridy=2; straints.gridx=0;straints.gridwidth=160;
		help = new JLabel("Predator-Prey-Sim. Start new sim by entering the desired amount of animals in the textfield above and push 'Start'. The current number of animals is shown there, too. ");
		grid.setConstraints(help, straints);
		Controller.add(help);
		straints.gridy=3;
		help2= new JLabel("Pause/Unpause/stop with the buttons. On the bottom, you can see the overall development. Grey=Rabbit, Red=Fox. You may turn evolution on or off. By Niklas Götz");
		grid.setConstraints(help2, straints);
		Controller.add(help2);

		straints.gridwidth=100;
		straints.gridy=4;
		mutator=new JButton("Mutation off");
		grid.setConstraints(mutator, straints);
		Controller.add(mutator);
		mutator.addActionListener(action);
		mutator.setActionCommand("mutator");
		straints.gridy=5;
		mut1=new JLabel ("Effects of Mutations on Foxes");
		straints.gridx=GridBagConstraints.RELATIVE;
		grid.setConstraints(mut1, straints);
		Controller.add(mut1);
		straints.gridy=6;
		straints.gridwidth=100;
		straints.gridx=GridBagConstraints.WEST;
		mut2=new JLabel("Effects of Mutations on Rabbits");
		grid.setConstraints(mut2, straints);
		Controller.add(mut2);

		meinJDialog.add(Controller, BorderLayout.NORTH);
		Vis AVis = new Vis();//including 2D-map-space
		if(h>1000){
			AVis.setPreferredSize(new Dimension(w,650));}
		else{AVis.setPreferredSize(new Dimension(w,500));}
		meinJDialog.add(AVis, BorderLayout.CENTER);
		AGraph AG = new AGraph();//including space for graphics
		AG.setPreferredSize(new Dimension(w,170));
		meinJDialog.add(AG, BorderLayout.SOUTH);


		pack();

		meinJDialog.setVisible(true);
		timer=new Timer(1000, new ActionListener (){//main timer, coordinating the animiation, firing every second once
			public void actionPerformed(ActionEvent ae){

				Game.nexttick();
				AGUI.fox.setText("Foxes Alive:"+Game.getNumberf());
				AGUI.rabbit.setText("Rabbits Alive:"+Game.getNumberr());
				AGUI.tick.setText("Current tick: "+Game.getTick());
				int a=0;
				int b=0;
				int c=0;
				int d=0;
				int e=0;
				int a2=0;
				int b2=0;
				int c2=0;
				int d2=0;
				int e2=0;
				for(int i=0; i<Game.getNumberf(); i++){//counts mutation points
					a=a+foxes.elementAt(i).getGenome()[0];
					b=b+foxes.elementAt(i).getGenome()[1];
					c=c+foxes.elementAt(i).getGenome()[2];
					d=d+foxes.elementAt(i).getGenome()[3];
					e=e+foxes.elementAt(i).getGenome()[4];
				}
				for(int i=0; i<Game.getNumberr(); i++){
					a2=a2+rabbits.elementAt(i).getGenome()[0];
					b2=b2+rabbits.elementAt(i).getGenome()[1];
					c2=c2+rabbits.elementAt(i).getGenome()[2];
					d2=d2+rabbits.elementAt(i).getGenome()[3];
					e2=e2+rabbits.elementAt(i).getGenome()[4];
				}
				if(Game.getNumberf()!=0){//calculates average mutation bonus
					String k=String.format("Avg. Mutation Level(Foxes): Max. Age:%.2f Energy Need:%.2f Reprod. Time:%.2f Fertility:%.2f Speed:%.2f", 
							((double)(a/3)/(double)Game.getNumberf()),((double)(b/3)/(double)Game.getNumberf()),((double)(c/4)/(double)Game.getNumberf()),((double)(d/3)/(double)Game.getNumberf()),((double)(e/3)/(double)Game.getNumberf()));

					AGUI.mut1.setText(k);
				}
				if(Game.getNumberr()!=0){
					String k=String.format("Avg. Mutation Level(Rabbits): Max. Age:%.2f Energy Need:%.2f Reprod. Time:%.2f Fertility:%.2f Speed:%.2f", 
							((double)(a2/3)/(double)Game.getNumberr()),((double)(b2/3)/(double)Game.getNumberr()),((double)(c2/4)/(double)Game.getNumberr()),((double)(d2/3)/(double)Game.getNumberr()),((double)(e2/3)/(double)Game.getNumberr()));

					AGUI.mut2.setText(k);
				}

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
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setColor(Color.DARK_GRAY);
			for(int i=0; i<rsave.size()-1;i++){//drawing graph showing amount of rabbits
				g.drawLine(i, getHeight()-(int)(rsave.elementAt(i)/(double)280), (i+1), getHeight()-(int)(rsave.elementAt(i+1)/(double)(280)));}
			g.setColor(Color.RED);
			for(int i=0; i<fsave.size()-1;i++){//drawing graph showing amount of foxes
				g.drawLine(i, getHeight()-(int)(fsave.elementAt(i)/(double)280), (i+1), getHeight()-(int)(fsave.elementAt(i+1)/(double)(280)));}

		}
	}
	class Vis extends JPanel //shows a 2D map of all animals
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g)
		{	

			int width=4;
			int height=3;
			if(AGUI.height<1000){
				width=3;
				height=2;
			}



			int [][]data=Arctic.getWorld();//get data about the position of all animals alive
			for(int i=0; i<215; i++){
				for(int k=0; k<200; k++){
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

				startwelt= new int [215][200];
				rabbits=new Vector<Rabbit>();
				foxes=new Vector<Fox>();
				Arctic.setWorld(startwelt);
				Arctic.setRabbits(rabbits);
				Arctic.setFoxes(foxes);
				Game.setTick(0);
				rsave=new Vector<Integer>();
				fsave=new Vector<Integer>();
				try{
					Game.initialize(Integer.parseInt(AGUI.rabbit.getText()),Integer.parseInt(AGUI.fox.getText()));}
				catch (Exception ex){
					AGUI.fox.setText("You must enter numbers!");
					AGUI.rabbit.setText("You must enter numbers!");
				}
				info.setText("  Active");
				timer.start();

			}
			if(cmd.equals("stop")||cmd.equals("pause")){//both just pausing. Stop is there for "feel-good"

				info.setText("  Paused");
				timer.stop();

			}
			if(cmd.equals("unpause")){//continuing old game
				info.setText("  Active");
				timer.start();

			}
			if(cmd.equals("mutator")){//activates/deactivates evolution
				if(Game.getMutation()){
					mutator.setText("Mutation on");
				}else{
					mutator.setText("Mutation off");

				}
				Game.setMutation(!Game.getMutation());
			}

		}
	}
	public static void main (String[] args){//just open window.
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize ();
		AGUI a= new AGUI(1000,screensize.height-75);
	}

}

