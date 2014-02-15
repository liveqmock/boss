package com.dtv.oss.web.util.download;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.exception.WebActionException;

public class Calculate {
	public static final int COMMONDATA = 1;
	public static final int METHOD = 2;
	public static final int SQL = 3;
	public static final int STAT = 4;
	public static final int STATMETHOD_SUM = 10;
	public static final int STATMETHOD_AVG = 11;
	public static final int STATMETHOD_MAX = 12;
	public static final int STATMETHOD_MIN = 13;
	public static final int STATMETHOD_COUNT = 14;
	
	private final String posternName = "com.dtv.oss.util.Postern";
	
	private int operator;
	private String methodName;
	private String methodObjectName;
	private String[] parameterName;
	private String commonDataName;
	private int statMethod;
	private String statParameter;
	private BigDecimal statValue;
	private long statCount = 0;
	private static final int scale=6;
	protected void finalize()throws Throwable{
		super.finalize();
		parameterName=null;
		methodName=null;
		methodObjectName=null;
		commonDataName=null;
		statParameter=null;
		statValue=null;
	}
	
	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	private Calculate() {
	};

	public static Calculate init(String str) throws WebActionException {
		// System.out.println("Calculate.init.str "+str);
		Calculate cal = new Calculate();
//		if (str == null || "".equals(str) || str.indexOf("{") == -1) {
		if (str == null || "".equals(str)) {
			throw new WebActionException("无效的运算内容:"+str);
		}
		if(str.indexOf("{") == -1)return null;
		int s = str.indexOf("{");
		int e = str.indexOf("}");
		// 操作描述,method/commandata等内容,
		String op = str.substring(0, s).toUpperCase();
		// 操作内容,{}中的部分,
		String opc = str.substring(s + 1, e);
		if ("COMMONDATA".equals(op)) {
			cal.setOperator(COMMONDATA);
			// 操作内容分分隔,以:分隔.
			String[] arr = opc.split(":");
			if (arr.length != 2) {
				throw new WebActionException("无效的配置:" + str);
			}
			// 设备公用数据名
			cal.setCommonDataName(arr[0]);
			String[] para = new String[] { arr[1].toUpperCase() };
			// 设备公用数据Key,
			cal.setParameterName(para);
		}
		if ("METHOD".equals(op)) {
			cal.setOperator(METHOD);
			// 以逗号分隔,第一个是方法名,后面是参数,可以有N个,
			String[] arr = opc.split(",");
			// System.out.println("Calculate.init.arr[0] "+arr[0]);
			// 分解方法名,.前面是方法是主体,后面是方法名称,
			String[] marr = arr[0].split("\\.");
			// System.out.println("Calculate.init.marr "+arr[0].indexOf("."));
			// 必须是两部分,
			if (marr.length != 2) {
				throw new WebActionException("无效的配置:" + str);
			}
			// 设备方法主体,
			cal.setMethodObjectName(marr[0].toUpperCase());
			int me = marr[1].indexOf("(");
			if (me != -1) {
				marr[1] = marr[1].substring(0, me);
			}
			// 设备方法名
			cal.setMethodName(marr[1]);
			// System.out.println("Calculate.init.MethodName "+marr[1]);
			// 设备参数,按顺序的,
			String[] pl = new String[arr.length - 1];
			for (int i = 1; i < arr.length; i++) {
				pl[i - 1] = arr[i].toUpperCase();
			}
			cal.setParameterName(pl);
		}
		if ("STAT".equals(op)) {
			// 设置操作内容.
			cal.setOperator(STAT);
			int ss = opc.indexOf("(");
			int se = opc.indexOf(")");
			if (ss == -1 || se == -1) {
				throw new WebActionException("无效的配置:" + str);
			}
			// 取得统计方法,
			String method = opc.substring(0, ss);
			String para = opc.substring(ss + 1, se);
			// 设置统计方法名.
			if ("sum".equalsIgnoreCase(method)) {
				cal.setStatMethod(STATMETHOD_SUM);
			} else if ("count".equalsIgnoreCase(method)) {
				cal.setStatMethod(STATMETHOD_COUNT);
			} else if ("avg".equalsIgnoreCase(method)) {
				cal.setStatMethod(STATMETHOD_AVG);
			} else if ("max".equalsIgnoreCase(method)) {
				cal.setStatMethod(STATMETHOD_MAX);
			} else if ("min".equalsIgnoreCase(method)) {
				cal.setStatMethod(STATMETHOD_MIN);
			}
			// 设置统计参数,一般是列名,在数据库的定义的
			cal.setStatParameter(para);
		}
		// 不打算支持了,动态sql查询不支持N多数据.效率很差.
		if ("SQL".equals(op)) {
			cal.setOperator(SQL);
		}

		return cal;
	}

