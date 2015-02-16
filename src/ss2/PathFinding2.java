package ss2;

import java.awt.Point;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;




public class PathFinding2 {
	
	private OpenedList openedList;   //�����б�
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
		
		nodes = new Node2[_map.length][_map[0].length];        //�õ�ͼ���鹹��ڵ�����
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
			while(isHit(destiPos.x, destiPos.y)){                        //�յ����ϰ����ţ�
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
				
		startNode.sourcePoint=0;                                //��·���Ѿ����Ľڵ���
		startNode.destiPoint=startNode.GetCost(destiNode);     //�õ�ֱ�߾���
		startNode._parentnode=null;
		openedList.add(startNode);          //���
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
             //closedList.add(firstNode);        //�ѱ���List
        	 firstNode.isVisited = true;
        	 firstNode.isOpened = false;
        	 
        	 
             // get the mobile area of firstNode
            // LinkedList _limit = firstNode.getLimit();    
        	 
        	 
        	 
        	 //��ð���ڵ�
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
             
             
             
             // ����˸��ڵ��ж�
             for (int i = 0; i < _limit.size(); i++){
                 //get the adjacent node
                 Node2 neighborNode = (Node2) _limit.get(i);
                 //
                // boolean isOpen = openedList.contains(neighborNode);       //����ӣ�
                 boolean isOpen = neighborNode.isOpened;
                 
                 
                 
                 
                 //check if it can work
                 //boolean isClosed = closedList.contains(neighborNode);      //�ѱ�����
                 boolean isClosed = neighborNode.isVisited;
                 
                 
                 //
                 boolean isHit = isHit(neighborNode._Pos.x,           //���ϰ���
                         neighborNode._Pos.y);  
                 //all of them are negative
                 
                 
                 if (!isOpen && !isClosed && !isHit) {              //�����ǵĻ�
                	 
                	/* if(neighborNode.cost!=5){                                //�ж���б��ʱ��ǽ�ǵ���ײ
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
                     neighborNode.sourcePoint= firstNode.sourcePoint + neighborNode.cost ;      //�����Ѿ�������
                     //set the costToObject 
                     neighborNode.destiPoint = neighborNode         //���¼���ֱ�߾���
                             .GetCost(destiNode);
                     //change the neighborNode's parent nodes
                     neighborNode._parentnode = firstNode;         //���ڵ���ǰһ���ڵ�
                     
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
                     
                     //add������д
                     openedList.add(neighborNode);               //���Ѽ���ϵ»��ѱ����ײ�
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

	private LinkedList<Node2> makePath(Node2 node)          //������·���Ծ������Ľڵ��List����ʽ����
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
	
	private boolean isHit(int x,int y)               //���ϰ���
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
		 * �ѽ�Ҫ��ӵĽڵ�����бȽ�.�����·�����϶̵ķ���ǰ��
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


