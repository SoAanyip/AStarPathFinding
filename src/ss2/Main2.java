package ss2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;


public class Main2 extends JPanel implements Runnable,MouseListener,MouseMotionListener{              //JPanel类，负责监听和重绘
    private final static int DEFAULT_WIDTH=600;
    private final static int DEFAULT_HEIGHT=600;
  
	private List<Node2> path;              //移动路径 
	private List<Node2> savePath;          //保存起来使用的路径
	
	public static final int[] HIT={0};   //  决定0是障碍
	private static final int DEFAULT_ROW=DEFAULT_WIDTH/300;   //每格单位长度
	private static final int DEFAULT_COL=DEFAULT_WIDTH/300;
 
	private static Point START_POS=new Point(30,48);//起始点
	private static Point OBJECT_POS = new Point(30, 140);//终点
	private PathFinding2 astar;
	static int [][]MAP;       //地图节点数组
	private boolean judPress = false;   //是否按住鼠标
	private int mx,my,sx,sy;            //记录画障碍
   
	public int[][] getPoints() {
		
		//初始化地图
		int countX = 234;
		int countY = 96;
		int[][] points = new int[300][300];
		int size = points.length;
		for(int i = 0;i<points.length;i++){
			for(int j = 0;j<points.length;j++){
				if(j==countX && i==countY){
					if(countX>30 || countY<240){
						points[i][j] = 0;
						points[i][j-1] = 0;
						countX--;
						countY++;
					}
				}else if(j==60 && i<120){
					points[i][j] = 0;
				}else if(j==120 && i<180){
					points[i][j] = 0;
				}else if(j==100 && i<160 && i>20){
					points[i][j] = 0;
				}else if(i==170 && j<120 && j>20){
					points[i][j] = 0;
				}
				else if(i==160 && j<110){
					points[i][j] = 0;
				}
				else
					points[i][j] = 1;
			}
		}
		return points;
	}
	
	
	
	 public Main2()
	 {
		  MAP = getPoints();
		  setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		  setFocusable(true);
		  astar=new PathFinding2(MAP,HIT);
		  path=astar.searchPath(START_POS, OBJECT_POS);
		  savePath = new LinkedList<Node2>();
		  savePath.addAll(path);
		  this.addMouseListener(this);
		  this.addMouseMotionListener(this);
		  new Thread(this).start();
	 }
	 
	 protected void paintComponent(Graphics g){
		 super.paintComponent(g);
		 
		 
		 Color c = g.getColor();
			g.setColor(Color.BLACK);
			
			for(int i = 0;i<MAP.length;i++){
				for(int j = 0;j<MAP[i].length;j++){
					if(MAP[j][i]==0){
						g.setColor(Color.DARK_GRAY);
						g.fillRect(DEFAULT_ROW*i,DEFAULT_ROW*j , DEFAULT_ROW, DEFAULT_ROW);  //画出障碍
					}
				}
			}
			
			if (path != null){
				//画出路径
				g.setColor(Color.YELLOW);
				for (int i = 0; i < path.size(); i++){
					Node2 node = (Node2) path.get(i);
					Point pos = node._Pos;
				
		            g.fillRect(pos.x * DEFAULT_ROW, pos.y *DEFAULT_ROW, DEFAULT_ROW,DEFAULT_ROW);
		            }
		        }
			
			//起点和终点
			g.setColor(Color.BLUE);
			g.fillRect(DEFAULT_ROW*START_POS.x, DEFAULT_ROW*START_POS.y, DEFAULT_ROW, DEFAULT_ROW);
			g.setColor(Color.orange);
			g.fillRect(DEFAULT_ROW*OBJECT_POS.x, DEFAULT_ROW*OBJECT_POS.y, DEFAULT_ROW, DEFAULT_ROW);
			
			//鼠标按住划线
			if(judPress){
				g.setColor(Color.black);
				g.drawLine(sx, sy, mx, my);
			}
			
			
			g.setColor(c);
		 
    }
 
