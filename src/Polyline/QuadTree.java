package Polyline;

public class QuadTree {

	private TreeNode root;

	public QuadTree() {
		super();
	}

	public void setRoot(TreeNode treenode) {

		this.root = treenode;
	}

	public TreeNode getRoot() {

		return root;
	}

	public boolean Include(Node node, TreeNode treenode) { //Node가 TreeNode의 BBOX에 포함되는지를 확인하는 함수
		if (treenode.getBox().getMinX() <= node.getX() && treenode.getBox().getMaxX() >= node.getX()
				&& treenode.getBox().getMinY() <= node.getY() && treenode.getBox().getMaxY() >= node.getY()) {
			return true;
		} else {
			return false;
		}
	}

	public void MakeQuadTree(ShapeList shapelist, double dt) {// QuadTree만들기

		// TreeNode끼리 연결
		TreeNode[] treenode = new TreeNode[21];
		treenode[0] = new TreeNode(0);
		treenode[1] = new TreeNode(1);
		treenode[2] = new TreeNode(2);
		treenode[3] = new TreeNode(3);
		treenode[4] = new TreeNode(4);
		treenode[5] = new TreeNode(5);
		treenode[6] = new TreeNode(6);
		treenode[7] = new TreeNode(7);
		treenode[8] = new TreeNode(8);
		treenode[9] = new TreeNode(9);
		treenode[10] = new TreeNode(10);
		treenode[11] = new TreeNode(11);
		treenode[12] = new TreeNode(12);
		treenode[13] = new TreeNode(13);
		treenode[14] = new TreeNode(14);
		treenode[15] = new TreeNode(15);
		treenode[16] = new TreeNode(16);
		treenode[17] = new TreeNode(17);
		treenode[18] = new TreeNode(18);
		treenode[19] = new TreeNode(19);
		treenode[20] = new TreeNode(20);

		setRoot(treenode[0]);

		treenode[0].setBox(shapelist.getLargestBox());

		treenode[1].setBox(treenode[0].getBox().splitBox(dt)[0]);
		treenode[2].setBox(treenode[0].getBox().splitBox(dt)[1]);
		treenode[3].setBox(treenode[0].getBox().splitBox(dt)[2]);
		treenode[4].setBox(treenode[0].getBox().splitBox(dt)[3]);

		treenode[5].setBox(treenode[1].getBox().splitBox(dt)[0]);
		treenode[6].setBox(treenode[1].getBox().splitBox(dt)[1]);
		treenode[7].setBox(treenode[1].getBox().splitBox(dt)[2]);
		treenode[8].setBox(treenode[1].getBox().splitBox(dt)[3]);

		treenode[9].setBox(treenode[2].getBox().splitBox(dt)[0]);
		treenode[10].setBox(treenode[2].getBox().splitBox(dt)[1]);
		treenode[11].setBox(treenode[2].getBox().splitBox(dt)[2]);
		treenode[12].setBox(treenode[2].getBox().splitBox(dt)[3]);

		treenode[13].setBox(treenode[3].getBox().splitBox(dt)[0]);
		treenode[14].setBox(treenode[3].getBox().splitBox(dt)[1]);
		treenode[15].setBox(treenode[3].getBox().splitBox(dt)[2]);
		treenode[16].setBox(treenode[3].getBox().splitBox(dt)[3]);

		treenode[17].setBox(treenode[4].getBox().splitBox(dt)[0]);
		treenode[18].setBox(treenode[4].getBox().splitBox(dt)[1]);
		treenode[19].setBox(treenode[4].getBox().splitBox(dt)[2]);
		treenode[20].setBox(treenode[4].getBox().splitBox(dt)[3]);

		for (int i = 1; i <= 4; i++) {
			treenode[0].makeChild(i - 1, treenode[i]);
			treenode[i].setParent(treenode[0]);
			treenode[i].setLevel(1);
		}
		for (int i = 5; i <= 8; i++) {
			treenode[1].makeChild(i - 5, treenode[i]);
			treenode[i].setParent(treenode[1]);
			treenode[i].setLevel(2);
		}
		for (int i = 9; i <= 12; i++) {
			treenode[2].makeChild(i - 9, treenode[i]);
			treenode[i].setParent(treenode[2]);
			treenode[i].setLevel(2);
		}
		for (int i = 13; i <= 16; i++) {
			treenode[3].makeChild(i - 13, treenode[i]);
			treenode[i].setParent(treenode[3]);
			treenode[i].setLevel(2);
		}
		for (int i = 17; i <= 20; i++) {
			treenode[4].makeChild(i - 17, treenode[i]);
			treenode[i].setParent(treenode[4]);
			treenode[i].setLevel(2);
		}

		TreeNode currentTN = new TreeNode();
		
		
		//-------------------------------------------------------------------------------
		
		/* 
		 * root 노드부터 시작해서 객체를 TreeNode에 집어넣기
		 */
		currentTN = treenode[0];
		for (int i = 0; i < shapelist.size(); i++) {
			shapelist.getShape(i).setSntree(treenode[0]);
			shapelist.getShape(i).setEntree(treenode[0]);
		}
		for (int i = 0; i < shapelist.size(); i++) {
			Shape sp = new Shape();
			sp = shapelist.getShape(i);
			while(true) {
				if (sp.getSntree() == sp.getEntree() && sp.getSntree().getLevel() !=2) {

						if (Include(sp.getStart(), sp.getSntree().getChild()[0])) {
							sp.setSntree(sp.getSntree().getChild()[0]);
						} else if (Include(sp.getStart(), sp.getSntree().getChild()[1])) {
							sp.setSntree(sp.getSntree().getChild()[1]);
						} else if (Include(sp.getStart(), sp.getSntree().getChild()[2])) {
							sp.setSntree(sp.getSntree().getChild()[2]);
						} else if(Include(sp.getStart(), sp.getSntree().getChild()[3])){
							sp.setSntree(sp.getSntree().getChild()[3]);
						}
						
						if (Include(sp.getEnd(), sp.getEntree().getChild()[0])) {
							sp.setEntree(sp.getEntree().getChild()[0]);
						} else if (Include(sp.getEnd(), sp.getEntree().getChild()[1])) {
							sp.setEntree(sp.getEntree().getChild()[1]);
						} else if (Include(sp.getEnd(), sp.getEntree().getChild()[2])) {
							sp.setEntree(sp.getEntree().getChild()[2]);
						} else if(Include(sp.getEnd(), sp.getEntree().getChild()[3])){
							sp.setEntree(sp.getEntree().getChild()[3]);
						}		
					
				}
				else {
					//level을 추가하자
					if(sp.getSntree().getLevel()>sp.getEntree().getLevel()) {
						sp.setSntree(sp.getEntree());
						treenode[sp.getSntree().getId()].addShape(sp);break;
					}else if(sp.getSntree().getLevel()<sp.getEntree().getLevel()) {
						sp.setEntree(sp.getSntree());
						treenode[sp.getSntree().getId()].addShape(sp);break;
					}
					else {
						if(sp.getSntree()==sp.getEntree()) {
							treenode[sp.getSntree().getId()].addShape(sp);break;
						}else{
							sp.setSntree(sp.getSntree().getParent());
							sp.setEntree(sp.getEntree().getParent());
							treenode[sp.getSntree().getId()].addShape(sp);break;
						}
					}
					
				}
			}
		}
  	
		//---------------------------------------------------------------------------------
		/*
		 * 단말노드부터 시작해서 객체를 TreeNode에 집어넣기
		 * 
		for (int i = 0; i < shapelist.size(); i++) {
			currentTN = treenode[0];

			while (currentTN != null) {

				if (currentTN.getChild()[0] != null && currentTN.getChild()[1] != null
						&& currentTN.getChild()[2] != null && currentTN.getChild()[3] != null) {
					if (Include(shapelist.getShape(i).getStart(), currentTN.getChild()[0])) {
						shapelist.getShape(i).setSntree(currentTN.getChild()[0]);
						currentTN = currentTN.getChild()[0];
					} else if (Include(shapelist.getShape(i).getStart(), currentTN.getChild()[1])) {
						shapelist.getShape(i).setSntree(currentTN.getChild()[1]);
						currentTN = currentTN.getChild()[1];
					} else if (Include(shapelist.getShape(i).getStart(), currentTN.getChild()[2])) {
						shapelist.getShape(i).setSntree(currentTN.getChild()[2]);
						currentTN = currentTN.getChild()[2];
					} else {
						shapelist.getShape(i).setSntree(currentTN.getChild()[3]);
						currentTN = currentTN.getChild()[3];
					}
				} else {
					break;
				}
			}
		}

		for (int i = 0; i < shapelist.size(); i++) {
			currentTN = treenode[0];

			while (currentTN != null) {

				if (currentTN.getChild()[0] != null && currentTN.getChild()[1] != null
						&& currentTN.getChild()[2] != null && currentTN.getChild()[3] != null) {
					if (Include(shapelist.getShape(i).getEnd(), currentTN.getChild()[0])) {
						shapelist.getShape(i).setEntree(currentTN.getChild()[0]);
						currentTN = currentTN.getChild()[0];
					} else if (Include(shapelist.getShape(i).getEnd(), currentTN.getChild()[1])) {
						shapelist.getShape(i).setEntree(currentTN.getChild()[1]);
						currentTN = currentTN.getChild()[1];
					} else if (Include(shapelist.getShape(i).getEnd(), currentTN.getChild()[2])) {
						shapelist.getShape(i).setEntree(currentTN.getChild()[2]);
						currentTN = currentTN.getChild()[2];
					} else {
						shapelist.getShape(i).setEntree(currentTN.getChild()[3]);
						currentTN = currentTN.getChild()[3];
					}
				} else {
					break;
				}
			}
		}

		for (int i = 0; i < shapelist.size(); i++) {
			while (true) {
				if (shapelist.getShape(i).getSntree() == shapelist.getShape(i).getEntree()) {
					treenode[shapelist.getShape(i).getSntree().getId()].addShape(shapelist.getShape(i));
					break;
				} else {
					shapelist.getShape(i).setSntree(shapelist.getShape(i).getSntree().getParent());
					shapelist.getShape(i).setEntree(shapelist.getShape(i).getEntree().getParent());
				}
			}
		}
	*/
	//-------------------------------------------------------------------------------

	}

