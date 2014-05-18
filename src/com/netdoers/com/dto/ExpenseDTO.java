/* HISTORY
 * CATEGORY 		:- ACTIVITY
 * DEVELOPER		:- VIKALP PATEL
 * AIM			    :- ADD IPD ACTIVITY
 * DESCRIPTION 		:- SAVE IPD
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION		DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------------------------------------
 */
package com.netdoers.com.dto;

import java.util.ArrayList;

public class ExpenseDTO {
	
	private String _id;
	private String date;
	private String amount;
	private String paument_mode;
	private String description;
	private String category;
	private String status;
	private ArrayList<ExpenseDetailsDTO> paths;
	public ExpenseDTO(String id, String date, String amount,
			String paumentMode, String description, String category,
			String status, ArrayList<ExpenseDetailsDTO> paths) {
		super();
		_id = id;
		this.date = date;
		this.amount = amount;
		paument_mode = paumentMode;
		this.description = description;
		this.category = category;
		this.status = status;
		this.paths = paths;
	}
	public ExpenseDTO() {
		// TODO Auto-generated constructor stub
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String id) {
		_id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPaument_mode() {
		return paument_mode;
	}
	public void setPaument_mode(String paumentMode) {
		paument_mode = paumentMode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList<ExpenseDetailsDTO> getPaths() {
		return paths;
	}
	public void setPaths(ArrayList<ExpenseDetailsDTO> paths) {
		this.paths = paths;
	}
	
	
}
