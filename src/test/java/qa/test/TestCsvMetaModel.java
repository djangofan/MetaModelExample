package qa.test;

import java.io.File;
import org.apache.metamodel.query.SelectItem;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.base.Joiner;

public class TestCsvMetaModel {
	
	private File input = new File("src/test/resources/Test.csv");
    private org.slf4j.Logger logger = LoggerFactory.getLogger( this.getClass().getSimpleName() );
	
	@BeforeTest
	private void setUp() {
		System.setProperty( SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
		logger.info("------------------------------");
	}

	@Test( dataProvider = "csv" )
	public void testCsv( SelectItem[] cols, Object[] data ) {
		String aRow = Joiner.on("|").join( data );
		logger.info( aRow );        	
	}

	@DataProvider( name = "csv" ) 
	public Object[][] gatherData() 
	{
		return Data.getCsvData( input );
	}
	
	@AfterTest
	private void cleanUp() {
		logger.info("------------------------------");
	}

}
