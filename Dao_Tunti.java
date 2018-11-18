package dao;

import java.util.ArrayList;
import model.Tunti;


public class Dao_Tunti extends Dao {
	
	public boolean lisaaTunti(Tunti tunti){
		boolean paluuArvo=true;
		sql="INSERT INTO VJ_tunnit(alkuaika, loppuaika) VALUES(?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql);
			stmtPrep.setTime(1, tunti.getAlkuaika());
			stmtPrep.setTime(2, tunti.getLoppuaika());
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public ArrayList<Tunti> haeTunnit(String hakusana) throws Exception{		
		ArrayList<Tunti> tunnit = new ArrayList<Tunti>();
		sql = "SELECT * FROM VJ_tunnit WHERE alkuaika LIKE ? OR loppuaika LIKE ?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setString(1, "%"+hakusana+"%");
			stmtPrep.setString(2, "%"+hakusana+"%");
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					Tunti tunti = new Tunti();
					tunti.setTunti_id(rs.getInt("tunti_id"));
					tunti.setAlkuaika(rs.getTime("alkuaika"));
					tunti.setLoppuaika(rs.getTime("loppuaika"));
					tunnit.add(tunti);
				}					
			}
			con.close();
		}			
		return tunnit;
	}
	
	public Tunti haeTunti(int tunti_id) throws Exception{
		Tunti tunti=null;		
		sql = "SELECT * FROM VJ_tunnit WHERE tunti_id=?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setInt(1, tunti_id);			
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					tunti = new Tunti();
					tunti.setTunti_id(rs.getInt("tunti_id"));
					tunti.setAlkuaika(rs.getTime("alkuaika"));
					tunti.setLoppuaika(rs.getTime("loppuaika"));
				}					
			}
			con.close();
		}			
		return tunti;
	}

}
