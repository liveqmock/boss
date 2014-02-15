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
			throw new WebActionException("��Ч����������:"+str);
		}
		if(str.indexOf("{") == -1)return null;
		int s = str.indexOf("{");
		int e = str.indexOf("}");
		// ��������,method/commandata������,
		String op = str.substring(0, s).toUpperCase();
		// ��������,{}�еĲ���,
		String opc = str.substring(s + 1, e);
		if ("COMMONDATA".equals(op)) {
			cal.setOperator(COMMONDATA);
			// �������ݷַָ�,��:�ָ�.
			String[] arr = opc.split(":");
			if (arr.length != 2) {
				throw new WebActionException("��Ч������:" + str);
			}
			// �豸����������
			cal.setCommonDataName(arr[0]);
			String[] para = new String[] { arr[1].toUpperCase() };
			// �豸��������Key,
			cal.setParameterName(para);
		}
		if ("METHOD".equals(op)) {
			cal.setOperator(METHOD);
			// �Զ��ŷָ�,��һ���Ƿ�����,�����ǲ���,������N��,
			String[] arr = opc.split(",");
			// System.out.println("Calculate.init.arr[0] "+arr[0]);
			// �ֽⷽ����,.ǰ���Ƿ���������,�����Ƿ�������,
			String[] marr = arr[0].split("\\.");
			// System.out.println("Calculate.init.marr "+arr[0].indexOf("."));
			// ������������,
			if (marr.length != 2) {
				throw new WebActionException("��Ч������:" + str);
			}
			// �豸��������,
			cal.setMethodObjectName(marr[0].toUpperCase());
			int me = marr[1].indexOf("(");
			if (me != -1) {
				marr[1] = marr[1].substring(0, me);
			}
			// �豸������
			cal.setMethodName(marr[1]);
			// System.out.println("Calculate.init.MethodName "+marr[1]);
			// �豸����,��˳���,
			String[] pl = new String[arr.length - 1];
			for (int i = 1; i < arr.length; i++) {
				pl[i - 1] = arr[i].toUpperCase();
			}
			cal.setParameterName(pl);
		}
		if ("STAT".equals(op)) {
			// ���ò�������.
			cal.setOperator(STAT);
			int ss = opc.indexOf("(");
			int se = opc.indexOf(")");
			if (ss == -1 || se == -1) {
				throw new WebActionException("��Ч������:" + str);
			}
			// ȡ��ͳ�Ʒ���,
			String method = opc.substring(0, ss);
			String para = opc.substring(ss + 1, se);
			// ����ͳ�Ʒ�����.
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
			// ����ͳ�Ʋ���,һ��������,�����ݿ�Ķ����
			cal.setStatParameter(para);
		}
		// ������֧����,��̬sql��ѯ��֧��N������.Ч�ʺܲ�.
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
			throw new WebActionException("��Ч����ֵ.");
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
			throw new WebActionException("��Ч����ֵ.");
		}
	}

	private BigDecimal calculateAvgValue(Object obj) throws WebActionException {
		if(obj==null)return null;
		BigDecimal sum = calculateSumValue(obj);
		Long a = calculateCountValue(obj);
		BigDecimal res=sum.divide(new BigDecimal(a.toString()),scale,BigDecimal.ROUND_HALF_EVEN);
//		LogUtility.log(this.getClass(), LogLevel.ERROR, "�����calculateAvgValue�����"+res);
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
			throw new WebActionException("��Ч����ֵ:"+obj);
		}
	}

	private String getValueWithCommonData(String key) throws WebActionException {
		if (key == null) {
			return "";
//			throw new WebActionException("��Ч��ֵ:" + this.commonDataName);
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
					throw new Exception(posternName + "�еķ���ֻ֧�־�̬����.");
				}
			} catch (Exception e) {
//				e.printStackTrace();
				LogUtility.log(this.getClass(), LogLevel.ERROR, "ȡֵ�쳣,"
						+ getMethodObjectName() + "." + getMethodName()
						+ ",�쳣����:" +e.getClass().getName()+","+ e.getMessage()
						+ ",����:"+Arrays.asList(para));
			}
		}
		return val;
	}

	/**
	 * ���ҿ��õķ���,
	 * 
	 * @param methodsҪ�����ķ�������
	 * @param name������
	 * @param para��������
	 * @return
	 * @throws Exception
	 */
	private Method searchMethod(Method[] methods, String name, Object[] para)
			throws WebActionException {
		if (methods == null || name == null || "".equals(name)) {
			return null;
		}
		List mlist = new ArrayList();
		// ��ȥ�����Ʋ�ͬ��
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];
			String mName = m.getName();
			if (mName.equalsIgnoreCase(name)) {
				mlist.add(m);
			}
		}
		// System.out.println("Calculate.searchMethodͬ������ "+mlist);

		// ���ֻ��һ��,�򷵻���
		if (mlist.size() == 1) {
			return (Method) mlist.get(0);
			// ������ж��,����������������.��������Ϊ��,
		} else if (mlist.size() > 1) {
			for (Iterator it = mlist.iterator(); it.hasNext();) {
				Method m = (Method) it.next();
				if (m.getParameterTypes().length != para.length) {
					mlist.remove(m);
				}
			}
		}
		// System.out.println("Calculate.searchMethodͬ�������ȷ��� "+mlist);
		// ���ֻ��һ��,�򷵻���
		if (mlist.size() == 1) {
			return (Method) mlist.get(0);
			// ������ж��,���������ƴ�Сд������.��������Ϊ��,
		} else if (mlist.size() > 1) {
			for (Iterator it = mlist.iterator(); it.hasNext();) {
				Method m = (Method) it.next();
				String mName = m.getName();
				if (!mName.equals(name)) {
					mlist.remove(m);
				}
			}
		}
		// System.out.println("Calculate.searchMethod�ϸ�ͬ������ "+mlist);
		if (mlist.size() == 1) {
			return (Method) mlist.get(0);
			// ������ж��,���������ƴ�Сд������.��������Ϊ��,
		} else {
			throw new WebActionException("�Ҳ���ƥ��ķ���");
		}

	}

	/**
	 * �����������,parameterTypeҪ��������,���õ����Ƕ�����,����ִ����Ҫ������,ת���Ƚ����� ,
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
