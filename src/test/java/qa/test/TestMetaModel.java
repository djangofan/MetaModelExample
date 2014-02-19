package qa.test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.DataContextFactory;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.excel.ExcelConfiguration;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestMetaModel {
	
	public static File input = new File("src/test/resources/Test.xls");
	private static Logger logger = LogManager.getLogger( "TestMetaModel" );

	@Test( dataProvider = "dp" )
	public void testMethod( Row testRow ) {
		Object[] rowVals = testRow.getValues();
        for ( int i = 0; i < rowVals.length; i++ ) {
        	System.out.print( rowVals[i] + ", " );        	
        }
        System.out.println();
	}

	@DataProvider( name = "dp" ) 
	public Object[][] gatherData() 
	{		
		logger.info("Running dataprovider.");
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
		System.out.println("Size: " + rows.size() );
		Object[][] myArray = new Object[rows.size()][1];
		int i = 0;
		for ( Row r : rows ) {
			System.out.println( Arrays.deepToString( r.getValues() ) );
			myArray[i][0] = r;
			i++;
		}
		System.out.println( Arrays.deepToString( myArray ) );
		return myArray;
	} 

}
