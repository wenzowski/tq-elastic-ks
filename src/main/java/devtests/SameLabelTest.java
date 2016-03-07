/**
 * 
 */
package devtests;

import org.topicquests.common.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.TicketPojo;
import org.topicquests.ks.api.ITQCoreOntology;
import org.topicquests.ks.api.ITQDataProvider;
import org.topicquests.ks.api.ITicket;
import org.topicquests.ks.tm.api.ISubjectProxy;
import org.topicquests.ks.tm.api.ISubjectProxyModel;

/**
 * @author Admin
 *
 */
public class SameLabelTest {
	private SystemEnvironment environment;
	private ITQDataProvider database;
	private ITicket credentials;
	private ISubjectProxyModel proxyModel;

	/**
	 * 
	 */
	public SameLabelTest() {
		environment = new SystemEnvironment();
		database = environment.getDatabase();
		credentials = new TicketPojo(ITQCoreOntology.SYSTEM_USER);
		proxyModel = database.getSubjectProxyModel();
		runTest();
	}

	void runTest() {
		ISubjectProxy n = proxyModel
				.newInstanceNode("136", "ClassType", "My first node", "Hi there", "en", "jackpark", "smallImagePath.png", "largeImagePath.png", false);
		System.out.println("AAA "+n.toJSONString());
		IResult r = database.putNode(n);
		ISubjectProxy n1 = proxyModel
				.newInstanceNode("137", "ClassType", "My second node", "Hi there", "en", "jackpark", "smallImagePath.png", "largeImagePath.png", false);
		System.out.println("BBB "+n1.toJSONString());
		r = database.putNode(n1);
		ISubjectProxy n3 = proxyModel
				.newInstanceNode("138", "ClassType", "My first node", "Hi there", "en", "jackpark", "smallImagePath.png", "largeImagePath.png", false);
		System.out.println("CCC "+n3.toJSONString());
		r = database.putNode(n3);
		environment.shutDown();
		System.exit(0);
	}
	
	//{"my first node":["136","138"],"my second node":["137"]}
}
