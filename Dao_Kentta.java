package dao;

import java.util.ArrayList;
import model.Halli;
import model.Kentta;

public class Dao_Kentta extends Dao {

	public Kentta haeKentta(int kentta_id) throws Exception{
		Kentta kentta=null;		
		sql = "SELECT * FROM VJ_kentat WHERE kentta_id=?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setInt(1, kentta_id);			
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					kentta = new Kentta();
					kentta.setKentta_id(rs.getInt("kentta_id"));	
					kentta.setHalli_id(rs.getInt("halli_id"));
				}					
			}
			con.close();
		}			
		return kentta;
	}
	
	public ArrayList<Kentta> haeKentat(String hakusana) throws Exception{		
		ArrayList<Kentta> kentat = new ArrayList<Kentta>();		
		sql = "SELECT * FROM VJ_kentat WHERE (halli_id LIKE ?)"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setString(1, "%"+hakusana+"%");
			stmtPrep.setString(2, "%"+hakusana+"%");
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					Kentta kentta = new Kentta();
					kentta.setKentta_id(rs.getInt("kentta_id"));
					kentta.setHalli_id(rs.getInt("halli_id"));
					kentat.add(kentta);
				}					
			}
			con.close();
		}	
		return kentat;
	}
	
	public boolean lisaaKentta(Kentta kentta){
		boolean paluuArvo=true;
		sql="INSERT INTO VJ_kentat(halli_id) VALUES(?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql);
			//stmtPrep.setInt(1, laji.getLaji_id());
			//stmtPrep.setString(1, laji.getNimi());
			stmtPrep.setInt(1, kentta.getHalli_id());
			//stmtPrep.setString(4, "1");
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public boolean muutaKentta(Kentta kentta){
		boolean paluuArvo=true;
		sql="UPDATE VJ_kentat SET halli_id=? WHERE kentta_id=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql);
			//stmtPrep.setString(1, laji.getNimi());
			stmtPrep.setInt(1, kentta.getHalli_id());
			//stmtPrep.setString(4, "1");
			stmtPrep.setInt(2, kentta.getKentta_id());
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public Halli haeHalli(int halli_id)throws Exception{		
		Halli halli = null;
		sql= "SELECT * FROM VJ_hallit, VJ_kentat WHERE VJ_kentat.halli_id=VJ_hallit.halli_id AND halli_id=?";			
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
	
}
