/*
 *    
 *check_TenDate(myDate,canBlank,callName) ����ı�����ĸ�ʽ�Ƿ�����YYYY-MM-DD�ĸ�ʽ
 *check_Blank(myInput,hintError,callName) �ı�����ļ�飬���hintError=true��ʾ����ı�����Ϊ�ո���߲����룬�򵯳���ʾ��Ϣ
 *del_Blank(myInput)                      ɾ���ı�����������пո�
 *trim(string)                            ɾ���ı��������ǰ��ո�
 *check_Num(myNum,canBlank,callName)      ����ı���������Ƿ�������
 *checkPlainNum(myNum,canBlank,len,callName)      ����ı���������Ƿ������֣����Ҳ��ܳ���ָ���ĳ���
 *check_Money(myNum,canBlank,callName)    ����ı���������Ƿ�����####.##������-#####.## �Ļ�����ʽ
 *check_Float(myNum,canBlank,callName)    ����ı���������Ƿ�����˫�����͵�
 *check_fullDate(myDate,canBlank,callName)��check_TenDate���ܸ�ǿ���ܼ�����������
 *getCurrentDate()                        �õ���ǰ������
 *validateICBCAccount                     ����ı���������Ƿ����㹤�����еĸ��ֿ���ʽ
 *check_Email(FormName,emailName)         ����������ĺϷ���(FormName ����һ������emailName һ���ı����������һ���ַ����͵�)
 */
function checkPlainNum(myNum,canBlank,len,callName){ 
 	if(check_Blank(myNum,false,"") && canBlank) return true;
 	var numtype="0123456789"; 
 	if(myNum.value.length>len){
 		alert(callName+"���볤�Ȳ��ܳ���"+len);
 		return false;
	}
 	for(i=0;i<myNum.value.length;i++){ 
	    if(numtype.indexOf(myNum.value.substring(i,i+1))<0) {
	    	alert(callName+"�����˷������ַ�");
	    	return false; 
	 	} 
	}
	 return true; 
}
//YYYY-MM-DD 
function  check_TenDate(myDate,canBlank,callName)
{
 if(check_Blank(myDate,false,callName) && canBlank) return true;  
 var vale=myDate.value;
 var isValidDate=false;
 /*if (vale.length>9)
 {
 */
   valy=vale.substring(0,vale.indexOf("-"));  
   vale=vale.substr(vale.indexOf("-")+1);
   valm=vale.substring(0,vale.indexOf("-"));
   vald=vale.substr(vale.indexOf("-")+1);
  
   chk_date=new Date(valy,valm-1,vald);
   isValidDate=check_BaseDate(myDate,chk_date,valy,valm,vald,callName+"����һ����Ч������,�밴YYYY-MM-DD��ʽ����һ����Ч������!");
 /*
 }
 Else
 {
   chk_date=new Date();
   isValidDate=check_BaseDate(myDate,chk_date,0,0,0,callName+"����һ����Ч������,�밴YYYY-MM-DD��ʽ����һ����Ч������!");
 }*/
 
 return isValidDate;
}

function check_fullDate(myDate,canBlank,callName)
{
 if(check_Blank(myDate,false,callName) && canBlank) return true;  
    var vale=myDate.value;
    var isValidDate=false;
    valy=vale.substring(0,vale.indexOf("-"));  
    vale=vale.substr(vale.indexOf("-")+1);
    valm=vale.substring(0,vale.indexOf("-"));
    vald=vale.substr(vale.indexOf("-")+1);
    isValidDate=IsDate(valy,valm,vald);
    if (!isValidDate){
    	alert(callName+"���ǺϷ����ڣ�");
    	myDate.focus();
    }
    return isValidDate;	
}


