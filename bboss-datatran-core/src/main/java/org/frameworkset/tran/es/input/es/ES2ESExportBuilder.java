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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frameworkset.orm.annotation.ESIndexWrapper;
import org.frameworkset.tran.DataStream;
import org.frameworkset.tran.DataTranPlugin;
import org.frameworkset.tran.config.BaseImportConfig;
import org.frameworkset.tran.context.ImportContext;
import org.frameworkset.tran.es.input.ESExportBuilder;
import org.frameworkset.tran.es.output.ESOutputConfig;
import org.frameworkset.tran.es.output.ESOutputContextImpl;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2019/1/11 21:29
 * @author biaoping.yin
 * @version 1.0
 */
public class ES2ESExportBuilder extends ESExportBuilder {
	@JsonIgnore
	private ESOutputConfig esOutputConfig;
	@Override
	public DataTranPlugin buildDataTranPlugin(ImportContext importContext,ImportContext targetImportContext){
		return new ES2ESDataTranPlugin(  importContext,  targetImportContext);
	}
	protected ImportContext buildTargetImportContext(BaseImportConfig importConfig){
		ESOutputContextImpl esOutputContext = new ESOutputContextImpl(importConfig);
		esOutputContext.init();

		return esOutputContext;
	}
	@Override
	protected DataStream innerBuilder(){
		DataStream dataStream = super.innerBuilder();
//		this.buildDBConfig();
//		this.buildStatusDBConfig();
		try {
			if(logger.isInfoEnabled()) {
				logger.info("ES2ES Import Configs:");
				logger.info(this.toString());
			}
		}
		catch (Exception e){

		}


		if(esOutputConfig != null) {
			if(esOutputConfig.getTargetIndex() != null) {
				ESIndexWrapper esIndexWrapper = new ESIndexWrapper(esOutputConfig.getTargetIndex(), esOutputConfig.getTargetIndexType());
//			esIndexWrapper.setUseBatchContextIndexName(this.useBatchContextIndexName);
				esOutputConfig.setEsIndexWrapper(esIndexWrapper);
			}
			dataStream.setTargetImportContext(buildTargetImportContext(esOutputConfig));
		}
		else
			dataStream.setTargetImportContext(dataStream.getImportContext());
		dataStream.setDataTranPlugin(this.buildDataTranPlugin(dataStream.getImportContext(),dataStream.getTargetImportContext()));

		return dataStream;

	}


	public ESOutputConfig getEsOutputConfig() {
		return esOutputConfig;
	}

	public void setEsOutputConfig(ESOutputConfig esOutputConfig) {
		this.esOutputConfig = esOutputConfig;
	}
}
