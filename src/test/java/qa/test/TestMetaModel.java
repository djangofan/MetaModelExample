package qa.test;

import java.io.File;
import java.util.List;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.DataContextFactory;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.excel.ExcelConfiguration;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.base.Joiner;

public class TestMetaModel {
	
	private File input = new File("src/test/resources/Test.xls");
    private org.slf4j.Logger logger = LoggerFactory.getLogger( this.getClass().getSimpleName() );
	
	@BeforeTest
	private void setUp() {
		System.setProperty( SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
		logger.info("------------------------------");
	}

	@Test( dataProvider = "dp" )
	public void testMethod( Row testRow ) {
		Object[] rowVals = testRow.getValues();
		String aRow = Joiner.on("'").join( rowVals );
		logger.info( aRow );        	
	}

	@DataProvider( name = "dp" ) 
	public Object[][] gatherData() 
	{		
		logger.info("#################################");
		logger.info("## Data Provider: dp           ##");
		ExcelConfiguration conf = new ExcelConfiguration();
		conf.isSkipEmptyLines();
		DataContext dataContext = DataContextFactory.createExcelDataContext( input, conf );
		DataSet dataSet = dataContext.query()
				.from("Sheet1")
				.select("testCaseName", "url", "role")
				.where("runRow").eq("Y")
				.and("role").eq("member")
				.execute();
		List<Row> rows = dataSet.toRows();
		Object[][] myArray = new Object[rows.size()][1];
		int i = 0;
		for ( Row r : rows ) {
			myArray[i][0] = r;
			i++;
		}
		logger.info("#################################");
		return myArray;
	}
	
	@AfterTest
	private void cleanUp() {
		logger.info("------------------------------");
	}

}
