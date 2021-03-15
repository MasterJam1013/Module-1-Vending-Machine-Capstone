package com.techelevator;

import com.techelevator.view.MenuDrivenCLI;

public class Application {
 //inventory test:
//	public static void main(String[] args){
//		VendingMachine sut = new VendingMachine();
//		sut.makeOpeningInventory();
//		sut.listAllItems();
//		sut.feedMoney(4);
//		sut.printAuditFile();
//		sut.purchaseItemFromVendingMachine();
//		sut.feedMoney(4);
//		sut.purchaseItemFromVendingMachine();
//		sut.makeChange();
//		sut.printAuditFile();
//		sut.printSalesReport();
//	}


	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_SECRET_SALES_REPORT = "";

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction (makes change)";


	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SECRET_SALES_REPORT };
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT,PURCHASE_MENU_OPTION_FINISH_TRANSACTION };

	private final MenuDrivenCLI ui = new MenuDrivenCLI();
	private VendingMachine myVendingMachine = new VendingMachine();


	public static void main(String[] args) {
		Application application = new Application();

		application.run();
	}

	public void run() {

		myVendingMachine.makeOpeningInventory();
		//set boolean for while loop to exit program.
		while (true) {
			String selection = ui.promptForSelection(MAIN_MENU_OPTIONS);
			//MAKE BELOW INTO A SWITCH STATEMENT
			switch(selection) {
				case MAIN_MENU_OPTION_DISPLAY_ITEMS:
				myVendingMachine.listAllItems();
				break;
				case MAIN_MENU_OPTION_PURCHASE:
				runPurchaseMenu();
				break;
				case MAIN_MENU_OPTION_EXIT:
				break;
				case MAIN_MENU_OPTION_SECRET_SALES_REPORT:
				myVendingMachine.printSalesReport();
				break;
			}


		}
	}

	private void runPurchaseMenu() {
		String selection = ui.promptForSelection(PURCHASE_MENU_OPTIONS);
		switch(selection) {
			case PURCHASE_MENU_OPTION_FEED_MONEY:
				System.out.println("Enter number of whole dollars to feed into machine.");
				ui.pauseOutput();

//				myVendingMachine.feedMoney();
				break;
			case MAIN_MENU_OPTION_PURCHASE:
				runPurchaseMenu();
				break;
			case MAIN_MENU_OPTION_EXIT:
				break;
		}
	}


}
