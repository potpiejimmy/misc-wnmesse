package com.wincor.bcon.wnmesse.server.vo;

import javax.validation.constraints.Size;

public class AppCustomization {
	private byte[] img;
	private String color;
    @Size(min = 1, max = 64)
	private String name;
	private String favfood;
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFavfood() {
		return favfood;
	}
	public void setFavfood(String favfood) {
		this.favfood = favfood;
	}
}
