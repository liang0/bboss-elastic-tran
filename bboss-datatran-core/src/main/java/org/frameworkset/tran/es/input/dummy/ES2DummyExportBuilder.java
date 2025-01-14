package org.frameworkset.tran.es.input.dummy;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.frameworkset.tran.DataStream;
import org.frameworkset.tran.DataTranPlugin;
import org.frameworkset.tran.context.ImportContext;
import org.frameworkset.tran.es.input.ESExportBuilder;
import org.frameworkset.tran.ouput.custom.CustomDummyUtil;
import org.frameworkset.tran.ouput.dummy.DummyOupputConfig;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2019/1/11 21:29
 * @author biaoping.yin
 * @version 1.0
 */
public class ES2DummyExportBuilder extends ESExportBuilder {

	@JsonIgnore
	private DummyOupputConfig dummyOupputConfig;
	@Override
	public DataTranPlugin buildDataTranPlugin(ImportContext importContext,ImportContext targetImportContext){
		return new ES2EDummyDataTranPlugin(  importContext,  targetImportContext);
	}

	@Override
	protected DataStream innerBuilder(){
		DataStream dataStream = super.innerBuilder();
//		this.buildDBConfig();
//		this.buildStatusDBConfig();
		try {
			if(logger.isInfoEnabled()) {
				logger.info("ES2Log Import Configs:");
				logger.info(this.toString());
			}
		}
		catch (Exception e){

		}


		CustomDummyUtil.setTargetImportContext(dummyOupputConfig,dataStream);
		dataStream.setDataTranPlugin(this.buildDataTranPlugin(dataStream.getImportContext(),dataStream.getTargetImportContext()));

		return dataStream;

	}
	public DummyOupputConfig getDummyOupputConfig() {
		return dummyOupputConfig;
	}

	public ES2DummyExportBuilder setDummyOupputConfig(DummyOupputConfig dummyOupputConfig) {
		this.dummyOupputConfig = dummyOupputConfig;

		return this;
	}



}
