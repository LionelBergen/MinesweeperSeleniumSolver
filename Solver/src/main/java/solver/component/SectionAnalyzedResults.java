package solver.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import component.model.Section;
import component.model.gamesquare.GameSquare;

public class SectionAnalyzedResults {
	private RuleSet sectionRules;
	private Map<SectionSet, Section> contents;

	public SectionAnalyzedResults(List<Rule> set) {
		contents = new HashMap<>();
		this.sectionRules = new RuleSet(set);
	}
	
	public Map<SectionSet, Section> getContents() {
		return contents;
	}
	
	public void put(GameSquare gameSquare) {
		List<Section> setsThisSquareIsAPartOf = 
				this.sectionRules.getRules().stream()
					.filter(e -> e.getSquares()
					.contains(gameSquare))
					.map(e -> new Section(new HashSet<>(e.getSquares())))
					.collect(Collectors.toList());
		
		Set<GameSquare> otherSquaresInSameSet = this.get(setsThisSquareIsAPartOf);
		
		if (otherSquaresInSameSet == null) {
			otherSquaresInSameSet = new HashSet<GameSquare>();
			this.put(setsThisSquareIsAPartOf, otherSquaresInSameSet);
		}
		
		// Add the square to the set
		otherSquaresInSameSet.add(gameSquare);
	}

	public Set<GameSquare> get(List<Section> otherSetsThisSquareIsAPartOf) {
		for (SectionSet rsc : this.contents.keySet()) {
			if (areEqual(rsc, otherSetsThisSquareIsAPartOf)) {
				Set<GameSquare> resultts = contents.get(rsc).getGameSquares();
				
				if (resultts == null) {
					throw new RuntimeException("issue with hashcode crap");
				}
				
				return resultts;
			}
		}
		
		return null;
	}
	
	private boolean areEqual(SectionSet object1, List<Section> object2) {
		if (object1.getSections().size() == object2.size()) {
			return object1.getSections().containsAll(object2);
		}
		
		return false;
	}
	
	public List<Rule> getOriginalSet() {
		return this.sectionRules.getRules();
	}
	
	public List<Set<Section>> getResultSets() {
		List<Set<Section>> results = new ArrayList<>();
		
		for (SectionSet resultCollection : contents.keySet()) {
			results.add(resultCollection.getSections());
		}
		
		return results;
	}

	private void put(final List<Section> otherSetsThisSquareIsAPartOf, Set<GameSquare> otherSquaresInSameSet) {
		if (!otherSetsThisSquareIsAPartOf.isEmpty()) {
			contents.put(new SectionSet(new HashSet<>(otherSetsThisSquareIsAPartOf)), createSection(otherSquaresInSameSet));
		}
	}
	
	private Section createSection(Set<GameSquare> squares) { 
		return new Section(squares);
	}
}
