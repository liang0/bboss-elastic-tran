package org.frameworkset.tran.context;
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
import com.frameworkset.orm.annotation.ESIndexWrapper;
import org.frameworkset.spi.geoip.IpInfo;
import org.frameworkset.tran.FieldMeta;
import org.frameworkset.tran.Record;
import org.frameworkset.tran.TranMeta;
import org.frameworkset.tran.config.ClientOptions;
import org.frameworkset.tran.schedule.TaskContext;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2018/9/11 17:48
 * @author biaoping.yin
 * @version 1.0
 */
public interface Context {
	Map<String, Object> getGeoipConfig();
	public TaskContext getTaskContext();
	void afterRefactor();
	void setClientOptions(ClientOptions clientOptions);

	/**
	 * 直接获取数据源记录中对应的字段值
	 * @param fieldName
	 * @return
	 */
	public Object getResultSetValue(String fieldName);
	Context addFieldValue(String fieldName, Object value);
	Context addFieldValue(String fieldName, String dateFormat, Object value);
	Context addFieldValue(String fieldName, String dateFormat, Object value, String locale, String timeZone);
	Context addIgnoreFieldMapping(String dbColumnName);
	ESIndexWrapper getESIndexWrapper();
	Object getVersion() throws Exception;
//	public Object getEsVersionType();
	void refactorData() throws Exception;
	TranMeta getMetaData();
	DateFormat getDateFormat();
	Boolean getUseJavaName();
	Boolean getUseLowcase();
//	public Boolean getEsDocAsUpsert();
//	public Object getEsDetectNoop();
//	public Boolean getEsReturnSource();
	List<FieldMeta> getGlobalFieldValues();
	Object getValue(int i, String colName, int sqlType) throws Exception;
	ImportContext getImportContext();
	String getDBName();

	/**
	 * 获取加工字段或者源字段值，优先返回字段
	 * 记录级添加字段优先级>全局添加字段值优先级>数据源级字段优先级
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	Object getValue(String fieldName) throws Exception;
	Object getMetaValue(String fieldName) throws Exception;
	String getStringValue(String fieldName) throws Exception;
	Object getParentId() throws Exception;
	Object getRouting() throws Exception;
//	public Object getEsRetryOnConflict();
	long getLongValue(String fieldName) throws Exception;
	String getStringValue(String fieldName, String defaultValue) throws Exception;
	boolean getBooleanValue(String fieldName) throws Exception;
	boolean getBooleanValue(String fieldName, boolean defaultValue) throws Exception;
	double getDoubleValue(String fieldName) throws Exception;
	float getFloatValue(String fieldName) throws Exception;
	int getIntegerValue(String fieldName) throws Exception;
	Date getDateValue(String fieldName) throws Exception;
	Date getDateValue(String fieldName, DateFormat dateFormat) throws Exception;
	List<FieldMeta> getFieldValues();
	Map<String,FieldMeta> getFieldMetaMap();

	/**
	 * 记录级字段映射优先级>全局字段映射优先级
	 * @param colName
	 * @return
	 */
	FieldMeta getMappingName(String colName);
	Object getEsId() throws Exception;
	ClientOptions getClientOptions();
//	ESField getEsIdField();
	boolean isDrop();

	/**
	 * 设置是否过滤掉记录，true过滤，false 不过滤（默认值）
	 * @param drop
	 */
	void setDrop(boolean drop);
	IpInfo getIpInfo(String fieldName) throws Exception;
	IpInfo getIpInfoByIp(String ip) ;

	/**
	 * 重命名字段和修改字段值
	 * @param fieldName
	 * @param newFieldName
	 * @param newFieldValue
	 * @throws Exception
	 */
	void newName2ndData(String fieldName, String newFieldName, Object newFieldValue)throws Exception;

	BatchContext getBatchContext();

	/**
	 * 获取原始数据对象,可能是一个map，jdbcresultset，DBObject,hbaseresult
	 * @return
	 */
	Object getRecord();

	/**
	 * 获取记录对象
	 * @return
	 */
	public Record getCurrentRecord();

	/**
	 * 标识记录状态为insert操作（增加），默认值

	 */
	void markRecoredInsert();
	/**
	 * 标识记录状态为update操作（修改）

	 */
	void markRecoredUpdate();

	/**
	 * 标识记录状态为delete操作（删除）

	 */
	void markRecoredDelete();

	/**
	 * 判断记录是否是新增记录(默认值)
	 * @return
	 */
	boolean isInsert();

	/**
	 * 判断记录是否是更新操作
	 * @return
	 */
	boolean isUpdate();

	/**
	 * 判断记录是否删除操作
	 * @return
	 */
	boolean isDelete();

	/**
	 * 进行记录级别索引表名称设置
	 */
	void setIndex(String indice);

	/**
	 * 进行记录级别索引类型设置
	 */
	void setIndexType(String indiceType);

	/**
	 * 获取记录对应的索引表名称
	 * @return
	 */
	String getIndex();

	/**
	 * 获取记录对应的索引类型名称
	 * @return
	 */
	String getIndexType();

	String getOperation();

	boolean isUseBatchContextIndexName() ;

	void setUseBatchContextIndexName(boolean useBatchContextIndexName) ;

	/**
	 * 数据预校验，true 继续后续处理，false 忽略当前记录处理
	 * @return
	 */
	boolean removed();

	boolean reachEOFClosed();
}
