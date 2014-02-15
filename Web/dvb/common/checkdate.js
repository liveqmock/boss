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
