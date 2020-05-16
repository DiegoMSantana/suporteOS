package br.com.sabre.support.controller.page;

import java.util.List;

import org.springframework.data.domain.Page;

public class PageWrapper<T> {

	private Page<T> pageWrapper;
	private int draww;

	public PageWrapper(Page<T> page, int draw) {
		this.pageWrapper = page;
		this.draww = draw;
	}

	public int getDraw() {
		return this.draww;
	}

	public List<T> getData() {
		return pageWrapper.getContent();
	}

	public int getLength() {
		return pageWrapper.getSize();
	}

	public int getStart() {
		return pageWrapper.getNumber();
	}

	public Long getRecordsTotal() {
		return pageWrapper.getTotalElements();
	}

	public Long getRecordsFiltered() {
		return pageWrapper.getTotalElements();
	}

}
