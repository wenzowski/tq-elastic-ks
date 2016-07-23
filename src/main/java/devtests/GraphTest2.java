/**
 * 
 */
package devtests;

import org.topicquests.common.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.graph.GraphEnvironment;
import org.topicquests.ks.graph.GraphVertex;
import org.topicquests.ks.graph.api.IGraphOntology;
import org.topicquests.ks.graph.api.IGraphProvider;
import org.topicquests.ks.graph.api.IVertex;

/**
 * @author park
 *
 */
public class GraphTest2 {
	private SystemEnvironment environment;
	private GraphEnvironment graphEnvironment;
	private IGraphProvider graphDatabase;
	private final String
		VID_1 = "VAx7",
		VID_2 = "VAx8",
		VID_3 = "VAX9",
		EID	  = "EDGx5",
		EID2  = "EDGx6";

	/**
	 * 
	 */
	public GraphTest2() {
		environment = new SystemEnvironment();
		graphEnvironment = environment.getGraphEnvironment();
		graphDatabase = graphEnvironment.getDatabase();
		
		runTest();
		environment.shutDown();
		System.exit(0);
	}

	void runTest() {
		IVertex v = graphDatabase.newVertex();
		v.setId(VID_1);
		v.setLabel("First Vertex");
		v.setVersion(Long.toString(System.currentTimeMillis()));
		IResult r = graphDatabase.addVertex(v);
		System.out.println("AAA "+r.getErrorString()+" "+r.getResultObject());
		IVertex v2 = graphDatabase.newVertex();
		v2.setId(VID_2);
		v2.setLabel("Second Vertex");
		v2.setVersion(Long.toString(System.currentTimeMillis()));
		r = graphDatabase.addVertex(v2);
		System.out.println("BBB "+r.getErrorString()+" "+r.getResultObject());
		r = graphDatabase.addEdge(EID, v2, v, "My Edge");
		System.out.println("CCC "+r.getErrorString()+" "+r.getResultObject());
		v2 = graphDatabase.newVertex();
		v2.setId(VID_3);
		v2.setLabel("Third Vertex");
		v2.setVersion(Long.toString(System.currentTimeMillis()));
		r = graphDatabase.addVertex(v2);
		r = graphDatabase.addEdge(EID2, v2, v, "My Other Edge");
		System.out.println("DDD "+r.getErrorString()+" "+r.getResultObject());
		r = graphDatabase.getVertex(VID_1, true);
		v = (IVertex)r.getResultObject();
		r = v.listAdjacentVertices(IGraphOntology.DIRECTION_OUT, graphDatabase, null);
		System.out.println("FFF "+r.getErrorString()+" "+r.getResultObject());
		//FFF  [org.topicquests.hyperbrane.graph.GraphVertex@641fa9ab, 
		// org.topicquests.hyperbrane.graph.GraphVertex@5abc4ce7]

	}
	
	
}
