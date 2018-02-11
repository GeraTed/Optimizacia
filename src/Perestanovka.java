import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gera Tedeev on 23.03.2017.
 */
public class Perestanovka {
    private List<Integer> firstLine = new ArrayList<>();
    private List<Integer> secondLine = new ArrayList<>();
    private List<Integer> thirdLine = new ArrayList<>();
    private int column;
    private Integer[][] matrix;
    private List<Integer[][]> matrixes = new ArrayList<>();
    private int countPerm;
    private int count;

    public Perestanovka(Integer[][] matrix, int column) {
        this.matrix = matrix;
        this.column = column;

        countPerm =  factorial(column);
        fillMatrix();

        init();
        permuteHelper(firstLine.stream().mapToInt(i -> i).toArray(), 0, 0);
        count = 0;
        permuteHelper(secondLine.stream().mapToInt(i -> i ).toArray(), 0, 1);
        count = 0;
        permuteHelper(thirdLine.stream().mapToInt(i -> i).toArray(), 0, 2);
    }

    private void init() {
        for (int i = 0; i < column; i++) {
            firstLine.add(matrix[0][i]);
            secondLine.add(matrix[1][i]);
            thirdLine.add(matrix[2][i]);
        }
    }

    private void fillMatrix() {
        for (int i = 0; i < countPerm; i++) {
            matrixes.add(new Integer[3][column]);
        }
    }

    private int factorial(int n) {
        if (n == 1) {
            return 1;
        }
        return n * factorial(n-1);
    }

    private void permuteHelper(int[] arr, int index, int row){
        if(index >= arr.length - 1){ //If we are at the last element - nothing left to permute
            fillMatrixColumn(row, arr, count++);
            return;
        }
        for(int i = index; i < arr.length; i++){ //For each index in the sub array arr[index...end]

            //Swap the elements at indices index and i
            int t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;

            //Recurse on the sub array arr[index+1...end]
            permuteHelper(arr, index+1, row);

            //Swap the elements back
            t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;

        }
    }

    private void fillMatrixColumn(int row, int[] newColumn, int count) {
        Integer[][] matrix = matrixes.get(count);
        for (int j = 0; j < column; j++) {
            matrix[row][j] = newColumn[j];
        }
    }

    public List<Integer[][]> getMatrixes() {
        return matrixes;
    }
}
