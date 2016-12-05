package com.eden.americanbiblesociety;

import com.caseyjbrooks.eden.bible.AbstractVerse;
import com.caseyjbrooks.eden.interfaces.VerseFormatter;

public class ABSFormatter implements VerseFormatter {
    protected AbstractVerse verse;

    @Override
    public String onPreFormat(AbstractVerse verse) {
        if(!(verse instanceof ABSPassage)) {
            throw new IllegalArgumentException("ABSFormatter expects a verse of type ABSPassage");
        }

        this.verse = verse;
        return "";
    }

    @Override
    public String onFormatVerseStart(int i) {
        return "";
    }

    @Override
    public String onFormatText(String s) {
        return s;
    }

    @Override
    public String onFormatVerseEnd() {
        return " ";
    }

    @Override
    public String onPostFormat() {
        return "";
    }
}
