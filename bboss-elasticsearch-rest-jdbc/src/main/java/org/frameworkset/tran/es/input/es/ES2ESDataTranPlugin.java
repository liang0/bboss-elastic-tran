package org.frameworkset.tran.es.input.es;
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

import org.frameworkset.tran.BaseDataTran;
import org.frameworkset.tran.DataTranPlugin;
import org.frameworkset.tran.TranResultSet;
import org.frameworkset.tran.context.ImportContext;
import org.frameworkset.tran.es.input.ESInputPlugin;
import org.frameworkset.tran.es.output.AsynESOutPutDataTran;
import org.frameworkset.tran.schedule.Status;
import org.frameworkset.tran.schedule.TaskContext;

import java.util.concurrent.CountDownLatch;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2019/10/9 14:35
 * @author biaoping.yin
 * @version 1.0
 */
public class ES2ESDataTranPlugin  extends ESInputPlugin implements DataTranPlugin {

	@Override
	protected BaseDataTran createBaseDataTran(TaskContext taskContext, TranResultSet jdbcResultSet, CountDownLatch countDownLatch, Status currentStatus){
		AsynESOutPutDataTran asynESOutPutDataTran = new AsynESOutPutDataTran(  taskContext,jdbcResultSet,importContext,   targetImportContext,
				targetImportContext.getTargetElasticsearch(),countDownLatch,  currentStatus);
		asynESOutPutDataTran.init();
		return asynESOutPutDataTran;
	}
	@Override
	protected void doBatchHandler(TaskContext taskContext){

	}

	public ES2ESDataTranPlugin(ImportContext importContext,ImportContext targetImportContext){
		super(  importContext,  targetImportContext);


	}

	@Override
	public void beforeInit() {
		super.beforeInit();
//		this.initDS(importContext.getDbConfig());
		initOtherDSes(importContext.getConfigs());

	}


}
