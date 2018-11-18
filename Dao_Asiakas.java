package dao;

import java.util.ArrayList;

import model.Asiakas;

public class Dao_Asiakas extends Dao {
	
	public boolean lisaaAsiakas(Asiakas asiakas){
		boolean paluuArvo=true;
		sql="INSERT INTO VJ_asiakkaat(etunimi, sukunimi, nimimerkki, puhelin, osoite, salasana, sahkoposti) VALUES(?,?,?,?,?,?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, asiakas.getEtunimi());
			stmtPrep.setString(2, asiakas.getSukunimi());
			stmtPrep.setString(3, asiakas.getNimimerkki());
			stmtPrep.setString(4, asiakas.getPuhelin());
			stmtPrep.setString(5, asiakas.getOsoite());
			stmtPrep.setString(6, asiakas.getSalasana());
			stmtPrep.setString(7, asiakas.getSahkoposti());
			//stmtPrep.setString(7, "1");
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public boolean muutaAsiakas(Asiakas asiakas){
		boolean paluuArvo=true;
		sql="UPDATE VJ_asiakkaat SET etunimi=?, sukunimi=?, nimimerkki=?, puhelin=?, osoite=?, salasana=?, sahkoposti=? WHERE asiakas_id=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, asiakas.getEtunimi());
			stmtPrep.setString(2, asiakas.getSukunimi());
			stmtPrep.setString(3, asiakas.getNimimerkki());
			stmtPrep.setString(4, asiakas.getPuhelin());
			stmtPrep.setString(5, asiakas.getOsoite());
			stmtPrep.setString(6, asiakas.getSalasana());
			stmtPrep.setString(7, asiakas.getSahkoposti());
			stmtPrep.setInt(8, asiakas.getAsiakas_id());
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public ArrayList<Asiakas> haeAsiakkaat(String hakusana) throws Exception{		
		ArrayList<Asiakas> asiakkaat = new ArrayList<Asiakas>();
		sql = "SELECT * FROM VJ_asiakkaat WHERE (etunimi LIKE ? OR sukunimi LIKE ? OR nimimerkki LIKE ?) AND poistettu=0"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setString(1, "%"+hakusana+"%");
			stmtPrep.setString(2, "%"+hakusana+"%");
			stmtPrep.setString(3, "%"+hakusana+"%");
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					Asiakas asiakas = new Asiakas();
					asiakas.setAsiakas_id(rs.getInt("asiakas_id"));
					asiakas.setEtunimi(rs.getString("etunimi"));
					asiakas.setSukunimi(rs.getString("sukunimi"));
					asiakas.setNimimerkki(rs.getString("nimimerkki"));
					asiakas.setPuhelin(rs.getString("puhelin"));
					asiakas.setOsoite(rs.getString("osoite"));
					asiakas.setSahkoposti(rs.getString("sahkoposti"));
					asiakkaat.add(asiakas);
				}					
			}
			con.close();
		}			
		return asiakkaat;
	}
	
	public Asiakas haeAsiakas(int asiakas_id) throws Exception{
		Asiakas asiakas=null;		
		sql = "SELECT * FROM VJ_asiakkaat WHERE asiakas_id=?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setInt(1, asiakas_id);			
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					asiakas = new Asiakas();
					asiakas.setAsiakas_id(rs.getInt("asiakas_id"));
					asiakas.setEtunimi(rs.getString("etunimi"));
					asiakas.setSukunimi(rs.getString("sukunimi"));
					asiakas.setNimimerkki(rs.getString("nimimerkki"));
					asiakas.setPuhelin(rs.getString("puhelin"));
					asiakas.setOsoite(rs.getString("osoite"));
					asiakas.setSahkoposti(rs.getString("sahkoposti"));
					asiakas.setSalasana(rs.getString("salasana"));
				}					
			}
			con.close();
		}			
		return asiakas;
	}
	
	public void poistaAsiakas(int Asiakas_id) throws Exception{		
		sql = "UPDATE VJ_asiakkaat SET poistettu=1 WHERE asiakas_id=?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setInt(1, Asiakas_id);			
			stmtPrep.executeUpdate();			
			con.close();
		}		
	}
}













