package org.frameworkset.tran.es.input.db;
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

import com.frameworkset.common.poolman.ConfigSQLExecutor;
import com.frameworkset.common.poolman.SQLExecutor;
import org.frameworkset.elasticsearch.entity.ESDatas;
import org.frameworkset.elasticsearch.scroll.HandlerInfo;
import org.frameworkset.tran.context.ImportContext;
import org.frameworkset.tran.db.DBImportContext;
import org.frameworkset.tran.es.BaseESExporterScrollHandler;
import org.frameworkset.tran.schedule.TaskContext;

import java.util.List;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2019/1/11 15:19
 * @author biaoping.yin
 * @version 1.0
 */
public class ESDirectExporterScrollHandler<T> extends BaseESExporterScrollHandler<T> {
	protected ConfigSQLExecutor configSQLExecutor;
	protected DBImportContext es2DBContext ;
	protected TaskContext taskContext;
//	private ESTranResultSet esTranResultSet ;
	public ESDirectExporterScrollHandler(TaskContext taskContext,ImportContext importContext, ImportContext targetImportContext, ConfigSQLExecutor configSQLExecutor ) {
		super(  importContext,targetImportContext);
		this.configSQLExecutor = configSQLExecutor;
		this.es2DBContext = (DBImportContext)targetImportContext;
		this.taskContext = taskContext;
	}

	public void handle(ESDatas<T> response, HandlerInfo handlerInfo) throws Exception {//自己处理每次scroll的结果

//		ES2DBDataTran es2DBDataTran = new ES2DBDataTran(esTranResultSet,importContext);



		long totalSize = response.getTotalSize();
		List<T> datas = response.getDatas();
		int batchNo = importContext.getExportCount().increamentCount();
		if(totalSize == 0 || datas == null || datas.size() == 0){
			if(logger.isInfoEnabled()){
				logger.info("Igonre Execute export task {}:zero or null datas.",batchNo);
			}
			return;

		}
		final int batchSize = importContext.getStoreBatchSize();
		if(logger.isInfoEnabled()){
			logger.info("Execute task {} start.",batchNo);
		}
//		DBConfig targetDB = es2DBContext.getTargetDBConfig(taskContext);
//		if(targetDB == null)
//			targetDB = importContext.getDbConfig();
		String targetDBName = es2DBContext.getTargetDBName(taskContext);
		if(targetDBName == null){
			targetDBName = importContext.getTargetDBName();
		}
		if(es2DBContext.getSql() == null) {
			configSQLExecutor.executeBatch(targetDBName, es2DBContext.getSqlName(),
					datas, batchSize, es2DBContext.getBatchHandler());
		}
		else{
			SQLExecutor.executeBatch(targetDBName,
					es2DBContext.getSql(), datas, batchSize, es2DBContext.getBatchHandler());
		}
		if(logger.isInfoEnabled()){
			logger.info("Execute task {} complete and export data {} record.",batchNo,datas.size());
		}

	}


}
