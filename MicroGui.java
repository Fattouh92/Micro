import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class MicroGui extends javax.swing.JFrame {

	/**
	 * Creates new form MicroGui
	 */
	public MicroGui() {
		initComponents();
	}

	private void initComponents() {

		fileChooser = new javax.swing.JFileChooser();
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
		jLabel4 = new JLabel();
		jLabel5 = new JLabel();
		jLabel6 = new JLabel();
		cache_level = new JTextField();
		spec_cache = new JTextField();
		ins_cache_level = new JTextField();
		spec_ins_cache_level = new JTextField();
		spec_hrd_org = new JTextField();
		st_addr = new JTextField();
		Browse_ins_btn = new JButton();
		run_prog_btn = new JButton();
		jScrollPane1 = new JScrollPane();
		textarea = new JTextArea();
		jLabel7 = new JLabel();
		mem_data_req = new JTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Micro Program");

		jLabel1.setText("The number of cache level to simulate:");

		jLabel2.setText("specifications for the cache :");

		jLabel3.setText("the number of instruction cache levels to simulate:");

		jLabel4.setText("specifications for the instruction cache:");

		jLabel5.setText("specifications for the hardware organization:");

		jLabel6.setText("the start address:");

		Browse_ins_btn.setText("Instructions file");
		Browse_ins_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Browse_ins_btnActionPerformed(evt);
			}
		});

		run_prog_btn.setText("Run");
		run_prog_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				run_prog_btnActionPerformed(evt);
			}
		});

		textarea.setColumns(20);
		textarea.setRows(5);
		jScrollPane1.setViewportView(textarea);

		jLabel7.setText("the memory data required:");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														cache_level,
														javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(spec_cache)
												.addComponent(ins_cache_level)
												.addComponent(
														spec_ins_cache_level)
												.addComponent(spec_hrd_org)
												.addGroup(
														layout.createSequentialGroup()
																.addContainerGap()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										jLabel6)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE)
																								.addComponent(
																										st_addr,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										73,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										jLabel7)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										mem_data_req,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										328,
																										javax.swing.GroupLayout.PREFERRED_SIZE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(10,
																										10,
																										10)
																								.addComponent(
																										Browse_ins_btn,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										135,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE)
																								.addComponent(
																										run_prog_btn,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										227,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addGap(128,
																										128,
																										128)))))
								.addContainerGap())
				.addComponent(jScrollPane1)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel1)
												.addComponent(jLabel2)
												.addComponent(jLabel3)
												.addComponent(jLabel4)
												.addComponent(jLabel5))
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jLabel1)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(cache_level,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jLabel2)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(spec_cache,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(4, 4, 4)
								.addComponent(jLabel3)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(ins_cache_level,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jLabel4)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(spec_ins_cache_level,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jLabel5)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(spec_hrd_org,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										33,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel6)
												.addComponent(
														st_addr,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														30,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel7)
												.addComponent(
														mem_data_req,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(Browse_ins_btn)
												.addComponent(run_prog_btn))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										239, Short.MAX_VALUE)));

		pack();
	}

	private void run_prog_btnActionPerformed(java.awt.event.ActionEvent evt) {
		int cacheLevels = Integer.parseInt(cache_level.getText());

		switch (cacheLevels) {
		case 1:
			System.out
					.println("Please Enter your specifications for the cache in the following format: "
							+ "first level s,l,m, "
							+ "first level hit_policy (1=write_throught 2=write_back),"
							+ "first level miss policy (1=write allocate 2=write around),"
							+ "first level cycles to access data,"
							+ "first level memory access time (in cycles)");
			cacheSpecifications = spec_cache.getText().split(",");
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
			cacheSpecifications = spec_cache.getText().split(",");
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
			cacheSpecifications = spec_cache.getText().split(",");
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
				.println("Please Enter the number of instruction cache levels	you	want to	simulate.");
		int icacheLevels = Integer.parseInt(ins_cache_level.getText());
		switch (icacheLevels) {
		case 1:
			System.out
					.println("Please Enter your specifications for the instruction cache in the following format: "
							+ "first level s,l,m, "
							+ "first level hit_policy (1=write_throught 2=write_back),"
							+ "first level miss policy (1=write allocate 2=write around),"
							+ "first level cycles to access data,"
							+ "first level memory access time (in cycles)");
			cacheSpecifications = spec_ins_cache_level.getText().split(",");
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
			cacheSpecifications = spec_ins_cache_level.getText().split(",");
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
			cacheSpecifications = spec_ins_cache_level.getText().split(",");
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
		String[] tomasuloSpecifications = spec_hrd_org.getText().split(",");
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
				Integer.parseInt(tomasuloSpecifications[16]),
				Integer.parseInt(tomasuloSpecifications[17]));
		System.out
				.println("Please enter the start address then write your program in the file.");
		startAddress = Integer.parseInt(st_addr.getText());
		instructions = assembler.assemble(file);
		for (int i = 0; i < instructions.length; i++) {
			memory.writeData(Integer.toBinaryString(startAddress),
					instructions[i]);
			startAddress += 2;
		}
		for (int q = 0; q <= assembler.returnRegisters().size(); q++) {
			if (!registers.contains(assembler.returnRegisters().get(q))) {
				Register r = new Register(assembler.returnRegisters().get(q), 0);
				registers.add(r);
			}
		}
		System.out
				.println("Please enter the memory data required for your program in the form (value, address), if no data, enter (no data).");
		String[] memData = mem_data_req.getText().split(",");
		if (!memData[0].equals("no data")) {
			for (int d = 0; d < memData.length; d += 2) {
				memory.writeData(memData[d + 1], memData[d]);
			}
		}
		tomasulo.start(registers, icache, cache, memory, pc);
		textarea.append("the number of cycles is : " + tomasulo.cycles);
		System.out.println(tomasulo.cycles);

	}

	private void Browse_ins_btnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Browse_ins_btnActionPerformed
		// TODO add your handling code here:

		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			System.out.println(file.getAbsolutePath());
			try {

				textarea.read(new FileReader(file.getAbsolutePath()), null);
			} catch (IOException ex) {
				System.out.println("problem accessing file"
						+ file.getAbsolutePath());
			}
		} else {
			System.out.println("File access cancelled by user.");
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MicroGui.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MicroGui.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MicroGui.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MicroGui.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MicroGui().setVisible(true);
			}
		});
	}

	public File file;
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

	private JButton Browse_ins_btn;
	private JTextField cache_level;
	private JFileChooser fileChooser;
	private JTextField ins_cache_level;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JScrollPane jScrollPane1;
	private JTextField mem_data_req;
	private JButton run_prog_btn;
	private JTextField spec_cache;
	private JTextField spec_hrd_org;
	private JTextField spec_ins_cache_level;
	private JTextField st_addr;
	private JTextArea textarea;
}