	@Override
	public void run() {
		//点移动的线程
		while(true){
			while(!savePath.isEmpty()){
				START_POS = savePath.get(0)._Pos;
				savePath.remove(0);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.repaint();
			}
			this.repaint();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//指定新终点
		if(e.getButton()==MouseEvent.BUTTON3){
			System.out.println("x:" + e.getX() + "y:" + e.getY());
			System.out.println("pointX:" + e.getX()/DEFAULT_ROW + "pointY:" + e.getY()/DEFAULT_ROW);
			OBJECT_POS = new Point(e.getX()/DEFAULT_ROW, e.getY()/DEFAULT_ROW);
			path=new PathFinding2(MAP, HIT).searchPath(START_POS, OBJECT_POS);
			savePath = new LinkedList<Node2>();
			if(path!=null)
				savePath.addAll(path);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1){
			judPress = true;
			sx = e.getX();
			sy = e.getY();
			mx = sx;
			my = sy;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//画出障碍
		if(e.getButton()==MouseEvent.BUTTON1){
			judPress = false;
			int lineLen = 0;
			double realWid = (mx-sx)/DEFAULT_ROW;
			double realHei = (my-sy)/DEFAULT_ROW;
			lineLen = (int)(Math.sqrt((mx-sx)*(mx-sx)+(my-sy)*(my-sy))/DEFAULT_ROW);
			if(lineLen==0){
				lineLen++;
			}
			//System.out.println(lineLen);
			//System.out.println(new Point(sx/DEFAULT_COL,sy/DEFAULT_COL));
			//System.out.println(new Point(mx/DEFAULT_ROW,my/DEFAULT_COL));
			//System.out.println(realWid + "  " + realHei);
			LinkedList<Point> line = new LinkedList<Point>();
			int tempX = 0,tempY = 0;
			for(int i = 0;i<lineLen;i++){
				try{
					//不让障碍之间能使点穿过
					if(tempX!=0 && tempY!=0){
						if((Math.abs(tempX-(sy/DEFAULT_ROW+(int)((realHei/lineLen)*i))) + Math.abs(tempY-(sx/DEFAULT_ROW+(int)((realWid/lineLen)*i))))==2){
							if(tempX-(sy/DEFAULT_ROW+(int)((realHei/lineLen)*i))==-1 && tempY-(sx/DEFAULT_ROW+(int)((realWid/lineLen)*i))==-1)
								MAP[sy/DEFAULT_ROW+(int)((realHei/lineLen)*i)-1][sx/DEFAULT_ROW+(int)((realWid/lineLen)*i)]=0;
							else if(tempX-(sy/DEFAULT_ROW+(int)((realHei/lineLen)*i))==1 && tempY-(sx/DEFAULT_ROW+(int)((realWid/lineLen)*i))==-1)
								MAP[sy/DEFAULT_ROW+(int)((realHei/lineLen)*i)][sx/DEFAULT_ROW+(int)((realWid/lineLen)*i)-1]=0;
							else if(tempX-(sy/DEFAULT_ROW+(int)((realHei/lineLen)*i))==1 && tempY-(sx/DEFAULT_ROW+(int)((realWid/lineLen)*i))==1)
								MAP[sy/DEFAULT_ROW+(int)((realHei/lineLen)*i)+1][sx/DEFAULT_ROW+(int)((realWid/lineLen)*i)]=0;
							else if(tempX-(sy/DEFAULT_ROW+(int)((realHei/lineLen)*i))==-1 && tempY-(sx/DEFAULT_ROW+(int)((realWid/lineLen)*i))==1)
								MAP[sy/DEFAULT_ROW+(int)((realHei/lineLen)*i)][sx/DEFAULT_ROW+(int)((realWid/lineLen)*i)+1]=0;
						}
					}
					tempX = sy/DEFAULT_ROW+(int)((realHei/lineLen)*i);
					tempY = sx/DEFAULT_ROW+(int)((realWid/lineLen)*i);
					MAP[sy/DEFAULT_ROW+(int)((realHei/lineLen)*i)][sx/DEFAULT_ROW+(int)((realWid/lineLen)*i)]=0;
				}catch(ArrayIndexOutOfBoundsException aioobe){
					;
				}
				
				//line.add(new Point((mx/sx* (1/(i+1)) ),))
				//System.out.println(line.get(i));
				//System.out.println((realWid/lineLen)*i + "    " + (realHei/lineLen)*i);
				//System.out.println(sx/DEFAULT_ROW +"    "+ (int)((realWid/lineLen)*i));
				//System.out.println(new Point(sx/DEFAULT_ROW+(int)((realWid/lineLen)*i),sy/DEFAULT_ROW+(int)((realHei/lineLen)*i)));
			}
			//把新终点重新检测最短路径
			MAP[START_POS.y][START_POS.x] = 1;
			path=new PathFinding2(MAP, HIT).searchPath(START_POS, OBJECT_POS);
			savePath = new LinkedList<Node2>();
			if(path!=null)
				savePath.addAll(path);
			/*for(int i = 0;i<MAP.length;i++){
				for(int j = 0;j<MAP[0].length;j++){
					
				}
			}*/
		}
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
			mx = e.getX();
			my = e.getY();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	

	

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
 
}