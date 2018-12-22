package org.zen.simulate;

import org.zen.user.punter.Punter;

public class ModelPrint {

	private Model model;
	
	public ModelPrint(Model model)
	{
		setModel(model);
		System.out.println("");
		System.out.println("ZEN MODEL CONTENTS");
		System.out.println("");
		printPunter(model.getZenModel().getRoot());
		System.out.println("Model population " + model.getZenModel().getPopulation());
		System.out.println("Model revenue $" + model.getZenModel().calcModelRevenue() 
								+ " Max Rating : " + model.getZenModel().getMaxRating());
	}
	
	private void printPunter(Punter punter) {
		printLine(punter);
		for (Punter child : punter.getChildren())
			printPunter(child);
	}

	private void printLine(Punter punter)
	{
		String padding = "";
		for (int i=0; i<=punter.getLevel(); i++)
				padding += " ";
		System.out.println(padding + punter.getContact() + " children : " 
				+ punter.getChildren().size() + " Rating : "
				+ punter.getRating().getRating() 
				+ "  $" + punter.getAccount().getBalance());
			
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
	
	
}
