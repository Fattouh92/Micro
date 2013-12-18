import java.io.File;
import java.net.URL;

public class Micro {
static int startAddress;
static String[] instructions;
        public static void startSimulation()
                        throws NumberFormatException, Exception {
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