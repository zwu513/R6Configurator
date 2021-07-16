package main.java;

import java.io.*;

/**
 * Program Name
 * <p>
 * brief description of the program
 * <p>
 * a list of your sources of help (if any)
 *
 * @author Zachary Wu
 * @version date of completion
 */
public class R6ConfigFile {

    public String detectServer(File configFile) {
        if (configFile == null) {
            return null;
        }

        String configFileText = readFile(configFile);
        String result = findLine(configFileText, "DataCenterHint=").replace("DataCenterHint=", "");

        if (result.contains("playfab/")) {
            result = result.replace("playfab/", "");
        }
        return result;
    }

    public int detectFPS(File configFile) {
        if (configFile == null) {
            return -1;
        }

        String configFileText = readFile(configFile);
        String result = findLine(configFileText, "FPSLimit=").replace("FPSLimit=", "");

        return Integer.parseInt(result);
    }

    public void changeFPSLimit(File configFile, int FPSLimit) {
        if (configFile == null) {
            return;
        }
        String configFileText = readFile(configFile);
        writeToFile(configFile, replaceConfigFile(configFileText, "FPSLimit=", "FPSLimit=" + FPSLimit));
    }

    public void changeDataCenter(File configFile, String datacenterHint) {
        if (configFile == null) {
            return;
        }
        String configFileText = readFile(configFile);
        writeToFile(configFile, replaceConfigFile(configFileText, "DataCenterHint=", "DataCenterHint=playfab/" + datacenterHint));
    }

    /* helpers methods*/

    private String findLine(String text, String toFind) {
        String current = text.substring(text.indexOf(toFind));
        current = current.substring(0, current.indexOf('\n'));
        return current;
    }

    private String readFile(File file) {
        StringBuilder output = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while (br.ready()) {
                output.append(br.readLine()).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    private String replaceConfigFile(String text, String oldLine, String newLine) {
        StringBuilder stringBuilder = new StringBuilder(text);
        StringBuilder output = new StringBuilder();

        while (stringBuilder.length() > 0) {
            String currentLine;
            // to prevent errors on last line
            if (stringBuilder.indexOf("\n") >= 0) {
                currentLine = stringBuilder.substring(0, stringBuilder.indexOf("\n"));
            } else {
                currentLine = stringBuilder.toString();
            }

            if (currentLine.contains(oldLine)) {
                output.append(newLine).append("\r\n");
            } else {
                output.append(currentLine).append("\r\n");
            }

            stringBuilder.delete(0, stringBuilder.indexOf("\n") + 1);
        }

        return output.toString();
    }

    private void writeToFile(File file, String text) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(file, false));
            pw.write(text);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
