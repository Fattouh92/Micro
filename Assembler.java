
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class Assembler {

        FileInputStream fis;
        FileWriter fw;
        BufferedWriter bw;
        File input_file;
        File first_pass_file;


        public String[] assemble(File file) {
                String[] result = new String[countNumberOfLines(file)];
                int instructionNumber = 0;
                try {
                        FileInputStream fis = new FileInputStream(file);
                        int content;
                        while ((content = fis.read()) != -1) {
                        	if(content == 10){
                        		instructionNumber++;
                        	}
                        	else{
                        		result[instructionNumber]+=(char)content;
                        	}
                        }
                } catch (Exception e) {

                        System.out.println("Error trying to read from file.");
                        e.printStackTrace();
                }
                return result;
        }
     
        public static int countNumberOfLines(File file) {
                FileReader fr = null;
                try {
                        fr = new FileReader(file);
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                }
                LineNumberReader lnr = new LineNumberReader(fr);

                int linesCount = 0;
                try {
                        while (lnr.readLine() != null) {
                                linesCount++;
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return linesCount;
        }
}