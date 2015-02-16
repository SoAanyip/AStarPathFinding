package ss2;


import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class AstarFrame2 extends JFrame{
	private JPanel panel;
	private final static int DEFAULT_WIDTH=600;
	   private final static int DEFAULT_HEIGHT=600;
	   
	   public AstarFrame2()
	   {
		   super();
	   }
	   
	   public void init()
	   {
		   panel=new Main2();
		   //System.out.println(new Date());
		   
		   this.setTitle("AStar FindingPath");
		   this.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT+20);
		   this.setVisible(true);
		   this.setResizable(true);
		   this.add(panel);
		   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	   }
	   
	   public static void main(String []args){
		   new AstarFrame2().init();

	   }
}








