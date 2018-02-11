import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Gera Tedeev on 21.03.2017.
 */
public class Matrica {
    private int column;
    private List<Integer> delays = new ArrayList<>();
    private List<Integer> lastDelays = new ArrayList<>();
    private List<Integer> newSecond;
    private List<Integer> firstLine = new ArrayList<>();
    private List<Integer> secondLine = new ArrayList<>();
    private List<Integer> thirdLine = new ArrayList<>();

    public Matrica(int column) {
        this.column = column;
    }

    private void init(Integer[][] matrix) {
        for (int i = 0; i < column; i++) {
            firstLine.add(matrix[0][i]);
            secondLine.add(matrix[1][i]);
            thirdLine.add(matrix[2][i]);
        }

        newSecond = new ArrayList<>(secondLine);
    }

    private void addNewDelays(Integer[][] matrix) {
        init(matrix);
        compareTwoLines(firstLine, secondLine, 0);
        compareTwoLines(newSecond, thirdLine, 1);
    }

    public int getHandleTime(Integer[][] matrix) {
        addNewDelays(matrix);
        int firstSum = firstLine.get(0) + secondLine.get(0);

        int thirdSum = 0;
        for (Integer third: thirdLine) {
            thirdSum += third;
        }

        int delaySum = 0;
        for (Integer delay: lastDelays) {
            delaySum += delay;
        }

        lastDelays.clear();
        firstLine.clear();
        secondLine.clear();
        newSecond.clear();
        thirdLine.clear();
        delays.clear();

        return firstSum + delaySum + thirdSum;
    }

    private void compareTwoLines(List<Integer> firstLine, List<Integer> secondLine, int value) {
        for (int j = 1; j < column; j++) {
            if (firstLine.get(j) > secondLine.get(j-1)) {
                int delay = firstLine.get(j) - secondLine.get(j-1);
                newSecond.set(j, secondLine.get(j) + delay);
                if (value == 1) {
                    lastDelays.add(delay);
                }
                delays.add(delay);
            }
            else {
                if (!Objects.equals(firstLine.get(j), secondLine.get(j - 1))) {
                    int sum1 = firstLine.get(j);
                    int sum2 = secondLine.get(j - 1);
                    while (true) {
                        if ((firstLine.get(j) < secondLine.get(j-1) && j == column-1) || (j == column-1) && sum1 <= sum2)
                            break;
                        sum1 += firstLine.get(j + 1);
                        sum2 += secondLine.get(j);
                        j++;
                        if (sum1 > sum2) {
                            int delay = sum1 - sum2;
                            delays.add(delay);
                            if (value == 1) {
                                lastDelays.add(delay);
                            }
                            newSecond.set(j, secondLine.get(j) + delay);
                            break;
                        }
                    }
                }
            }
        }
    }

}
