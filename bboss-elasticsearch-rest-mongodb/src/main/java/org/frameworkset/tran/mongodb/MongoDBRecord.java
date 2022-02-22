package org.frameworkset.tran.mongodb;
/**
 * Copyright 2020 bboss
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.frameworkset.tran.ESDataImportException;
import org.frameworkset.tran.record.BaseRecord;
import org.frameworkset.tran.schedule.TaskContext;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2020</p>
 * @Date 2021/9/23 14:21
 * @author biaoping.yin
 * @version 1.0
 */
public class MongoDBRecord extends BaseRecord {
	private DBObject record;
	public MongoDBRecord(DBObject record,TaskContext taskContext) {
		super(taskContext);
		this.record = record;
	}

	@Override
	public Object getValue(int i, String colName, int sqlType) throws ESDataImportException {
		return getValue(  colName);
	}

	@Override
	public Object getValue(String colName, int sqlType) throws ESDataImportException {
		return getValue(  colName);
	}


	@Override
	public Object getValue(String colName) {
		Object value = record.get(colName);
		if(value != null) {
			if (colName.equals("_id") && value instanceof ObjectId) {
				return ((ObjectId)value).toString();
			}

		}
		return value;
	}

	@Override
	public Object getKeys() {
		return  record.keySet();
	}

	@Override
	public Object getData() {
		return record;
	}

	@Override
	public Object getMetaValue(String metaName) {
		return this.getValue(metaName);
	}

	@Override
	public long getOffset() {
		return 0;
	}

	@Override
	public boolean removed() {
		return false;
	}

	@Override
	public boolean reachEOFClosed() {
		return false;
	}
}
