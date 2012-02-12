package com.pachube.jpachube;

public class Data {

	private String id;

	private String tag;

	private String value;

	private Double minValue;

	private Double maxValue;

	public Data(String id, String tag, String value, Double minValue,
			Double maxValue) {
		super();
		this.id = id;
		this.tag = tag;
		this.value = value;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public Data() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		if (minValue != null) {
			this.minValue = minValue;
		}
	}

	public void setMinValue(String minValue) {
		if (minValue != null) {
			this.minValue = Double.parseDouble(minValue);
		}
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		if (maxValue != null) {
			this.maxValue = maxValue;
		}
	}

	public void setMaxValue(String maxValue) {
		if (minValue != null) {
			this.maxValue = Double.parseDouble(maxValue);
		}
	}

	public String toXMLWithWrapper(){
		String ret = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<eeml xmlns=\"http://www.eeml.org/xsd/005\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"5\" xsi:schemaLocation=\"http://www.eeml.org/xsd/005 http://www.eeml.org/xsd/005/005.xsd\"><environment>";
		ret = ret + this.toXML() + "</environment></eeml>";
		return ret;
	}

	public String toXML() {
		String ret = "";
		ret = "<data id=\"" + this.id + "\">\n\t\t";
		ret = ret + "<tag>" + this.tag + "</tag>\n\t\t";
		ret = ret + "<value ";

		if (this.minValue != null) {
			ret = ret + "minValue=\"" + this.minValue + "\" ";
		}

		if(this.maxValue != null){
			ret = ret + "maxValue=\""+this.maxValue+"\" ";
		}
		ret = ret + ">"+ this.value +"</value>\n\t";

		ret = ret + "</data>";

		return ret;
	}

	@Override
	public String toString() {
		return "Data [id=" + id + ", maxValue=" + maxValue + ", minValue="
				+ minValue + ", tag=" + tag + ", value=" + value + "]";
	}

}
