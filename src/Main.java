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
    public static void sortTheElementsByDistance(){
        for (int i = 0; i < distances.length; i++) {
            for (int j = 1; j < (distances.length - i); j++) {
                if (distances[j - 1] > distances[j]) {
                    swapDoubles(distances, j);
                    swapStrings(names, j);
                    swapDoubles(whenOpens, j);
                    swapDoubles(whenCloses, j);
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
            }
            if (whenCloses[i] > whenOpens[i] && (timeNow >= whenOpens[i] && timeNow <= whenCloses[i])){
            }
        }
    }
    public static void printResult(){
        for (int i = 0; i < names.length; i++) {
            System.out.println(i + 1 + ". " + names[i] + "(" + whenOpens[i] + " - " + whenCloses[i] + ") - " + Math.round(distances[i]) + "m");
        }
    }
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
            System.out.println("Изберете опция: СПИСЪК ВСИЧКИ (1), СПИСЪК ОТВОРЕНИ (2), КАРТА (3), ИЗХОД (4)");
             option = scan.nextByte();
             switch (option){
                 case 1: sortTheElementsByDistance(); printResult();break;
                 case 2:
                 case 3:
                 case 4:break;
                 default:
                     System.out.println("Invalid input");
             }
        }while (option != 4);
    }
}