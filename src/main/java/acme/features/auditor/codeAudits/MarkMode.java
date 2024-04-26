
package acme.features.auditor.codeAudits;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import acme.entities.auditRecords.Mark;

public class MarkMode {

	private MarkMode() {
		throw new IllegalStateException("Utility class.");
	}

	public static String calculate(final Collection<Mark> marks) {
		if (marks == null || marks.isEmpty())
			return null;

		Map<Mark, Integer> frequencyMap = new EnumMap<>(Mark.class);

		for (Mark mark : marks)
			if (frequencyMap.containsKey(mark))
				frequencyMap.put(mark, frequencyMap.get(mark) + 1);
			else
				frequencyMap.put(mark, 1);

		Mark markMode = Mark.F_MINUS;
		int maxFrequency = 0;

		for (Map.Entry<Mark, Integer> entry : frequencyMap.entrySet()) {
			Mark mark = entry.getKey();
			int frequency = entry.getValue();

			if (frequency > maxFrequency || frequency == maxFrequency && mark.ordinal() > markMode.ordinal()) {
				markMode = mark;
				maxFrequency = frequency;
			}
		}

		return markMode.toString();
	}

}
