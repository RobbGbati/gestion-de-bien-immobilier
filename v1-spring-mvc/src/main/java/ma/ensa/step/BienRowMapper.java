package ma.ensa.step;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ma.ensa.model.Bien;

public class BienRowMapper  implements RowMapper<Bien> {
	boolean firstTime=true;
	@Override
	public Bien mapRow(ResultSet rs, int rowNum) throws SQLException {
		// 
		try {
		    if(firstTime==true) {
		        Thread.sleep(20000);
		        firstTime = false;
		    }
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
		Bien bien =new Bien();
		bien.setId(rs.getInt("id"));
		bien.setVille(rs.getString("ville"));
		bien.setMontant(rs.getInt("montant"));
		bien.setDescription(rs.getString("description"));
		bien.setStatus(rs.getString("status"));
		bien.setType_bien(rs.getString("type_bien"));
		bien.setType_offre(rs.getString("type_offre"));
		bien.setAnciennete(rs.getString("anciennete"));
		return bien;
	}

}
