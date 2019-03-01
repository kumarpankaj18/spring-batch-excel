package org.springframework.batch.item.excel.poi;

import org.apache.poi.ss.usermodel.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DefaulltMappingPoiSheet extends PoiSheet {
	
	
	private CellType defaultCellType = CellType.STRING;
	
	public DefaulltMappingPoiSheet(final org.apache.poi.ss.usermodel.Sheet delegate){
		super(delegate);
	}
	
	private static String getCellValueAsString(Cell cell) {
		String strCellValue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					strCellValue = cell.toString();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"dd/MM/yyyy");
						strCellValue = dateFormat.format(cell.getDateCellValue());
					} else {
						Double value = cell.getNumericCellValue();
						Long longValue = value.longValue();
						strCellValue = new String(longValue.toString());
					}
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					strCellValue = new String(new Boolean(
							cell.getBooleanCellValue()).toString());
					break;
				case Cell.CELL_TYPE_BLANK:
					strCellValue = "";
					break;
			}
		}
		return strCellValue;
	}
	
	@Override
	public String[] getRow(final int rowNumber) {
		final Row row = this.delegate.getRow(rowNumber);
		if (row == null) {
			return null;
		}
		final List<String> cells           = new LinkedList<>();
		final int          numberOfColumns = row.getLastCellNum();
		
		for (int i = 0; i < numberOfColumns; i++) {
			Cell cell = row.getCell(i);
			cells.add(getCellValueAsString(cell));
		}
		return cells.toArray(new String[0]);
	}
}