	public double getDistance(double x, double y, double x1, double y1) {//두 좌표사이의 거리 

		return Math.sqrt(Math.pow(Math.abs(x1 - x), 2) + Math.pow(Math.abs(y1 - y), 2));

	}
	public  void AddDegreeItself(TreeNode cur, double dt) { //같은 TreeNode에 속한 객체끼리 비교 후, AddDegree
															//dt:오차
		Node SN1 = new Node();
		Node EN1 = new Node();
		Node SN2 = new Node();
		Node EN2 = new Node();
		
	   for(int i=0;i<cur.getNumOfShape();i++) {
		   for(int j=0;j<cur.getNumOfShape();j++) {
			   if(i!=j) {
				   
				   SN1 = cur.getShape()[i].getStart();
					EN1 = cur.getShape()[i].getEnd();
					SN2 = cur.getShape()[j].getStart();
					EN2 = cur.getShape()[j].getEnd();

					double cal1 = getDistance(SN1.getX(), SN1.getY(), EN2.getX(), EN2.getY());
					double cal2 = getDistance(SN1.getX(), SN1.getY(), SN2.getX(), SN2.getY());
					double cal3 = getDistance(EN1.getX(), EN1.getY(), EN2.getX(), EN2.getY());
					double cal4 = getDistance(EN1.getX(), EN1.getY(), SN2.getX(), SN2.getY());
					
	
						if (cal1 <= dt) {
							cur.getShape()[i].setDsn(cur.getShape()[i].getDsn() + 1);
							cur.getShape()[j].setDen(cur.getShape()[j].getDen() + 1);
						} 
						if (cal2 <= dt) {
							cur.getShape()[i].setDsn(cur.getShape()[i].getDsn() + 1);
							cur.getShape()[j].setDsn(cur.getShape()[j].getDsn() + 1);
						}
						if (cal3 <= dt) {
							cur.getShape()[i].setDen(cur.getShape()[i].getDen() + 1);
							cur.getShape()[j].setDen(cur.getShape()[j].getDen() + 1);
						} 
						if(cal4 <= dt){
							cur.getShape()[i].setDen(cur.getShape()[i].getDen() + 1);
							cur.getShape()[j].setDsn(cur.getShape()[j].getDsn() + 1);
						}
			   }
		   }
	   }

		
	}

