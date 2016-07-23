/**
 * 
 */
package devtests;

import java.util.*;
import java.util.HashMap;
import org.topicquests.common.api.IResult;
import org.topicquests.ks.SystemEnvironment;
import org.topicquests.ks.graph.GraphEnvironment;
import org.topicquests.ks.graph.GraphVertex;
import org.topicquests.ks.graph.api.IEdge;
import org.topicquests.ks.graph.api.IGraphOntology;
import org.topicquests.ks.graph.api.IGraphProvider;
import org.topicquests.ks.graph.api.IVertex;

/**
 * @author park
 *
 */
public class FirstGraphTest {
	private SystemEnvironment environment;
	private GraphEnvironment graphEnvironment;
	private IGraphProvider graphDatabase;
	private final String
		VID_1 = "VA13",
		VID_2 = "VA14",
		EID	  = "EDG7";

	/**
	 * 
	 */
	public FirstGraphTest() {
		environment = new SystemEnvironment();
		graphEnvironment = environment.getGraphEnvironment();
		graphDatabase = graphEnvironment.getDatabase();
		
		runTest();
		runTest2();
		runTest3();
		environment.shutDown();
		System.exit(0);
	}
	
	void runTest3() {
		IResult r = graphDatabase.getVertex(VID_1, true);
		IVertex v3 = (IVertex)r.getResultObject();
		System.out.println("# "+v3.toJSONString());
		Iterator<IEdge>itr = v3.listEdges(IGraphOntology.DIRECTION_OUT, null);
		while (itr.hasNext())
			System.out.println("XXX "+itr.next().toJSONString());
	}

	void runTest2() {
		IResult r = graphDatabase.getVertex(VID_1, true);
		IVertex v3 = (IVertex)r.getResultObject();
		System.out.println("KKK "+r.getErrorString()+" "+r.getResultObject());
		if (v3 != null)
			System.out.println("MMM"+v3.toJSONString());
		r = graphDatabase.getVertex(VID_2, true);
		v3 = (IVertex)r.getResultObject();
		System.out.println("NNN "+r.getErrorString()+" "+r.getResultObject());
		if (v3 != null)
			System.out.println("PPP"+v3.toJSONString());
		//environment.shutDown();
		//System.exit(0);
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
		System.out.println("CCC "+r.getErrorString()); //+" "+r.getResultObject());
		r = graphDatabase.getVertex(VID_1, true);
		IVertex v3 = (IVertex)r.getResultObject();
		System.out.println("DDD "+r.getErrorString()+" "+r.getResultObject());
		if (v3 != null) {
			//System.out.println("EEE"+v3.toJSONString());
			r = v3.listAdjacentVertices(IGraphOntology.DIRECTION_IN, graphDatabase, null);
			System.out.println("EEE1 "+r.getErrorString()+" "+r.getResultObject());
			r = v3.listAdjacentVertices(IGraphOntology.DIRECTION_OUT, graphDatabase, null);
			System.out.println("EEE2 "+r.getErrorString()+" "+r.getResultObject());
		}


		r = graphDatabase.getVertex(VID_2, true);
		v3 = (IVertex)r.getResultObject();
		System.out.println("EEE3 "+v3.toJSONString());
	/*	if (v3 != null) {
			System.out.println("FOO "+v3.getId()+" "+v3.getData().size());
			Iterator<String>itr = v3.listPropertyKeys();
			String key;
			while (itr.hasNext()) {
				key = itr.next();
				System.out.print("FFF "+key+" ");
				System.out.println(((Map<String,Object>)v3.getData()).get(key).toString());
			}
		}*/
		//FFF{"id":"VA14","vers":"1447022081943","InEdges":
		//[{"data":{"InVert":{"data":{"id":"VA13","outEdgeIdList":["EDG7"],
		//"vers":"1447022081943","label":"First Vertex"}},"id":"EDG7",
		//"vers":"1447022081943","label":"My Edge",
		//"OutVert":{"data":{"id":"VA14","vers":"1447022081943",
		//"label":"Second Vertex","inEdgeIdList":["EDG7"]}},
		//"InVertexId":"VA13","OutVertexId":"VA14"}}],
		//"label":"Second Vertex","inEdgeIdList":["EDG7"]}


		r = graphDatabase.getEdge(EID);
		System.out.println("GGG "+r.getErrorString()+" "+r.getResultObject());
		IEdge ex = (IEdge)r.getResultObject();
		if (ex != null)
			System.out.println("HHH"+ex.toJSONString());
		//HHH{"InVert":{"data":{"id":"VA13","outEdgeIdList":["EDG7"],
		//"vers":"1447022081943","label":"First Vertex"}},"id":"EDG7",
		//"vers":"1447022081943","label":"My Edge","OutVert":{"data":{"id":"VA14","vers":"1447022081943","label":"Second Vertex","inEdgeIdList":["EDG7"]}},"InVertexId":"VA13","OutVertexId":"VA14"}
		//environment.shutDown();
		//System.exit(0);
	}
}
