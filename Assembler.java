import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class Assembler {

	FileInputStream fis;
	FileWriter fw;
	BufferedWriter bw;
	File input_file;
	File first_pass_file;
	ArrayList<String> registerNames = new ArrayList<String>();

	@SuppressWarnings("resource")
	public String[] assemble(File file) {
		String[] result = new String[countNumberOfLines(file)];
		int instructionNumber = 0;
		try {
			FileInputStream fis = new FileInputStream(file);
			int content;
			while ((content = fis.read()) != -1) {
				if (content == 10) {
					String[] instruction = result[instructionNumber].split(" ");
					for (int i = 0; i < instruction.length; i++) {

						System.out.println(instruction[i]);
					}
					switch (instruction[0]) {

					case "load":
						registerNames.add(instruction[1]);
						registerNames.add(instruction[2]);
						break;

					case "store":
						registerNames.add(instruction[1]);
						registerNames.add(instruction[2]);
						break;
					case "add":
						registerNames.add(instruction[1]);
						registerNames.add(instruction[2]);
						registerNames.add(instruction[3]);
						break;
					case "sub":
						registerNames.add(instruction[1]);
						registerNames.add(instruction[2]);
						registerNames.add(instruction[3]);
						break;
					case "nand":
						registerNames.add(instruction[1]);
						registerNames.add(instruction[2]);
						registerNames.add(instruction[3]);
						break;
					case "mult":
						registerNames.add(instruction[1]);
						registerNames.add(instruction[2]);
						registerNames.add(instruction[3]);
						break;
					case "addi":
						registerNames.add(instruction[1]);
						registerNames.add(instruction[2]);
						break;
					case "jmp":
						registerNames.add(instruction[1]);
						break;
					case "beq":
						registerNames.add(instruction[1]);
						registerNames.add(instruction[2]);
						break;
					case "jalr":
						registerNames.add(instruction[1]);
						registerNames.add(instruction[2]);
						break;
					case "ret":
						registerNames.add(instruction[1]);
						break;
					default:
						break;
					}
					instructionNumber++;
				} else {
					result[instructionNumber] += (char) content;
				}
			}
		} catch (Exception e) {

			System.out.println("Error trying to read from file.");
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<String> returnRegisters() {
		return registerNames;
	}

	@SuppressWarnings("resource")
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