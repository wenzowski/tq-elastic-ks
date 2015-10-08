/*
 * Copyright 2015, TopicQuests
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.topicquests.ks.cg.api;

/**
 * @author park
 *
 */
public interface IMatchingScheme {
	public final static int
	  /** Graph matching control flag: arguments must be the same Graph instance.  **/
	  GR_MATCH_INSTANCE = 0,
	  /** Graph matching control flag: arguments completely match.  **/
	  GR_MATCH_COMPLETE = 1,
	  /** Graph matching control flag: first argument must be a subgraph of the second.  **/
	  GR_MATCH_SUBGRAPH = 2,
	  /** Graph matching control flag: first argument must be a subgraph of the second.  **/
	  GR_MATCH_PROPER_SUBGRAPH = 3,
	  /** Graph matching control flag: one argument must be a subgraph of the other.  **/
	  GR_MATCH_EITHER_SUBGRAPH = 4,
	  /** Graph matching control flag: one argument must be a proper subgraph of the other.  **/
	  GR_MATCH_EITHER_PROPER_SUBGRAPH = 5,
	  /** Graph matching control flag: graphs must share one or more common subgraphs.  **/
	  GR_MATCH_COMMON_SUBGRAPH = 6,
	  /** Graph matching control flag: arguments will always match.  **/
	  GR_MATCH_ANYTHING = 7,

	  /** Concept matching control flag: arguments must be the same Concept instance.  **/
	  CN_MATCH_INSTANCE = 10,
	  /** Concept matching control flag: arguments will be matched according to the concept type match flag.  **/
	  CN_MATCH_TYPES = 11,
	  /** Concept matching control flag: arguments will be matched according to the coreference, designator and quantifier match flags.  **/
	  CN_MATCH_REFERENTS = 12,
	  /** Concept matching control flag: arguments will be matched using coreference flag only.  **/
	  CN_MATCH_COREFERENTS = 13,
	  /** Concept matching control flag: arguments will be matched using coreference, quantifier, designator, and type flags.  **/
	  CN_MATCH_ALL = 14,
	  /** Concept matching control flag: arguments will always match.  **/
	  CN_MATCH_ANYTHING = 15,

	  /** Relation matching control flag: arguments must be the same Relation instance.  **/
	  RN_MATCH_INSTANCE = 20,
	  /** Relation matching control flag: arguments will be matched according to the relation type match flag.  **/
	  RN_MATCH_TYPES = 21,
	  /** Relation matching control flag: arguments will be matched according to the arc match flag.  **/
	  RN_MATCH_ARCS = 22,
	  /** Relation matching control flag: arguments will be matched according to all other match flags.  **/
	  RN_MATCH_ALL = 23,
	  /** Relation matching control flag: arguments will always match.  **/
	  RN_MATCH_ANYTHING = 24,

	  /** ConceptType matching control flag: arguments must be the same ConceptType instance.  **/
	  CT_MATCH_INSTANCE = 30,
	  /** ConceptType matching control flag: arguments must have the exact same type label.  **/
	  CT_MATCH_LABEL = 31,
	  /** ConceptType matching control flag: first argument must be a subtype of the second.  **/
	  CT_MATCH_SUBTYPE = 32,
	  /** ConceptType matching control flag: first argument must be a supertype of the second.  **/
	  CT_MATCH_SUPERTYPE = 33,
	  /** ConceptType matching control flag: arguments must have a sub/supertype relationship.  **/
	  CT_MATCH_EQUIVALENT = 34,
	  /** ConceptType matching control flag: arguments will always match.  **/
	  CT_MATCH_ANYTHING = 35,

	  /** RelationType matching control flag: arguments must be the same RelationType instance.  **/
	  RT_MATCH_INSTANCE = 40,
	  /** RelationType matching control flag: arguments must have the exact same type label.  **/
	  RT_MATCH_LABEL = 41,
	  /** RelationType matching control flag: first argument must be a subtype of the second.  **/
	  RT_MATCH_SUBTYPE = 42,
	  /** RelationType matching control flag: first argument must be a supertype of the second.  **/
	  RT_MATCH_SUPERTYPE = 43,
	  /** RelationType matching control flag: arguments must have a sub/supertype relationship.  **/
	  RT_MATCH_EQUIVALENT = 44,
	  /** RelationType matching control flag: arguments will always match.  **/
	  RT_MATCH_ANYTHING = 45,

