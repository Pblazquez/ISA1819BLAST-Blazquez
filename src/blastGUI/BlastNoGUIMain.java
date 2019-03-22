package blastGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import blast.BlastController;

public class BlastNoGUIMain {
	
	private static final String dataBaseFile = new String("yeast.aa");
	private static final String dataBaseIndexes = new String("yeast.aa.indexs");
	
	public static void main(String args[]){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowGUI();
			}
		});
	}
	
	private static void createAndShowGUI() {
		//JFRAME
		
		JFrame frame= new JFrame("BLAST"); // creamos el JFrame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(650, 400);//tamaño del JFrame

		JPanel panel1= new JPanel(); //creamos el primer JPanel
		panel1.setLayout(new FlowLayout());
		panel1.setBackground(Color.WHITE);
		JPanel panel2= new JPanel();//creamos el segundo JPanel
		panel2.setLayout(new FlowLayout());
		panel2.setBackground(Color.RED);
		JPanel panel3= new JPanel();//creamos el tercero JPanel
		panel3.setLayout(new FlowLayout());
		panel3.setBackground(Color.BLUE);
		JPanel panel4= new JPanel();//creamos el cuarto JPanel
		panel4.setLayout(new FlowLayout());
		panel4.setBackground(Color.LIGHT_GRAY);
		
		//añado cada uno de los paneles y los coloco en la posición que yo quiero
		frame.getContentPane().add(panel1, BorderLayout.PAGE_START);
		frame.getContentPane().add(panel2, BorderLayout.LINE_START);
		frame.getContentPane().add(panel3, BorderLayout.LINE_END);	
		frame.getContentPane().add(panel4, BorderLayout.PAGE_END);
		
		//PANEL 1
		
		JRadioButton b1= new JRadioButton("Proteínas");//creamos los dos RadioButtons
		JRadioButton b2= new JRadioButton("Secuencias nucleotidos");
		
		//creo una acción que si un RadioButton este pulsado el otro deje de estar pulsado automaticamente
		b1.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				if(b1.isSelected()) {
					b2.setSelected(false);
				}
			}
		});
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(b2.isSelected()) {
					b1.setSelected(false);
				}
			}
		});
				
		
		
		JLabel label1= new JLabel("Elige una: ");// creo un Label que indique que se puede hacer en este panel
		
		//añado los botones y el label al primer panel
		panel1.add(label1);
		panel1.add(b1);
		panel1.add(b2);
		
		//PANEL 2
		
		JComboBox<String> box = new JComboBox<String>(); // creo un JComboBox para las secuencias
		box.setEditable(true);// permite que la secuuencia sea escrita por el usuario
		
		//creo una acción que al escribir una secuencia esta se guarde automaticamente como una de las opciones a elegir del JComboBox
		box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String secuencia= box.getEditor().getItem().toString();
				box.addItem(secuencia);
			}
		});
		JLabel label2= new JLabel("Elija una opción o escriba una:");// creo un label que indique que se puede hacer en este panel
		
		//añado el JComboBox y el JLabel al panel 2
		panel2.add(label2);
		panel2.add(box);
		
		//PANEL 3
		
		JLabel label3= new JLabel("Introduzca un porcentaje de consulta (únicamente un número del 0 al 1): ");//// creo un label que indique que se puede hacer en este panel
		
		JTextField porcentaje= new JTextField("1",3); // creo un TextArea donde se pueda poner el porcentaje de consulta
		porcentaje.setEditable(true);// permite que el porcentaje sea escrito por el usuario
		
		//añado el label y el JTextArea al panel 3
		panel3.add(label3);
		panel3.add(porcentaje);
		
		//PANEL 4
		
		JButton bfinal= new JButton("Pulse para ver el resultado");// creo un botón para que el usuario pueda ver el resultado
		JTextArea resultado= new JTextArea(10,30);// creo un JTextArea donde el usuario puede ver el resultado
		
		JScrollPane scrollup = new JScrollPane(resultado);//asignamos la barra al cuadro del resultado
		scrollup.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel4.add(scrollup);
		
		//añado el JButton y el JTextArea al panel 4
		panel4.add(bfinal);		
		
		
		//creo una acción en la que se obtenga el resultado final
		bfinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float porcentaj=Float.parseFloat(porcentaje.getText()); // paso el porcentaje escrito del JTextBox a un valor float
				String seq= box.getSelectedItem().toString(); //guardo en seq lo que el usuario ha escrito en el JComboBox
				BlastController bCnt = new BlastController();
				try {
					if(b2.isSelected()) { // si está seleccionado la opción secuencia de proteínas 
						resultado.setText(bCnt.blastQuery('n', dataBaseFile, dataBaseIndexes, porcentaj, seq));
					}else {// si está seleccioando la opción secuencia de nucleotidos o no está seleccionada ninguna opción
						resultado.setText(bCnt.blastQuery('p', dataBaseFile, dataBaseIndexes, porcentaj, seq));
					}
				} catch(Exception exc){// si da un valor error
					resultado.setText("No existe la opción dada");
				}
					
			}
				
		});				
	}
	
}