function compareDate(startDate,endDate,message){
	returnedValue=false;
	if(startDate.value!='' && endDate.value !=''){
		startvale=startDate.value;
		starty=startvale.substring(0,startvale.indexOf("-"))*1;  
	    starte=startvale.substr(startvale.indexOf("-")+1);
	    startm=starte.substring(0,starte.indexOf("-"))*1;
	    startd=starte.substr(starte.indexOf("-")+1)*1;
		endvale=endDate.value;
		endy=endvale.substring(0,endvale.indexOf("-"))*1;  
	    ende=endvale.substr(endvale.indexOf("-")+1);
	    endm=ende.substring(0,ende.indexOf("-"))*1;
	    endd=ende.substr(ende.indexOf("-")+1)*1;
		if(starty<endy)
			returnedValue=true;
		else if (starty==endy)
		{
			if (startm<endm)
				returnedValue=true;
			else if (startm==endm)
			{
				if(startd<=endd)
					returnedValue=true;
			}
			
		}
	}
	else returnedValue=true;
	
	if(!returnedValue){
		alert(message);
		endDate.focus();
	}
	return returnedValue;	
}

function check_Blank(myInput,hintError,callName) {
   var myvalue=del_Blank(myInput);
   var isBlank=(myvalue.length==0);
   if(hintError && isBlank) 
   {
      alert(callName+"��ֵ����!");
      if(myInput.type!="hidden"){
          myInput.focus();
      }
      if((myInput.type=="text")||(myInput.type=="textarea"))
        myInput.select();
   }
  return isBlank;
}

function del_Blank(myInput)
 {
    var myValue="";
  for(var i=0; i<myInput.value.length;i++)
    {
      var c=myInput.value.charAt(i);
      if (c!=' ') myValue=myValue+c;	
    }
   return myValue; 
 }

function check_BaseDate(myDate,chk_date,valy,valm,vald,callName){
 var isValidDate=false;  
  isValidDate=!(( valy < 1000 ) |(chk_date.getFullYear()!=valy)|(chk_date.getMonth()!=valm-1)|(chk_date.getDate()!=vald));
  if(!isValidDate) {
	     alert(callName);	
       
       if((myDate.type=="text")||(myDate.type=="textarea")) 
       {
       	 myDate.focus();      
         myDate.select();
       }
 }	
 return isValidDate; 
  
}  


function IsDate(strYear,strMonth,strDay)
{
  var Month,day;
  var Days=new Array(31,28,31,30,31,30,31,31,30,31,30,31);
  if (strMonth.substring(0,1)=="0")
    Month=strMonth.substring(1,2);
  else
    Month=strMonth;

  if (strDay.substring(0,1)=="0")
    Day=strDay.substring(1,2);
  else
    Day=strDay;


  Year=parseInt(strYear);
  Month=parseInt(Month);
  Day=parseInt(Day);

  if(!(IsNumber(strYear) && IsNumber(strMonth) && IsNumber(strDay)))
    return false;
  if(Month<1 || Month>12)
    return false;
  if(Month==2 && (((Year % 4 ==0) && (Year % 100 != 0)) || (Year % 400 == 0)))
  {
    if(Day<1 || Day>Days[Month-1]+1)
      return false;
  }
  else
  {
    if(Day<1 || Day>Days[Month-1])
      return false;
  }

  return true;
}

function getCurrentDate(){
  var gdCurDate = new Date();
  var giYear = gdCurDate.getFullYear();
  var giMonth = gdCurDate.getMonth() + 1 ;
  var giDay = gdCurDate.getDate();
  return giYear+"-"+giMonth+"-"+giDay;
}


function trim(string) {
   var tmpchar, i, j, result;
   i = 0;
   tmpchar = string.charAt(i);
   while (tmpchar == ' ') {
      i ++;
      tmpchar = string.charAt(i);
   }
   j = string.length - 1;
   tmpchar = string.charAt(j);
   while (tmpchar == ' ') {
      j --;
      tmpchar = string.charAt (j);
   }
   if ( i <= j)
      result = string.substring(i,j+1);
   else
      result = "";
   return result;
}

