package org.frameworkset.tran.input.file;
/**
 * Copyright 2008 biaoping.yin
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

import org.frameworkset.tran.record.CommonMapRecord;

import java.util.Map;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2019/11/19 11:09
 * @author biaoping.yin
 * @version 1.0
 */
public class FileLogRecord extends CommonMapRecord {
	/**
	 * 		   common.put("hostIp", BaseSimpleStringUtil.getIp());
	 *         common.put("hostName",BaseSimpleStringUtil.getHostName());
	 *         common.put("filePath",file.getAbsoluteFile());
	 *         common.put("timestamp",new Date());
	 *         common.put("pointer",pointer);
	 *         common.put("fileId"
	 */
	private Map meta;
	private boolean removed;
	private boolean reachEOFClosed;
	public FileLogRecord( Map meta,Object key, Map<String,Object> record, long offset,boolean reachEOFClosed){
		super(  key,   record,   offset);
		this.meta = meta;
		this.reachEOFClosed = reachEOFClosed;

	}
	@Override
	public boolean reachEOFClosed(){
		return reachEOFClosed;
	}
	public FileLogRecord(Map meta,Map<String,Object> record, long offset,boolean reachEOFClosed){
		super(    record,   offset);
		this.meta = meta;
		this.reachEOFClosed = reachEOFClosed;
	}
	public FileLogRecord(boolean removed, long offset,boolean reachEOFClosed){
		super(    (Map<String,Object>)null,   offset);
		this.removed = removed;
		this.reachEOFClosed = reachEOFClosed;
	}
	@Override
	public boolean removed() {
		return removed;
	}



	@Override
	public Object getMetaValue(String metaName) {
		return meta.get(metaName);
	}


}