package org.zen.simulate;

import org.zen.user.punter.Punter;

public class ModelCodeMaker {

	public static String makeCode(Punter parent)
	{
		if (parent==null)
			return "zen";
		if (parent.getParent()==null)
			return "z" + (parent.getChildren().size()+1);
		String pcode = parent.getEmail().substring(0,parent.getEmail().indexOf('@'));
		pcode += "." + (parent.getChildren().size()+1);
		return pcode;
	}
}
