package jdbc_pool_c3p0_study;

import jdbc_pool_c3p0_study.jdbc.MyDataSource;

public class Main {

	public static void main(String[] args) {
		MyDataSource mds = MyDataSource.getInstance();
	}
}
