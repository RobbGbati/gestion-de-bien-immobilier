package ma.ensa.step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Sort;

import ma.ensa.model.Bien;
import ma.ensa.repository.BienRepository;

/**
 * {@link ItemReader} with hard-coded input data.
 */
//@Component
//@Scope(value = "step")
public class BienItemReader /*implements ItemReader<Bien>*/ {
   /*
	@Autowired
	private DataSource dataSource;
	
	@Value("#{jobParameters['ville']}")
	private String ville;
	
	@Value("#{jobParameters['client']}")
	private String client;
	
	@Value("#{jobParameters['montant']}")
	private String montant;
	
	@Override
	public JdbcCursorItemReader<Bien> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		JdbcCursorItemReader<Bien> reader = new JdbcCursorItemReader<Bien>();
		String sql = "select * from Bien where ville = "+ville+" and montant ='"+montant+"'";
		
		reader.setSql(sql);
		reader.setDataSource(dataSource);
		reader.setRowMapper(rowMapper());	

		return reader;
	}
	
	@Bean
	public BienRowMapper rowMapper(){
		return new BienRowMapper();
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getMontant() {
		return montant;
	}

	public void setMontant(String montant) {
		this.montant = montant;
	}
	
	*/
	
}