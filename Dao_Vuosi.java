package dao;

import java.util.ArrayList;

import model.Vuosi;

public class Dao_Vuosi extends Dao {
	
	public Vuosi haeVuosi(int vuosi_id) throws Exception{
		Vuosi vuosi=null;		
		sql = "SELECT * FROM VJ_vuodet WHERE vuosi_id=?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setInt(1, vuosi_id);			
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					vuosi = new Vuosi();
					vuosi.setVuosi_id(rs.getInt("vuosi_id"));
					vuosi.setVuosinro(rs.getInt("vuosinro"));
				}					
			}
			con.close();
		}			
		return vuosi;
	}
	
	public ArrayList<Vuosi> haeVuodet(String hakusana) throws Exception{		
		ArrayList<Vuosi> vuodet = new ArrayList<Vuosi>();		
		sql = "SELECT * FROM VJ_vuodet WHERE (vuosi_id LIKE ? OR vuosinro LIKE ?)"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setString(1, "%"+hakusana+"%");
			stmtPrep.setString(2, "%"+hakusana+"%");
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					Vuosi vuosi = new Vuosi();
					vuosi.setVuosi_id(rs.getInt("vuosi_id"));
					vuosi.setVuosinro(rs.getInt("vuosinro"));
					vuodet.add(vuosi);
				}					
			}
			con.close();
		}	
		return vuodet;
	}

}
