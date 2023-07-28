package ma.ensa.config;


import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.listener.JobParameterExecutionContextCopyListener;

import ma.ensa.listener.JobListener;
import ma.ensa.model.Bien;
import ma.ensa.step.BienItemProcessor;
import ma.ensa.step.BienItemWriter;
import ma.ensa.step.BienRowMapper;


@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	private String email;
	
	public BatchConfig() {
		this.email="user@gmail.com";
	}
    
	@Bean
	public Job job2(){
		return jobBuilderFactory.get("job2")
				.start(step4())
				.build();
			}
	
	@Bean
	public Step step4(){
		return stepBuilderFactory.get("step4")
				 .<Bien, Bien> chunk(200)
				.reader(reader(null,null,null))
				.processor(processor2())
				.writer(writer2(null))
				 .listener(new JobParameterExecutionContextCopyListener())
				.build();
	}

	@Bean
	@StepScope
	public BienItemWriter writer2(@Value("#{jobParameters['client']}") String client) {
		return  new BienItemWriter(client);
	}

	@Bean
	public BienItemProcessor processor2() {
		return new BienItemProcessor();
	}
	

	@Bean
	@StepScope
	public ItemReader<Bien> reader(@Value("#{jobParameters['ville']}")String ville,
			@Value("#{jobParameters['montant']}") Long montant,
			@Value("#{jobParameters['client']}") String client){
		Bien b = new Bien();
		b.setVille(ville);
		b.setMontant(montant);
		JdbcCursorItemReader<Bien> reader = new JdbcCursorItemReader<Bien>();
		String sql = "select * from BIEN where VILLE = '"+b.getVille()+"' or MONTANT ="+b.getMontant()+"";
		
		reader.setSql(sql);
		reader.setDataSource(dataSource);
		reader.setRowMapper(rowMapper());	
	    
		System.out.println(" "+ville+" "+montant+ client);
		this.setEmail(client);
		reader.open(new ExecutionContext());
		
		
		return reader;
	}
	
	@Bean
	public BienRowMapper rowMapper(){
		return new BienRowMapper();
	}
	
	@Bean
    public JobListener listener() {
        return new JobListener("user@gmail.com","user@gmail.com");
    }
   
	
	public String getEmail() {
		return this.email;
	}
     
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	


}


