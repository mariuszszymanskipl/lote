package com.merapar.loadtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class jMeterCommand {

    public static void run() throws java.io.IOException, InterruptedException {

        try {
            String[] cmdArray = new String[6];

            cmdArray[0] = "jmeter";
            cmdArray[1] = "-n";
            cmdArray[2] = "-t";
            cmdArray[3] = "load_test_1.jmx";
            cmdArray[4] = "-l";
            cmdArray[5] = "load-test-results.jtl";

            System.out.println("Executing jmeter load test");

            Process process = Runtime.getRuntime().exec(cmdArray,null);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
