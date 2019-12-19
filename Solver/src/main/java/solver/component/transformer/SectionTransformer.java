package solver.component.transformer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import component.model.Section;
import solver.component.AssignedValue;

public class SectionTransformer {
	private SectionTransformer() { }
	
	public static List<AssignedValue> transformSectionsToKeyValues(final Collection<Section> sections, final int defaultValue) {
		return sections.stream().map(e -> new AssignedValue(defaultValue, e.getGameSquares().size(), e)).collect(Collectors.toList());
	}
}
