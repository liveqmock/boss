/*
 *    
 *check_TenDate(myDate,canBlank,callName) 检查文本输入的格式是否满足YYYY-MM-DD的格式
 *check_Blank(myInput,hintError,callName) 文本输入的检查，如果hintError=true表示如果文本输入为空格或者不输入，则弹出提示信息
 *del_Blank(myInput)                      删除文本框输入的所有空格
 *trim(string)                            删除文本框输入的前后空格
 *check_Num(myNum,canBlank,callName)      检查文本框输入的是否是整数
 *checkPlainNum(myNum,canBlank,len,callName)      检查文本框输入的是否是数字，并且不能超过指定的长度
 *check_Money(myNum,canBlank,callName)    检查文本框输入的是否满足####.##或者是-#####.## 的货币形式
 *check_Float(myNum,canBlank,callName)    检查文本框输入的是否满足双精度型的
 *check_fullDate(myDate,canBlank,callName)比check_TenDate功能更强大，能检查润年的日期
 *getCurrentDate()                        得到当前的日期
 *validateICBCAccount                     检查文本框输入的是否满足工商银行的各种卡格式
 *check_Email(FormName,emailName)         检查电子邮箱的合法性(FormName 表单是一个对象，emailName 一个文本框的名字是一个字符串型的)
 */
function checkPlainNum(myNum,canBlank,len,callName){ 
 	if(check_Blank(myNum,false,"") && canBlank) return true;
 	var numtype="0123456789"; 
 	if(myNum.value.length>len){
 		alert(callName+"输入长度不能超过"+len);
 		return false;
	}
 	for(i=0;i<myNum.value.length;i++){ 
	    if(numtype.indexOf(myNum.value.substring(i,i+1))<0) {
	    	alert(callName+"输入了非数字字符");
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
   isValidDate=check_BaseDate(myDate,chk_date,valy,valm,vald,callName+"不是一个有效的日期,请按YYYY-MM-DD方式输入一个有效的日期!");
 /*
 }
 Else
 {
   chk_date=new Date();
   isValidDate=check_BaseDate(myDate,chk_date,0,0,0,callName+"不是一个有效的日期,请按YYYY-MM-DD方式输入一个有效的日期!");
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
    	alert(callName+"不是合法日期！");
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
      alert(callName+"的值必填!");
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
   	    alert(callName+"不是有效的数值!");
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
   	    	alert(callName+"长度不能超过9位数字");
   	    	    
   	    alert(callName+"不是有效的数值!");
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
		alert(callName+"不是有效的数值!");
		myNum.focus();
            	if((myNum.type=="text")||(myNum.type=="textarea"))            
              	myNum.select();
            	return false;
	}
return true;
}

/** 检查电子邮箱的合法性
  * FormName 表单是一个对象，emailName 一个文本框的名字是一个字符串型的
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
        alert(emailName+"控件不存在，请查明！");
	return false;
   } else{
     var emailPat='^[a-zA-Z0-9]{1}[\.a-zA-Z0-9_-]*[a-zA-Z0-9]{1}@[a-zA-Z0-9]+[-]{0,1}[a-zA-Z0-9]+[\.]{1}[a-zA-Z]+[\.]{0,1}[a-zA-Z]+$';
     var matchArray=email.value.match(emailPat); 
     if (matchArray == null) {
	alert("请检查您的信箱地址填写是否有误！"); 
        email.focus(); 
        return false;
     }
     return true;
  }
}

//取得当前日期，格式：YYYY-MM-DD
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
         alert("时间格式不正确，请以2005-9-6,或者2004-10-12的格式输入！");
         return false;
      } else {
        if (IsDate(s[0],s[1],s[2])){
          return true;
        }else {
          alert("时间格式不正确，请以2005-9-6,或者2004-10-12的格式输入！");
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
 
 
//新的日期输入框检查 2008-2-25 begin 
function viewModule(obj)
{
	if(obj.value=='') 
	{
		obj.value='____-__-__';
		//值为空时将光标置于行首
		selection(obj,0,0);
	}
}
function inputDate(obj)
{
 //按键常量
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
	    
//键盘键的值
var keyCode=window.event.keyCode;

// 当前光标位置 
var nCursorPos = getCursor(obj).start;
//选择时光标的结束位置
var nCursorPosEnd = getCursor(obj).end;
// 当前文本框中的文本 
var strText =obj.value;
// 文本长度 
var nTextLen=strText.length;
// 特殊处理的按键 
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
    //忽略按键
    window.event.returnValue = false; 

var len = nCursorPosEnd-nCursorPos;
if(len==0)len=1;
//自行处理按键
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
	            
	        //一般键盘按键处理 
	        default :
	        //防止超长
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
    //判断日期合法性
    if(DealWith(strText))
    {
      obj.value=strText;
    	selection(obj,nCursorPos,nCursorPos); 
    }
}

//失去焦点时发生
function lostFocus(obj)
{
	if(obj.value=='____-__-__')
		obj.value='';
	else
	{
		if(!isValidDate(obj.value))
		{
			obj.focus();
			alert("日期不合法");
		}
	}
}
//根据光标所在的位置，判断输入的字符是否合法
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
	        

	        //光标所在的位置进行判断当前字符串是否合法
			return isValidDate(ls_date); 
	}
//是否为日期
function isValidDate(strDate)
	{
		var ls_regex = "^((((((0[48])|([13579][26])|([2468][048]))00)|([0-9][0-9]((0[48])|([13579][26])|([2468][048]))))-02-29)|(((000[1-9])|(00[1-9][0-9])|(0[1-9][0-9][0-9])|([1-9][0-9][0-9][0-9]))-((((0[13578])|(1[02]))-31)|(((0[1,3-9])|(1[0-2]))-(29|30))|(((0[1-9])|(1[0-2]))-((0[1-9])|(1[0-9])|(2[0-8]))))))$";
		var exp = new RegExp(ls_regex, "i");
		return exp.test(strDate);
}

//完全替换
function replaceAll(strOrg,strFind,strReplace){
 var index = 0;
 while(strOrg.indexOf(strFind,index) != -1){
  strOrg = strOrg.replace(strFind,strReplace);
  index = strOrg.indexOf(strFind,index);
 }
 return strOrg;
}

//动作：获取光标所在的位置，包括起始位置和结束位置
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
	
//动作：让field的start到end被选中
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
//新的日期输入框检查 2008-2-25 end

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