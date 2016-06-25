package cn.yl.web.jfinal;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;

/**
 * GeneratorDemo
 */
public class GeneratorDemo {
	
	public static DataSource getDataSource() {
		Prop p = PropKit.use("config.properties");
		DruidPlugin druidPlugin = new DruidPlugin(p.get("jdbcUrl"),p.get("user"),p.get("password").trim(),p.get("jdbcdriver"));
		druidPlugin.start();
		return druidPlugin.getDataSource();
	}
	
	public static void main(String[] args) {
		// base model ��ʹ�õİ���
		String baseModelPackageName = "cn.yl.web.model.base";
		// base model �ļ�����·��
		String baseModelOutputDir = "G:\\workspace\\idea\\web\\src\\main\\java\\cn\\yl\\web\\model\\base";
		System.out.println(baseModelOutputDir);
		// model ��ʹ�õİ��� (MappingKit Ĭ��ʹ�õİ���)
		String modelPackageName = "cn.yl.web.model";
		// model �ļ�����·�� (MappingKit �� DataDictionary �ļ�Ĭ�ϱ���·��)
		String modelOutputDir = baseModelOutputDir + "/..";

		// ����������
		Generator gernerator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		gernerator.setDialect(new PostgreSqlDialect());
		// ��Ӳ���Ҫ���ɵı���
//		gernerator.addExcludedTable("adv");
		// �����Ƿ��� Model ������ dao ����
		gernerator.setGenerateDaoInModel(true);
		// �����Ƿ������ֵ��ļ�
		gernerator.setGenerateDataDictionary(false);
		// ������Ҫ���Ƴ��ı���ǰ׺��������modelName��������� "osc_user"���Ƴ�ǰ׺ "osc_"�����ɵ�model��Ϊ "User"���� OscUser
		gernerator.setRemovedTableNamePrefixes("im_","r_","sys_");
		// ����
		gernerator.generate();
	}
}




