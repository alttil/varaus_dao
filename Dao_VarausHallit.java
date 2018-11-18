package dao;

import java.util.ArrayList;
import model.Asiakas;
import model.Kentta;
import model.Tunti;
import model.Varaus;
import model.Varaushalli;
//import java.sql.Date;
//import java.time.LocalDate;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;



public class Dao_VarausHallit extends Dao {
	
	public ArrayList<Varaushalli> haeVaraushallit(String hakusana) throws Exception{		
		ArrayList<Varaushalli> varaushallit = new ArrayList<Varaushalli>();
		/*
		
		TUNNIN ALKUAIKA
		SELECT tu.alkuaika FROM VJ_varaus_hallit as vh, VJ_tunnit as tu ;
		where vh.tunti_id = tu.tunti_id ;
		
		ASIAKKAAN ETU-JA SUKUNIMI
		SELECT a.etunimi, a.sukunimi FROM VJ_varaus_hallit as vh, VJ_asiakkaat as a ;
		where vh.asiakas_id = a.asiakas_id ;
		
		HALLIN NIMI
		SELECT h.nimi FROM VJ_kentat as k, VJ_hallit as h ;
		where k.halli_id = h.halli_id ;
		
		PAIVAMAARA
		SELECT varaus_id, paiva FROM VJ_varaus_hallit ;
		
		 */
		//VJ_paivat, VJ_kuukaudet
		//sql+= "AND VJ_varaus_hallit.paiva_id=VJ_paivat.paiva_id ";
		//sql+= "AND VJ_varaus_hallit.kuukausi_id=VJ_kuukaudet.kuukausi_id ";
		
		sql = "SELECT * FROM VJ_varaus_hallit, VJ_tunnit, VJ_asiakkaat, VJ_kentat, VJ_hallit ";
		sql+= "WHERE VJ_varaus_hallit.tunti_id=VJ_tunnit.tunti_id ";
		sql+= "AND VJ_varaus_hallit.asiakas_id=VJ_asiakkaat.asiakas_id ";
		sql+= "AND VJ_varaus_hallit.kentta_id=VJ_kentat.kentta_id ";
		sql+= "AND VJ_kentat.halli_id=VJ_hallit.halli_id ";
		sql+= "AND (VJ_varaus_hallit.varaus_id LIKE ? OR VJ_tunnit.tunti_id LIKE ? OR VJ_asiakkaat.asiakas_id LIKE ? "
				+ "OR VJ_kentat.kentta_id LIKE ? OR VJ_varaus_hallit.paiva LIKE ? OR VJ_tunnit.alkuaika LIKE ? OR VJ_asiakkaat.etunimi LIKE ? "
				+ "OR VJ_asiakkaat.sukunimi LIKE ? OR VJ_asiakkaat.nimimerkki LIKE ? OR VJ_hallit.nimi LIKE ?)";
		
		//sql+= "AND (VJ_tunnit.alkuaika LIKE ? OR VJ_asiakkaat.etunimi LIKE ? OR VJ_asiakkaat.sukunimi LIKE ? OR VJ_hallit.nimi LIKE ?)";
		//sql = "SELECT * FROM VJ_varaus_hallit WHERE (varaus_id LIKE ? OR tunti_id LIKE ? OR asiakas_id LIKE ? OR kentta_id LIKE ? OR paiva LIKE ?)"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setString(1, "%"+hakusana+"%");
			stmtPrep.setString(2, "%"+hakusana+"%");
			stmtPrep.setString(3, "%"+hakusana+"%");
			stmtPrep.setString(4, "%"+hakusana+"%");
			stmtPrep.setString(5, "%"+hakusana+"%");
			stmtPrep.setString(6, "%"+hakusana+"%");
			stmtPrep.setString(7, "%"+hakusana+"%");
			stmtPrep.setString(8, "%"+hakusana+"%");
			stmtPrep.setString(9, "%"+hakusana+"%");
			stmtPrep.setString(10, "%"+hakusana+"%");
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					Varaushalli varaushalli = new Varaushalli();
					varaushalli.setVaraus_id(rs.getInt("varaus_id"));
					varaushalli.setTunti_id(rs.getInt("tunti_id"));
					varaushalli.setAsiakas_id(rs.getInt("asiakas_id"));
					varaushalli.setKentta_id(rs.getInt("kentta_id"));
					varaushalli.setPaiva(rs.getDate("paiva"));
					varaushalli.setAlkuaika(rs.getTime("alkuaika"));
					varaushalli.setLoppuaika(rs.getTime("loppuaika"));
					varaushalli.setEtunimi(rs.getString("etunimi"));
					varaushalli.setSukunimi(rs.getString("sukunimi"));
					varaushalli.setNimimerkki(rs.getString("nimimerkki"));
					varaushalli.setNimi(rs.getString("nimi"));
					//varaushalli.setPaiva_id(rs.getInt("paiva_id"));
					//varaushalli.setKuukausi_id(rs.getInt("kuukausi_id"));
					varaushallit.add(varaushalli);
				}					
			}
			con.close();
		}			
		return varaushallit;
	}
	
	public Varaushalli haeVaraushalli(int varaus_id) throws Exception{
		Varaushalli varaushalli=null;
		sql= "SELECT * FROM VJ_varaus_hallit, VJ_tunnit, VJ_asiakkaat, VJ_kentat ";
		sql+= "WHERE VJ_varaus_hallit.tunti_id=VJ_tunnit.tunti_id ";
		sql+= "AND VJ_varaus_hallit.asiakas_id=VJ_asiakkaat.asiakas_id ";
		sql+= "AND VJ_varaus_hallit.kentta_id=VJ_kentat.kentta_id ";
		sql+= "AND VJ_kentat.halli_id=VJ_hallit.halli_id ";
		sql+= "AND varaus_id=? ";

		//sql = "SELECT * FROM VJ_varaus_hallit WHERE varaus_id=?"; 		
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql); 
			stmtPrep.setInt(1, varaus_id);			
    		rs = stmtPrep.executeQuery();  
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					varaushalli = new Varaushalli();
					varaushalli.setVaraus_id(rs.getInt("varaus_id"));
					varaushalli.setTunti_id(rs.getInt("tunti_id"));
					varaushalli.setAsiakas_id(rs.getInt("asiakas_id"));
					varaushalli.setKentta_id(rs.getInt("kentta_id"));
					varaushalli.setPaiva(rs.getDate("paiva"));

				}					
			}
			con.close();
		}			
		return varaushalli;
	}
	
	public boolean lisaaVaraushalli(Varaushalli varaushalli){
		boolean paluuArvo=true;
		sql="INSERT INTO VJ_varaus_hallit(tunti_id, asiakas_id, kentta_id, paiva) VALUES(?,?,?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql);
			stmtPrep.setInt(1, varaushalli.getTunti_id());
			stmtPrep.setInt(2, varaushalli.getAsiakas_id());
			stmtPrep.setInt(3, varaushalli.getKentta_id());
			stmtPrep.setDate(4, new java.sql.Date(varaushalli.getPaiva().getTime()));
			
			//stmtPrep.setInt(4, varaushalli.getKentta_id());
			
			
			/*
			stmtPrep.setInt(1, varaushalli.getVaraus_id());
			stmtPrep.setInt(2, varaushalli.getTunti_id());
			stmtPrep.setInt(3, varaushalli.getAsiakas_id());
			stmtPrep.setInt(4, varaushalli.getKentta_id());
			*/
			//stmtPrep.setDate(6, new java.sql.Date(varaushalli.getPaiva().getTime()));
			//stmtPrep.setString(5, "1");
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	// https://stackoverflow.com/questions/530012/how-to-convert-java-util-date-to-java-sql-date

public Asiakas haeAsiakas(int varaus_id)throws Exception{		
	Asiakas asiakas = null;
	sql= "SELECT * FROM VJ_varaus_hallit, VJ_asiakkaat WHERE VJ_varaus_hallit.asiakas_id=VJ_asiakkaat.asiakas_id AND varaus_id=?";			
	con=yhdista();
	if(con!=null){ //jos yhteys onnistui
		stmtPrep = con.prepareStatement(sql); 
		stmtPrep.setInt(1, varaus_id);			
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
				asiakas.setSalasana(rs.getString("salasana"));
				asiakas.setSahkoposti(rs.getString("sahkoposti"));
			}					
		}
		con.close();
	}			
	return asiakas;
}

