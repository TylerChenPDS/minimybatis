package session.defaults;

import java.io.File;
import java.net.URL;

import constants.Constant;
import session.Configuration;
import session.SqlSession;
import session.SqlSessionFactory;
import utils.AnnotationUtil;
import utils.CommonUtis;
import utils.XmlUtil;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

	/** the configuration */
	private final Configuration configuration;

	/**
	 * @param configuration
	 */
	public DefaultSqlSessionFactory(Configuration configuration) {

		this.configuration = configuration;
		// 这里是通过包全名解析
		loadMappersXmlInfo(Configuration.getProperty(Constant.MAPPER_LOCATION).replaceAll("\\.", "/"));
		
		//通过注解解析
		loadMappersAnnotationInfo(Configuration.getProperty(Constant.MAPPER_ANNOTATION_LOCATION).replaceAll("\\.", "/"));
	}

	/**
	 * 开启会话
	 * 
	 * @return
	 */
	@Override
	public SqlSession openSession() {
		SqlSession session = new DefaultSqlSession(this.configuration);
		return session;
	}

	private void loadMappersAnnotationInfo(String dirName) {
		URL resources = DefaultSqlSessionFactory.class.getClassLoader().getResource(dirName);
		File mappersDir = new File(resources.getFile());

		if (mappersDir.isDirectory()) {
			// 显示包下所有文件
			File[] mappers = mappersDir.listFiles();
			for (File file : mappers) {
				System.out.println(file.getName());
				if (file.getName().endsWith(".class")) {
					System.out.println(dirName);
					String clazzName = dirName.replaceAll("/", ".") + "." + file.getName();
					System.out.println(clazzName);
					AnnotationUtil.readMapperJava(clazzName.substring(0,clazzName.indexOf(".class")), configuration);
				} else if (file.isDirectory()) {
					loadMappersAnnotationInfo(dirName + "/" + file.getName());
				}
			}
		}

	}

	/**
	 * 加载xml形式的文件
	 * 
	 * @param dirName
	 * @see
	 */
	private void loadMappersXmlInfo(String dirName) {

		URL resources = DefaultSqlSessionFactory.class.getClassLoader().getResource(dirName);

		if(resources == null) return;
		File mappersDir = new File(resources.getFile());

		if (mappersDir.isDirectory()) {
			// 显示包下所有文件
			File[] mappers = mappersDir.listFiles();
			if (CommonUtis.isNotEmpty(mappers)) {
				for (File file : mappers) {

					// 对文件夹继续递归
					if (file.isDirectory()) {
						loadMappersXmlInfo(dirName + "/" + file.getName());

					} else if (file.getName().endsWith(Constant.MAPPER_FILE_SUFFIX)) {
						// 只对XML文件解析
						XmlUtil.readMapperXml(file, this.configuration);
					}

				}

			}
		}

	}

}
