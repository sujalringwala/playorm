package com.alvazan.orm.parser.antlr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alvazan.orm.api.z8spi.meta.DboTableMeta;
import com.alvazan.orm.api.z8spi.meta.TypeInfo;
import com.alvazan.orm.api.z8spi.meta.ViewInfo;

public class InfoForWiring {

	private Map<String,TypeInfo> parameterFieldMap = new HashMap<String, TypeInfo>();
	private ViewInfoImpl noAliasTable;
	private Map<String, ViewInfoImpl> aliasToMeta = new HashMap<String, ViewInfoImpl>();
	private boolean selectStarDefined;
	private String query;
	private String targetTable;
	private ExpressionNode astTree;
	private Map<String, Integer> attributeUsedCount = new HashMap<String, Integer>();
	private DboTableMeta metaQueryTargetTable;
	private List<ViewInfo> targetTables = new ArrayList<ViewInfo>();
	private List<ViewInfo> aliases = new ArrayList<ViewInfo>();
	
	public InfoForWiring(String query, String targetTable) {
		this.query = query;
		this.targetTable= targetTable;
	}
	
	public void setNoAliasTable(ViewInfoImpl metaClass) {
		this.noAliasTable = metaClass;
	}

	public ViewInfoImpl getNoAliasTable() {
		return noAliasTable;
	}

	public void putAliasTable(String alias, ViewInfoImpl metaClass) {
		aliases.add(metaClass);
		aliasToMeta.put(alias, metaClass);
	}

	public ViewInfoImpl getInfoFromAlias(String alias) {
		return aliasToMeta.get(alias);
	}

	public void setSelectStarDefined(boolean defined) {
		selectStarDefined = defined;
	}

	public boolean isSelectStarDefined() {
		return selectStarDefined;
	}

	public String getQuery() {
		return query;
	}

	public String getTargetTable() {
		return targetTable;
	}

	public void setAstTree(ExpressionNode node) {
		this.astTree = node;
	}

	public ExpressionNode getAstTree() {
		return astTree;
	}

	public void incrementAttributesCount(String attributeName) {
		int count = 0;
		Integer counter = attributeUsedCount.get(attributeName);
		if(counter != null) {
			count = counter;
		}
		count++;
		attributeUsedCount.put(attributeName, count);
	}

	public Map<String, Integer> getAttributeUsedCount() {
		return attributeUsedCount;
	}

	public List<ViewInfo> getAllViews() {
		return aliases;
	}

	public void setMetaQueryTargetTable(DboTableMeta metaClass) {
		this.metaQueryTargetTable= metaClass;
	}

	public DboTableMeta getMetaQueryTargetTable() {
		return metaQueryTargetTable;
	}

	public Map<String,TypeInfo> getParameterFieldMap() {
		return this.parameterFieldMap;
	}

	public List<ViewInfo> getTargetTables() {
		return targetTables;
	}

	public void addTargetTable(ViewInfoImpl info) {
		this.targetTables.add(info);
	}

}