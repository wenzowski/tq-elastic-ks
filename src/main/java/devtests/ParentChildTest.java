/**
 * 
 */
package devtests;

import org.topicquests.support.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.TicketPojo;
import org.topicquests.ks.api.ITQDataProvider;
import org.topicquests.ks.api.ITicket;
import org.topicquests.ks.tm.api.ISubjectProxy;
import org.topicquests.ks.tm.api.ISubjectProxyModel;

/**
 * @author park
 *
 */
public class ParentChildTest {
	private SystemEnvironment environment;
	private ITQDataProvider database;
	private ISubjectProxyModel proxyModel;
	private ITicket credentials;
	private static String
		USERID = "SystemUser",
		N1 	= "TreeNode1",
		N2 	= "TreeNode2",
		N3 	= "TreeNode3",
		N4 	= "TreeNode1",
		N5 	= "TreeNode1";
	/**
	 * 
	 */
	public ParentChildTest() {
		environment = new SystemEnvironment();
		database = environment.getDatabase();
		proxyModel = database.getSubjectProxyModel();
		credentials = new TicketPojo();
		credentials.setUserLocator(USERID);
		N1 = "N1"+System.currentTimeMillis();
		N2 = "N2"+System.currentTimeMillis();
		N3 = "N3"+System.currentTimeMillis();
		N4 = "N4"+System.currentTimeMillis();
		N5 = "N5"+System.currentTimeMillis();
		runTest();
		environment.shutDown();
	}
	
	//////////////////////////////////
	//Build 5 nodes
	// One root N1
	//   One child
	//     Two children
	// Second Root
	//   One child from the first tree
	// Dump them to validate parentchild stuff
	// do a multifetch based on the root's list
	// do a multifetch based on first child's list
	//////////////////////////////////
	private void runTest() {
		System.out.println("BEGIN TEST");
		IResult r;
		ISubjectProxy r1, p1, p2, p3, r2;
		//build some proxies
		r1 = proxyModel.newNode(N1, "Root1", "", "en", USERID, "", "", false);
		r = database.putNode(r1);
		System.out.println("A "+r.getErrorString());
		p1 = proxyModel.newNode(N2, "Child1", "", "en", USERID, "", "", false);
		r = database.putNode(p1);
		System.out.println("B "+r.getErrorString());
		p2 = proxyModel.newNode(N3, "Child2", "", "en", USERID, "", "", false);
		r = database.putNode(p2);
		System.out.println("C "+r.getErrorString());
		p3 = proxyModel.newNode(N4, "Child3", "", "en", USERID, "", "", false);
		r = database.putNode(p3);
		System.out.println("D "+r.getErrorString());
		r2 = proxyModel.newNode(N5, "Root2", "", "en", USERID, "", "", false);
		r = database.putNode(r2);
		System.out.println("E "+r.getErrorString());
		//wire them into a tree
		//proxy, contextLocator, smallIcon, childLocator, subject, transcLocator
		//ADD N2 to N1
		r = proxyModel.addChildNode(r1, N1, "", N2, p1.getLabel("en"), null);
		System.out.println("F "+r.getErrorString());
		r = proxyModel.addChildNode(p1, N1, "", N3, p2.getLabel("en"), null);
		System.out.println("G "+r.getErrorString());
		r = proxyModel.addChildNode(p1, N1, "", N4, p3.getLabel("en"), null);
		System.out.println("H "+r.getErrorString());
		r = proxyModel.addChildNode(r2, N5, "", N4, p3.getLabel("en"), null);
		System.out.println("I "+r.getErrorString());
		//Display them  these must be fetched again
		r = database.getNode(r1.getLocator(), credentials);
		r1 = (ISubjectProxy)r.getResultObject();
		r = database.getNode(r2.getLocator(), credentials);
		r2 = (ISubjectProxy)r.getResultObject();
		System.out.println("R1 "+r1.toJSONString());
		r = database.getNode(p1.getLocator(), credentials);
		p1 = (ISubjectProxy)r.getResultObject();
		System.out.println("BB "+p1.toJSONString());
		System.out.println("CC "+p2.toJSONString());
		System.out.println("DD "+p3.toJSONString());
		System.out.println("R2 "+r2.toJSONString());
	}
}


/**
R1
{
	"cNL": [{
		"contextLocator": "N11454641138035",
		"smallImagePath": "",
		"subject": "Child1",
		"locator": "N21454641138035"
	}],
	"crDt": "2016-02-04T18:58:58-08:00",
	"crtr": "SystemUser",
	"lox": "N11454641138035",
	"sIco": "",
	"isPrv": false,
	"ptCdl": ["N21454641138035", "N31454641138035", "N41454641138035"],
	"_ver": "1454641138497",
	"lEdDt": "2016-02-04T18:58:58-08:00",
	"details": [""],
	"label": ["Root1"],
	"lIco": ""
}
BB 
{
	"cNL": [{
		"contextLocator": "N11454641138035",
		"smallImagePath": "",
		"subject": "Child2",
		"locator": "N31454641138035"
	}, {
		"contextLocator": "N11454641138035",
		"smallImagePath": "",
		"subject": "Child3",
		"locator": "N41454641138035"
	}],
	"crDt": "2016-02-04T18:58:58-08:00",
	"crtr": "SystemUser",
	"lox": "N21454641138035",
	"sIco": "",
	"isPrv": false,
	"_ver": "1454641138493",
	"lEdDt": "2016-02-04T18:58:58-08:00",
	"details": [""],
	"label": ["Child1"],
	"lIco": ""
}
R2 
{
	"cNL": [{
		"contextLocator": "N51454641138035",
		"smallImagePath": "",
		"subject": "Child3",
		"locator": "N41454641138035"
	}],
	"crDt": "2016-02-04T18:58:58-08:00",
	"crtr": "SystemUser",
	"lox": "N51454641138035",
	"sIco": "",
	"isPrv": false,
	"ptCdl": ["N41454641138035"],
	"_ver": "1454641138506",
	"lEdDt": "2016-02-04T18:58:58-08:00",
	"details": [""],
	"label": ["Root2"],
	"lIco": ""
}


 */
