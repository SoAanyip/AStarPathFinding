package ss2;

import java.awt.Point;
import java.util.LinkedList;



public class Node2 implements Comparable{
	 public  Point _Pos;   //点的位置
	 public float sourcePoint;  //点已行进路程
	 public float destiPoint;   //点距终点直线距离
	 
	public Node2 _parentnode;   //父节点
	public boolean isVisited = false;  //是否已遍历
	public boolean isOpened = false;    //是否在openList里面了
	//public Point[] blocks = new Point[4];     
	public float cost = 5;     //默认代价
	
	 //initialize the Node
	 public Node2(Point _Pos)
	 {
		 this._Pos=_Pos;
	 }
	 
	 //get the cost of the Path
	 public float GetCost(Node2 node)
	 {
		  double m=node._Pos.x-_Pos.x;
		  double n=node._Pos.y-_Pos.y;
		  return (float)Math.sqrt(m*m+n*n)*4.4f;
	 }
	 
	 //check if the node is the destination point
	 public boolean equals(Object node)
	 {
		 if (_Pos.x == ((Node2) node)._Pos.x && _Pos.y == ((Node2) node)._Pos.y)
		  {
			 return true;
		  }
		 return false;
	 }
	 
	 //估价
	 public int compareTo(Object node)       
	 {
		  float a1=sourcePoint+destiPoint;
		  float a2=((Node2)node).sourcePoint+((Node2)node).destiPoint;
		  if(a1<a2){
			  return -1;
		  }else if(a1==a2)
		  	{return 0;}
		  else return 1;
	 
	 }
	 
	 
	 /*public LinkedList<Node2> getLimit()
	 {
		  LinkedList<Node2> limit=new LinkedList<Node2>();
		  int x=_Pos.x;
		  int y=_Pos.y;
		  limit.add(new Node(new Point(x,y-1)));   //up
		  limit.add(new Node(new Point(x,y+1)));   //down
		  limit.add(new Node(new Point(x-1,y)));   //left
		  limit.add(new Node(new Point(x+1,y)));   //right
		  
		  if( (y-1) !=-1)
			  limit.add(new Node2(new Point(x,y-1)));   //up
		  if( (y+1) !=Main2.MAP.length)
			  limit.add(new Node2(new Point(x,y+1)));   //down
		  if( (x-1) !=-1)
			  limit.add(new Node2(new Point(x-1,y)));   //left
		  if( (x+1) !=Main2.MAP.length)
			  limit.add(new Node2(new Point(x+1,y)));   //right
		  return limit;
	 }*/
 
}