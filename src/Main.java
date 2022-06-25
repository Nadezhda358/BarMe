import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class Main {
    public static String[] names = {"Almost Famous", "Soho", "Милениум", "No Mercy", "Enjoy", "Бялата къща", "Хаджи Николов Хан",
            "Ресторант Капитан Блъд", "Бар Занзибар", "Винарна Абритус"};
    public static double[] barLatitude = {43.52755023562406, 43.5258028565389, 43.51910958149257, 43.52173293102872, 43.52552829519936,
            43.524879563629675, 43.527419311882305, 43.52687371512204, 43.52554377894517, 43.52658131141369};
    public static double[] barLongitude = {26.518606935419914, 26.522469757107643, 26.52075541216495, 26.52226412377693, 26.528079654992204,
            26.522540368484, 26.529784811793945, 26.531229493375804, 26.525185704164105, 26.531128698176378};
    public static double[] whenOpens = {08.00, 09.00, 09.00, 21.00, 08.00, 07.00, 10.00, 07.00, 08.00, 19.00};
    public static double[] whenCloses = {23.30, 24.00, 23.30, 04.00, 24.00, 21.00, 23.00, 01.00, 23.00, 24.00};
    public static double[] distances = new double[barLatitude.length];
    public static double[] timeUntilCloses = new double[whenOpens.length];

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
    public static double calculateDistance(double yourLatitude, double yourLongitude, int i){
        final int earthRadiusMetres = 6371000;

        double latDistance = toRad(barLatitude[i] - yourLatitude);
        double lonDistance = toRad(barLongitude[i] - yourLongitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(toRad(yourLatitude)) * Math.cos(toRad(barLatitude[i])) *
                   Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return earthRadiusMetres * c;
    }
    public static void putTheDistancesIntoArray(double yourLatitude, double yourLongitude){
        for (int i = 0; i < barLatitude.length; i++) {
            distances[i] = calculateDistance(yourLatitude, yourLongitude, i);
        }
    }
    public static void swapStrings(String[] array, int j){
        String temp = array[j - 1];
        array[j - 1] = array[j];
        array[j] = temp;
    }
    public static void swapDoubles(double[] array, int j){
        double temp = array[j - 1];
        array[j - 1] = array[j];
        array[j] = temp;
    }
    public static void sortTheElements(double[] array){
        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < (array.length - i); j++) {
                if (array[j - 1] > array[j]) {
                    swapDoubles(distances, j);
                    swapDoubles(timeUntilCloses, j);
                    swapStrings(names, j);
                    swapDoubles(whenOpens, j);
                    swapDoubles(whenCloses, j);
                    swapDoubles(barLatitude, j);
                    swapDoubles(barLongitude, j);
                }
            }
        }
    }
    public static void findWhichBarsWorks(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH.mm");
        LocalDateTime now = LocalDateTime.now();
        double timeNow = Double.parseDouble(dtf.format(now));

        for (int i = 0; i < timeUntilCloses.length; i++) {
            if (whenCloses[i] < whenOpens[i] && (timeNow >= whenOpens[i] || timeNow <= whenCloses[i])){
                calculateRemainingTime1(i, timeNow);
            }else if (whenCloses[i] > whenOpens[i] && (timeNow >= whenOpens[i] && timeNow <= whenCloses[i])){
                calculateRemainingTime2(i, timeNow);
            }
        }
    }
    public static void calculateRemainingTime1(int i, double now){
        double timeLeft = whenCloses[i] - now + 24 -0.40;
        if (timeLeft % 1 >= 0.6){
            timeLeft += 0.40;
        }
        timeUntilCloses[i] = timeLeft;
    }
    public static void calculateRemainingTime2(int i, double now){
        double timeLeft = whenCloses[i] - now - 0.40;
        if (timeLeft % 1 >= 0.6){
            timeLeft += 0.40;
        }
        timeUntilCloses[i] = timeLeft;
    }
    public static void printResultOption1(){
        for (int i = 0; i < names.length; i++) {
            System.out.println(i + 1 + ". " + names[i] + "(" + whenOpens[i] + "0 - " + whenCloses[i] + "0) - " + Math.round(distances[i]) + "m");
        }
    }
    public static void printResultOption2(){
        byte count = 1;
        for (int i = 0; i < names.length; i++) {
            if (timeUntilCloses[i] != 0){
                System.out.println(count + ". " + names[i] + "(" + whenOpens[i] + "0 - " + whenCloses[i] + "0)");
                count++;
            }
        }
    }
    //1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
    //*броим колко елемента трябва да има във свеки масив
    //*създаваме масивите със толкова елемента - 1, като объщаме левия
    //печатаме ляво, х, дясно с различни методи
    public static void createArrays(double yourLatitude){
        int countLeft = 0;
        int countRight = 0;
        for (int i = 0; i < barLatitude.length; i++) {
            if (barLatitude[i] <= yourLatitude){
                countLeft++;
            }else{
                countRight++;
            }
        }

        int[] leftSide = new int[countLeft];
        int[] nameIndexLeft = new int[countLeft];
        int[] rightSide = new int[countRight];
        int[] nameIndexRight = new int[countRight];

        byte index1 = 0;
        byte index2 = 0;
        for (int i = 0; i < barLatitude.length; i++) {
            if (barLatitude[i] <= yourLatitude){
                leftSide[index1] = (int) distances[i] / 50;
                nameIndexLeft[index1] = i;
                index1++;
            }else{
                rightSide[index2] = (int) distances[i] / 50;
                nameIndexRight[index2] = i;
                index2++;
            }
        }
        printMapOption3(leftSide, rightSide, nameIndexLeft, nameIndexRight);
    }
    public static void printMapOption3(int[] left, int[] right, int[] indexLeft, int[] indexRight){
        int countUnderscore = 0;
        String underscore = "_";
        for (int i = left.length - 1; i >= 0; i--) {
            if (i == 0){
                countUnderscore = left[i];
            }else{
                countUnderscore = left[i] - left[i - 1];
            }

            System.out.print(indexLeft[i] + 1);
            System.out.print(underscore.repeat(countUnderscore));
        }
        System.out.print("X");
        for (int i = 0; i < right.length; i++) {
            if (i == 0){
                countUnderscore = right[i];
            }else{
                countUnderscore = right[i] - right[i-1];
            }

            System.out.print(underscore.repeat(countUnderscore));
            System.out.print(indexRight[i] + 1);
        }
    }
    //2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Въведете локацията си. ");
        System.out.print("Ширина (в градуси): ");
        double latitude = scan.nextDouble();
        System.out.print("Дължина (в градуси): ");
        double longitude = scan.nextDouble();

        putTheDistancesIntoArray(latitude, longitude);

        byte option;
        do {
            System.out.println();
            System.out.println("Изберете опция: СПИСЪК ВСИЧКИ (1), СПИСЪК ОТВОРЕНИ (2), КАРТА (3), ИЗХОД (4)");
             option = scan.nextByte();
             switch (option){
                 case 1: sortTheElements(distances); printResultOption1();break;
                 case 2:findWhichBarsWorks();sortTheElements(timeUntilCloses);printResultOption2();break;
                 case 3: sortTheElements(distances);
                 createArrays(latitude);
                 case 4:break;
                 default:
                     System.out.println("Invalid input");
             }
        }while (option != 4);
    }
}