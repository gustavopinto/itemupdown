package com.my.iud.shadow;

import org.guzz.exception.GuzzException;
import org.guzz.orm.AbstractShadowTableView;

public class CommonShadowView extends AbstractShadowTableView {

	@Override
	public String toTableName(Object tableCondition) {
		// TODO Auto-generated method stub
		if (tableCondition == null) {
			throw new GuzzException("null table conditon is not allowed.");
		}
		
		long uid = (Long)tableCondition;
	    long i = uid /100 + 1;
		return super.getConfiguredTableName() + "_" + i;
	}

}
