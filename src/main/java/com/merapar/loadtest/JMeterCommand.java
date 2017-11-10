package com.merapar.loadtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JMeterCommand {

    public static void runLoadTest1() throws java.io.IOException, InterruptedException {

        System.out.println("Removing old HTTP report");
        removeOldHTMLReport();

        try {
            String[] cmdArray = new String[6];

            cmdArray[0] = "jmeter";
            cmdArray[1] = "-n";
            cmdArray[2] = "-t";
            cmdArray[3] = "load_test_1.jmx";
            cmdArray[4] = "-l";
            cmdArray[5] = "load-test-results-1.jtl";

            System.out.println("Executing jmeter load test");

            Process process = Runtime.getRuntime().exec(cmdArray, null);

            systemPrintLnOut(process);

            generateHTMLReport();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void runLoadTest2() throws java.io.IOException, InterruptedException {

        System.out.println("Removing old HTTP report");
        removeOldHTMLReport();

        try {
            String[] cmdArray = new String[6];

            cmdArray[0] = "jmeter";
            cmdArray[1] = "-n";
            cmdArray[2] = "-t";
            cmdArray[3] = "load_test_2.jmx";
            cmdArray[4] = "-l";
            cmdArray[5] = "load-test-results-2.jtl";

            System.out.println("Executing jmeter load test");

            Process process = Runtime.getRuntime().exec(cmdArray, null);

            systemPrintLnOut(process);

            generateHTMLReport();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void runLoadTest3(JMeterParameters parameters) throws java.io.IOException, InterruptedException {

        System.out.println("Removing old HTTP report");
        removeOldHTMLReport();

        try {
            String[] cmdArray = new String[8];

            cmdArray[0] = "jmeter";
            cmdArray[1] = "-n";
            cmdArray[2] = "-t";
            cmdArray[3] = "load_test_3.jmx";
            cmdArray[4] = "-Jusers=" + parameters.getUsersNumber();
            cmdArray[5] = "-Jduration=" + parameters.getDuration();
            cmdArray[6] = "-l";
            cmdArray[7] = "load-test-results-3.jtl";

            System.out.println("Executing jmeter load test");

            Process process = Runtime.getRuntime().exec(cmdArray, null);

            systemPrintLnOut(process);

            generateHTMLReport();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void generateHTMLReport() {

        System.out.println("Generating new HTTP Report");

        try {
            String[] cmdArray = new String[5];

            cmdArray[0] = "jmeter";
            cmdArray[1] = "-g";
            cmdArray[2] = "/test/results/report.csv";
            cmdArray[3] = "-o";
            cmdArray[4] = "/test/results/HTTPReport";

            Process process = Runtime.getRuntime().exec(cmdArray, null);

            systemPrintLnOut(process);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void removeOldHTMLReport() {
        try {
            String[] cmdArray = new String[3];

            cmdArray[0] = "rm";
            cmdArray[1] = "-r";
            cmdArray[2] = "results";

            Process process = Runtime.getRuntime().exec(cmdArray, null);

            systemPrintLnOut(process);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void systemPrintLnOut(Process process) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String line = null;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }
}