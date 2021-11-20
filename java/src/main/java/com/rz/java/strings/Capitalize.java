package com.rz.java.strings;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;


public class Capitalize {
    /**
     * <p>Normalize the spaces and capitalize all the separated words in a String.</p>
     *
     * <p></p>Can handle words separated by space and other characters (such as an apostrophe).</p>
     *
     * <pre>
     *     Capitalize.capitalizeWords(null)         = null
     *     Capitalize.capitalizeWords("")           = ""
     *     Capitalize.capitalizeWords(" ")          = ""
     *     Capitalize.capitalizeWords("i am FINE")    = "I Am FINE"
     *     Capitalize.capitalizeWords("rosanna d'amico") = "Rosanna D'Amico"
     * </pre>
     *
     * @param string the String to capitalize, may be null
     * @return capitalized String, <code>null</code> if null String input
     * @see StringUtils#normalizeSpace(String)
     * @see WordUtils#capitalize(String)
     * @see WordUtils#capitalize(String, char...)
     */
    public static String capitalizeWords(String string) {
        String s0 = StringUtils.normalizeSpace(string);
        String s1 = WordUtils.capitalize(s0);
        return WordUtils.capitalize(s1, '\'');
    }
}
