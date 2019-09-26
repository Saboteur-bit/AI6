package ccc.interaction.global;

import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JDialog;

import ccc.interaction.global.addons.ANPQuestionHolder;
import ccc.interaction.internalFeatures.soundBoard;
import ccc.mainComponent.AIname;
import ccc.mainComponent.globalID;
import ccc.mainComponent.experimental.DEBUG;
import servicePackage.stabilizer;

public class autoNotificationPusher {
	private  boolean notiBox = false;
	private  boolean notiFrame = false; //default is false
	//change to fix auto without Frame activation
	private  boolean autoed = false;
	private  boolean temp_Disable = false;
	
	private  boolean exprParallel = false;
	private  String exprStringText = "";
	private  Class exprClassStore;
	private  String exprTitle = "";
	
	@SuppressWarnings("static-access")
	public  void checkActive() {
		if(temp_Disable == false) {
			notiBox = globalInteractionService.TrayBoxAC;
			notiFrame = globalInteractionService.FrameAC; //name sensitive
		}
		if(stabilizer.getStabilizerValue() == true && exprParallel == true) {popUpBox.warningBox("Automatic Pop-Up reader may cause overlay sound produce.", "Unstable - Expr System");}
	}
	
	public  void notPush(String type, String Title, String info, Class classObj, String selector) {
		exprParallel = true;
		pushSteam(type, Title, info, classObj, selector, "" , false, null);
	}
	
	public  void notPush(String type, String Title, String info, Class classObj, String selector, String extendedTitle) {
		exprParallel = true;
		pushSteam(type, Title, info, classObj, selector, extendedTitle, false, null);
	}
	
	public  void notPush(String type, String Title, String info, Class classObj, String selector, String extendedTitle, boolean b, boolean exprParallelValue) {
		exprParallel = exprParallelValue;
		pushSteam(type, Title, info, classObj, selector, extendedTitle, false, null);
	}
	
	public  String notPushQuestionYN(String type, String Title) {
		ANPQuestionHolder getHolder = new ANPQuestionHolder();
		
			//if(getHolder.equals("NO")) {System.out.println("HI");}
		return getHolder.questionYN(type, Title);
	}
	
	private  void pushSteam(String type, String Title, String info, Class classObj, String selector, String customNotification , boolean playSound, Exception e) {
		checkActive();
		if((notiBox == true && autoed == false) && (selector.equals("TRAY") || selector.equals("AUTO")) && stabilizer.getAddOnsStable() == true) {
			autoed = true;
			MessageType storeType = MessageType.INFO; //default
			if(info.equals("WARNING")) {
				storeType = MessageType.WARNING;
			}else if(type.equals("INFO")){
				storeType = MessageType.INFO;
			}else if(type.equals("ERROR")) {
				storeType = MessageType.ERROR;
			}else if(type.equals("ALERT")) {
				globalID.addErrorCount(e);
				globalID.errorPopC++;
			}else if(type.equals("")) {
				if(stabilizer.getASValue() == true) {
					storeType = MessageType.NONE;
					notPush("INFO", "Internal notificationSystem", "stabilizer executed.", classObj, "TRAY");
				}
			}
			autoed = false;
			globalInteractionService.pushNotiTray(customNotification, Title, info, storeType);
			//if cN is null or empty it will not add Semicolon.
		}else if((notiFrame == true && autoed == false) && (selector.equals("FRAME") || selector.equals("AUTO")) && stabilizer.getASValue() == false){
			autoed = true;
			notificationFrame.generate(info);
			autoed = false;
		}else if((notiFrame == false && autoed == false) && (selector.equals("FRAME") || selector.equals("AUTO")) && stabilizer.getAddOnsStable() == false){
			//Frame needs to be activate first.
			String setting = notPushQuestionYN("Would you like to turn NotiFram on?", "Unable to wake notiFrame.");
			if(setting.equals("YES")) {
				autoed = true;
				notificationFrame.activation=true;
				notificationFrame.enable();
				notiFrame = true;
				notificationFrame.generate(Title+info);
				if(stabilizer.getASValue() == true) {
					JDialog.setDefaultLookAndFeelDecorated(true);
					popUpBox.warningBox("Stabilizing attempted on notification frame core.", "Stabilizer Report");
					JDialog.setDefaultLookAndFeelDecorated(false);
					temp_Disable = true; //for some Reason.class the Code keep getting wrong value.
				}
					autoed = false;
			}else if(setting.equals("NO")) {
				if(stabilizer.getASValue()==true) {
					JDialog.setDefaultLookAndFeelDecorated(true);
					popUpBox.alertError("Local code stabilizer can't handle this option yet.", "Stabilizing Error");
					JDialog.setDefaultLookAndFeelDecorated(false);
				}
				autoed = true;
				if(!customNotification.equals("")) {Title = customNotification + " : " + Title;}
				popUpAlt(type, Title, info, classObj);
			}
		}else {
			if(!customNotification.equals("")) {Title = customNotification +  " : " + Title;}
			popUpAlt(type, Title, info, classObj);
			}
	}
	
	private  void popUpAlt(String type, String Title, String info, Class classObj) {
		if(type.equals("INFO")) {
			if(exprParallel==false) {popUpBox.infoBox(info, classObj, Title);}
			else{
				ANPCallableExpr autoExpr = new ANPCallableExpr();
				autoExpr.CallableStore("popUpInfo", Title , info);}}
		else if(type.equals("WARNING")){
			if(exprParallel==false) {popUpBox.warningBox(info, Title);}
			else{
				ANPCallableExpr autoExpr = new ANPCallableExpr();
				autoExpr.CallableStore("popUpWarning", Title , info);}}
		else if (type.equals("ALERT")) {
			//if(exprParallel==false) {popUpBox.alertError(info, Title,true);}
			if(exprParallel==false) {popUpBox.alertError(info, Title);}
			else{
				ANPCallableExpr autoExpr = new ANPCallableExpr();
				autoExpr.CallableStore("popUpError", Title , info);}}
		autoed = false;
	}
	

	public  void notPush(String string, String invalidValue, String string2, Class<Class> class1, String string3,
			String string4, boolean b) {
		pushSteam(string, invalidValue, string2, class1, string3, string4, b, null);
		exprParallel = true;
	}

	public  String getExprTitle() {
		return exprTitle;
	}

	public  void setExprTitle(String exprTitle) {
		autoNotificationPusher.exprTitle = exprTitle;
	}

	public  String getExprStringText() {
		return exprStringText;
	}

	public  void setExprStringText(String exprStringText) {
		autoNotificationPusher.exprStringText = exprStringText;
	}

	public  Class getExprClassStore() {
		return exprClassStore;
	}

	public  void setExprClassStore(Class exprClassStore) {
		autoNotificationPusher.exprClassStore = exprClassStore;
	}

	
}