	public Object getValue(Object[] obj) throws WebActionException {
		Object value = null;
		switch (operator) {
		case COMMONDATA:
			value = getValueWithCommonData((String) obj[0]);
			break;
		case METHOD:
			value = getValueWithMethod(obj);
			break;
		case SQL:
			break;
		case STAT:
			value = calculateStatValue(obj[0]);
			break;
		}
		// System.out.println("Calculate.getValue.value "+value);
		return value;
	}

	private Object calculateStatValue(Object obj) throws WebActionException {
//		System.out.println("calculateStatValue.obj:"+obj);
		switch (statMethod) {
		case STATMETHOD_COUNT:
			return calculateCountValue(obj);
		case STATMETHOD_SUM:
			return calculateSumValue(obj);
		case STATMETHOD_AVG:
			return calculateAvgValue(obj);
		case STATMETHOD_MAX:
			return calculateMaxValue(obj);
		case STATMETHOD_MIN:
			return calculateMinValue(obj);
		}
		return null;
	}

	private BigDecimal calculateMinValue(Object obj) throws WebActionException {
		if(obj==null)return null;
		if (obj instanceof Number) {
			BigDecimal bg = new BigDecimal(obj.toString());
			if (statValue == null){
				statValue = bg;
			}else{
				statValue = statValue.min(bg);
			}
			return statValue;
		} else {
			throw new WebActionException("无效的数值.");
		}
	}

	private BigDecimal calculateMaxValue(Object obj) throws WebActionException {
		if(obj==null)return null;
		if (obj instanceof Number) {
			BigDecimal bg = new BigDecimal(obj.toString());
			if (statValue == null){
				statValue = bg;
			}else{
				statValue = statValue.max(bg);
			}
			return statValue;
		} else {
			throw new WebActionException("无效的数值.");
		}
	}

	private BigDecimal calculateAvgValue(Object obj) throws WebActionException {
		if(obj==null)return null;
		BigDecimal sum = calculateSumValue(obj);
		Long a = calculateCountValue(obj);
		BigDecimal res=sum.divide(new BigDecimal(a.toString()),scale,BigDecimal.ROUND_HALF_EVEN);
//		LogUtility.log(this.getClass(), LogLevel.ERROR, "★★★★calculateAvgValue★★★★"+res);
		return res;
	}

	private Long calculateCountValue(Object obj) {
		if (getStatParameter() == null || "*".equals(getStatParameter())) {
			statCount++;
			return new Long(statCount);
		} else {
			if (obj != null) {
				statCount++;
				return new Long(statCount);
			}
		}
		return null;
	}

	private BigDecimal calculateSumValue(Object obj) throws WebActionException {
		if(obj==null)return null;
		if (obj instanceof Number) {
			BigDecimal bg = new BigDecimal(obj.toString());
			if (statValue == null)
				statValue = new BigDecimal(0);
			statValue = statValue.add(bg);
			return statValue;
		} else {
			throw new WebActionException("无效的数值:"+obj);
		}
	}

	private String getValueWithCommonData(String key) throws WebActionException {
		if (key == null) {
			return "";
//			throw new WebActionException("无效的值:" + this.commonDataName);
		}
		return Postern.getCommonSettingDataValueByNameAndKey(
				getCommonDataName(), key);
	}

	private Object getValueWithMethod(Object[] para) {
		Object val = "";
		// System.out.println("Calculate.getValueWithMethod.para.length
		// "+para.length);
		// System.out.println("Calculate.getValueWithMethod.para.length
		// "+para[0]);
		if ("POSTERN".equals(getMethodObjectName())) {
			try {
				Method method = searchMethod(Postern.class.getMethods(), this
						.getMethodName(), para);
//				 System.out.println("Calculate.getValueWithMethod.method"+method);
				if (Modifier.isStatic(method.getModifiers())) {
					val = method.invoke(null, para);
				} else {
					throw new Exception(posternName + "中的方法只支持静态类型.");
				}
			} catch (Exception e) {
//				e.printStackTrace();
				LogUtility.log(this.getClass(), LogLevel.ERROR, "取值异常,"
						+ getMethodObjectName() + "." + getMethodName()
						+ ",异常描述:" +e.getClass().getName()+","+ e.getMessage()
						+ ",参数:"+Arrays.asList(para));
			}
		}
		return val;
	}

