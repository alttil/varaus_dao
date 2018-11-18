package dao;

import java.util.ArrayList;
import model.Laji;


public class Dao_Laji extends Dao {
	
	public boolean lisaaLaji(Laji laji){
		boolean paluuArvo=true;
		sql="INSERT INTO VJ_lajit(nimi, halli_id) VALUES(?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql);
			//stmtPrep.setInt(1, laji.getLaji_id());
			stmtPrep.setString(1, laji.getNimi());
			stmtPrep.setInt(2, laji.getHalli_id());
			//stmtPrep.setString(4, "1");
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public boolean muutaLaji(Laji laji){
		boolean paluuArvo=true;
		sql="UPDATE VJ_lajit SET nimi=?, halli_id=? WHERE laji_id=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql);
			stmtPrep.setString(1, laji.getNimi());
			stmtPrep.setInt(2, laji.getHalli_id());
			//stmtPrep.setString(4, "1");
			stmtPrep.setInt(3, laji.getLaji_id());
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public Laji haeLaji(int laji_id) throws Exception{
		Laji laji=null;		
		sql = "SELECT * FROM VJ_lajit WHERE laji_id=?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setInt(1, laji_id);			
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					laji = new Laji();
					laji.setLaji_id(rs.getInt("laji_id"));
					laji.setNimi(rs.getString("nimi"));	
					laji.setHalli_id(rs.getInt("halli_id"));
				}					
			}
			con.close();
		}			
		return laji;
	}
	
	public ArrayList<Laji> haeLajit(String hakusana) throws Exception{		
		ArrayList<Laji> lajit = new ArrayList<Laji>();		
		sql = "SELECT * FROM VJ_lajit WHERE (laji_id LIKE ? OR nimi LIKE ?) AND poistettu=0"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setString(1, "%"+hakusana+"%");
			stmtPrep.setString(2, "%"+hakusana+"%");
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					Laji laji = new Laji();
					laji.setLaji_id(rs.getInt("laji_id"));
					laji.setNimi(rs.getString("nimi"));	
					laji.setHalli_id(rs.getInt("halli_id"));
					lajit.add(laji);
				}					
			}
			con.close();
		}	
		return lajit;
	}
	
	public void poistaLaji(int laji_id) throws Exception{		
		sql = "UPDATE VJ_lajit SET Poistettu=1 WHERE laji_id=?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setInt(1, laji_id);			
			stmtPrep.executeUpdate();			
			con.close();
		}		
	}

}
