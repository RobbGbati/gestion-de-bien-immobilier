package ma.ensa.step;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import ma.ensa.model.Bien;
import ma.ensa.service.EmailService;

/**
 * Dummy {@link ItemWriter} which only logs data it receives.
 */
public class BienItemWriter implements ItemWriter<Bien> {
  
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BienItemWriter.class);
	
	@Autowired 
	EmailService emailservice;
    
	private String email;
	
	public BienItemWriter(String email) {
		this.email=email;
	}
	
	/**
	 * @see ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List<? extends Bien> data) throws Exception {
		LOGGER.info("{}", data);
		String msg=" ";
		for(Bien item:data) {
			System.out.println("Numéro : "+item.getId()+ "\n "
					+ "Montant: "+ item.getMontant()+ "\n Ville: "+item.getVille());
			msg= msg+"\n ***Produit: "+item.getId()+"***\n *Montant: "+item.getMontant()+"\n *Ville:"
					+ " "+item.getVille()+"\n *Description: "+item.getDescription()+"\n *Status: "+item.getStatus()+""
							+ "\n *Type Offre :"+item.getType_offre()+"\n *Type Bien: "+item.getType_bien()+"\n"
									+ " *Anciennete: "+item.getAnciennete()+"\n ******** ";
		}
	
		int i=emailservice.sendMessage(this.email,msg);
		if(i==1) {
			System.out.println("Message envoyé");
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
}