	public void AddDegreewithchild(TreeNode cur1, TreeNode cur2, double dt) { //자손또는 후손에있는 객체들과 비교 후, AddDegree

		Node SN1 = new Node();
		Node EN1 = new Node();
		Node SN2 = new Node();
		Node EN2 = new Node();

		for (int i = 0; i < cur1.getNumOfShape(); i++) {
			for (int j = 0; j < cur2.getNumOfShape(); j++) {
				SN1 = cur1.getShape()[i].getStart();
				EN1 = cur1.getShape()[i].getEnd();
				SN2 = cur2.getShape()[j].getStart();
				EN2 = cur2.getShape()[j].getEnd();

				//double dt = 0;

				double cal1 = getDistance(SN1.getX(), SN1.getY(), EN2.getX(), EN2.getY());
				double cal2 = getDistance(SN1.getX(), SN1.getY(), SN2.getX(), SN2.getY());
				double cal3 = getDistance(EN1.getX(), EN1.getY(), EN2.getX(), EN2.getY());
				double cal4 = getDistance(EN1.getX(), EN1.getY(), SN2.getX(), SN2.getY());
				
		
			
					if (cal1 <= dt) {
						cur1.getShape()[i].setDsn(cur1.getShape()[i].getDsn() + 1);
						cur2.getShape()[j].setDen(cur2.getShape()[j].getDen() + 1);
					}
					if (cal2 <= dt) {
						cur1.getShape()[i].setDsn(cur1.getShape()[i].getDsn() + 1);
						cur2.getShape()[j].setDsn(cur2.getShape()[j].getDsn() + 1);
					}
					if (cal3 <= dt) {
						cur1.getShape()[i].setDen(cur1.getShape()[i].getDen() + 1);
						cur2.getShape()[j].setDen(cur2.getShape()[j].getDen() + 1);
					} 
					if(cal4 <= dt) {
						cur1.getShape()[i].setDen(cur1.getShape()[i].getDen() + 1);
						cur2.getShape()[j].setDsn(cur2.getShape()[j].getDsn() + 1);
					}
				
			}
		}

	}

	public void search(TreeNode cur,double dt) { //재귀적으로 후손 찾기
		if(cur==null) {return;}
		else {
			AddDegreeItself(cur,dt);
			search(cur.getChild()[0],dt);
			search(cur.getChild()[1],dt);
			search(cur.getChild()[2],dt);
			search(cur.getChild()[3],dt);
			
		}
	}

	public void AddDegree(double dt) {// AddDegreeItself, AddDegreeWithChild 하기
		TreeNode currentTN = new TreeNode();
		currentTN = this.root;
		
		search(this.root,dt);
		
		for (int i = 0; i < 4; i++) {
			AddDegreewithchild(currentTN, currentTN.getChild()[i],dt);
			for (int j = 0; j < 4; j++) {
				AddDegreewithchild(currentTN.getChild()[i], currentTN.getChild()[i].getChild()[j],dt);
				AddDegreewithchild(currentTN, currentTN.getChild()[i].getChild()[j],dt);
			}
		}
		
	}

}
