/**
 * 
 */
package org.topicquests.ks.graph;

import java.util.*;

import net.minidev.json.JSONObject;

import org.topicquests.support.api.IResult;
import org.topicquests.ks.graph.api.IEdge;
import org.topicquests.ks.graph.api.IElement;
import org.topicquests.ks.graph.api.IGraphOntology;
import org.topicquests.ks.graph.api.IGraphProvider;
import org.topicquests.ks.graph.api.IQuery;
import org.topicquests.ks.graph.api.IVertex;

import com.google.common.collect.Lists;

/**
 * @author park
 *
 */
public class VertexQuery implements IQuery {
	private IGraphProvider database;
	private IVertex myVertex;
	private String _direction = "";
	private List<String> _labels = null;
	private long _limit = -1;
	private String _key = null;
	private Object _value = null;
	/**
	 * 
	 */
	public VertexQuery(IVertex v) {
		database = GraphUtility.getInstance().getDatabase();
		//JUST IN CASE
		IResult r = database.populateVertex(v);
		myVertex = v;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IQuery#count()
	 */
	@Override
	public long count() {
		JSONObject jo = myVertex.getData();
		//we count id values so no need to populate
		String prop = IGraphOntology.IN_EDGE_ID_LIST_PROPERTY_TYPE;
		if (_direction.equals(IGraphOntology.DIRECTION_OUT))
			prop = IGraphOntology.OUT_EDGE_ID_LIST_PROPERTY_TYPE;
		List<IEdge> edges = (List<IEdge>)jo.get(prop);
		if (edges != null)
			return edges.size();
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IQuery#direction(java.lang.String)
	 */
	@Override
	public IQuery direction(String direction) {
		this._direction = direction;
		return this;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IQuery#edges()
	 */
	@Override
	public Iterator<IEdge> edges() {
		String [] labels = null;
		if (_labels != null) {
			int len = _labels.size();
			labels = new String[len];
			for (int i=0;i<len; i++)
				labels[i] = _labels.get(i);
				
		}
		Iterator<IEdge> itr = myVertex.listEdges(_direction, labels);
		if (_key != null && _value != null) {
			List<IEdge> le = new ArrayList<IEdge>();
			IEdge e;
			while (itr.hasNext()) {
				e = itr.next();
				if (e.hasKeyValue(_key, _value))
					le.add(e);
				if (_limit > -1 && (le.size()-1) == _limit)
					break;
			}
			return le.iterator();
		}
		return itr;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IQuery#has(java.lang.String, java.lang.Object)
	 */
	@Override
	public IQuery has(String key, Object value) {
		_key = key;
		_value = value;
		return this;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IQuery#labels(java.lang.String[])
	 */
	@Override
	public IQuery labels(String... labels) {
		_labels = Lists.newArrayListWithExpectedSize(labels.length);
        for (String l : labels)
        	_labels.add(l);
		return this;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IQuery#limit(long)
	 */
	@Override
	public IQuery limit(long max) {
		_limit = max;
		return this;
	}

	/* (non-Javadoc)
	 * @see org.topicquests.hyperbrane.graph.api.IQuery#vertices()
	 */
	@Override
	public Iterator<IVertex> vertices() {
		List<IVertex>  result = new ArrayList<IVertex>();
		Iterator<IEdge> edges = edges();
		IEdge e;
		IElement elem;
		if (_direction.equals(IGraphOntology.DIRECTION_IN)) {
			// In to me, thus get In from the edge
			while (edges.hasNext()) {
				e = edges.next();
				elem = e.getVertex(IGraphOntology.DIRECTION_IN);
				if (elem.isVertex())
					result.add((IVertex)elem);
			}
		} else {
			while (edges.hasNext()) {
				e = edges.next();
				elem = e.getVertex(IGraphOntology.DIRECTION_OUT);
				if (elem.isVertex())
					result.add((IVertex)elem);
			}			
		}
		return result.iterator();
	}

}
