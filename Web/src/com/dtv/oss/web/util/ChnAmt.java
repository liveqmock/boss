package com.dtv.oss.web.util;

import java.text.DecimalFormat;

/**   
 *   Сд���ת����д���   
 *   @author   Lucky   &   Ryan   
 */
public class ChnAmt {
	private String[] saChnAmount = new String[50];

	private int iArrayLen = 0;

	private int iArrayPos = 0;

	/**   
	 *   ����-Сд���ת����д����(������)   
	 *   @param String sAmount   
	 */
	public ChnAmt(String sAmount) {
		int iDecFrac = 0;

		if (sAmount.indexOf(".") != -1) {
			iDecFrac = sAmount.length() - sAmount.indexOf(".");
			if (iDecFrac > 3) {
				iDecFrac = 3;
			}
			sAmount = sAmount.substring(0, sAmount.indexOf("."))
					+ sAmount.substring(sAmount.indexOf(".") + 1, sAmount
							.indexOf(".")
							+ iDecFrac);
			for (; iDecFrac < 3; iDecFrac++) {
				sAmount += "0";
			}
		} else {
			sAmount += "00";
		}

		toChnAmt(sAmount, 2, false);
	}

	/**   
	 *   ����-Сд���ת����д����(����)   
	 *   @param String sAmount   
	 *   @param int iDec   
	 *   @param boolean bDot   
	 */
	public ChnAmt(String sAmount, int iDec, boolean bDot) {
		if (bDot) {
			if (sAmount.indexOf(".") < 0) {
				//   û��С����   
				sAmount += ".";
				for (int iFor = iDec; iFor > 0; iFor--) {
					sAmount += "0";
				}
			} else {
				int iDecLen = sAmount.length() - sAmount.indexOf(".") - 1;
				if (iDecLen > iDec) {
					//   ȥλ   
					sAmount = sAmount.substring(0, sAmount.indexOf(".") + iDec
							+ 1);
				} else if (iDecLen < iDec) {
					//   ����   
					for (int iFor = iDec - iDecLen; iFor > 0; iFor--) {
						sAmount += "0";
					}
				}
			}
		}
		toChnAmt(sAmount, iDec, bDot);
	}

