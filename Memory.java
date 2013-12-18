import java.util.ArrayList;

public class Memory {
        ArrayList<String> memory = new ArrayList<String>();
        ArrayList<Integer> index = new ArrayList<Integer>();
        
        public String readData(String address){
        		if (index.contains(address)) {
                        int i = index.indexOf(address);
                        return memory.get(i);
                } else {
                        return "null";
                }
        }
        
        public void writeData(int address, String data){
                index.add(address);
                memory.add(data);
        }
}