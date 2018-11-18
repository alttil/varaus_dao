package dao;

import java.util.ArrayList;

import model.Kuukausi;

public class Dao_Kuukausi extends Dao {
	
	public Kuukausi haeKuukausi(int kuukausi_id) throws Exception{
		Kuukausi kuukausi=null;		
		sql = "SELECT * FROM VJ_kuukaudet WHERE kuukausi_id=?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setInt(1, kuukausi_id);			
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					kuukausi = new Kuukausi();
					kuukausi.setKuukausi_id(rs.getInt("kuukausi_id"));
					kuukausi.setKuukausinro(rs.getInt("kuukausinro"));
				}					
			}
			con.close();
		}			
		return kuukausi;
	}
	
	public ArrayList<Kuukausi> haeKuukaudet(String hakusana) throws Exception{		
		ArrayList<Kuukausi> kuukaudet = new ArrayList<Kuukausi>();		
		sql = "SELECT * FROM VJ_kuukaudet WHERE (kuukausi_id LIKE ? OR kuukausinro LIKE ?)"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setString(1, "%"+hakusana+"%");
			stmtPrep.setString(2, "%"+hakusana+"%");
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					Kuukausi kuukausi = new Kuukausi();
					kuukausi.setKuukausi_id(rs.getInt("kuukausi_id"));
					kuukausi.setKuukausinro(rs.getInt("kuukausinro"));
					kuukaudet.add(kuukausi);
				}					
			}
			con.close();
		}	
		return kuukaudet;
	}

}
