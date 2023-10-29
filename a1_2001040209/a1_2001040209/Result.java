package engine;

import java.util.ArrayList;
import java.util.List;

public class Result implements Comparable<Result>{
    private final Doc d;
    private final List<Match> matches;
    private final int matchCount;
    private int totalFrequency;
    private double averageFirstIndex;
    public Result(Doc d, List<Match> matches) {
        this.d = d;
        this.matches = matches;
        this.matchCount = matches.size();
        for (Match match : matches) {
            totalFrequency += match.getFreq();
            averageFirstIndex += match.getFirstIndex();
        }
        averageFirstIndex /= matches.size();
    }
    public Doc getDoc() {
        return this.d;
    }
    public List<Match> getMatches() {
        return matches;
    }
    public int getTotalFrequency() {
        return this.totalFrequency;
    }
    public double getAverageFirstIndex() {
        return this.averageFirstIndex;
    }
    public String htmlHighlight() {
        List<Match> htmlMatches = new ArrayList<>();
        for (Match match: matches) {
            Match m = new Match(d,Word.createWord(match.getWord().getText()), match.getFreq(), match.getFirstIndex());
            htmlMatches.add(m);
        }
        for (Match htmlMatch: htmlMatches) {
            for (Word wd: d.getTitle()) {
                if (htmlMatch.getWord().equals(wd)) {
                    wd.titleHighLight(true);
                }
            }
            for (Word wd: d.getBody()) {
                if (htmlMatch.getWord().equals(wd)) {
                    wd.titleHighLight(false);
                }
            }
        }
        return d.toString();
    }
    @Override
    public int compareTo(Result o) {
         if (this.matchCount != o.matchCount) {
             return -(this.matchCount - o.matchCount);
         } else if (this.totalFrequency != o.totalFrequency) {
             return -(this.totalFrequency - o.totalFrequency);
         } else {
             return (int) -(this.averageFirstIndex - o.averageFirstIndex);
         }
    }
}
