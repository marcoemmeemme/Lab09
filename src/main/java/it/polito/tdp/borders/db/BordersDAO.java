package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries(Map<Integer, Country> idMap) {
		List<Country> result=new ArrayList<>();
		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";	
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Country c=new Country(rs.getString("StateAbb"),rs.getInt("CCode"),rs.getString("StateNme"));
				idMap.put(c.getCCode(), c);
				result.add(c);
			}
			
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Country> loadAllCountriesPerYear(Map<Integer, Country> idMap, int anno)
	{
		List<Country> result=new ArrayList<>();
		final String sql="SELECT cou.CCode, cou.StateAbb, cou.StateNme, COUNT(con.state2no) AS conta\r\n" + 
				"FROM contiguity con, country cou\r\n" + 
				"WHERE con.year<=?\r\n" + 
				"AND conttype=1\r\n" + 
				"AND cou.CCode=con.state1no\r\n" + 
				"GROUP BY con.state1no;\r\n";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st;
			st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			while (rs.next()) 
			{
				Country c=new Country(rs.getString("StateAbb"),rs.getInt("CCode"),rs.getString("StateNme"));
				c.setStatiConfinanti(rs.getInt("conta"));
				idMap.put(c.getCCode(), c);
				result.add(c);
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

	}
	
	public List<Border> getCountryPairs(Map<Integer, Country> idMap,int anno) 
	{
		final String sql="SELECT dyad, state1no, state2no, conttype, year\r\n" + 
						 "FROM contiguity\r\n" + 
				   	 	 "WHERE YEAR<=?\r\n" + 
				 		 "AND conttype=1\r\n" + 
						 "AND state1no<state2no";
		List<Border> result=new ArrayList<>();	
		try 
		{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st;
			st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			while (rs.next()) 
			{
				Country c1=idMap.get(rs.getInt("state1no"));
				Country c2=idMap.get(rs.getInt("state2no"));
				Border b=new Border(rs.getInt("dyad"),c1,c2,rs.getInt("year"),rs.getInt("conttype"));
				result.add(b);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
}