	  /** Quantifier matching control flag: arguments will always match.  **/
	  QF_MATCH_ANYTHING = 50,

	  /** Designator matching control flag: arguments must be the same Designator instance.  **/
	  DG_MATCH_INSTANCE = 60,
	  /** Designator matching control flag: arguments refer to the same individual.  **/
		  DG_MATCH_INDIVIDUAL = 61,
	  /** Designator matching control flag: arguments may have a generic/generic or generic/specific relationship.  **/
	  DG_MATCH_EQUIVALENT = 62,
	  /** Designator matching control flag: the first argument may be a restriction of the second.  **/
	  DG_MATCH_RESTRICTION = 63,
	  /** Designator matching control flag: the first argument may be a generalization of the second.  **/
	  DG_MATCH_GENERALIZATION = 64,
	  /** Designator matching control flag: the first argument must be a restriction of the second.  **/
	  DG_MATCH_PROPER_RESTRICTION = 65,
	  /** Designator matching control flag: the first argument must be a generalization of the second.  **/
	  DG_MATCH_PROPER_GENERALIZATION = 66,
	  /** Designator matching control flag: arguments will always match.  **/
	  DG_MATCH_ANYTHING = 67,

	  /** Arc matching control flag: arguments must be same Concept instances.  **/
	  ARC_MATCH_INSTANCE = 70,
	  /** Arc matching control flag: arguments will be matched according to scheme's concept flag.  **/
	  ARC_MATCH_CONCEPT = 71,
	  /** Arc matching control flag: arguments must have the same number of arcs.  **/
	  ARC_MATCH_VALENCE = 72,
	  /** Arc matching control flag: arguments will always match.  **/
	  ARC_MATCH_ANYTHING = 73,

	  /** Coreference matching control flag: coreferent concepts will be treated as normal concepts.  **/
	  COREF_AUTOMATCH_OFF = 80,
	  /** Coreference matching control flag: coreferent concepts will match regardless of types and referents.  **/
	  COREF_AUTOMATCH_ON = 81,

	  /** Coreference matching control flag: when matching a concept, all coreferent concepts must match as well.  **/
	  COREF_AGREE_OFF = 90,
	  /** Coreference matching control flag: when matching a concept, coreferent concepts will not be considered.  **/
	  COREF_AGREE_ON = 91,

	  /** Folding matching control flag: folds will not be matched.  **/
	  FOLD_MATCH_OFF = 100,
	  /** Folding matching control flag: folds will be matched.  **/
	  FOLD_MATCH_ON = 101,

	  /** Connected graph matching control flag: disconnected matches are allowed.  **/
	  CONN_MATCH_OFF = 110,
	  /** Connected graph matching control flag: disconnected matches are not allowed.  **/
	  CONN_MATCH_ON = 111,
	  
	  /** Marker matching control flag: markers match only if their ID's are the same. **/
	  MARKER_MATCH_ID = 120,
	  /** Marker matching control flag: markers are matched using the marker comparator 
	      specified in this scheme. **/
	  MARKER_MATCH_COMPARATOR = 121,
	  /** Marker matching control flag: markers always match. **/
	  MARKER_MATCH_ANYTHING = 122;
	
	int getGraphFlag();
	
	int getConceptFlag();
	
	int getRelationFlag();
	
	int getConceptTypeFlag();
	
	int getRelationTypeFlag();
	
	int getQuantifierFlag();
	
	int getDesignatorFlag();
	
	int getArcFlag();
	
	int getMarkerFlag();
	
	int getCoreferenceAutoMatchFlag();
	
	int getCoreferenceAgreementFlag();
	
	int getFoldingFlag();
	
	int getConnectedFlag();
	
	int getMaxMatches();
	
	IMatchingScheme getNestedMatchingScheme();
	
	IMarkerComparator getMarkerComparator();
}
