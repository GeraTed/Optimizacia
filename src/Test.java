
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.DoubleStream;

/**
 * Created by Gera Tedeev on 31.03.2017.
 */
public class Test {
    public static void main(String[] args) {
        int column = 10;

        Integer[][] newMatrix = {{7,19,4,7,1,4,5,11,19,15},{3,1,8,3,3,8,13,19,19,3},{18,20,1,2,5,9,12,3,11,9}};/*new Integer[3][column];*/

        /*for (int i = 0; i < 3; i++) {
            for (int j = 0; j < column; j++) {
                newMatrix[i][j] = 1 + (int) (Math.random() * 20);
            }
        }*/

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(newMatrix[i][j] + "\t");
            }
            System.out.println();
        }

        Algorithm algorithm = new Algorithm(newMatrix, column);
        Perestanovka perestanovka = new Perestanovka(newMatrix, column);
        Matrica task = new Matrica(column);


        List<Integer> time = new ArrayList<>();

        perestanovka.getMatrixes().forEach(permMatrix -> time.add(task.getHandleTime(permMatrix)));

        int currentTime = task.getHandleTime(newMatrix);
        System.out.println();
        System.out.println("ВРЕМЯ ОБРАБОТКИ ИСХОДНОЙ МАТРИЦЫ: " + currentTime);
        int min = time.stream().min(Integer::compareTo).get();
        System.out.println("МИНИМАЛЬНОЕ ВРЕМЯ ОБРАБОТКИ МЕТОДОМ ПЕРЕСТАНОВКИ: " + min);
        int max = time.stream().max(Integer::compareTo).get();
        System.out.println("МАКСИМАЛЬНОЕ ВРЕМЯ ОБРАБОТКИ МЕТОДОМ ПЕРЕСТАНОВКИ: " + max);


        int optTime = task.getHandleTime(algorithm.optimize());

        if (optTime > currentTime)
            optTime = currentTime;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Asus\\Desktop\\optim.txt", true));
             BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Asus\\Desktop\\optim.txt"))) {

            double k = (double) (optTime - min)/(max-min);

            writer.write(String.format(Locale.ENGLISH, "%.3f", k) + " ");
            writer.flush();

            String str;
            while ((str = reader.readLine()) != null) {
                if (str.startsWith("С") || str.endsWith(".")) {
                    continue;
                }

                String[] coefs = str.split(" ");

                if (coefs.length == 10) {
                    double[] mass = new double[10];
                    for (int i = 0; i < 10; i++) {
                        mass[i] = Double.parseDouble(coefs[i]);
                    }
                    double average = Double.parseDouble((String.format(Locale.ENGLISH, "%.3f", DoubleStream.of(mass).average().getAsDouble())));
                    writer.write(".");
                    writer.newLine();
                    writer.write("Среднее значение для " + column + " столбцов - " + average);
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("ВРЕМЯ ОБРАБОТКИ ИСХОДНОЙ НАШИМ МЕТОДОМ: " + (optTime+3));
    }
}