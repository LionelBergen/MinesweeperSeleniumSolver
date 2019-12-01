package solver.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import component.model.GameSquare;

public class SectionAnalyzedResults {
	private RuleSet sectionRules;
	// TODO: Maybe get rid of 'ResultSetCollection' class and use lists
	// private Map<ResultSetCollection, List<GameSquare>> contents;
	private Map<RuleSet, Section> contents;
	
	public SectionAnalyzedResults() {
		this(new ArrayList<Rule>());
	}

	public SectionAnalyzedResults(List<Rule> set) {
		contents = new HashMap<>();
		this.sectionRules = new RuleSet(set);
	}
	
	public Map<RuleSet, Section> getContents() {
		return contents;
	}
	
	public List<Rule> get(GameSquare square) {
		List<Rule> results = new ArrayList<Rule>();
		
		for (RuleSet rsc : this.contents.keySet()) {
			for (Rule rs : rsc.getResultSets()) {
				if (rs.getSquares().contains(square)) {
					results.add(rs);
				}
			}
		}
		
		// TODO: Why does this have a bunch of duplicates
		return new ArrayList<Rule>(new HashSet<Rule>(results));
	}
	
	public void put(GameSquare gameSquare) {
		List<Rule> setsThisSquareIsAPartOf = this.sectionRules.getResultSets().stream().filter(e -> e.getSquares().contains(gameSquare)).collect(Collectors.toList());
		
		Set<GameSquare> otherSquaresInSameSet = this.get(setsThisSquareIsAPartOf);
		
		if (otherSquaresInSameSet == null) {
			otherSquaresInSameSet = new HashSet<GameSquare>();
			this.put(setsThisSquareIsAPartOf, otherSquaresInSameSet);
		}
		
		// Add the square to the set
		otherSquaresInSameSet.add(gameSquare);
	}

	public Set<GameSquare> get(List<Rule> otherSetsThisSquareIsAPartOf) {
		for (RuleSet rsc : this.contents.keySet()) {
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
	
	public List<Rule> getOriginalSet() {
		return this.sectionRules.getResultSets();
	}
	
	public List<List<Rule>> getResultSets() {
		List<List<Rule>> results = new ArrayList<>();
		
		for (RuleSet resultCollection : contents.keySet()) {
			results.add(resultCollection.getResultSets());
		}
		
		return results;
	}

	private void put(final List<Rule> otherSetsThisSquareIsAPartOf, Set<GameSquare> otherSquaresInSameSet) {
		if (!otherSetsThisSquareIsAPartOf.isEmpty()) {
			contents.put(new RuleSet(otherSetsThisSquareIsAPartOf), createSection(otherSquaresInSameSet));
		}
	}
	
	private Section createSection(Set<GameSquare> squares) { 
		return new Section(squares);
	}
}
