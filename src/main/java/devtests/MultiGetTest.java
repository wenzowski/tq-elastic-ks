/**
 * 
 */
package devtests;

import java.util.*;
import org.topicquests.support.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.api.ITQDataProvider;
import org.topicquests.node.provider.Client;

/**
 * @author park
 *
 */
public class MultiGetTest {
	private SystemEnvironment environment;
	private ITQDataProvider database;
	/**
	 * 
	 */
	public MultiGetTest() {
		environment = new SystemEnvironment();
		database = environment.getDatabase();
		Client client = environment.getProvider().getClient();
		IResult r = client.getNodeAsJSONObject("TypeType", "topics");
		System.out.println("A "+r.getErrorString()+" | "+r.getResultObject());
		List<String>locs = new ArrayList<String>();
		locs.add("TypeType");
		locs.add("ClassType");
		locs.add("NodeType");
		r = client.multiGetNodes(locs, "topics");
		System.out.println("B"+r.getErrorString()+" | "+r.getResultObject());
		environment.shutDown();
	}
//	B | [
	//{"crDt":"2015-12-07T21:39:55-08:00","crtr":"SystemUser","lox":"TypeType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"_ver":"1449553195215","lEdDt":"2015-12-07T21:39:55-08:00","details":"Topic Map root type","label":"Type type","lIco":"\/images\/cogwheel.png","isFdrtd":false}, 
	//{"crtr":"SystemUser","_ver":"1449553198216","lEdDt":"2015-12-07T21:39:58-08:00","label":"Class type","isFdrtd":false,"trCl":["TypeType"],"crDt":"2015-12-07T21:39:58-08:00","sbOf":"TypeType","lox":"ClassType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Class type","lIco":"\/images\/cogwheel.png"}, 
	//{"crtr":"SystemUser","_ver":"1448384289926","lEdDt":"2015-11-24T08:58:09-08:00","label":"ClassType","isFdrtd":false,"trCl":["TypeType","ClassType"],"crDt":"2015-11-24T08:58:09-08:00","sbOf":"ClassType","lox":"NodeType","sIco":"\/images\/cogwheel_sm.png","isPrv":false,"details":"Topic Map upper Node type","lIco":"\/images\/cogwheel.png"}]

}
