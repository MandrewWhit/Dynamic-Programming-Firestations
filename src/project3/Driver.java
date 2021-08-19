package project3;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    public static String filename;
    public static boolean testResponse;
    public static boolean testPosFireStation;

    public static void main(String[] args) throws Exception {
        parseArgs(args);

        TownPlan problem = parseTownPlanProblem(filename);
        testRun(problem);
    }

    private static void usage() {
        System.err.println("usage: java Driver [-r] [-p] <filename>");
        System.err.println("\t-r\tTest optimal response time");
        System.err.println("\t-p\tTest positions of fire stations");
        System.exit(1);
    }

    public static void parseArgs(String[] args) {
        if (args.length == 0) {
            usage();
        }

        filename = "";
        testResponse = false;
        testPosFireStation = false;
        boolean flagsPresent = false;

        for (String s : args) {
            if (s.equals("-r")) {
                flagsPresent = true;
                testResponse = true;
            } else if (s.equals("-p")) {
                flagsPresent = true;
                testPosFireStation = true;
            } else if (!s.startsWith("-")) {
                filename = s;
            } else {
                System.err.printf("Unknown option: %s\n", s);
                usage();
            }
        }

        if (!flagsPresent) {
            testResponse = true;
            testPosFireStation = true;
        }
    }

    public static TownPlan parseTownPlanProblem(String inputFile) throws Exception {
        int n = 0;
        int k = 0;
        ArrayList<Float> position_houses;

        Scanner sc = new Scanner(new File(inputFile));
        String[] inputSizes = sc.nextLine().split(" ");

        n = Integer.parseInt(inputSizes[0]);
        k = Integer.parseInt(inputSizes[1]);
        position_houses = readPositionsList(sc, n);

        TownPlan problem = new TownPlan(n, k, position_houses);

        return problem;
    }

    private static ArrayList<Float> readPositionsList(Scanner sc, int n) {
        ArrayList<Float> position_houses = new ArrayList<Float>(0);

        String[] pos = sc.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            position_houses.add(Float.parseFloat(pos[i]));
        }

        return position_houses;
    }

    public static void testRun(TownPlan problem) {
        Program3 program = new Program3();

        if (testResponse) {
	        problem.setResponse(null);
            problem.setPositionFireStations(null);
            TownPlan responseTownPlan = program.OptimalResponse(problem);
            System.out.println(responseTownPlan);
        }

        if (testPosFireStation) {
	        problem.setResponse(null);
	        problem.setPositionFireStations(null);
            TownPlan PosFireTownPlan = program.OptimalPosFireStations(problem);
            System.out.println(PosFireTownPlan);
        }
    }
}
