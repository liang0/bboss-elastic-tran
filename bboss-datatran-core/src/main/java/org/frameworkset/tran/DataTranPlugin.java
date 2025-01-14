package org.frameworkset.tran;
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

import com.frameworkset.orm.annotation.BatchContext;
import org.frameworkset.tran.context.Context;
import org.frameworkset.tran.context.ImportContext;
import org.frameworkset.tran.schedule.ScheduleAssert;
import org.frameworkset.tran.schedule.ScheduleService;
import org.frameworkset.tran.schedule.Status;
import org.frameworkset.tran.schedule.TaskContext;

import java.util.List;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2019/10/9 14:34
 * @author biaoping.yin
 * @version 1.0
 */
public interface DataTranPlugin {
	public boolean isEnableAutoPauseScheduled();
	public boolean isSchedulePaussed(boolean autoPause);
	public ScheduleAssert getScheduleAssert();
	public void setScheduleAssert(ScheduleAssert scheduleAssert);
	public void preCall(TaskContext taskContext);
	public void afterCall(TaskContext taskContext);
	public void init(ImportContext importContext,ImportContext targetImportContext);

		public void throwException(TaskContext taskContext,Exception e);
	public Context buildContext(TaskContext taskContext,TranResultSet jdbcResultSet, BatchContext batchContext);
	public void forceflushLastValue(Status currentStatus);
	public  void handleOldedTasks(List<Status> olded );
	public  void handleOldedTask(Status olded );
		boolean assertCondition();

	void setErrorWrapper(TranErrorWrapper tranErrorWrapper);

	void doImportData(TaskContext taskContext)  throws ESDataImportException;
	void importData() throws ESDataImportException;
	public String getLastValueVarName();
	ScheduleService getScheduleService();
	ImportContext getImportContext();
	public void setImportContext(ImportContext importContext);

	public Long getTimeRangeLastValue();

	void flushLastValue(Object lastValue,Status currentStatus,boolean reachEOFClosed);




	void destroy(boolean waitTranStop);

	public void setHasTran();
	public void setNoTran();
	public boolean isPluginStopAppending();
	boolean isPluginStopREADY();
	void init();

//	Object getValue(String columnName) throws ESDataImportException;
//
//	Object getDateTimeValue(String columnName) throws ESDataImportException;
	void setForceStop();
//	public Object getLastValue() throws ESDataImportException;


	String getLastValueClumnName();

	boolean isContinueOnError();

	Status getCurrentStatus();

	ExportCount getExportCount();

	boolean isMultiTran();
}
