package org.springframework.batch.item.excel.mapping;

import org.springframework.batch.item.excel.Sheet;
import org.springframework.batch.item.excel.support.rowset.*;

import java.util.List;

/**
 * Generic Row set factory with different constructor
 */
public class GenericRowSetFactory implements RowSetFactory {
	
	private ColumnNameExtractor columnNameExtractor;
	
	
	public GenericRowSetFactory(List<String> column) {
		this.columnNameExtractor = new StaticColumnNameExtractor(column.toArray(new String[0]));
	}
	
	public RowSet create(Sheet sheet) {
		DefaultRowSetMetaData metaData = new DefaultRowSetMetaData(sheet, this.columnNameExtractor);
		return new DefaultRowSet(sheet, metaData);
	}
	
	public void setColumnNameExtractor(ColumnNameExtractor columnNameExtractor) {
		this.columnNameExtractor = columnNameExtractor;
	}
}