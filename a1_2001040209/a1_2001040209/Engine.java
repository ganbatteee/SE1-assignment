package engine;

import java.io.*;
import java.util.*;

public class Engine {
    Doc[] docs;

    public int loadDocs(String dirname) throws IOException {
        docs = new Doc[0];
        ArrayList<Doc> docList = new ArrayList<>();
        File[] docChild = new File(dirname).listFiles();
        if (docChild == null)
            return 0;
        for (int i = 0; i < docChild.length; i++) {
            Scanner sc = null;
            try {
                sc = new Scanner(docChild[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (sc.hasNextLine()) {
                String content = sc.nextLine() + "\n" + sc.nextLine();
                Doc docContent = new Doc(content);
                docList.add(docContent);
            }
        }
        docs = docList.toArray(docs);
        return docs.length;
    }

    public Doc[] getDocs() {
        return docs;
    }

    public List<Result> search(Query q) {
        ArrayList<Result> res = new ArrayList<>();
        for (Doc d : docs) {
            ArrayList<Match> fitRes = (ArrayList<Match>) q.matchAgainst(d);
            Result result = new Result(d, fitRes);
            if (fitRes.size() != 0) {
                res.add(result);
            }
        }
        Collections.sort(res);
        return res;
    }

    public String htmlResult(List<Result> results) {
        String render = "";
        for (Result result : results) {
            render += result.htmlHighlight();
        }
        return render;
    }
}
