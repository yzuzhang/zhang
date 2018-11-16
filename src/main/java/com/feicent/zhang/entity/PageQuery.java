package com.feicent.zhang.entity;

import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Map;

public class PageQuery {

	public static final String CURRENT_PAGE = "page";
	public static final String DIRECTION_ASC = "ASC";
	public static final String DIRECTION_DESC = "DESC";

	private int currentPageNo;
	private int pageSize = 20;
	private int startIndex;
	private int startRow;
	private long totalCount;
	private int pageCount;
	private String orderBy;
	private String direction;

	/**
	 * 防止对象被json化或者序列化
	 */
	private transient Map<String, Object> params = new HashMap<String, Object>();

	public PageQuery() {
		this(20);
	}

	public PageQuery(int pageSize) {
		//this.params = new HashMap<String, Object>();
		setPageSize(pageSize);
	}

	
	public PageQuery(int pageSize, Map<String, Object> params) {
		this.params = params;
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if(params!=null){
			params.put("pageSize", pageSize);
		}
	}

	public int getStartIndex() {
		startIndex = (getCurrentPageNo() - 1) * pageSize;
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		this.init();
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}


	public int getCurrentPageNo() {
		if(currentPageNo != 0){
			return currentPageNo;
		}
		
		String cpn = String.valueOf(this.params.get(PageQuery.CURRENT_PAGE));
		if (StringUtils.isBlank(cpn) || !StringUtils.isNumeric(cpn) || cpn.length() > 11) {
			cpn = "1";
		}
		currentPageNo = Integer.parseInt(cpn);
		if (currentPageNo < 1) currentPageNo = 1;
		if (currentPageNo > pageCount) currentPageNo = pageCount;
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
		if(totalCount != 0){
			this.init();
		}
	}

	/**
	 * pageCount在之后重新设置
	 */
	public void initWithoutTotal() {
		//这里先设置为最大值，如果后期不重新设置回真实总量，分页显示会出现问题
		this.setTotalCount(Integer.MAX_VALUE);
	}

	private void init() {
		pageCount = (int) totalCount / pageSize;
		if (totalCount % pageSize > 0 || pageCount == 0) {
			pageCount++;
		}
		
		// 把与分页相关的数据放入Map用于查询
		params.put("startIndex", getStartIndex());
		params.put("startRow", getStartIndex());
		params.put("pageSize", getPageSize());
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public int getStartRow() {
		startRow = this.getStartIndex();
        return startRow;
    }
	
	public void setStartRow(int startRow) {
		this.setStartIndex(startRow);
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
		params.put("orderBy", this.getOrderBy());
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
		params.put("sortType", this.getDirection());
	}

	/**
	 * 添加查询参数
	 * 
	 * @param name
	 * @param value
	 */
	public void addQueryParam(String name, Object value) {
		params.put(name, value);
	}

    /**
     * 创建一个PageQuery对象
     * @return
     */
    public static PageQuery create() {
		return new PageQuery();
    }

    public PageQuery put(String key, Object value) {
        this.addQueryParam(key,value);
        return this;
    }

    public PageQuery rows(int size) {
        this.pageSize = size;
        return this;
    }

    /**
     * 判断入参中bKey如果为boolean类型时，返回是否为false,如果bKey中的值不是boolean类型，始终返回true
     * @param bKey
     * @return  返回true表示:bKey中的值是false或者不是boolean类型的值;
     *          返回false表示：bKey中的值是true;
     */
    public boolean isNot(String bKey) {
        Object obj = this.params.get(bKey);
        if(obj != null){
            if(obj instanceof Boolean){
                return !((Boolean) obj);
            }
        }
        return true;
    }

    /**
     * 判断入参中key的值与obj是否相等
     * @param key
     * @param obj
     * @return
     */
    public boolean paramEquals(String key, Object obj) {
        Object val =  params.get(key);
        if(val != null && obj != null){
            return val.equals(obj) || val.toString().equals(obj.toString());
        }
        return false;
    }

	public boolean containsParam(String key) {
		return params==null ? false : params.containsKey(key);
	}


	/**
	 * 获取字符串的参数值
	 * @param key
	 * @return
	 */
	public String getParamString(String key) {
		return params==null ? null : (String)params.get(key);
	}

	/**
	 * 获取参数值
	 * @param key
	 * @return
	 */
	public Object getParamObject(String key) {
		return params==null ? null : params.get(key);
	}

}
