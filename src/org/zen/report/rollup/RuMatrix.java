package org.zen.report.rollup;

import java.util.ArrayList;
import java.util.List;

public class RuMatrix {
	private List<RuRow> rows = new ArrayList<RuRow>();
	
	public RuMatrix()
	{
	}

	public List<RuRow> getRows() {
		return rows;
	}

	public void setRows(List<RuRow> rows) {
		this.rows = rows;
	}
	

}
