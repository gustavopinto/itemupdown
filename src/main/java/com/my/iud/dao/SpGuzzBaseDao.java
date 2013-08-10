/*
 * Copyright 2008-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.my.iud.dao;

import java.util.List;

import org.guzz.Guzz;
import org.guzz.GuzzContext;
import org.guzz.dao.GuzzBaseDao;
import org.guzz.exception.GuzzException;
import org.guzz.jdbc.ObjectBatcher;
import org.guzz.transaction.TransactionManager;
import org.guzz.transaction.WriteTranSession;
import org.springframework.beans.factory.annotation.Autowired;

public class SpGuzzBaseDao extends GuzzBaseDao{

	@Override
	@Autowired
	public void setGuzzContext(GuzzContext guzzContext) {
		// TODO Auto-generated method stub
		super.setGuzzContext(guzzContext);
	}
	
    public void batchSave(List<?> batchObject, Object tableCondition){
		 if(batchObject == null || batchObject.size() == 0){
			 throw new GuzzException("batch list is null");
		 }
		 Guzz.setTableCondition(tableCondition);
    	 TransactionManager tm  = super.getTransactionManager();
    	 WriteTranSession session = tm.openRWTran(false) ;
         ObjectBatcher batcher = session.createObjectBatcher() ;
         for(Object saveObject : batchObject){
        	 batcher.insert(saveObject);
         }
         batcher.executeBatch();
         session.commit() ;
         session.close() ;
	}
    
    
	public void batchUpdate(List<?> batchObject, Object tableCondition) {
		if (batchObject == null || batchObject.size() == 0) {
			throw new GuzzException("batch list is null");
		}
		Guzz.setTableCondition(tableCondition);
		TransactionManager tm = super.getTransactionManager();
		WriteTranSession session = tm.openRWTran(false);
		ObjectBatcher batcher = session.createObjectBatcher();
		for (Object updateObject : batchObject) {
			batcher.update(updateObject);
		}
		batcher.executeBatch();
		session.commit();
		session.close();
	}
	
	
}
