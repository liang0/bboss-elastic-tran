package org.frameworkset.tran.output.es;
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

import org.frameworkset.tran.BaseDataTran;
import org.frameworkset.tran.TranResultSet;
import org.frameworkset.tran.context.ImportContext;
import org.frameworkset.tran.es.output.AsynESOutPutDataTran;
import org.frameworkset.tran.input.file.FileBaseDataTranPlugin;
import org.frameworkset.tran.schedule.Status;
import org.frameworkset.tran.schedule.TaskContext;

/**
 * <p>Description:采集日志数据并导入到elasticsearch插件，支持增量和全量导入 </p>
 * <p></p>
 * <p>Copyright (c) 2020</p>
 * @Date 2021/3/29 11:25
 * @author yin-bp@163.com
 * @version 1.0
 */
public class FileLog2ESDataTranPlugin extends FileBaseDataTranPlugin {
	public FileLog2ESDataTranPlugin(ImportContext importContext, ImportContext targetImportContext) {
		super(importContext, targetImportContext);
	}

	@Override
	protected BaseDataTran createBaseDataTran(TaskContext taskContext, TranResultSet jdbcResultSet, Status currentStatus) {

		AsynESOutPutDataTran asynESOutPutDataTran = new AsynESOutPutDataTran( taskContext,jdbcResultSet,importContext,targetImportContext,  currentStatus);
		asynESOutPutDataTran.initTran();
		return asynESOutPutDataTran;
	}
}
