package labMapReduce;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rameshor.pathak
 */
public class CustomReduceCounter {

    private static final String FIRST_FILE = "src/files/test.txt";
    private static final String SECOND_FILE = "src/files/test1.txt";
    private static final String THIRD_FILE = "src/files/test2.txt";

    private static final int FIRST_MAPPER_NO = 0;
    private static final int SECOND_MAPPER_NO = 1;
    private static final int THIRD_MAPPER_NO = 2;

    private Map<Integer, String> mappers= new HashMap();

    public void initialize() {
        this.mappers.put(FIRST_MAPPER_NO, FIRST_FILE);
        this.mappers.put(SECOND_MAPPER_NO, SECOND_FILE);
        this.mappers.put(THIRD_MAPPER_NO, THIRD_FILE);
    }

    public static void main(String[] args) {
        CustomReduceCounter crc = new CustomReduceCounter();
        crc.initialize();
        System.out.println("========No of Tokens Needed to Transfer through Network===============");
        System.out.println("Without Using Combiner: " + crc.getNumberOfWordsTransfered(false));
        System.out.println("Using Using Combiner: " + crc.getNumberOfWordsTransfered(true));
    }

    public int getNumberOfWordsTransfered(Boolean useCombiner) {
        int totalTransfered = 0;

        for (Map.Entry<Integer, String> entry : mappers.entrySet()) {
            totalTransfered += getWordsTransferedPerFile(entry.getKey(), entry.getValue(), useCombiner);
        }
        return totalTransfered;
    }

    public int getWordsTransferedPerFile(Integer fileNumber, String filePath, Boolean useCombiner) {
        int tTrans=0;
        List<String> wordsCombined=new ArrayList();
        wordsCombined = getWordsFromFile(filePath);
        if (useCombiner) {
            wordsCombined = combineWords(wordsCombined);
        }
        for(String word:wordsCombined){
            if(isTransfered(word, fileNumber)){
                tTrans++;
            }
        }
        return tTrans;
    }

    public Boolean isTransfered(String word, int fileNumber) {
        if (partitionWords(word) == fileNumber) {
            return false;
        } else {
            return true;
        }
    }

    public int partitionWords(String word) {
        if (word.compareToIgnoreCase("k") < 0) {
//            System.out.println("Less than k: "+word);
            return FIRST_MAPPER_NO;
        } else if (word.compareToIgnoreCase("p") > 0) {
//            System.out.println("Greater than p: "+word);
            return THIRD_MAPPER_NO;
        } else {
//             System.out.println("other: "+word);
            return SECOND_MAPPER_NO;
        }
    }

    public List combineWords(List<String> words) {
        List<String> wordsCombined = new ArrayList();
        for (String word : words) {
            if (!wordsCombined.contains(word)) {
                wordsCombined.add(word);
            }
        }
        return wordsCombined;
    }

    public List getWordsFromFile(String filePath) {
        List<String> words = new ArrayList();
        BufferedReader br = null;
        try {
            String line;
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                for (String word : line.split("\\s+")) {
                    words.add(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

}
