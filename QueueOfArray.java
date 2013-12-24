
public class QueueOfArray {
		int maxSize; // Maximum Capacity for the queue
		String[][] queArray; // The array that holds the items
		int front;
		int rear;
		int nItems;
		// constructor
		public QueueOfArray(int height, int width) {
			maxSize = height; // set array size
			queArray = new String[maxSize][width]; // create array
			front = 0;
			rear = -1;
			nItems = 0; // no items yet
		} // end of constructor
		public void enqueue(String[] i) {
			if (rear == maxSize - 1)
			rear = -1;
			queArray[++rear] = i; // increment rear, insert item
			nItems++;
		}
		public String[] dequeue() {
			String[] result = queArray[front]; // get value
			front++; // increment front
			if (front == maxSize) // deal with wraparound
			front = 0;
			nItems--;
			return result;
		}
		public String[] peek() {
			return queArray[front];
		}
		public boolean isEmpty() {
			return (nItems == 0);
		}
		public boolean isFull() {
			return (nItems == maxSize);
		}
		public int size() {
			return nItems;
		}
		public String get(int row, int coloumn){
			return queArray[row][coloumn];
		}
		public String[] getArray(int index){
			return queArray[index];
		}
		public void modify(int row, int coloumn, String val){
			queArray[row][coloumn] = val;
		}
}
