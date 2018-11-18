package dao;

import java.util.ArrayList;
import model.Halli;

public class Dao_Halli extends Dao {
	
	public boolean lisaaHalli(Halli halli){
		boolean paluuArvo=true;
		sql="INSERT INTO VJ_hallit(nimi, puhelin, sahkoposti, osoite) VALUES(?,?,?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, halli.getNimi());
			stmtPrep.setString(2, halli.getPuhelin());
			stmtPrep.setString(3, halli.getSahkoposti());
			stmtPrep.setString(4, halli.getOsoite());
			//stmtPrep.setString(5, "1");
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public boolean muutaHalli(Halli halli){
		boolean paluuArvo=true;
		sql="UPDATE VJ_hallit SET nimi=?, puhelin=?, sahkoposti=?, osoite=? WHERE halli_id=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, halli.getNimi());
			stmtPrep.setString(2, halli.getPuhelin());
			stmtPrep.setString(3, halli.getSahkoposti());
			stmtPrep.setString(4, halli.getOsoite());
			//stmtPrep.setString(5, "1");
			stmtPrep.setInt(5, halli.getHalli_id());
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public ArrayList<Halli> haeHallit(String hakusana2) throws Exception{		
		ArrayList<Halli> hallit = new ArrayList<Halli>();
		sql = "SELECT * FROM VJ_hallit WHERE (nimi LIKE ? OR osoite LIKE ?) AND Poistettu=0"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setString(1, "%"+hakusana2+"%");
			stmtPrep.setString(2, "%"+hakusana2+"%");
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					Halli halli = new Halli();
					halli.setHalli_id(rs.getInt("halli_id"));
					halli.setNimi(rs.getString("nimi"));
					halli.setPuhelin(rs.getString("puhelin"));
					halli.setSahkoposti(rs.getString("sahkoposti"));
					halli.setOsoite(rs.getString("osoite"));
					hallit.add(halli);
				}					
			}
			con.close();
		}			
		return hallit;
	}
	
	public Halli haeHalli(int halli_id) throws Exception{
		Halli halli=null;		
		sql = "SELECT * FROM VJ_hallit WHERE halli_id=?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setInt(1, halli_id);			
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					halli = new Halli();
					halli.setHalli_id(rs.getInt("halli_id"));
					halli.setNimi(rs.getString("nimi"));
					halli.setPuhelin(rs.getString("puhelin"));
					halli.setSahkoposti(rs.getString("sahkoposti"));
					halli.setOsoite(rs.getString("osoite"));
				}					
			}
			con.close();
		}			
		return halli;
	}
	
	public void poistaHalli(int halli_id) throws Exception{		
		sql = "UPDATE VJ_hallit SET Poistettu=1 WHERE halli_id=?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setInt(1, halli_id);			
			stmtPrep.executeUpdate();			
			con.close();
		}		
	}
}