function  check_Float(myNum,canBlank,callName) 
{
   if(check_Blank(myNum,false,"") && canBlank) return true;  
   var allNum=true;		
   var cnt=0;
   myNum.value=del_Blank(myNum);
   for(var i=0;i<myNum.value.length;i++)
   {
      var c=myNum.value.charAt(i);
      if(i==0 && (c=='+' || c=='-')) continue;
      if(myNum.value.charAt(0)== '.' ){
         cnt =2;
	 break;
      } 
      if ((i==1) && ((myNum.value.charAt(0) =='+' || myNum.value.charAt(0) =='-')  && c=='.')){
         cnt =2;
	 break;
       }  
      if (i==1 && myNum.value.charAt(0)== '0' &&  c !='.'){
	 cnt =2;
	 break;
      }
      if ((i==2) && ((myNum.value.charAt(0) =='+' || myNum.value.charAt(0) =='-') && myNum.value.charAt(1)=='0' &&  myNum.value.charAt(2)!='.')){
         cnt =2;
	 break;
      }  
      if(c == '.')      {  cnt = cnt + 1;      }
      if(cnt>1)       {  break;      }
      if((c<'0'||c>'9')&&(c != '.'))      {  allNum=false;   break;  }
   }
   if( cnt<2 &&  allNum) 
     {
      return true;
     }
   else { 
   	    alert(callName+"������Ч����ֵ!");
            myNum.focus();
            if((myNum.type=="text")||(myNum.type=="textarea"))            
              myNum.select();
            return false;
           }
  
  return true;
}


function  check_Num(myNum,canBlank,callName) 
{
   if(check_Blank(myNum,false,"") && canBlank) return true;  
   var allNum=true;
   var tooLong=false;		
   var zerohead=true;
   myNum.value=del_Blank(myNum);
   if (myNum.value.length>9) {allNum=false;tooLong=true;}
   for(var i=0;i<myNum.value.length;i++)
   {
      var c=myNum.value.charAt(i);
      if (c=='0'&&zerohead&&(i<(myNum.value.length-1))) { allNum=false; break;}
      else {zerohead=false;}
      if(c<'0'||c>'9')      {  allNum=false;   break;  }
   }
   if(allNum) 
     {
      return true;
     }
   else { 
   	    if (tooLong)
   	    	alert(callName+"���Ȳ��ܳ���9λ����");
   	    	    
   	    alert(callName+"������Ч����ֵ!");
            myNum.focus();
            if((myNum.type=="text")||(myNum.type=="textarea"))            
              myNum.select();
            return false;
           }
  
  return true;
}

function check_Money(myNum,canBlank,callName)
{
	if(check_Blank(myNum,false,"") && canBlank) return true;  
	myNum.value=del_Blank(myNum);
	var validNum=true;
	var dotnum=0;
	var dotlocate=0;
	var dotstatus=false;
	
	if (myNum.value.charAt(myNum.value.length-1)=='.')
	{
		validNum=false;
	}
	for(var i=0;i<myNum.value.length;i++)
	{		
		var c=myNum.value.charAt(i);
		
		if((c<'0'||c>'9')&&c!='.')      
		{  
			if (!((i==0)&&(c=='-')))
			{
			    validNum=false; break;  
			}    
		}
		if (c=='.') 
		{
			dotnum++;
			dotlocate=i;
			dotstatus=true;
		}
		if (dotnum>=2) {validNum=false;}
		if (dotstatus && ((myNum.value.length-dotlocate)>3))
		{
			validNum=false;
		}
	}
	if (validNum)
	{
		return true;
	}
	else
	{
		alert(callName+"������Ч����ֵ!");
		myNum.focus();
            	if((myNum.type=="text")||(myNum.type=="textarea"))            
              	myNum.select();
            	return false;
	}
return true;
}

/** ����������ĺϷ���
  * FormName ����һ������emailName һ���ı����������һ���ַ����͵�
  */
function check_Email(FormName,emailName){
   var a  = FormName.tags("input");
   var email =null;
   if (a!=null) {	        
       if (a.length!=null) {
	  for (i=0; i<a.length; i++) {
	     if (a(i).type== "text" && a(i).name==emailName){
	        email =a(i);
		break;
	     }
	  }
       }
    }

   if (email ==null){
        alert(emailName+"�ؼ������ڣ��������");
	return false;
   } else{
     var emailPat='^[a-zA-Z0-9]{1}[\.a-zA-Z0-9_-]*[a-zA-Z0-9]{1}@[a-zA-Z0-9]+[-]{0,1}[a-zA-Z0-9]+[\.]{1}[a-zA-Z]+[\.]{0,1}[a-zA-Z]+$';
     var matchArray=email.value.match(emailPat); 
     if (matchArray == null) {
	alert("�������������ַ��д�Ƿ�����"); 
        email.focus(); 
        return false;
     }
     return true;
  }
}

