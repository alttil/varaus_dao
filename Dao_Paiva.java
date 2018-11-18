package dao;

import java.util.ArrayList;

import model.Paiva;

public class Dao_Paiva extends Dao {

	public Paiva haePaiva(int paiva_id) throws Exception{
		Paiva paiva=null;		
		sql = "SELECT * FROM VJ_paivat WHERE paiva_id=?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setInt(1, paiva_id);			
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					paiva = new Paiva();
					paiva.setPaiva_id(rs.getInt("paiva_id"));
					paiva.setPaivanro(rs.getInt("paivanro"));
				}					
			}
			con.close();
		}			
		return paiva;
	}
	
	public ArrayList<Paiva> haePaivat(String hakusana) throws Exception{		
		ArrayList<Paiva> paivat = new ArrayList<Paiva>();		
		sql = "SELECT * FROM VJ_paivat WHERE (paiva_id LIKE ? OR paivanro LIKE ?)"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setString(1, "%"+hakusana+"%");
			stmtPrep.setString(2, "%"+hakusana+"%");
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					Paiva paiva = new Paiva();
					paiva.setPaiva_id(rs.getInt("paiva_id"));
					paiva.setPaivanro(rs.getInt("paivanro"));
					paivat.add(paiva);
				}					
			}
			con.close();
		}	
		return paivat;
	}
	
	
}
