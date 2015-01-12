import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class spatial_search {
	
	
	public static void main(String [ ] args) throws SQLException
	{

        // Connect to Oracle JDBC Driver
		try {			 
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
		}
		Connection connection = null;
		
		// Add connection to Database
		try { 
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:DBHW", "SCOTT",
					"");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	
		if(args[0].compareTo("window")==0){
			getWindoData(args,connection);
		}
		else if(args[0].compareTo("within")==0){
			getwithinData(args,connection);
		}
		else if(args[0].compareTo("nn")==0){
			getnnData(args,connection);
		}
		else if(args[0].compareTo("demo")==0){
			getdemoData(args,connection);
		}    
	      connection.close();
	}

	private static void getdemoData(String[] args, Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		Statement stmt = null;
		ResultSet rs = null;
		 String sql=null;	
		if(args[1].compareTo("1")==0){
			sql="SELECT b.BNAME FROM BUILDING b WHERE REGEXP_LIKE (b.BNAME, '^S') AND b.bid not in ( select  f.BID FROM FIREB f  )";
			stmt = connection.createStatement();
		    rs = stmt.executeQuery(sql);
		    while(rs.next()){
				   System.out.println("Name: "+rs.getString("BNAME"));
			      }
		}	
		else if(args[1].compareTo("2")==0){
			sql="SELECT b2.BNAME, b.BID FROM FIREHYDRANT b, FIREB  b2 WHERE sdo_nn(b.shape, b2.shape,'sdo_num_res=5', 1)= 'TRUE'";
			stmt = connection.createStatement();
		    rs = stmt.executeQuery(sql);
		    while(rs.next()){
				   System.out.println(rs.getString("BNAME")+" "+rs.getString("BID"));
			      }
		}
		else if(args[1].compareTo("3")==0){
			sql="SELECT f.bid FROM BUILDING b, FIREHYDRANT f WHERE  SDO_WITHIN_DISTANCE(b.SHAPE,f.SHAPE, 'distance = 120') = 'TRUE' GROUP BY f.bid ORDER BY COUNT(*) DESC OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY";
			stmt = connection.createStatement();
		    rs = stmt.executeQuery(sql);
		    while(rs.next()){
				   System.out.println("BID: "+rs.getString("BID"));
			      }
		}	
		else if(args[1].compareTo("4")==0){
			sql="SELECT  f.bid FROM BUILDING b, FIREHYDRANT f  WHERE ( sdo_nn(f.shape, b.shape,'sdo_num_res=1', 1)= 'TRUE' ) GROUP BY f.bid ORDER  BY COUNT(*) DESC  OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY";
			stmt = connection.createStatement();
		    rs = stmt.executeQuery(sql);
		    while(rs.next()){
				   System.out.println("BID: "+rs.getString("BID"));
			      }
		}
		else if(args[1].compareTo("5")==0){
			sql="SELECT SDO_AGGR_MBR(g).SDO_ORDINATES FROM (SELECT SDO_AGGR_UNION( MDSYS.SDOAGGRTYPE(b.shape, 0.05)) AS g FROM BUILDING b WHERE REGEXP_LIKE (b.BNAME, '.HE')) ";
			stmt = connection.createStatement();
		    rs = stmt.executeQuery(sql);
		    while(rs.next()){
	            Array rsArr = rs.getArray("SDO_AGGR_MBR(g).SDO_ORDINATES");
	            Number[] pos = (Number[])rsArr.getArray();
	            for(int j=0;j<pos.length;j+=2)
	            {           
	            	System.out.println("("+pos[j].intValue()+","+pos[j+1].intValue()+")");

	                }
	            

			      }

		}		   

	}

	private static void getwithinData(String[] args, Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		Statement stmt = null;
		ResultSet rs = null;
		String type=null;
		
		if(args[1].compareTo("building")==0){
			type="BUILDING ";
			//System.out.println(type);
		}	
		else if(args[1].compareTo("firehydrant")==0){
			type="FIREHYDRANT ";
			//System.out.println(type);
		}
		else if(args[1].compareTo("firebuilding")==0){
			type="FIREB ";
			//System.out.println(type);
		}
		try {
			stmt = connection.createStatement();
			 String sql;	
			   if(args[1].compareTo("firebuilding")==0)
				   sql = "SELECT f.bid, f.BNAME FROM BUILDING b, "+ type+" f WHERE b.BNAME='"+args[2]+"' AND  SDO_WITHIN_DISTANCE(b.SHAPE,f.SHAPE, 'distance = "+args[3]+"' ) = 'TRUE' ";
			   else if(args[1].compareTo("firehydrant")==0){
				   sql = "SELECT f.bid FROM BUILDING b, "+ type+" f WHERE b.BNAME='"+args[2]+"' AND  SDO_WITHIN_DISTANCE(b.SHAPE,f.SHAPE, 'distance = "+args[3]+"' ) = 'TRUE' ";
			   }
			   else
			   {
				   sql = "SELECT f.bid, f.BNAME FROM BUILDING b, "+ type+" f WHERE b.BNAME='"+args[2]+"' AND  SDO_WITHIN_DISTANCE(b.SHAPE,f.SHAPE, 'distance = "+args[3]+"' ) = 'TRUE' ";
					
			   }
			   // System.out.println(sql);     
		      rs = stmt.executeQuery(sql);
	   }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	    while(rs.next()){
	   if(args[1].compareTo("firebuilding")==0){
	    	if(!rs.getString("BNAME").equals(args[2]))
	    	System.out.println(rs.getString("BID"));
	   }
	   else if(args[1].compareTo("firehydrant")==0){
		   System.out.println(rs.getString("BID"));
	   }else
	   {
		   if(!rs.getString("BNAME").equals(args[2]))
		   System.out.println(rs.getString("BID"));
	   }
	   
	      }
	}

	private static void getnnData(String[] args, Connection connection) throws SQLException {
				Statement stmt = null;
				ResultSet rs = null;
				String type=null;
				
				if(args[1].compareTo("building")==0){
					type="BUILDING ";
				//	System.out.println(type);
				}	
				else if(args[1].compareTo("firehydrant")==0){
					type="FIREHYDRANT ";
				//	System.out.println(type);
				}
				else if(args[1].compareTo("firebuilding")==0){
					type="FIREB ";
				//	System.out.println(type);
				}
				   try {
						stmt = connection.createStatement();
						 String sql;	
						 int num_nn = Integer.parseInt(args[3])+1;
					      sql = "SELECT * FROM "+type+ "b, "+type +" b2 WHERE b2.BID='"+args[2] +"'  AND  sdo_nn(b.shape, b2.shape,'sdo_num_res="+num_nn+"', 1)= 'TRUE'"; 
					  //  System.out.println(sql);     
					      rs = stmt.executeQuery(sql);
				   }catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
				    while(rs.next()){
				    	if(!rs.getString("BID").equals(args[2]))
				    		System.out.println(rs.getString("BID"));

				      }
	}

	private static void getWindoData(String[] args, Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		Statement stmt = null;
		ResultSet rs = null;
		String type=null;
		
		if(args[1].compareTo("building")==0){
			type="BUILDING ";
			//System.out.println(type);
		}	
		else if(args[1].compareTo("firehydrant")==0){
			type="FIREHYDRANT ";
			//System.out.println(type);
		}
		else if(args[1].compareTo("firebuilding")==0){
			type="FIREB ";
			//System.out.println(type);
		}
		   try {
				stmt = connection.createStatement();
				 String sql;
			      sql = "SELECT * FROM "+type+ "f WHERE sdo_filter (f.shape,sdo_geometry (2003, NULL,null,sdo_elem_info_array (1,1003,3),sdo_ordinate_array (";      
			     sql+=args[2]+","+args[3]+","+args[4]+","+args[5]+")),'mask=inside querytype=WINDOW') = 'TRUE'"; 
			    // System.out.println(sql);     
			      rs = stmt.executeQuery(sql);
		   }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		    while(rs.next()){	   
			   System.out.println(rs.getString("BID"));
		      }
		   
	}
}
