import java.io.File;
import java.util.Scanner;

public class Micro {
static int startAddress;
static String[] instructions;
        public static void startSimulation()
                        throws NumberFormatException, Exception {
        	Scanner in = new Scanner(System.in);
        	System.out.println("Please Enter the number of cache levels	you	want to	simulate.");
        	int cacheLevels = in.nextInt();
        	//initialize cache with specified levels
        	for(int j=1; j<=cacheLevels; j++){
        		System.out.println("Please specify the full	cache geometry (S, L, M).");
            	String cacheGeometry = in.nextLine();
            	//specify cache geometry for each level
        	}
        	//specify all other data required for program to start then initialize everything
                Memory memory = new Memory();
                Assembler assembler = new Assembler();
                instructions = assembler.assemble(new File("src/pack/input_program.txt"));
                for(int i=0; i<instructions.length; i=i+2){
                	memory.writeData(startAddress,instructions[i]);
                	startAddress++;
                }
        }

        public static void main(String[] args) throws NumberFormatException,
                        Exception {
                System.out.println("*************************************************SIMULATION START*************************************************");
                startSimulation();
        }

}