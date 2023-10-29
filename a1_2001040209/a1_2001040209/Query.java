package engine;

import java.util.*;

public class Query {
    private final List<Word> keyWords;
    public Query(String searchPhrase) {
        keyWords = new ArrayList<>();
        StringTokenizer stri = new StringTokenizer(searchPhrase, " ", false);
        while (stri.hasMoreTokens()) {
            Word w = Word.createWord(stri.nextToken());
            if (w.isKeyword())
                keyWords.add(w);
        }
    }

    public List<Word> getKeywords() {
        return this.keyWords;
    }

    public  List<Match> matchAgainst(Doc d) {
        HashMap<String, Map.Entry<Word, Integer>> fredList = new HashMap<>();

        ArrayList<Word> combinePart = new ArrayList<>();
        combinePart.addAll(d.getTitle());
        combinePart.addAll(d.getBody());

        for (Word keyWord: keyWords) {
            for (Word word: combinePart) {
                if (keyWord.getText().equals(word.getText())) {
                    if (fredList.containsKey(word.getText().toLowerCase())) {
                        Map.Entry<Word, Integer> couple = fredList.get(word.getText().toLowerCase());
                        couple.setValue(couple.getValue() + 1);
                    } else {
                        Map.Entry<Word, Integer> entryMap = new AbstractMap.SimpleEntry<>(word, 1);
                        fredList.put(word.getText().toLowerCase(), entryMap);
                    }
                }
            }
        }
        List<Match> fitMatch = new ArrayList<>();
        fredList.forEach((key, val) -> {
            Match match = new Match(d, val.getKey(), val.getValue(), combinePart.indexOf(val.getKey()));
            fitMatch.add(match);
        });
        Collections.sort(fitMatch);
        return fitMatch;
    }
}
