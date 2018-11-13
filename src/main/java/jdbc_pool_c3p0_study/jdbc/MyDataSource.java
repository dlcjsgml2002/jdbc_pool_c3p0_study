package jdbc_pool_c3p0_study.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

public class MyDataSource {
	// singleton pattern 적용
	public static final MyDataSource instance = new MyDataSource();

	public static MyDataSource getInstance() {
		return instance;
	}

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void close() {
		try {
			DataSources.destroy(dataSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private MyDataSource() {
		Properties prop = loadProperties();
		try {
			DataSource ds_unpooled = DataSources.unpooledDataSource(prop.getProperty("url"), prop);
			Map<String, Object> overrides = new HashMap<>();
			overrides.put("maxStatements", "200");
			overrides.put("maxPoolSize", new Integer(50));
			dataSource = DataSources.pooledDataSource(ds_unpooled, overrides);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Properties loadProperties() {
		Properties properties = new Properties();
		try (InputStream is = ClassLoader.getSystemResourceAsStream("db.properties")) {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

}