	/**   
	 *   Сд���ת����д   
	 *   @param String sAmount   
	 *   @param int iDec   
	 *   @param boolean bDot   
	 */
	public void toChnAmt(String sAmount, int iDec, boolean bDot) {
		//   ����   
		final String[] sUserCode = { "��", "Ҽ", "��", "��", "��", "��", "½", "��",
				"��", "��" };
		//   ��λ   
		final String[] sChnUnit = { "��", "��", "��", "Ԫ", "ʮ", "��", "ǧ", "��",
				"ʮ", "��", "ǧ", "��", "ʮ", "��", "ǧ", "��" };
		//   ��   
		final String sSpecEnd = "��";
		//   ��־   
		//   '9'����ʮ   
		//   '3'   -   'Ԫ',   '1'   -   '��'(   ����ǰһ�����ַ�Ϊ'��'��������'��'   ),   '3'   -   '��'   
		//   '0'   -   ����   
		final byte[] byFlag = { 0, 0, 0, 3, 9, 0, 0, 1, 9, 0, 0, 3, 9, 0, 0, 0 };

		int iCounter, iPos;
		byte byPreFlag = -1;
		char cDigit, cPreDigit = ' ';

		iArrayLen = 0;

		switch (iDec) {
		//   ����   
		case 0:
			sAmount += "0";
		case 1:
			sAmount += "0";
		case 2:
			sAmount += "0";
		case 3:
			break;
		default:
			sAmount = sAmount.substring(0, sAmount.length() - iDec + 3);
		}

		if (bDot) {
			//   ת��С����   
			sAmount = sAmount.substring(0, sAmount.length() - 4)
					+ sAmount.substring(sAmount.length() - 3);
		}

		//   ��ʼת��   
		for (iCounter = sAmount.length(); iCounter > 0; iCounter--) {
			//   ��������λ��   
			iPos = sAmount.length() - iCounter;
			//   ��������ַ�   
			cDigit = sAmount.charAt(iPos);

			//   ��������   
			if (cDigit < '0' || cDigit > '9')
				cDigit = '0';

			if (cDigit != '0') {
				//   ��ǰ���ֲ�Ϊ'0'   
				if (cPreDigit == '0') {
					//   ǰһ����Ϊ'0'   
					if (byPreFlag != 3 && byPreFlag != 1) {
						//   '��'��'��'��'Ԫ'   �󲻼�   '��'   
						saChnAmount[++iArrayLen] = sUserCode[0];
					}
				}

				if (cDigit == '1' && byFlag[iCounter - 1] == 9
						&& iArrayLen == 0) {
					//   ����Ϊ'1'����λΪ'ʮ'����Ϊ��һ������ʱ������'1'��ֻ��'ʮ'   
					saChnAmount[++iArrayLen] = sChnUnit[iCounter - 1];
				} else {
					//   ��������   
					saChnAmount[++iArrayLen] = sUserCode[Character.digit(
							cDigit, 10)];
					//   ���ɵ�λ   
					saChnAmount[++iArrayLen] = sChnUnit[iCounter - 1];

					//   ��¼��ǰλ��־   
					byPreFlag = byFlag[iCounter - 1];
				}
			} else {
				//   ����Ϊ'0'   
				if (iArrayLen == 0) {
					//   ������λ'0'   
					continue;
				} else {
					if (byFlag[iCounter - 1] == 3
							|| (byFlag[iCounter - 1] == 1 && byPreFlag != 3 && !saChnAmount[iArrayLen]
									.equals(sChnUnit[11]))) {
						//   ����'��'��'��'��'Ԫ'�ĵ�λ���������'ǧ��'λ��'��'λ��Ϊ0��������'��'   
						saChnAmount[++iArrayLen] = sChnUnit[iCounter - 1];
					}
					//   ��¼��ǰλ��־   
					byPreFlag = byFlag[iCounter - 1];
				}
			}

			//   ��¼��ǰ����   
			cPreDigit = cDigit;
		}

		if (iArrayLen == 0) {
			//   ת���������鳤��Ϊ0������'0Ԫ��'   
			saChnAmount[++iArrayLen] = sUserCode[0];
			saChnAmount[++iArrayLen] = sChnUnit[3];
			saChnAmount[++iArrayLen] = sSpecEnd;
		}

		if (saChnAmount[iArrayLen].equals(sChnUnit[3])) {
			//   ���λΪ'Ԫ'������'��'   
			saChnAmount[++iArrayLen] = sSpecEnd;
		}

	}

	/**   
	 *   �ж��Ƿ�����һ��ת��Ԫ��   
	 *   @return boolean   
	 */
	public boolean next() {
		boolean bRtn = false;

		if (iArrayPos++ < iArrayLen) {
			bRtn = true;
		}
		return bRtn;

	}

	/**   
	 *   ��ȡ��ǰת��Ԫ��   
	 *   @return String   
	 */
	public String getResult() {
		String sRtn = "";

		sRtn = saChnAmount[iArrayPos];

		return sRtn;
	}

	/**   
	 *   ת��double��String   
	 *   @param double dMoney   
	 *   @return String   
	 */
	public static String Double2String(double dMoney) {
		String sMoney = "";

		DecimalFormat df = new DecimalFormat("############.###");
		try {
			sMoney = df.format(dMoney);
		} catch (Exception e) {
			sMoney = "";
		}

		return sMoney;
	}
	

	
	public static void main(String[] sArgv) {
		ChnAmt chnAMT;

		chnAMT = new ChnAmt("1100.00");

		System.out.println("ChnAmt()   [936.00]   to   ");

		while (chnAMT.next()) {
			System.out.println("Value[" + chnAMT.getResult() + "]");
		}
	}
}

