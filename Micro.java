import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Micro {
        static int startAddress;
        static String[] instructions;
        static ArrayList<Register> registers = new ArrayList<Register>();
        static String[] cacheSpecifications;
        static Cache cache;
        static Memory memory = new Memory();
        static ICache icache;
        static TomasuloAlg tomasulo;
        static Assembler assembler = new Assembler();
        static Register pc = new Register("pc", 0);

        @SuppressWarnings("resource")
        public static void startSimulation() throws NumberFormatException,
                        Exception {
                Scanner in = new Scanner(System.in);
                System.out
                                .println("Please Enter the number of cache levels        you        want to        simulate.");
                int cacheLevels = in.nextInt();
                switch (cacheLevels) {
                case 1:
                        System.out
                                        .println("Please Enter your specifications for the cache in the following format: "
                                                        + "first level s,l,m, "
                                                        + "first level hit_policy (1=write_throught 2=write_back),"
                                                        + "first level miss policy (1=write allocate 2=write around),"
                                                        + "first level cycles to access data,"
                                                        + "first level memory access time (in cycles)");
                        cacheSpecifications = in.next().split(",");
                        cache = new Cache(Integer.parseInt(cacheSpecifications[0]),
                                        Integer.parseInt(cacheSpecifications[1]),
                                        Integer.parseInt(cacheSpecifications[2]), 0, 0, 0, 0, 0, 0,
                                        Integer.parseInt(cacheSpecifications[5]), 0, 0,
                                        Integer.parseInt(cacheSpecifications[6]),
                                        Integer.parseInt(cacheSpecifications[3]),
                                        Integer.parseInt(cacheSpecifications[4]), 0, 0, 0, 0,
                                        cacheLevels, memory);
                        break;
                case 2:
                        System.out
                                        .println("Please Enter your specifications for the cache in the following format: "
                                                        + "first level s,l,m, "
                                                        + "first level hit_policy (1=write_throught 2=write_back),"
                                                        + "first level miss policy (1=write allocate 2=write around),"
                                                        + "first level cycles to access data,"
                                                        + "memory access time (in cycles),"
                                                        + "second level s,l,m, "
                                                        + "second level hit_policy (1=write_throught 2=write_back),"
                                                        + "second level miss policy (1=write allocate 2=write around),"
                                                        + "second level cycles to access data");
                        cacheSpecifications = in.next().split(",");
                        cache = new Cache(Integer.parseInt(cacheSpecifications[0]),
                                        Integer.parseInt(cacheSpecifications[1]),
                                        Integer.parseInt(cacheSpecifications[2]),
                                        Integer.parseInt(cacheSpecifications[7]),
                                        Integer.parseInt(cacheSpecifications[8]),
                                        Integer.parseInt(cacheSpecifications[9]), 0, 0, 0,
                                        Integer.parseInt(cacheSpecifications[5]),
                                        Integer.parseInt(cacheSpecifications[12]), 0,
                                        Integer.parseInt(cacheSpecifications[6]),
                                        Integer.parseInt(cacheSpecifications[3]),
                                        Integer.parseInt(cacheSpecifications[4]),
                                        Integer.parseInt(cacheSpecifications[10]),
                                        Integer.parseInt(cacheSpecifications[11]), 0, 0,
                                        cacheLevels, memory);
                        break;
                case 3:
                        System.out
                                        .println("Please Enter your specifications for the cache in the following format: "
                                                        + "first level s,l,m, "
                                                        + "first level hit_policy (1=write_throught 2=write_back),"
                                                        + "first level miss policy (1=write allocate 2=write around),"
                                                        + "first level cycles to access data,"
                                                        + "memory access time (in cycles),"
                                                        + "second level s,l,m, "
                                                        + "second level hit_policy (1=write_throught 2=write_back),"
                                                        + "second level miss policy (1=write allocate 2=write around),"
                                                        + "second level cycles to access data,"
                                                        + "third level s,l,m, "
                                                        + "third level hit_policy (1=write_throught 2=write_back),"
                                                        + "third level miss policy (1=write allocate 2=write around),"
                                                        + "third level cycles to access data");
                        cacheSpecifications = in.next().split(",");
                        cache = new Cache(Integer.parseInt(cacheSpecifications[0]),
                                        Integer.parseInt(cacheSpecifications[1]),
                                        Integer.parseInt(cacheSpecifications[2]),
                                        Integer.parseInt(cacheSpecifications[7]),
                                        Integer.parseInt(cacheSpecifications[8]),
                                        Integer.parseInt(cacheSpecifications[9]),
                                        Integer.parseInt(cacheSpecifications[13]),
                                        Integer.parseInt(cacheSpecifications[14]),
                                        Integer.parseInt(cacheSpecifications[15]),
                                        Integer.parseInt(cacheSpecifications[5]),
                                        Integer.parseInt(cacheSpecifications[12]),
                                        Integer.parseInt(cacheSpecifications[18]),
                                        Integer.parseInt(cacheSpecifications[6]),
                                        Integer.parseInt(cacheSpecifications[3]),
                                        Integer.parseInt(cacheSpecifications[4]),
                                        Integer.parseInt(cacheSpecifications[10]),
                                        Integer.parseInt(cacheSpecifications[11]),
                                        Integer.parseInt(cacheSpecifications[16]),
                                        Integer.parseInt(cacheSpecifications[17]), cacheLevels,
                                        memory);
                        break;
                }
                System.out
                                .println("Please Enter the number of instruction cache levels        you        want to        simulate.");
                int icacheLevels = in.nextInt();
                switch (icacheLevels) {
                case 1:
                        System.out
                                        .println("Please Enter your specifications for the instruction cache in the following format: "
                                                        + "first level s,l,m, "
                                                        + "first level hit_policy (1=write_throught 2=write_back),"
                                                        + "first level miss policy (1=write allocate 2=write around),"
                                                        + "first level cycles to access data,"
                                                        + "first level memory access time (in cycles)");
                        cacheSpecifications = in.next().split(",");
                        icache = new ICache(Integer.parseInt(cacheSpecifications[0]),
                                        Integer.parseInt(cacheSpecifications[1]),
                                        Integer.parseInt(cacheSpecifications[2]), 0, 0, 0, 0, 0, 0,
                                        Integer.parseInt(cacheSpecifications[5]), 0, 0,
                                        Integer.parseInt(cacheSpecifications[6]),
                                        Integer.parseInt(cacheSpecifications[3]),
                                        Integer.parseInt(cacheSpecifications[4]), 0, 0, 0, 0,
                                        cacheLevels, memory);
                        break;
                case 2:
                        System.out
                                        .println("Please Enter your specifications for the instruction cache in the following format: "
                                                        + "first level s,l,m, "
                                                        + "first level hit_policy (1=write_throught 2=write_back),"
                                                        + "first level miss policy (1=write allocate 2=write around),"
                                                        + "first level cycles to access data,"
                                                        + "memory access time (in cycles),"
                                                        + "second level s,l,m, "
                                                        + "second level hit_policy (1=write_throught 2=write_back),"
                                                        + "second level miss policy (1=write allocate 2=write around),"
                                                        + "second level cycles to access data");
                        cacheSpecifications = in.next().split(",");
                        icache = new ICache(Integer.parseInt(cacheSpecifications[0]),
                                        Integer.parseInt(cacheSpecifications[1]),
                                        Integer.parseInt(cacheSpecifications[2]),
                                        Integer.parseInt(cacheSpecifications[7]),
                                        Integer.parseInt(cacheSpecifications[8]),
                                        Integer.parseInt(cacheSpecifications[9]), 0, 0, 0,
                                        Integer.parseInt(cacheSpecifications[5]),
                                        Integer.parseInt(cacheSpecifications[12]), 0,
                                        Integer.parseInt(cacheSpecifications[6]),
                                        Integer.parseInt(cacheSpecifications[3]),
                                        Integer.parseInt(cacheSpecifications[4]),
                                        Integer.parseInt(cacheSpecifications[10]),
                                        Integer.parseInt(cacheSpecifications[11]), 0, 0,
                                        cacheLevels, memory);
                        break;
                case 3:
                        System.out
                                        .println("Please Enter your specifications for the instruction cache in the following format: "
                                                        + "first level s,l,m, "
                                                        + "first level hit_policy (1=write_throught 2=write_back),"
                                                        + "first level miss policy (1=write allocate 2=write around),"
                                                        + "first level cycles to access data,"
                                                        + "memory access time (in cycles),"
                                                        + "second level s,l,m, "
                                                        + "second level hit_policy (1=write_throught 2=write_back),"
                                                        + "second level miss policy (1=write allocate 2=write around),"
                                                        + "second level cycles to access data,"
                                                        + "third level s,l,m, "
                                                        + "third level hit_policy (1=write_throught 2=write_back),"
                                                        + "third level miss policy (1=write allocate 2=write around),"
                                                        + "third level cycles to access data");
                        cacheSpecifications = in.next().split(",");
                        icache = new ICache(Integer.parseInt(cacheSpecifications[0]),
                                        Integer.parseInt(cacheSpecifications[1]),
                                        Integer.parseInt(cacheSpecifications[2]),
                                        Integer.parseInt(cacheSpecifications[7]),
                                        Integer.parseInt(cacheSpecifications[8]),
                                        Integer.parseInt(cacheSpecifications[9]),
                                        Integer.parseInt(cacheSpecifications[13]),
                                        Integer.parseInt(cacheSpecifications[14]),
                                        Integer.parseInt(cacheSpecifications[15]),
                                        Integer.parseInt(cacheSpecifications[5]),
                                        Integer.parseInt(cacheSpecifications[12]),
                                        Integer.parseInt(cacheSpecifications[18]),
                                        Integer.parseInt(cacheSpecifications[6]),
                                        Integer.parseInt(cacheSpecifications[3]),
                                        Integer.parseInt(cacheSpecifications[4]),
                                        Integer.parseInt(cacheSpecifications[10]),
                                        Integer.parseInt(cacheSpecifications[11]),
                                        Integer.parseInt(cacheSpecifications[16]),
                                        Integer.parseInt(cacheSpecifications[17]), cacheLevels,
                                        memory);
                        break;
                }
                System.out
                                .println("Please Enter your specifications for the hardware organization in the following format: "
                                                + "instruction buffer size, "
                                                + "number of load/store reservation stations,"
                                                + "number of add/sub reservation stations,"
                                                + "number of nand reservation stations,"
                                                + "number of mul reservation stations,"
                                                + "number of addi reservation stations,"
                                                + "number of ROB entries,"
                                                + "load/store cycles,"
                                                + "add/sub cycles,"
                                                + "nand cycles,"
                                                + "mul cycles,"
                                                + "addi cycles");
                String[] tomasuloSpecifications = in.next().split(",");
                tomasulo = new TomasuloAlg(Integer.parseInt(tomasuloSpecifications[0]),
                                Integer.parseInt(tomasuloSpecifications[1]),
                                Integer.parseInt(tomasuloSpecifications[2]),
                                Integer.parseInt(tomasuloSpecifications[3]),
                                Integer.parseInt(tomasuloSpecifications[4]),
                                Integer.parseInt(tomasuloSpecifications[5]),
                                Integer.parseInt(tomasuloSpecifications[6]),
                                Integer.parseInt(tomasuloSpecifications[7]),
                                Integer.parseInt(tomasuloSpecifications[8]),
                                Integer.parseInt(tomasuloSpecifications[9]),
                                Integer.parseInt(tomasuloSpecifications[10]),
                                Integer.parseInt(tomasuloSpecifications[11]),
                                Integer.parseInt(tomasuloSpecifications[12]),
                                Integer.parseInt(tomasuloSpecifications[13]),
                                Integer.parseInt(tomasuloSpecifications[14]),
                                Integer.parseInt(tomasuloSpecifications[15]),
                                Integer.parseInt(tomasuloSpecifications[16]));
                System.out
                                .println("Please enter the start address then write your program in the file.");
                startAddress = in.nextInt();
                instructions = assembler.assemble(new File("test.txt"));
                for (int i = 0; i < instructions.length; i++) {
                        memory.writeData(Integer.toBinaryString(startAddress),
                                        instructions[i]);
                        startAddress += 2;
                }
                for (int q = 0; q < assembler.returnRegisters().size(); q++) {
                        if (!registers.contains(assembler.returnRegisters().get(q))) {
                                Register r = new Register(assembler.returnRegisters().get(q),
                                                -1);
                                registers.add(r);
                        }
                }
                System.out
                                .println("Please enter the memory data required for your program in the form (value, address), if no data, enter (noData).");
                String[] memData = in.next().split(",");
                for (int d = 0; d < memData.length; d += 2) {
                        if (!memData[d].equals("noData"))
                                memory.writeData(memData[d + 1], memData[d]);
                }
                tomasulo.start(registers, icache, cache, memory, pc);
                System.out.println("Number of cycles: " + tomasulo.cycles);
                System.out.println("IPC: " + (instructions.length / tomasulo.cycles));
                switch (cacheLevels) {
                case 1:
                        System.out.println("Cache Hit ratio: "
                                        + (cache.hit / (cache.hit + cache.miss)));
                        break;
                case 2:
                        System.out.println("Cache Hit ratio: ");
                        System.out.println("Level1: "
                                        + (cache.hit / (cache.hit + cache.miss)));
                        System.out.println("Level2: "
                                        + (cache.hit2 / (cache.hit2 + cache.miss2)));
                        break;
                case 3:
                        System.out.println("Cache Hit ratio: ");
                        System.out.println("Level1: "
                                        + (cache.hit / (cache.hit + cache.miss)));
                        System.out.println("Level2: "
                                        + (cache.hit2 / (cache.hit2 + cache.miss2)));
                        System.out.println("Level3: "
                                        + (cache.hit3 / (cache.hit3 + cache.miss3)));
                        break;
                }
                switch (icacheLevels) {
                case 1:
                        System.out.println("Instruction Cache Hit ratio: "
                                        + (icache.hit / (icache.hit + icache.miss)));
                        break;
                case 2:
                        System.out.println("Instruction Cache Hit ratio: ");
                        System.out.println("Level1: "
                                        + (icache.hit / (icache.hit + icache.miss)));
                        System.out.println("Level2: "
                                        + (icache.hit2 / (icache.hit2 + icache.miss2)));
                        break;
                case 3:
                        System.out.println("Instruction Cache Hit ratio: ");
                        System.out.println("Level1: "
                                        + (icache.hit / (icache.hit + icache.miss)));
                        System.out.println("Level2: "
                                        + (icache.hit2 / (icache.hit2 + icache.miss2)));
                        System.out.println("Level3: "
                                        + (icache.hit3 / (icache.hit3 + icache.miss3)));
                        break;
                }
        }

        public static void main(String[] args) throws NumberFormatException,
                        Exception {
                System.out
                                .println("*************************************************SIMULATION START*************************************************");
                startSimulation();
        }

}