/**
 * 
 */
package devtests;

import java.util.*;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

/**
 * @author jackpark
 *
 */
public class JSONTest {

	/**
	 * 
	 */
	public JSONTest() {
		String json = "{\"inEdgeIdList\":[\"EDG7\"],\"vers\":\"1469229720907\",\"id\":\"VA14\",\"label\":\"Second Vertex\",\"eindx\":\"edges\",\"vindx\":\"vertices\"}";
		String json2 = "{\"OutVertexId\":\"VA14\",\"vers\":\"1469230374115\",\"InVertexId\":\"VA13\",\"label\":\"My Edge\",\"id\":\"EDG7\",\"eindx\":\"edges\",\"InVert\":{\"vers\":\"1469230374116\",\"outEdgeIdList\":[\"EDG7\"],\"id\":\"VA13\",\"label\":\"First Vertex\",\"OutEdges\":[{\"OutVert\":{\"inEdgeIdList\":[\"EDG7\"],\"vers\":\"1469230374116\",\"id\":\"VA14\",\"label\":\"Second Vertex\",\"eindx\":\"edges\",\"vindx\":\"vertices\"},\"OutVertexId\":\"VA14\",\"vers\":\"1469230374115\",\"InVertexId\":\"VA13\",\"label\":\"My Edge\",\"id\":\"EDG7\",\"eindx\":\"edges\",\"vindx\":\"vertices\"}],\"eindx\":\"edges\",\"vindx\":\"vertices\"},\"vindx\":\"vertices\"}";
		JSONParser p = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
		try {
			JSONObject jo = (JSONObject)p.parse(json);
			System.out.println("A "+jo.toJSONString());
			JSONObject jo2 = (JSONObject)p.parse(json2);
			System.out.println("AA "+jo2.toJSONString());
			List<JSONObject>l = new ArrayList<JSONObject>();
			l.add(jo2);
			jo.put("InEdges", l);
			System.out.println("B "+jo.toJSONString());			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
//A {"inEdgeIdList":["EDG7"],"vers":"1469229720907","id":"VA14","label":"Second Vertex","eindx":"edges","vindx":"vertices"}
//AA {"OutVertexId":"VA14","vers":"1469230374115","InVertexId":"VA13","label":"My Edge","id":"EDG7","eindx":"edges","InVert":{"vers":"1469230374116","outEdgeIdList":["EDG7"],"id":"VA13","label":"First Vertex","OutEdges":[{"OutVert":{"inEdgeIdList":["EDG7"],"vers":"1469230374116","id":"VA14","label":"Second Vertex","eindx":"edges","vindx":"vertices"},"OutVertexId":"VA14","vers":"1469230374115","InVertexId":"VA13","label":"My Edge","id":"EDG7","eindx":"edges","vindx":"vertices"}],"eindx":"edges","vindx":"vertices"},"vindx":"vertices"}
//B {"inEdgeIdList":["EDG7"],"InEdges":[{"OutVertexId":"VA14","vers":"1469230374115","InVertexId":"VA13","label":"My Edge","id":"EDG7","eindx":"edges","InVert":{"vers":"1469230374116","outEdgeIdList":["EDG7"],"id":"VA13","label":"First Vertex","OutEdges":[{"OutVert":{"inEdgeIdList":["EDG7"],"vers":"1469230374116","id":"VA14","label":"Second Vertex","eindx":"edges","vindx":"vertices"},"OutVertexId":"VA14","vers":"1469230374115","InVertexId":"VA13","label":"My Edge","id":"EDG7","eindx":"edges","vindx":"vertices"}],"eindx":"edges","vindx":"vertices"},"vindx":"vertices"}],"vers":"1469229720907","id":"VA14","label":"Second Vertex","eindx":"edges","vindx":"vertices"}