	/**
	 * 查找可用的方法,
	 * 
	 * @param methods要搜索的方法数组
	 * @param name方法名
	 * @param para方法参数
	 * @return
	 * @throws Exception
	 */
	private Method searchMethod(Method[] methods, String name, Object[] para)
			throws WebActionException {
		if (methods == null || name == null || "".equals(name)) {
			return null;
		}
		List mlist = new ArrayList();
		// 先去掉名称不同的
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];
			String mName = m.getName();
			if (mName.equalsIgnoreCase(name)) {
				mlist.add(m);
			}
		}
		// System.out.println("Calculate.searchMethod同名方法 "+mlist);

		// 如果只有一个,则返回他
		if (mlist.size() == 1) {
			return (Method) mlist.get(0);
			// 如果还有多个,按参数个数来过滤.参数可能为空,
		} else if (mlist.size() > 1) {
			for (Iterator it = mlist.iterator(); it.hasNext();) {
				Method m = (Method) it.next();
				if (m.getParameterTypes().length != para.length) {
					mlist.remove(m);
				}
			}
		}
		// System.out.println("Calculate.searchMethod同参数长度方法 "+mlist);
		// 如果只有一个,则返回他
		if (mlist.size() == 1) {
			return (Method) mlist.get(0);
			// 如果还有多个,按参数名称大小写来过滤.参数可能为空,
		} else if (mlist.size() > 1) {
			for (Iterator it = mlist.iterator(); it.hasNext();) {
				Method m = (Method) it.next();
				String mName = m.getName();
				if (!mName.equals(name)) {
					mlist.remove(m);
				}
			}
		}
		// System.out.println("Calculate.searchMethod严格同名方法 "+mlist);
		if (mlist.size() == 1) {
			return (Method) mlist.get(0);
			// 如果还有多个,按参数名称大小写来过滤.参数可能为空,
		} else {
			throw new WebActionException("找不到匹配的方法");
		}

	}

	/**
	 * 这个方法好用,parameterType要基本类型,而得到的是对象型,方法执行又要对象型,转换比较郁闷 ,
	 * 
	 * @return
	 */
	// private Method getMethod(Class cla,String name,Object[]para){
	// Method method=null;
	// Class[] paratype=new Class[para.length];
	// for(int i=0;i<para.length;i++){
	// Object o=para[i];
	// if(o instanceof Number){
	// if(o instanceof Integer){
	// Integer no=(Integer) o;
	// paratype[i]=no.TYPE;
	// }
	// if(o instanceof Double){
	// Double no=(Double) o;
	// paratype[i]=no.TYPE;
	// }
	// if(o instanceof Float){
	// Float no=(Float) o;
	// paratype[i]=no.TYPE;
	// }
	// if(o instanceof Long){
	// Long no=(Long) o;
	// paratype[i]=no.TYPE;
	// }
	// }else{
	// paratype[i]=para[i].getClass();
	// }
	// System.out.println("Calculate.getValueWithMethod.para.paratype[i]
	// "+paratype[i]);
	// }
	// try {
	// method = cla.getClass().getMethod(name,paratype);
	// } catch (SecurityException e) {
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// e.printStackTrace();
	// }
	// return method;
	// }
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String[] getParameterName() {
		return parameterName;
	}

	public void setParameterName(String[] parameterName) {
		this.parameterName = parameterName;
	}

	public String getCommonDataName() {
		return commonDataName;
	}

	public void setCommonDataName(String commonDataName) {
		this.commonDataName = commonDataName;
	}

	public String getMethodObjectName() {
		return methodObjectName;
	}

	public void setMethodObjectName(String methodObjectName) {
		this.methodObjectName = methodObjectName;
	}

	public int getStatMethod() {
		return statMethod;
	}

	public void setStatMethod(int statMethod) {
		this.statMethod = statMethod;
	}

	public String getStatParameter() {
		return statParameter;
	}

	public void setStatParameter(String statParameter) {
		this.statParameter = statParameter;
	}

	public String toString() {
		StringBuffer bf = new StringBuffer();
		bf.append("{");
		bf.append("operator=").append(operator).append(",");
		bf.append("method=").append(methodName).append(",");
		bf.append("commonDataName=").append(commonDataName).append(",");
		bf.append("statMethod=").append(statMethod).append(",");
		bf.append("statParameter=").append(statParameter).append(",");
		bf.append("}").append("\n");
		return bf.toString();
	}

	public Object getStatValue() {
		switch (statMethod) {
		case STATMETHOD_COUNT:
			return new Long(statCount);
		case STATMETHOD_SUM:
			return statValue;
		case STATMETHOD_AVG:
			return statValue.divide(new BigDecimal(statCount),scale,BigDecimal.ROUND_HALF_EVEN);
		case STATMETHOD_MAX:
			return statValue;
		case STATMETHOD_MIN:
			return statValue;
		}
		return statValue;
	}

	public long getStatCount() {
		return statCount;
	}

	public void setStatCount(long statCount) {
		this.statCount = statCount;
	}
}
