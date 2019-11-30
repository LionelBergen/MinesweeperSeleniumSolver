package solver.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import component.model.GameSquare;
import component.model.SquareValue;

public class SectionAnalyzedResults {
	// TODO: rename
	private ResultSetCollection originalSet;
	// TODO: Maybe get rid of 'ResultSetCollection' class and use lists
	// private Map<ResultSetCollection, List<GameSquare>> contents;
	private Map<ResultSetCollection, Section> contents;
	
	public SectionAnalyzedResults() {
		this(new ArrayList<ResultSet>());
	}

	public SectionAnalyzedResults(List<ResultSet> set) {
		contents = new HashMap<>();
		this.originalSet = new ResultSetCollection(set);
	}
	
	public List<ResultSet> get(ResultSet resultSet) {
		List<ResultSet> results = new ArrayList<ResultSet>();
		
		for (ResultSetCollection rsc : this.contents.keySet()) {
			for (ResultSet rs : rsc.getResultSets()) {
				if (rs.getSquares().containsAll(resultSet.getSquares())) {
					results.add(rs);
				}
			}
		}
		
		return results;
	}
	
	public Map<ResultSetCollection, Section> getContents() {
		return contents;
	}
	
	public List<ResultSet> get(GameSquare square) {
		List<ResultSet> results = new ArrayList<ResultSet>();
		
		for (ResultSetCollection rsc : this.contents.keySet()) {
			for (ResultSet rs : rsc.getResultSets()) {
				if (rs.getSquares().contains(square)) {
					results.add(rs);
				}
			}
		}
		
		// TODO: Why does this have a bunch of duplicates
		return new ArrayList<ResultSet>(new HashSet<ResultSet>(results));
	}
	
	public void put(GameSquare gameSquare) {
		List<ResultSet> setsThisSquareIsAPartOf = this.originalSet.getResultSets().stream().filter(e -> e.getSquares().contains(gameSquare)).collect(Collectors.toList());
		
		Set<GameSquare> otherSquaresInSameSet = this.get(setsThisSquareIsAPartOf);
		
		if (otherSquaresInSameSet == null) {
			otherSquaresInSameSet = new HashSet<GameSquare>();
			this.put(setsThisSquareIsAPartOf, otherSquaresInSameSet);
		}
		
		// Add the square to the set
		otherSquaresInSameSet.add(gameSquare);
	}

	public Set<GameSquare> get(List<ResultSet> otherSetsThisSquareIsAPartOf) {
		for (ResultSetCollection rsc : this.contents.keySet()) {
			if (rsc.getResultSets().size() == otherSetsThisSquareIsAPartOf.size() && rsc.getResultSets().containsAll(otherSetsThisSquareIsAPartOf)) {
				Set<GameSquare> resultts = contents.get(rsc).getGameSquares();
				
				if (resultts == null) {
					throw new RuntimeException("issue with hashcode crap");
				}
				
				return resultts;
			}
		}
		
		return null;
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

	private void put(final List<ResultSet> otherSetsThisSquareIsAPartOf, Set<GameSquare> otherSquaresInSameSet) {
		if (!otherSetsThisSquareIsAPartOf.isEmpty()) {
			contents.put(new ResultSetCollection(otherSetsThisSquareIsAPartOf), createSection(otherSquaresInSameSet));
		}
	}
	
	private Section createSection(Set<GameSquare> squares) { 
		return new Section(squares);
	}
}
