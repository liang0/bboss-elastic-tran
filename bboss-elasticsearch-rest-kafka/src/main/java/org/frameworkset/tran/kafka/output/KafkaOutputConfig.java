package org.frameworkset.tran.kafka.output;
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

import org.frameworkset.tran.config.BaseImportConfig;
import org.frameworkset.tran.util.RecordGenerator;

import java.util.Map;
import java.util.Properties;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2020</p>
 * @Date 2021/2/23 11:42
 * @author biaoping.yin
 * @version 1.0
 */
public class KafkaOutputConfig  extends BaseImportConfig {
	private Properties kafkaConfigs = null;
	private long logsendTaskMetric = 10000l;

	public boolean isKafkaAsynSend() {
		return kafkaAsynSend;
	}

	public KafkaOutputConfig setKafkaAsynSend(boolean kafkaAsynSend) {
		this.kafkaAsynSend = kafkaAsynSend;
		return this;
	}

	private boolean kafkaAsynSend = true;
	public RecordGenerator getRecordGenerator() {
		return recordGenerator;
	}

	public KafkaOutputConfig setRecordGenerator(RecordGenerator recordGenerator) {
		this.recordGenerator = recordGenerator;
		return this;
	}

	/**
	 * 输出文件记录处理器:org.frameworkset.tran.util.ReocordGenerator
	 */
	private RecordGenerator recordGenerator;
	public String getTopic() {
		return topic;
	}

	public KafkaOutputConfig setTopic(String topic) {
		this.topic = topic;
		return this;
	}

	private String topic;
	public KafkaOutputConfig addKafkaProperty(String name,String value){
		if(kafkaConfigs == null)
			kafkaConfigs = new Properties();
		kafkaConfigs.setProperty(name,value);
		return this;
	}
	public KafkaOutputConfig addKafkaProperties(Map properties){
		if(properties == null || properties.size() == 0)
			return this;
		if(kafkaConfigs == null)
			kafkaConfigs = new Properties();
		kafkaConfigs.putAll(properties);
		return this;
	}
	public Properties getKafkaConfigs() {
		return kafkaConfigs;
	}

	public long getLogsendTaskMetric() {
		return logsendTaskMetric;
	}


}
