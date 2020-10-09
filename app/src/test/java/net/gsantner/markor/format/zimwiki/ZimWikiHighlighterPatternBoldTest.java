/*#######################################################
 *
 *   Maintained by Gregor Santner, 2017-
 *   https://gsantner.net/
 *
 *   License of this file: Apache 2.0 (Commercial upon request)
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
#########################################################*/
package net.gsantner.markor.format.zimwiki;

import net.gsantner.markor.format.markdown.MarkdownHighlighterPattern;

import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class ZimWikiHighlighterPatternBoldTest {

    private Pattern pattern;

    @Before
    public void before() {
        pattern = ZimWikiHighlighterPattern.BOLD.pattern;
    }

    @Test
    public void starBoldInSentence() {
        Matcher m = pattern.matcher("this **sentence** has bold");
        assertThat(m.find()).isTrue();
        assertThat(m.group()).isEqualTo("**sentence**");
    }

    @Test
    public void multipleStarBoldInSentence() {
        Matcher m = pattern.matcher("this **sentence** has **bold**");
        assertThat(m.find()).isTrue();
        assertThat(m.group()).isEqualTo("**sentence**");
        assertThat(m.find()).isTrue();
        assertThat(m.group()).isEqualTo("**bold**");
    }

    @Test
    public void boldWithSpace() {
        Matcher m = pattern.matcher("**bold words**");
        assertThat(m.find()).isTrue();
        assertThat(m.group()).isEqualTo("**bold words**");
    }

    @Test
    public void mixedUnderlineAndStarShouldNotMatch() {
        Matcher m = pattern.matcher("__bold**");
        assertThat(m.find()).isFalse();
    }

    @Test
    public void mixedStarAndUnderlineShouldNotMatch2() {
        Matcher m = pattern.matcher("**bold__");
        assertThat(m.find()).isFalse();
    }

    @Test
    public void mixedStarAndUnderlineOnSameSideShouldNotMatch() {
        Matcher m = pattern.matcher("_*bold*_");
        assertThat(m.find()).isFalse();
    }

    @Test
    public void italicUnderlineShouldNotMatch() {
        Matcher m = pattern.matcher("_italic_");
        assertThat(m.find()).isFalse();
    }

    @Test
    public void italicStarShouldNotMatch() {
        Matcher m = pattern.matcher("*italic*");
        assertThat(m.find()).isFalse();
    }

    @Test
    public void italicsStarWithTwoStartCharactersShouldNotMatch() {
        Matcher m = pattern.matcher("**italic*");
        assertThat(m.find()).isFalse();
    }

    @Test
    public void boldInAList() {
        Matcher m = pattern.matcher("* **bold** word");
        assertThat(m.find()).isTrue();
        assertThat(m.group()).isEqualTo("**bold**");
    }

    @Test
    public void boldInAWordShouldNotMatch() {
        Matcher m = pattern.matcher("2**5 + 4**3");
        assertThat(m.find()).isFalse();
    }

}
