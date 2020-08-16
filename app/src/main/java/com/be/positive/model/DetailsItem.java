package com.be.positive.model;

import com.google.gson.annotations.SerializedName;

public class DetailsItem{

	@SerializedName("brand_name")
	private String brandName;

	@SerializedName("id")
	private String id;

	public String getBrandName(){
		return brandName;
	}

	public String getId(){
		return id;
	}
}