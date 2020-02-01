package org.frameworkset.tran.hbase.input;
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

import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.frameworkset.nosql.hbase.HBaseHelper;
import org.frameworkset.nosql.hbase.TableFactory;
import org.frameworkset.tran.BaseDataTranPlugin;
import org.frameworkset.tran.DataTranPlugin;
import org.frameworkset.tran.ESDataImportException;
import org.frameworkset.tran.context.ImportContext;
import org.frameworkset.tran.hbase.HBaseContext;
import org.frameworkset.tran.hbase.HBaseRecord;
import org.frameworkset.tran.hbase.HBaseResultSet;
import org.frameworkset.tran.schedule.ImportIncreamentConfig;

import java.util.Date;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2019/10/9 14:35
 * @author biaoping.yin
 * @version 1.0
 */
public class HBase2ESInputPlugin extends BaseDataTranPlugin implements DataTranPlugin {
	private TableFactory tableFactory;

	private HBaseContext hbaseContext;
	private byte[] incrementFamily;
	private byte[] incrementColumn;
	protected void init(ImportContext importContext){
		super.init(importContext);
		hbaseContext = (HBaseContext)importContext;


	}
	protected void doTran(ResultScanner rs) {
//		MongoDBResultSet mongoDB2ESResultSet = new MongoDBResultSet(importContext,dbCursor);
//		MongoDB2ESDataTran mongoDB2ESDataTran = new MongoDB2ESDataTran(mongoDB2ESResultSet,importContext);
//		mongoDB2ESDataTran.tran();
		HBaseResultSet hBaseResultSet = new HBaseResultSet(importContext,rs);
		HBase2ESDataTran hBase2ESDataTran = new HBase2ESDataTran(hBaseResultSet,importContext);
		hBase2ESDataTran.tran();
	}

	public HBase2ESInputPlugin(ImportContext importContext){
		super(importContext);


	}
	public void destroy(){

		HBaseHelper.destroy();

	}
	@Override
	public void beforeInit() {
		this.initES(importContext.getApplicationPropertiesFile());
		initHBase();
		initOtherDSes(importContext.getConfigs());


	}

	protected void initHBase() {
//		MongoDBConfig mongoDBConfig = new MongoDBConfig();
//		mongoDBConfig.setName(es2DBContext.getName());
//		mongoDBConfig.setCredentials(es2DBContext.getCredentials());
//		mongoDBConfig.setServerAddresses(es2DBContext.getServerAddresses());
//		mongoDBConfig.setOption(es2DBContext.getOption());//private String option;
//		mongoDBConfig.setWriteConcern(es2DBContext.getWriteConcern());//private String writeConcern;
//		mongoDBConfig.setReadPreference(es2DBContext.getReadPreference());//private String readPreference;
//		mongoDBConfig.setAutoConnectRetry(es2DBContext.getAutoConnectRetry());//private Boolean autoConnectRetry = true;
//
//		mongoDBConfig.setConnectionsPerHost(es2DBContext.getConnectionsPerHost());//private int connectionsPerHost = 50;
//
//		mongoDBConfig.setMaxWaitTime(es2DBContext.getMaxWaitTime());//private int maxWaitTime = 120000;
//		mongoDBConfig.setSocketTimeout(es2DBContext.getSocketTimeout());//private int socketTimeout = 0;
//		mongoDBConfig.setConnectTimeout(es2DBContext.getConnectTimeout());//private int connectTimeout = 15000;
//
//
//		/**是否启用sql日志，true启用，false 不启用，*/
//		mongoDBConfig.setThreadsAllowedToBlockForConnectionMultiplier(es2DBContext.getThreadsAllowedToBlockForConnectionMultiplier());//private int threadsAllowedToBlockForConnectionMultiplier;
//		mongoDBConfig.setSocketKeepAlive(es2DBContext.getSocketKeepAlive());//private Boolean socketKeepAlive = false;
//
//		mongoDBConfig.setMode( es2DBContext.getMode());
//		MongoDBHelper.init(mongoDBConfig);
//		HbaseConfigurationFactoryBean hbaseConfigurationFactoryBean = new HbaseConfigurationFactoryBean();
//		Configuration configuration = hbaseConfigurationFactoryBean.getConfiguration();
//		ExecutorService executorService = ThreadPoolFactory.buildThreadPool("HBase-Input","HBase tran",100,100,0L,1000l,1000,true,true);
//		ConnectionFactoryBean connectionFactoryBean = new ConnectionFactoryBean(configuration,executorService);
//		try {
//			tableFactory = new HbaseTableFactory(connectionFactoryBean.getConnection());
//		} catch (Exception e) {
//			throw new HBaseTranException(e);
//		}
		tableFactory = HBaseHelper.buildTableFactory(hbaseContext.getHbaseClientProperties(),
				hbaseContext.getHbaseClientThreadCount(),
				hbaseContext.getHbaseClientThreadQueue(),
				hbaseContext.getHbaseClientKeepAliveTime(),
				hbaseContext.getHbaseClientBlockedWaitTimeout(),
				hbaseContext.getHbaseClientWarnMultsRejects(),
				hbaseContext.isHbaseClientPreStartAllCoreThreads(),
				hbaseContext.getHbaseClientThreadDaemon());
	}
	@Override
	public void afterInit(){
		initIncrementInfos();
	}

