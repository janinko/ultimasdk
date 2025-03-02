package eu.janinko.andaria.ultimasdk.files.defs;

import lombok.Getter;

@Getter
public class DefLine<T extends DefEntry> {
    public static final String ENTRY_TEMPLATE = "<<ENTRY>>";
    private final T entry;
    private final String originalLine;
    private final String templateLine;

    public DefLine(T entry) {
        this.originalLine = entry.asString();
        this.entry = entry;
        this.templateLine = ENTRY_TEMPLATE;

    }

    public DefLine(String originalLine) {
        this.originalLine = originalLine;
        entry = null;
        templateLine = null;
    }

    public DefLine(String originalLine, T entry, String templateLine) {
        this.originalLine = originalLine;
        this.entry = entry;
        this.templateLine = templateLine;
    }

    public boolean hasEntry() {
        return entry != null;
    }

    public String asString() {
        if (entry == null) {
            return originalLine;
        } else {
            return templateLine.replace(ENTRY_TEMPLATE, entry.asString());
        }
    }

}
