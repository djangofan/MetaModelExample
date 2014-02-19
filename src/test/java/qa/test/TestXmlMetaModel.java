package qa.test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.query.Query;
import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.Table;
import org.apache.metamodel.xml.XmlSaxDataContext;
import org.apache.metamodel.xml.XmlSaxTableDef;
import org.testng.annotations.Test;

public class TestXmlMetaModel {
	
	File xmlFile = new File("src/test/resources/Test.xml");
	
	// http://metamodel.eobjects.org/example_xml_mapping.html
	
	@Test
	public void testXML() {
		
		XmlSaxTableDef employeeOrgTableDef = new XmlSaxTableDef(
		        "/root/organization/employees/employee",
		        new String[] {
		                "/root/organization/employees/employee/name",
		                "/root/organization/employees/employee/gender",
		                "index(/root/organization)"
		        }
		);
		
		XmlSaxTableDef employeeTableDef = new XmlSaxTableDef(
		        "/root/organization/employees/employee",
		        new String[] {
		                "/root/organization/employees/employee/name",
		                "/root/organization/employees/employee/gender"
		        }
		);

		XmlSaxTableDef organizationTableDef = new XmlSaxTableDef(
		        "/root/organization",
		        new String[] {
		                "/root/organization/name",
		                "/root/organization@type"
		        }
		);

		DataContext dc = new XmlSaxDataContext( xmlFile, employeeOrgTableDef, employeeTableDef, organizationTableDef );
		
		Table employeeTable = dc.getTableByQualifiedLabel( "company" );
		Table organizationTable = dc.getTableByQualifiedLabel( "governmental" );

		Column fk = employeeTable.getColumnByName("index(/root/organization)");
		Column empName = employeeTable.getColumnByName("/name");
		Column orgId = organizationTable.getColumnByName("row_id");
		Column orgName = organizationTable.getColumnByName("/name");

		Query q = dc.query().from(employeeTable)
		        .innerJoin(organizationTable).on(fk, orgId)
		        .select(empName).as("employee")
		        .select(orgName).as("company").toQuery();
		DataSet ds = dc.executeQuery(q);
		
		List<Row> rows = ds.toRows();
		for ( Row r : rows ) {
			System.out.println( Arrays.deepToString( r.getValues() ) );
		}		
		
	}

}
