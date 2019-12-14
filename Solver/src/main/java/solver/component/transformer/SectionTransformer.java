package solver.component.transformer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import component.model.Section;
import solver.component.KeyValue;

public class SectionTransformer {
	private SectionTransformer() { }
	
	public static List<KeyValue> transformSectionsToKeyValues(final Collection<Section> sections, final int defaultValue) {
		return sections.stream().map(e -> new KeyValue(defaultValue, e.getGameSquares().size(), e)).collect(Collectors.toList());
	}
}
