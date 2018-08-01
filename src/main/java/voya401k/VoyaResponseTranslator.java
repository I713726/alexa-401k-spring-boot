package voya401k;

/**
 * This interface allows us to plug in different translation APIs
 */

public interface VoyaResponseTranslator {

    /**
     * Translates a given string of text into the given language
     * @param text The text to be translated
     * @param locale The target language, one of the locale codes from 
     * @return text parameter translated into specified language.
     */
    public String translate(String text, String locale);
}
