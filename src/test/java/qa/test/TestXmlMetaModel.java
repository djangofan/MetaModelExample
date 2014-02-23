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

public class TestXmlMetaModel {
	
	File xmlFile = new File("src/test/resources/Test.xml");
	private org.slf4j.Logger logger = LoggerFactory.getLogger( this.getClass().getSimpleName() );
	
	@BeforeTest
	private void setUp() {
		System.setProperty( SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
		logger.info("------------------------------");
	}

	@Test( dataProvider = "xml" )
	public void testXML( SelectItem[] cols, Object[] data ) {
		String aRow = Joiner.on("|").join( data );
		logger.info( aRow ); 		
	}
	
	@DataProvider( name = "xml" ) 
	public Object[][] gatherCsvData() 
	{
		return Data.getXmlData( xmlFile );
	}
	
	@AfterTest
	private void cleanUp() {
		logger.info("------------------------------");
	}

}
