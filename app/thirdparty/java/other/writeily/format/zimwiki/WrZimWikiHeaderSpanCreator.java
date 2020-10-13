package other.writeily.format.zimwiki;

import android.text.ParcelableSpan;
import android.text.Spannable;

import net.gsantner.markor.format.zimwiki.ZimWikiHighlighter;
import net.gsantner.markor.ui.hleditor.SpanCreator;

import java.util.regex.Matcher;

import other.writeily.format.WrProportionalHeaderSpanCreator;

public class WrZimWikiHeaderSpanCreator implements SpanCreator.ParcelableSpanCreator {
    private static final Character EQUAL_SIGN = '=';
    private static final float STANDARD_PROPORTION_MAX = 1.80f;
    private static final float SIZE_STEP = 0.20f;
    private static final float STANDARD_PROPORTION_MIN = 1.80f-(SIZE_STEP*6);

    protected ZimWikiHighlighter _highlighter;
    private final Spannable _spannable;
    private final WrProportionalHeaderSpanCreator _spanCreator;

    public WrZimWikiHeaderSpanCreator(ZimWikiHighlighter highlighter, Spannable spannable, int color, boolean dynamicTextSize) {
        _highlighter = highlighter;
        _spannable = spannable;
        _spanCreator = new WrProportionalHeaderSpanCreator(highlighter._fontType, highlighter._fontSize, color, dynamicTextSize);
    }

    public ParcelableSpan create(Matcher m, int iM) {
        final char[] headingCharacters = extractMatchingRange(m);
        float proportion = calculateProportionBasedOnEqualSignCount(headingCharacters);
        return _spanCreator.createHeaderSpan(proportion);
    }

    private char[] extractMatchingRange(Matcher m) {
        return _spannable.subSequence(m.start(), m.end()).toString().trim().toCharArray();
    }

    private float calculateProportionBasedOnEqualSignCount(final char[] headingSequence) {
        float proportion = STANDARD_PROPORTION_MIN;
        int i = 0;
        // one level bigger for each '='
        while (EQUAL_SIGN.equals(headingSequence[i]) && proportion<STANDARD_PROPORTION_MAX) {
            proportion += SIZE_STEP;
            i++;
        }
        return proportion;
    }
}
