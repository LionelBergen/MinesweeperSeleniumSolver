package solver.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import component.model.GameSquare;

public class SectionAnalyzedResults {
	private ResultSetCollection originalSet;
	// TODO: Maybe get rid of 'ResultSetCollection' class and use lists
	private Map<ResultSetCollection, List<GameSquare>> contents;
	
	public SectionAnalyzedResults() {
		this(new ArrayList<ResultSet>());
	}

	public SectionAnalyzedResults(List<ResultSet> set) {
		contents = new HashMap<>();
		this.originalSet = new ResultSetCollection(set);
	}

	public List<GameSquare> get(List<ResultSet> otherSetsThisSquareIsAPartOf) {
		for (ResultSetCollection rsc : this.contents.keySet()) {
			if (rsc.getResultSets().size() == otherSetsThisSquareIsAPartOf.size() && rsc.getResultSets().containsAll(otherSetsThisSquareIsAPartOf)) {
				List<GameSquare> resultts = contents.get(rsc);
				
				if (resultts == null) {
					throw new RuntimeException("issue with hashcode crap");
				}
				
				return resultts;
			}
		}
		
		return null;
	}

	public void put(List<ResultSet> otherSetsThisSquareIsAPartOf, List<GameSquare> otherSquaresInSameSet) {
		contents.put(new ResultSetCollection(otherSetsThisSquareIsAPartOf), otherSquaresInSameSet);
	}
	
	public List<ResultSet> getOriginalSet() {
		return this.originalSet.getResultSets();
	}
	
	public List<List<ResultSet>> getResultSets() {
		List<List<ResultSet>> results = new ArrayList<>();
		
		for (ResultSetCollection resultCollection : contents.keySet()) {
			results.add(resultCollection.getResultSets());
		}
		
		return results;
	}
}
