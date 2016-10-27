package ar.com.lodev.medical_manager.ui.model;

import java.util.List;

import ar.com.lodev.medical_manager.model.dto.BaseEntityDTO;

public class DataTableData {
	
	private long draw = 1;
	private long recordsTotal = 0;
	private long recordsFiltered = 0;
	private List<? extends BaseEntityDTO> data;
	
	protected DataTableData(){
		
	}
	
	public DataTableData(long draw,long recordsTotal,long recordsFiltered,List<? extends BaseEntityDTO> data){
		this.draw = draw;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		this.data = data;
	}

	public long getDraw() {
		return draw;
	}

	public void setDraw(long draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<? extends BaseEntityDTO> getData() {
		return data;
	}

	public void setData(List<? extends BaseEntityDTO> data) {
		this.data = data;
	}
	
	
	
}
