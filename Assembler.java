
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
                        String[] instruction = { "", "", "", "" };
                        int separator = 0;
                        while ((content = fis.read()) != -1) {
                                // System.out.println(content);
                                if (content != 10) {
                                        if (separator == 0 && content == 35) {
                                                separator++;
                                        }
                                        if (separator == 0 && content == 36) {
                                                separator++;
                                                instruction[separator] += "$";

                                        } else if (content == 44
                                                        && (separator == 1 || separator == 2)) {
                                                separator++;
                                        } else {
                                                instruction[separator] += Character.toChars(content)[0];
                                        }
                                } else {
                                        System.out.println(instruction[0] + ":" + instruction[1]
                                                        + ":" + instruction[2] + ":" + instruction[3]);
                                        result[instructionNumber] = instructionAssemble(instruction);
                                        instruction[0] = "";
                                        instruction[1] = "";
                                        instruction[2] = "";
                                        instruction[3] = "";
                                        separator = 0;
                                        instructionNumber++;
                                }
                        }
                } catch (Exception e) {

                        System.out.println("Error trying to read from file.");
                        e.printStackTrace();
                }
                return result;
        }

        public String instructionAssemble(String[] instruction) {
                String result = "";
                if (instruction[0].equals("add")) {
                	//create registers
                	result= "add" + instruction[1]+instruction[2]+instruction[3];
                }
                if (instruction[0].equals("addi")) {
                	//create registers
                	result= "addi" + instruction[1]+instruction[2]+instruction[3];
                }
                if (instruction[0].equals("sub")) {
                	//create registers
                	result= "sub" + instruction[1]+instruction[2]+instruction[3];
                }
                if (instruction[0].equals("lw")) {
                	//create registers
                	result= "lw" + instruction[1]+instruction[2]+instruction[3];
                }
                if (instruction[0].equals("sw")) {
                	//create registers
                	result= "sw" + instruction[1]+instruction[2]+instruction[3];
                }
               
                if (instruction[0].equals("beq")) {
                	//create registers
                	result= "beq" + instruction[1]+instruction[2]+instruction[3];
                }
                if (instruction[0].equals("bne")) {
                	//create registers
                	result= "bne" + instruction[1]+instruction[2]+instruction[3];
                }
                if (instruction[0].equals("jmp")) {
                	//create registers
                	result= "jmp" + instruction[1]+instruction[2]+instruction[3];
                }
                if (instruction[0].equals("jalr")) {
                	//create registers
                	result= "jalr" + instruction[1]+instruction[2]+instruction[3];
                }
                if (instruction[0].equals("ret")) {
                	//create registers
                	result= "ret" + instruction[1]+instruction[2]+instruction[3];
                }
                if (instruction[0].equals("nand")) {
                	//create registers
                	result= "nand" + instruction[1]+instruction[2]+instruction[3];
                }
                if (instruction[0].equals("mul")) {
                	//create registers
                	result= "mul" + instruction[1]+instruction[2]+instruction[3];
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