//ȡ�õ�ǰ���ڣ���ʽ��YYYY-MM-DD
function getDate(){
    var varDate = new Date();
    return varDate.getYear()+"-"+(parseInt(varDate.getMonth())+1)+"-"+varDate.getDate();
}

	function startsWith(comparedStr,paramStr) {
		if (comparedStr == null || comparedStr == '' || paramStr == null || paramStr == '') {
			return false;
		}
		var param_len = paramStr.length;
		if (comparedStr.length < param_len) {
			return false;
		} else if (comparedStr.substring(0,param_len) == paramStr) {
			return true;			
		} else {
			return false;
		}
	}
	
	
	
	
var Days=new Array(31,28,31,30,31,30,31,31,30,31,30,31);

function IsNumber(theText)
{
  for (var  i=0;i<theText.length;i++)
  {
    var strTemp= theText.charAt(i)
              if (!(strTemp>='0' && strTemp<='9' || strTemp=='.'))
                return false;
  }
  return true;
}

function IsInteger(theText)
{
	var zerohead=true;
  for (var  i=0;i<theText.length;i++)
  {
    var strTemp= theText.charAt(i);
    if (strTemp=='0'&&zerohead&&(i<(theText.length-1))) { 
    	return false;
    }else {
    	zerohead=false;
    }
    if (!(strTemp>='0' && strTemp<='9' || strTemp=='-'))
      return false;
  }
  return true;
}

function IsDate(strYear,strMonth,strDay)
{
  var Month,day;

  if (strMonth.substring(0,1)=="0")
    Month=strMonth.substring(1,2);
  else
    Month=strMonth;

  if (strDay.substring(0,1)=="0")
    Day=strDay.substring(1,2);
  else
    Day=strDay;


  Year=parseInt(strYear);
  Month=parseInt(Month);
  Day=parseInt(Day);

  if(!(IsNumber(strYear) && IsNumber(strMonth) && IsNumber(strDay)))
    return false;
  if(Month<1 || Month>12)
    return false;
  if(Month==2 && (((Year % 4 ==0) && (Year % 100 != 0)) || (Year % 400 == 0)))
  {
    if(Day<1 || Day>Days[Month-1]+1)
      return false;
  }
  else
  {
    if(Day<1 || Day>Days[Month-1])
      return false;
  }

  return true;
}

function getCurrentDate(){
  var gdCurDate = new Date();
  var giYear = gdCurDate.getFullYear();
  var giMonth = gdCurDate.getMonth() + 1 ;
  var giDay = gdCurDate.getDate();
  return giYear+"-"+giMonth+"-"+giDay;
}

 function check_date(sourceStr){
      s =sourceStr.split("-");
      if (s.length !=3){
         alert("ʱ���ʽ����ȷ������2005-9-6,����2004-10-12�ĸ�ʽ���룡");
         return false;
      } else {
        if (IsDate(s[0],s[1],s[2])){
          return true;
        }else {
          alert("ʱ���ʽ����ȷ������2005-9-6,����2004-10-12�ĸ�ʽ���룡");
          return false;
        }
      }               
 } 
    
 function replaceStr(sourceStr,swapStr){
    var temp ="";
    s =sourceStr.split("-");
    for (i=0; i<s.length; i++){
       if (s[i].length <2) s[i] =swapStr+s[i];
       temp =temp+s[i];
    }
    return temp;
 }
 
 function compareDateAndToday(startDate,endDate,message){
	returnedValue=false;
	if(startDate!='' && endDate.value !=''){
		startvale=startDate;
		starty=startvale.substring(0,startvale.indexOf("-"))*1;  
	    starte=startvale.substr(startvale.indexOf("-")+1);
	    startm=starte.substring(0,starte.indexOf("-"))*1;
	    startd=starte.substr(starte.indexOf("-")+1)*1;
		endvale=endDate.value;
		endy=endvale.substring(0,endvale.indexOf("-"))*1;  
	    ende=endvale.substr(endvale.indexOf("-")+1);
	    endm=ende.substring(0,ende.indexOf("-"))*1;
	    endd=ende.substr(ende.indexOf("-")+1)*1;
		if(starty<endy)
			returnedValue=true;
		else if (starty==endy)
		{
			if (startm<endm)
				returnedValue=true;
			else if (startm==endm)
			{
				if(startd<=endd)
					returnedValue=true;
			}
			
		}
	}
	else returnedValue=true;
	
	if(!returnedValue){
		alert(message);
		endDate.focus();
	}
	return returnedValue;	
 } 
 
 