public Tunti haeTunti(int varaus_id)throws Exception{		
	Tunti tunti = null;
	sql= "SELECT * FROM VJ_varaus_hallit, VJ_tunnit WHERE VJ_varaus_hallit.tunti_id=VJ_tunnit.tunti_id AND varaus_id=?";			
	con=yhdista();
	if(con!=null){ //jos yhteys onnistui
		stmtPrep = con.prepareStatement(sql); 
		stmtPrep.setInt(1, varaus_id);			
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

public Kentta haeKentta(int varaus_id)throws Exception{		
	Kentta kentta = null;
	sql= "SELECT * FROM VJ_varaus_hallit, VJ_kentat WHERE VJ_varaus_hallit.kentta_id=VJ_kentat.kentta_id AND varaus_id=?";			
	con=yhdista();
	if(con!=null){ //jos yhteys onnistui
		stmtPrep = con.prepareStatement(sql); 
		stmtPrep.setInt(1, varaus_id);			
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

public boolean lisaaVaraus(Varaus varaus) throws Exception{
	boolean paluuArvo=true;		
	//tutkitaan ensin onko tunti+paiva+kentta yhdistelm� jo kannassa?	
	sql="SELECT * FROM VJ_varaus_hallit WHERE tunti_id = ? AND kentta_id = ? AND paiva = ?";
	con=yhdista();
	if(con!=null){ 
		stmtPrep = con.prepareStatement(sql);
		stmtPrep.setInt(1, varaus.getTunti_id());
		//stmtPrep.setInt(2, varaus.getAsiakas_id());
		stmtPrep.setInt(2, varaus.getKentta_id());
		stmtPrep.setDate(3, new java.sql.Date(varaus.getPaiva().getTime()));
		
		
		rs = stmtPrep.executeQuery();  
		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli varaus on k�yt�ss�
			paluuArvo=false; //merkki on jo kannassa    			 			       			
		}	
		con.close(); 
	}		
	if(paluuArvo==true){
		sql="INSERT INTO VJ_varaus_hallit(tunti_id, kentta_id, paiva) VALUES(?,?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql);
			stmtPrep.setInt(1, varaus.getTunti_id());
			//stmtPrep.setInt(2, varaus.getAsiakas_id());
			stmtPrep.setInt(2, varaus.getKentta_id());
			stmtPrep.setDate(3, new java.sql.Date(varaus.getPaiva().getTime()));
			stmtPrep.executeUpdate();
			
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
	}		
	return paluuArvo;
	
}
}

