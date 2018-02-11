import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gera Tedeev on 31.03.2017.
 */
public class Algorithm {
    private int column;
    private List<Integer> firstLine = new ArrayList<>();
    private List<Integer> secondLine = new ArrayList<>();
    private List<Integer> thirdLine = new ArrayList<>();
    private Integer[][] thisMatrix;

    public Algorithm(Integer[][] matrix, int column) {
        this.column = column;
        thisMatrix = new Integer[matrix.length][];
        for(int i = 0; i < matrix.length; i++)
            thisMatrix[i] = matrix[i].clone();

        init();
    }

    private void init() {
        for (int i = 0; i < column; i++) {
            firstLine.add(thisMatrix[0][i]);
            secondLine.add(thisMatrix[1][i]);
            thirdLine.add(thisMatrix[2][i]);
        }
    }

    public Integer[][] optimize() {
        int thirdMax = 0;
        int maxColumn = 0;
        for (int i = 0; i < column; i++) {
            if (thirdLine.get(i) > thirdMax) {
                thirdMax = thirdLine.get(i);
                maxColumn = i;
            }
        }
        swapColumn(0, maxColumn);

        for (int i = 0; i < column-1; i++) {
            int nF;
            int nS;
            int minJ = 0;
            int minN = 100;
            int minS = 100;
            for (int j = i+1; j < column; j++) {
                nF = firstLine.get(j) - secondLine.get(i);
                nS = nF - thirdLine.get(i);
                if (nF < minN && nS < minS) {
                    minN = nF;
                    minS = nS;
                    minJ = j;
                }
            }
            if (i == column-1) {
                int first = firstLine.get(i+1) - secondLine.get(i);
                int second = firstLine.get(i) - secondLine.get(i+1);
                if (first > second) {
                    swapColumn(i, column);
                    continue;
                }
            }
            swapColumn(i + 1, minJ);
        }

        return thisMatrix;
    }

    private void swapColumn(int indexFrom, int indexTo) {
        for (int i = 0; i < 3; i++) {
            int tmp = thisMatrix[i][indexFrom];
            thisMatrix[i][indexFrom] = thisMatrix[i][indexTo];
            thisMatrix[i][indexTo] = tmp;
        }
        int first = firstLine.get(indexFrom);
        int second = secondLine.get(indexFrom);
        int third = thirdLine.get(indexFrom);
        firstLine.set(indexFrom, firstLine.get(indexTo));
        firstLine.set(indexTo, first);
        secondLine.set(indexFrom, secondLine.get(indexTo));
        secondLine.set(indexTo, second);
        thirdLine.set(indexFrom, thirdLine.get(indexTo));
        thirdLine.set(indexTo, third);
    }
}
