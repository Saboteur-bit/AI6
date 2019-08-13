package ccc.interaction.global.addons;

//A6 experimental addon for autoNotificationPusher

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import ccc.interaction.global.popUpBox;

public class ANPQuestionHolder extends ccc.interaction.global.popUpBox{
	//private static JDialog holder;
	
	
	public String questionYN(String question, String title) {
		String value = "";
	
		JOptionPane holder = new JOptionPane();
		//holder.setDefaultLookAndFeelDecorated(true);
		
		int askHolder = holder.showConfirmDialog(null,  question, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		if(askHolder == holder.NO_OPTION) {
			value =  "NO";
		}else if(askHolder == holder.YES_OPTION) {
			value = "YES";}
		return value;
	}
}
