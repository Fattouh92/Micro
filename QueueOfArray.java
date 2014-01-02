public class QueueOfArray {
	int maxSize; // Maximum Capacity for the queue
	String[][] queArray; // The array that holds the items
	int front;
	int rear;
	int nItems;
	int width;

	// constructor
	public QueueOfArray(int height, int width) {
		maxSize = height; // set array size
		this.width = width;
		queArray = new String[maxSize][width]; // create array
		front = 0;
		rear = -1;
		nItems = 0; // no items yet
	} // end of constructor

	public void enqueue(String[] i) {
		// if (rear == maxSize - 1)
		// rear = -1;
		queArray[nItems] = i; // increment rear, insert item
		nItems++;
	}

	public String[] dequeue() {
		String[] result = queArray[0]; // get value
		for (int i = 0; i < nItems - 1; i++) {
			queArray[i] = queArray[i + 1];
		}
		// front++; // increment front
		// if (front == maxSize) // deal with wraparound
		// front = 0;
		nItems--;
		return result;
	}

	@SuppressWarnings("unused")
	public void remove(int index) {
		if (index == 0) {
			dequeue();
		} else {
			String[][] temp = new String[maxSize - 1][width];
			int counter = 0;
			for (int i = 0; i < maxSize; i++) {
				if (i == index) {
					String[] temp2 = dequeue();
				} else {
					temp[counter] = dequeue();
					counter++;
				}
			}
			for (int j = 0; j < maxSize - 1; j++) {
				enqueue(temp[j]);
			}
		}
	}

	public String[] peek() {
		return queArray[0];
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

	public String get(int row, int coloumn) {
		return queArray[row][coloumn];
	}

	public String[] getArray(int index) {
		return queArray[index];
	}

	public void modify(int row, int coloumn, String val) {
		queArray[row][coloumn] = val;
	}
}
