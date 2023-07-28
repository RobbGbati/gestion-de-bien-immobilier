package ma.ensa.step;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


import ma.ensa.model.Bien;

/**
 * Dummy {@link ItemProcessor} which only logs data it receives.
 */
public class BienItemProcessor implements ItemProcessor<Bien, Bien> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BienItemProcessor.class);

	@Override
	public Bien process(Bien item) throws Exception {
		LOGGER.info(" " +item);
		return item;
	}
}
	