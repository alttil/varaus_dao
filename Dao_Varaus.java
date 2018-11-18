package dao;

import java.util.ArrayList;
import model.Varaus;

public class Dao_Varaus extends Dao {
	
	public boolean lisaaVaraus(Varaus varaus){
		boolean paluuArvo=true;
		sql="INSERT INTO VJ_varaukset(varaus_id, asiakas_id, lisatietoja) VALUES(?,?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql);
			stmtPrep.setInt(1, varaus.getVaraus_id());
			stmtPrep.setInt(2, varaus.getAsiakas_id());
			stmtPrep.setString(3, varaus.getLisatietoja());
			stmtPrep.setString(4, "1");
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public boolean muutaVaraus(Varaus varaus){
		boolean paluuArvo=true;
		sql="UPDATE VJ_varaukset SET asiakas_id=?, lisatietoja=?, WHERE varaus_id=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql);
			stmtPrep.setInt(1, varaus.getVaraus_id());
			stmtPrep.setInt(2, varaus.getAsiakas_id());
			stmtPrep.setString(3, varaus.getLisatietoja());
			stmtPrep.setString(4, "1");
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public Varaus haeVaraus(int varaus_id) throws Exception{
		Varaus varaus=null;		
		sql = "SELECT * FROM VJ_varaukset WHERE varaus_id=?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setInt(1, varaus_id);			
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					varaus = new Varaus();
					varaus.setVaraus_id(rs.getInt("varaus_id"));
					varaus.setAsiakas_id(rs.getInt("asiakas_id"));
					varaus.setLisatietoja(rs.getString("lisatietoja"));	
				}					
			}
			con.close();
		}			
		return varaus;
	}
	
	public ArrayList<Varaus> haeVaraukset(String hakusana) throws Exception{		
		ArrayList<Varaus> varaukset = new ArrayList<Varaus>();
		sql = "SELECT * FROM VJ_varaukset WHERE (varaus_id LIKE ? OR asiakas_id LIKE ?) AND Poistettu=0"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setString(1, "%"+hakusana+"%");
			stmtPrep.setString(2, "%"+hakusana+"%");
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					Varaus varaus = new Varaus();
					varaus.setVaraus_id(rs.getInt("varaus_id"));
					varaus.setAsiakas_id(rs.getInt("asiakas_id"));
					varaus.setLisatietoja(rs.getString("lisatietoja"));
					varaukset.add(varaus);
				}					
			}
			con.close();
		}			
		return varaukset;
	}
	
	public void poistaVaraus(int varaus_id) throws Exception{		
		sql = "UPDATE VJ_varaukset SET Poistettu=1 WHERE varaus_id=?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setInt(1, varaus_id);			
			stmtPrep.executeUpdate();			
			con.close();
		}		
	}
}