	@Override
	public void initStatusTableId() {
		if(isIncreamentImport()) {
			//计算增量记录id
			String statusTableId = hbaseContext.getHbaseTable()+"|"+hbaseContext.getIncrementFamilyName();

			if(hbaseContext.getStartRow() != null )
				statusTableId =statusTableId +"|"+hbaseContext.getStartRow();
			if(hbaseContext.getEndRow() != null )
				statusTableId =statusTableId +"|"+hbaseContext.getEndRow();
			if(hbaseContext.getScanFilters() != null )
				statusTableId =statusTableId +"|"+hbaseContext.getScanFilters();
			importContext.setStatusTableId(statusTableId.hashCode());
//
//			String statusTableId = es2DBContext.getDB()+"|"+es2DBContext.getDBCollection()+"|"+es2DBContext.getServerAddresses();
//			if(es2DBContext.getQuery() != null){
//				statusTableId = statusTableId +"|" + es2DBContext.getQuery().toString();
//			}
//			importContext.setStatusTableId(statusTableId.hashCode());
		}

	}

	private void exportESData(){
//		MongoDB mogodb = MongoDBHelper.getMongoDB(es2DBContext.getName());
//		DB db = mogodb.getDB(es2DBContext.getDB());
//		DBCollection dbCollection = db.getCollection(es2DBContext.getDBCollection());
//		DBCollectionFindOptions dbCollectionFindOptions = null;
//		if(es2DBContext.getDBCollectionFindOptions() != null){
//			dbCollectionFindOptions = es2DBContext.getDBCollectionFindOptions();
//			dbCollectionFindOptions.batchSize(importContext.getFetchSize());
//		}
//		else
//		{
//			dbCollectionFindOptions = new DBCollectionFindOptions();
//			dbCollectionFindOptions.batchSize(importContext.getFetchSize());
//		}
//
//
////		dbCollectionFindOptions.
//		if(es2DBContext.getFetchFields() != null){
//			dbCollectionFindOptions.projection(es2DBContext.getFetchFields());
//		}
//		DBCursor dbCursor = dbCollection.find(dbObject,dbCollectionFindOptions);
////		MongoDBResultSet mongoDB2ESResultSet = new MongoDBResultSet(importContext,dbCursor);
////		MongoDB2ESDataTran mongoDB2ESDataTran = new MongoDB2ESDataTran(mongoDB2ESResultSet,importContext);
////		mongoDB2ESDataTran.tran();
//		doTran(  dbCursor);
		Table table = null;
		try {
			table = tableFactory.getTable(TableName.valueOf(hbaseContext.getHbaseTable()));
			Scan scan = new Scan();
			if(hbaseContext.getStartRow() != null){
				scan.withStartRow(Bytes.toBytes(hbaseContext.getStartRow()),true);
			}
			if(hbaseContext.getEndRow() != null){
				scan.withStopRow(Bytes.toBytes(hbaseContext.getEndRow()),true);
			}
			if(hbaseContext.getHbaseBatch() != null){
				scan.setBatch(hbaseContext.getHbaseBatch());
			}
			if(hbaseContext.getMaxResultSize() != null){
				scan.setMaxResultSize(hbaseContext.getMaxResultSize());
			}



			if(hbaseContext.getStartTimestamp() != null && hbaseContext.getEndTimestamp() != null)
				scan.setTimeRange(hbaseContext.getStartTimestamp(),hbaseContext.getEndTimestamp());
			if (isIncreamentImport()) {

				putLastParamValue(scan,hbaseContext.getScanFilters());

			}

			if(hbaseContext.getScanFilters() != null) {
				scan.setFilter(hbaseContext.getScanFilters());

			}
			if(importContext.getFetchSize() != null) {
				scan.setCaching(importContext.getFetchSize());
			}

			ResultScanner rs = table.getScanner(scan);
			doTran(rs);
		}
		catch (Exception e){
			throw new ESDataImportException(e);
		}
		finally {
			if(tableFactory != null && table != null){
				tableFactory.releaseTable(table);
			}
		}


	}
	private void initIncrementInfos(){
		if(getLastValueVarName() != null){

			if(hbaseContext.getIncrementFamilyName() != null){
				incrementFamily = Bytes.toBytes(hbaseContext.getIncrementFamilyName());
				incrementColumn = Bytes.toBytes(getLastValueVarName());
			}
			else{
				byte[][] infos = HBaseRecord.parserColumn(getLastValueVarName());
				incrementFamily = infos[0];
				incrementColumn = infos[1];
			}
		}


	}
	public void putLastParamValue(Scan scan,FilterList filterList){
		if(this.lastValueType == ImportIncreamentConfig.NUMBER_TYPE) {

			SingleColumnValueFilter scvf = new SingleColumnValueFilter(incrementFamily, incrementColumn,

					CompareOperator.GREATER, Bytes.toBytes((Long) this.currentStatus.getLastValue()));

			if (hbaseContext.getFilterIfMissing() != null)
				scvf.setFilterIfMissing(hbaseContext.getFilterIfMissing()); //默认为false， 没有此列的数据也会返回 ，为true则只返回name=lisi的数据
			if (filterList == null) {
				filterList.addFilter(scvf);

			} else {
				scan.setFilter(scvf);
			}
		}
		else{
			Object lv = null;
			if(this.currentStatus.getLastValue() instanceof Date) {
				lv = this.currentStatus.getLastValue();
//				params.put(getLastValueVarName(), this.currentStatus.getLastValue());
			}
			else {
				if(this.currentStatus.getLastValue() instanceof Long) {
					lv =   new Date((Long)this.currentStatus.getLastValue());
				}
				else if(this.currentStatus.getLastValue() instanceof Integer){
					lv =  new Date(((Integer) this.currentStatus.getLastValue()).longValue());
				}
				else if(this.currentStatus.getLastValue() instanceof Short){
					lv =  new Date(((Short) this.currentStatus.getLastValue()).longValue());
				}
				else{
					lv =  new Date(((Number) this.currentStatus.getLastValue()).longValue());
				}
			}
			SingleColumnValueFilter scvf= new SingleColumnValueFilter(incrementFamily, incrementColumn,

					CompareOperator.GREATER,Bytes.toBytes(((Date)this.currentStatus.getLastValue()).getTime()));

			if(hbaseContext.getFilterIfMissing() != null)
				scvf.setFilterIfMissing(hbaseContext.getFilterIfMissing()); //默认为false， 没有此列的数据也会返回 ，为true则只返回name=lisi的数据
			if(filterList == null){
				filterList.addFilter(scvf);

			}
			else {
				scan.setFilter(scvf);
			}

		}
		if(isPrintTaskLog()){
			logger.info(new StringBuilder().append("Current values: ").append(currentStatus.getLastValue()).toString());
		}
	}

	public void doImportData()  throws ESDataImportException {


			try {
				exportESData();
			} catch (ESDataImportException e) {
				throw e;
			} catch (Exception e) {
				throw new ESDataImportException(e);
			}

	}

	@Override
	public String getLastValueVarName() {
		return importContext.getLastValueClumnName();
	}
}