//�µ������������ 2008-2-25 begin 
function viewModule(obj)
{
	if(obj.value=='') 
	{
		obj.value='____-__-__';
		//ֵΪ��ʱ�������������
		selection(obj,0,0);
	}
}
function inputDate(obj)
{
 //��������
	    var KEY = {
			BACKSPACE: 8,
		    TAB: 9,
		    ENTER: 13,
			END: 35,
			HOME: 36,
		    LEFT: 37,
			RIGTH: 39,
		    DEL: 46,
		    INSERT:45
	    };
	    
//���̼���ֵ
var keyCode=window.event.keyCode;

// ��ǰ���λ�� 
var nCursorPos = getCursor(obj).start;
//ѡ��ʱ���Ľ���λ��
var nCursorPosEnd = getCursor(obj).end;
// ��ǰ�ı����е��ı� 
var strText =obj.value;
// �ı����� 
var nTextLen=strText.length;
// ���⴦��İ��� 
	    switch(keyCode){
	        case KEY.TAB:
			    case KEY.HOME:
			    case KEY.END:
	        case KEY.LEFT:
	        case KEY.RIGTH:
	        case KEY.INSERT:
	        case KEY.ENTER://??
				     return;
	    }
    //���԰���
    window.event.returnValue = false; 

var len = nCursorPosEnd-nCursorPos;
if(len==0)len=1;
//���д�����
 switch (keyCode) 
	    { 
	        case KEY.BACKSPACE:
	       for(i=0;i<len;i++)
	        {
	            if(nCursorPosEnd > 0)
	            { 
	                var fronttext = strText.substr(nCursorPosEnd - 1,1); 
	                
	                if(fronttext!="-")
	                
	                    fronttext="_"; 
	                    strText =  strText.substr(0,nCursorPosEnd - 1) + fronttext + strText.substr(nCursorPosEnd, nTextLen-nCursorPosEnd); 
	                    nCursorPosEnd--; 
	                
	             
	            }  
	         }
	         nCursorPos=nCursorPosEnd;
	         break; 
	        case KEY.DEL:
		       for(i=0;i<len;i++)
		       {
		            if(nCursorPos<nTextLen) 
		            { 
		                var behindtext = strText.substr(nCursorPos,1); 
		                if(behindtext!="-") behindtext="_"; 
		                if(nCursorPos + 1 == nTextLen) 
		                    strText =  strText.substr(0,nCursorPos) + behindtext; 
		                else 
		                    strText =  strText.substr(0,nCursorPos) + behindtext + strText.substr(nCursorPos+1,nTextLen-nCursorPos-1); 
		                nCursorPos++; 
		            } 
		       }
            nCursorPosEnd=nCursorPos;
           break;
	            
	        //һ����̰������� 
	        default :
	        //��ֹ����
        if(nCursorPos==nTextLen) return; 
        if(!(keyCode >=48 && keyCode<=57 || keyCode >=96 && keyCode<=105)) return;
				if (keyCode > 95) keyCode -= (95-47); 
        if(strText.substr(nCursorPos,1) !='-')
        {
        	strText = strText.substr(0,nCursorPos) + String.fromCharCode(keyCode) + strText.substr(nCursorPos+1,nTextLen);
        	nCursorPos++;
        }
        if(strText.substr(nCursorPos,1) =='-')
         nCursorPos++;
        
     }
    //�ж����ںϷ���
    if(DealWith(strText))
    {
      obj.value=strText;
    	selection(obj,nCursorPos,nCursorPos); 
    }
}

