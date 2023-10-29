package engine;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Doc {
    private List<Word> titles;
    private List<Word> bodies;

    public Doc(String content) {
       if (content != null) {
           titles = new ArrayList<>();
           bodies = new ArrayList<>();
           String[] conte = content.split("\n");
           String title = conte[0];
           String body = conte[1];
           String[] titleSplit = title.split(" ");
           for (String s : titleSplit) {
               titles.add(Word.createWord(s));
           }
           String[] bodySplit = body.split(" ");
           for (String s : bodySplit) {
               bodies.add(Word.createWord(s));
           }
       }
    }
    public List<Word> getTitle() {
        return this.titles;
    }

    public List<Word> getBody() {
       return this.bodies;
    }

    public boolean equals(Object o) {
        Doc doc = (Doc) o;
        if (titles.size() == doc.titles.size()) return true;
        if (bodies.size() == doc.bodies.size()) return true;
        for (int i = 0; i < this.bodies.size(); i++) {
            if (this.bodies.get(i).equals(doc.bodies.get(i))) return true;
        }
        for (int i = 0; i < this.titles.size(); i++) {
            if (this.titles.get(i).equals(doc.titles.get(i))) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String titleContent = " ";
        for (int i = 0; i < getTitle().size(); i++) {
            titleContent += getTitle().get(i).toString() + " ";
        }
        String bodyContent = " ";
        for (int i = 0; i < getBody().size(); i++) {
            bodyContent += getBody().get(i).toString() + " ";
        }

        return "<h3>" + titleContent.trim() + "</h3>" + "<p>" + bodyContent.trim() + "</p>";
    }
}
