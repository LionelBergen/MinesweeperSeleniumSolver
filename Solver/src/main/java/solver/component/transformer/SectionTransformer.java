package solver.component.transformer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import component.model.Section;
import solver.component.KeyValue;

public class SectionTransformer {
	private SectionTransformer() { }
	
	public static List<KeyValue> transformSectionsToKeyValues(Collection<Section> sections) {
		return sections.stream().map(e -> new KeyValue(0, e.getGameSquares().size(), e)).collect(Collectors.toList());
	}
}