//ʧȥ����ʱ����
function lostFocus(obj)
{
	if(obj.value=='____-__-__')
		obj.value='';
	else
	{
		if(!isValidDate(obj.value))
		{
			obj.focus();
			alert("���ڲ��Ϸ�");
		}
	}
}
//���ݹ�����ڵ�λ�ã��ж�������ַ��Ƿ�Ϸ�
	function DealWith(input) 
	{	   
          var inputStr = input;
          inputStr = replaceAll(inputStr,"_","0");
	        var ls_year = inputStr.substr(0,4);  
	        if(ls_year=="0000") ls_year = "2001";  
	        var ls_month = inputStr.substr(5,2);  
	        if(ls_month=="00") ls_month = "01";  
	        var ls_day = inputStr.substr(8,2);  
	        if(ls_day=="00") ls_day = "01";  
	        var ls_date = ls_year +"-"+ ls_month +"-"+ ls_day; 
	        

	        //������ڵ�λ�ý����жϵ�ǰ�ַ����Ƿ�Ϸ�
			return isValidDate(ls_date); 
	}
//�Ƿ�Ϊ����
function isValidDate(strDate)
	{
		var ls_regex = "^((((((0[48])|([13579][26])|([2468][048]))00)|([0-9][0-9]((0[48])|([13579][26])|([2468][048]))))-02-29)|(((000[1-9])|(00[1-9][0-9])|(0[1-9][0-9][0-9])|([1-9][0-9][0-9][0-9]))-((((0[13578])|(1[02]))-31)|(((0[1,3-9])|(1[0-2]))-(29|30))|(((0[1-9])|(1[0-2]))-((0[1-9])|(1[0-9])|(2[0-8]))))))$";
		var exp = new RegExp(ls_regex, "i");
		return exp.test(strDate);
}

//��ȫ�滻
function replaceAll(strOrg,strFind,strReplace){
 var index = 0;
 while(strOrg.indexOf(strFind,index) != -1){
  strOrg = strOrg.replace(strFind,strReplace);
  index = strOrg.indexOf(strFind,index);
 }
 return strOrg;
}

//��������ȡ������ڵ�λ�ã�������ʼλ�úͽ���λ��
function getCursor(textBox){
		var obj = new Object();
		var start = 0,end = 0;

			var range=textBox.createTextRange(); 
			var text = range.text;
			var selrange = document.selection.createRange();
			var seltext = selrange.text;
			while(selrange.compareEndPoints("StartToStart",range)>0){ 
				selrange.moveStart("character",-1); 
				start ++;
			}
			while(selrange.compareEndPoints("EndToStart",range)>0){ 
				selrange.moveEnd("character",-1); 
				end ++;
			}
		
		obj.start = start;
		obj.end = end;
		return obj;
}
	
//��������field��start��end��ѡ��
function selection(field, start, end) 
	{
		if( field.createTextRange ){
			var r = field.createTextRange();
			r.moveStart('character',start);
			r.collapse(true);
			r.select(); 
		} else if( field.setSelectionRange ){
			field.setSelectionRange(start, end);
		} else {
			if( field.selectionStart ){
				field.selectionStart = start;
				field.selectionEnd = end;
			}
		}
		field.focus();
}
//�µ������������ 2008-2-25 end

function validata_num(str_num,bit_num){
	  var myregexp;
	  if (bit_num ==0){
	  	  myregexp=/^[0-9]*$/;
	  } else{
	      myregexp = /^[0-9]+\.{0,1}[0-9]{0,bit_num}$/;
	    //  /^([1-9]+)(\.[0-9]{0,bit_num})?$/;
    }
    var num_match = myregexp.exec(str_num);
    if (num_match != null) {
        return true;
    } else {
        return false;
    }
}