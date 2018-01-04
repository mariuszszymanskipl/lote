package com.merapar.loadtest;

import com.merapar.loadtest.web.controller.SseController;
import com.merapar.loadtest.web.controller.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JMeterCommand {




    @Async
    public static void runLoadTest(String loadTestJmx){

        removeOldHTMLReport();
        String loadTestCommand = generateLoadTestCommand(loadTestJmx);
        execute(loadTestCommand);
    }

    private static String generateLoadTestCommand(String loadTestJmx) {

        return "jmeter -n -t "
                + loadTestJmx
                + " -l load-test-results.jtl";
    }

    private static void execute(String loadTestCommand) {

        Process process;
        try {
            process = Runtime.getRuntime().exec(loadTestCommand, null);
            systemPrintLnOut(process);
        } catch (IOException e) {
            e.printStackTrace();
        }
        generateHTMLReport();
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
            cmdArray[2] = "results/report.csv";
            cmdArray[3] = "-o";
            cmdArray[4] = "results/HTTPReport";

            Process process = Runtime.getRuntime().exec(cmdArray, null);

            systemPrintLnOut(process);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void removeOldHTMLReport() {

        System.out.println("Removing old HTTP report");

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



    public static Process runDemoLoadTest(String loadTestJmx){

        removeOldHTMLReport();
        String loadTestCommand = generateLoadTestCommand(loadTestJmx);
        return executeDemo(loadTestCommand);
    }

    private static Process executeDemo(String loadTestCommand) {

        Process process = null;
        try {
            process = Runtime.getRuntime().exec(loadTestCommand, null);
            systemPrintLnOut(process);

        } catch (IOException e) {
            e.printStackTrace();
        }
        generateHTMLReport();
        return process;
    }
}
