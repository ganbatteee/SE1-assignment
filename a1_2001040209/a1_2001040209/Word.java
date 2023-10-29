package engine;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Word {
    private boolean valid;
    private String prefix;
    private String text;
    private String suffix;
    private boolean isKeyword;
    private static final String ALPHABET = "^[a-zA-Z-']+";
    private static final String ALPHABET_PARENT = "^[a-zA-Z-'.]+";
    private static final String INVALID = "^[^a-zA-Z0-9][a-z]*[^a-zA-Z0-9]*";
    public static Set<String> stopWords = new HashSet<>();

    public Word(String prefix, String text, String suffix) {
        this.prefix = prefix;
        this.text = text;
        this.suffix = suffix;

    }

    public Word() {
    }

    public static Word createWord(String rawText) {
        Word word = new Word();
        String rawTextTrim = rawText.trim();
        if (rawTextTrim.length() <= 0)
            word.valid = false;
        else {
            if (rawTextTrim.length() == 1) {
                if (!(rawTextTrim.matches(ALPHABET))) {
                    word.valid = false;
                } else {
                    word.text = rawTextTrim;
                    word.valid = stopWords.contains(rawTextTrim);
                }
            } else {
               boolean indexStart = false;
                int i = 0;
                int j =  rawTextTrim.length() - 1;
                for (int k = 0; k < rawTextTrim.length(); k++) {
                    String s = rawTextTrim.charAt(k) + "";
                    if (s.matches(ALPHABET)) {
                        if (rawTextTrim.charAt(k) == '\'') {
                            if (rawTextTrim.charAt(k + 1) == 's') {
                                k++;
                                continue;
                            }
                        }
                        if (!indexStart) {
                            i = k;
                            indexStart = true;
                        } else
                            j = k;
                    }
                }
                word.prefix = rawTextTrim.substring(0, i);
                word.text = rawTextTrim.substring(i, j + 1);
                word.suffix = rawTextTrim.substring(j + 1);
                if (stopWords.contains(word.text.toLowerCase()))
                    return word;
                else if (!word.text.matches(ALPHABET_PARENT)) {
                    word.suffix = "";
                    word.prefix = "";
                    word.text = rawTextTrim;
                    return word;
                }
                if (!word.prefix.matches(INVALID))
                    if (!word.prefix.isEmpty()) {
                        word.prefix = "";
                        word.text = rawTextTrim;
                        word.suffix = "";
                        return word;
                    }
                if (!word.suffix.matches(INVALID))
                    if (!word.suffix.isEmpty()) {
                        word.text = rawTextTrim;
                        word.prefix = "";
                        word.suffix = "";
                        return word;
                    }
                word.valid = true;
                word.isKeyword = true;
            }
        }
        return word;
    }

    public boolean isKeyword() {
        if (!valid)
            isKeyword = false;
        return isKeyword;
    }
    public void titleHighLight(boolean title) {
        if (title)
            this.text = "<u>" + this.text + "<u/>";
        else
            this.text = "<b>" + this.text + "</b>";
    }
    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getText() {
        return this.text;
    }

    public static boolean loadStopWords(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            return false;
        }
        String stopWord;
        while ((stopWord = br.readLine()) != null) {
            stopWords.add(stopWord);
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        Word word = (Word) o;
        return this.getText().equalsIgnoreCase(word.getText());
    }

    @Override
    public String toString() {
        return getPrefix() + getText() + getSuffix();
    }

}
