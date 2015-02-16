package ss2;

import java.awt.Point;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;




public class PathFinding2 {
	
	private OpenedList openedList;   //开启列表
	//private LinkedList closedList;
	private int [][]_map;
	private int []_limit;
	Node2[][] nodes;
	
	public PathFinding2(int [][]map,int []limit)
	{
		_map=map;
		_limit=limit;
		openedList=new OpenedList();
		//closedList=new LinkedList();
		
		nodes = new Node2[_map.length][_map[0].length];        //用地图数组构造节点数组
		for(int i = 0;i<nodes.length;i++){
			for(int j = 0;j<nodes[i].length;j++){
				nodes[i][j] = new Node2(new Point(i,j));
			}
		}
 
	}
	
	public List<Node2> searchPath(Point startPos,Point destiPos)
	{
		//Node startNode=new Node(startPos);
				//Node destiNode=new Node(destiPos);
		int jud = 1;
			Node2 startNode = nodes[startPos.x][startPos.y];
			while(isHit(destiPos.x, destiPos.y)){                        //终点是障碍来着？
				if(!isHit(destiPos.x, destiPos.y+jud))
					destiPos.y+=jud;
				else if(!isHit(destiPos.x, destiPos.y-jud))
					destiPos.y-=jud;
				else if(!isHit(destiPos.x+jud, destiPos.y))
					destiPos.x+=jud;
				else if(!isHit(destiPos.x-jud, destiPos.y))
					destiPos.x-=jud;
				jud++;
			}
				Node2 destiNode = nodes[destiPos.x][destiPos.y];
				
		startNode.sourcePoint=0;                                //此路线已经历的节点数
		startNode.destiPoint=startNode.GetCost(destiNode);     //得到直线距离
		startNode._parentnode=null;
		openedList.add(startNode);          //入队
		while (!openedList.isEmpty())
		{
         // remove the initialized component
         Node2 firstNode = (Node2) openedList.removeFirst();
         // check the equality
         if (firstNode.equals(destiNode)) {
             System.out.println(firstNode.sourcePoint);
             System.out.println(new Date());
             return makePath(firstNode);
         } else {
             //
             // add to the closedList
             //closedList.add(firstNode);        //已遍历List
        	 firstNode.isVisited = true;
        	 firstNode.isOpened = false;
        	 
        	 
             // get the mobile area of firstNode
            // LinkedList _limit = firstNode.getLimit();    
        	 
        	 
        	 
        	 //获得八向节点
             LinkedList<Node2> _limit = new LinkedList<Node2>();
        	 if( (firstNode._Pos.y-1) !=-1){
        		 _limit.add(nodes[firstNode._Pos.x][firstNode._Pos.y-1]);   //up
        		 nodes[firstNode._Pos.x][firstNode._Pos.y-1].cost = 5;
        	 }
			if( (firstNode._Pos.y+1) !=Main2.MAP.length){
				_limit.add(nodes[firstNode._Pos.x][firstNode._Pos.y+1]);   //down
				nodes[firstNode._Pos.x][firstNode._Pos.y+1].cost = 5;
			}
			if( (firstNode._Pos.x-1) !=-1){
				_limit.add(nodes[firstNode._Pos.x-1][firstNode._Pos.y]);   //left
				nodes[firstNode._Pos.x-1][firstNode._Pos.y].cost = 5;
			}
			if( (firstNode._Pos.x+1) !=Main2.MAP.length){
				_limit.add(nodes[firstNode._Pos.x+1][firstNode._Pos.y]);   //right
				nodes[firstNode._Pos.x+1][firstNode._Pos.y].cost = 5;
			}
			
			if((firstNode._Pos.y-1) !=-1 && (firstNode._Pos.x-1) !=-1){
				_limit.add(nodes[firstNode._Pos.x-1][firstNode._Pos.y-1]);  // up+left
				nodes[firstNode._Pos.x-1][firstNode._Pos.y-1].cost = 9.5f;
				
			}
			if((firstNode._Pos.y+1) !=Main2.MAP.length && (firstNode._Pos.x-1) !=-1){
				_limit.add(nodes[firstNode._Pos.x-1][firstNode._Pos.y+1]);  // down+left
				nodes[firstNode._Pos.x-1][firstNode._Pos.y+1].cost = 9.5f;
			}
			if((firstNode._Pos.y+1) !=Main2.MAP.length && (firstNode._Pos.x+1) !=Main2.MAP.length){
				_limit.add(nodes[firstNode._Pos.x+1][firstNode._Pos.y+1]);  // down+right
				nodes[firstNode._Pos.x+1][firstNode._Pos.y+1].cost = 9.5f;
			}
			if((firstNode._Pos.y-1) !=-1 && (firstNode._Pos.x+1) !=Main2.MAP.length){
				_limit.add(nodes[firstNode._Pos.x+1][firstNode._Pos.y-1]);  // up+right
				nodes[firstNode._Pos.x+1][firstNode._Pos.y-1].cost = 9.5f;
			} 
             
             
             
             // 对这八个节点判断
             for (int i = 0; i < _limit.size(); i++){
                 //get the adjacent node
                 Node2 neighborNode = (Node2) _limit.get(i);
                 //
                // boolean isOpen = openedList.contains(neighborNode);       //已入队？
                 boolean isOpen = neighborNode.isOpened;
                 
                 
                 
                 
                 //check if it can work
                 //boolean isClosed = closedList.contains(neighborNode);      //已遍历？
                 boolean isClosed = neighborNode.isVisited;
                 
                 
                 //
                 boolean isHit = isHit(neighborNode._Pos.x,           //是障碍？
                         neighborNode._Pos.y);  
                 //all of them are negative
                 
                 
                 if (!isOpen && !isClosed && !isHit) {              //都不是的话
                	 
                	/* if(neighborNode.cost!=5){                                //判断走斜角时跟墙角的碰撞
                		 int count = 0;
                		 if(isHit(neighborNode._Pos.x+1,neighborNode._Pos.y) && neighborNode._Pos.x+1<Main2.MAP.length){
                			 neighborNode.blocks[count] = new Point(neighborNode._Pos.x+1,neighborNode._Pos.y);
                			 count++;
                		 }
                		 if(isHit(neighborNode._Pos.x-1,neighborNode._Pos.y) && neighborNode._Pos.x>0){
                			 neighborNode.blocks[count] = new Point(neighborNode._Pos.x-1,neighborNode._Pos.y);
                			 count++;
                		 }
                		 if(isHit(neighborNode._Pos.x,neighborNode._Pos.y+1) && neighborNode._Pos.y+1<Main2.MAP.length){
                			 neighborNode.blocks[count] = new Point(neighborNode._Pos.x,neighborNode._Pos.y+1);
                			 count++;
                		 }
                		 if(isHit(neighborNode._Pos.x,neighborNode._Pos.y-1) && neighborNode._Pos.y>0){
                			 neighborNode.blocks[count] = new Point(neighborNode._Pos.x+1,neighborNode._Pos.y-1);
                			 count++;
                		 }
                	 }*/
                	 
                	 
                	 
                     //set the costFromStart
                     neighborNode.sourcePoint= firstNode.sourcePoint + neighborNode.cost ;      //增加已经历代价
                     //set the costToObject 
                     neighborNode.destiPoint = neighborNode         //重新计算直线距离
                             .GetCost(destiNode);
                     //change the neighborNode's parent nodes
                     neighborNode._parentnode = firstNode;         //父节点是前一个节点
                     
                    /* boolean judAdd = false;
                     if(neighborNode.cost!=5 && neighborNode.blocks[0]!=null && neighborNode._parentnode.blocks[0]!=null){
	                     for(int m = 0;m<neighborNode.blocks.length;m++){
	                    	 for(int n = 0;n<neighborNode._parentnode.blocks.length;n++){
	                    		 if(neighborNode.blocks[m]!=null && neighborNode._parentnode.blocks[n]!=null && neighborNode.blocks[m].equals(neighborNode._parentnode.blocks[n]) ){
	                    			judAdd = true;
	                    		 }
	                    	 }
	                     }
	                     if(judAdd)
	                    	 openedList.addLast(neighborNode);
	                     else
	                    	 openedList.add(neighborNode);     
                     }else{
                    	 openedList.add(neighborNode);   
                        
                     }*/
                     
                     //add方法重写
                     openedList.add(neighborNode);               //现已加入肯德基已遍历套餐
                     neighborNode.isOpened = true;
                     
                     
                 }else{
                	// System.out.println(isOpen + "  " + isClosed + "  " + isHit);
                 }
             }
         }

     }
     //clear the data
     openedList.clear();
     //
     return  null;
 }

	private LinkedList<Node2> makePath(Node2 node)          //将完整路线以经历过的节点的List的形式返回
	{
		LinkedList<Node2> path=new LinkedList<Node2>();
		while(node._parentnode!=null)
		{
			path.addFirst(node);
			node=node._parentnode;
		}
		path.addFirst(node);
		return path;
	}
	
	private boolean isHit(int x,int y)               //是障碍？
	{
		for(int i=0;i<_limit.length;i++)
		{
			if(_map[y][x]==_limit[i])
			{
				return true;
			}
		}
		return false;
	}
	
	private class OpenedList extends LinkedList{
		
		/*
		 * 把将要入队的节点与队中比较.如果总路径长较短的放在前面
		 */
		
		public void add(Node2 node)
		{
			for(int i=0;i<size();i++)
			{
				if(node.compareTo(get(i))<=0)
				{
					add(i,node);
					return;
				}
			}
			addLast(node);
		}
	}
}


