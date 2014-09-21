/*
 *   
 * btnBack_onclick()                              返回前一页
 * getStringByteLength(strValue)                  返回字符串的长度。如果是中文字符，则一个中文字占用2个字节
 * checkOverStr(strSource, maxLength)             考虑到中文字符的问题，判断文本的长度是否超过了给定的长度
 * truncateStringByLength(strSource, maxLength)   考虑到中文字符的问题，对一个给定的字符串strSource从0位截取到给定的最大位maxLength
 * getSelectedIndex(sel, str)                     获得下拉匡中被选中的项
 * select_all_by_name(FormName,CheckBoxName,CheckFlag) 通过给定的表单FormName选中复选框名为checkBoxName的所有选项
 * SwtichAllChoose(FormName,CheckBoxName,SwtichBoxName) 通过一个SwtichBoxName复选框的选中还是取消决定表单FormName的复选框组CheckBoxName是全选中还是全取消
 * check_chosen(FormName,CheckBoxName)            检查文本的复选框是否已经选择了
 *
 */
 
function btnBack_onclick()
{
	history.go(-1);
}

/************************************
Method:			getStringByteLength
purpose:		正确判断中文字符的长度
parameters :	        the string which is to be checked
return value :	        the length of string
************************************/
function getStringByteLength(strValue)
{
	iLength = strValue.length;
	oLength = iLength;
	
	for(i=0;i<iLength;i++)
	{
		c = strValue.charCodeAt(i);
						
		if ((c < 0)||(c > 255)) oLength = oLength + 1;
						
	}
					
	return oLength;
			
}

/*
 * 考虑到中文字符的问题，判断文本的长度是否超过了给定的长度
 */
function checkOverStr(strSource, maxLength){
   if (getStringByteLength(strSource)> maxLength){
   	return true;
   } else{
        return false;
   } 
	
}

/*
考虑中文的截取方式
*/
function truncateStringByLength(strSource, maxLength)
{
	iLength = strSource.length;
	iLen = 0;
	
	for (i=0;i<iLength;i++)
	{
		c = strSource.charCodeAt(i);

		if ((c < 0)||(c > 255)) 
			iLen = iLen + 2;		
		 else iLen=iLen + 1;
		
		if (iLen>maxLength) break;					
	}
	
	return strSource.substring(0,i+1);
}

//获得下拉匡中被选中的项
function getSelectedIndex(sel, str)
{
    for (var i=0; i<sel.options.length; i++)  
    {
        if (sel.options(i).value == str) return i;      
    }

    return -1;
}

//选中所有
function selectall(idname,type) {
    var e = document.forms[idname];
    for (var i=0;i<e.elements.length;i++)
    	e.elements[i].checked = type;
}

//选中所有
function select_all_by_name(FormName,CheckBoxName,CheckFlag) {
    var a  = FormName.tags("input");
    if (a!=null)  
    {	        
	if (a.length!=null) 
	{
	    for (i=0; i<a.length; i++) 
	    {
	        if (a(i).type== "checkbox" && a(i).name==CheckBoxName) 
			a(i).checked=CheckFlag;

	    }
	}
    }   
}

//通过一个要求将其它复选框都选中或者都取消的复选文本框决定其它复选框是选中还是取消
function SwtichAllChoose(FormName,CheckBoxName,SwtichBoxName){
    var a  = FormName.tags("input");
    var SwtichBoxCheck =false;
    var iamount =0;
    if (a!=null)  
    {	        
	if (a.length!=null) 
	{
	    for (i=0; i<a.length; i++) 
	    {
	        if (a(i).type== "checkbox" && a(i).name==SwtichBoxName) {
	             if (a(i).checked) SwtichBoxCheck =true;	             
	             iamount =1; 
	             break;           
	        }
	    }
	}
    }   
    if (iamount ==1){
    	select_all_by_name(FormName,CheckBoxName,SwtichBoxCheck);
    }
}


//检查文本的复选框是否已经选择了
function check_chosen(FormName,CheckBoxName)
{
       
        var a  = FormName.tags("input");
	var iamount = 0;
	
	if (a!=null)  {	        
		if (a.length!=null) {
			for (i=0; i<a.length; i++) {
			    if (a(i).type== "checkbox") {
				if ( a(i).checked==true && a(i).name!=CheckBoxName) {
					iamount++;
				}
			    }				
			}
		}
	}

        if (iamount>0) return true;    
        else return false;
        
}

 
 /*日期平移方法
 sourceDate,字符串,如'2007-4-3',
 dateType,字符串,y,m,d,
 dateLength,int,时间长度,
 */
function transferDate(sourceDate,dateType,dateLength){
	if(sourceDate==null){
		sourceDate=getCurrentDate();
	}
	var arrTarget=sourceDate.split('-');
	var tYear=parseInt(arrTarget[0]);
	var tMonth=parseInt(arrTarget[1])-1;
	var tDay=parseInt(arrTarget[2]);
	if(!IsDate(tYear+'',tMonth+'',tDay+'')){
		return transferDate(null,dateType,dateLength);
	}
  if(dateType == 'Y' || dateType == 'y'){
	tYear = tYear + dateLength;
  } else if (dateType == 'M' || dateType == 'm') {
	tMonth = tMonth + dateLength;
  } else if (dateType == 'D' || dateType == 'd') {
	tDay = tDay + dateLength;
  }
  var targetDate=new Date(tYear,tMonth,tDay);
  return targetDate.getFullYear()+'-'+(targetDate.getMonth()+1)+'-'+targetDate.getDate()+'';
}


function getCurrentTime(){
  var gdCurDate = new Date();
  var giYear = gdCurDate.getFullYear();
  var giMonth = gdCurDate.getMonth() + 1 ;
  var giDay = gdCurDate.getDate();
  var giHour=gdCurDate.getHours();
  var giMin=gdCurDate.getMinutes();
  var giSec=gdCurDate.getSeconds();
  return giYear+"-"+giMonth+"-"+giDay+" "+giHour+" "+giMin+" "+giSec;
}

function BrowseFolder() 
{ 
	try{
		var Message="请选择文件保存位置"; 
		var Shell=new ActiveXObject("Shell.Application"); 
		var Folder=Shell.BrowseForFolder(0,Message,0x0040,0x11); 
		if(Folder!=null) 
		{ 
			Folder=Folder.items();//返回FolderItems对象 
			Folder=Folder.item();//返回Folderitem对象 
			Folder=Folder.Path;//返回路径 
			if(Folder.charAt(Folder.length-1)!="\\") 
			{ 
				Folder=Folder+"\\"; 
			} 
			return Folder; 
		}
	}catch(e){
//		alert(e.description);
		return -1;
	}
} 

function showFileDialog(){
	try{
		var dialog=new ActiveXObject("MSComDlg.CommonDialog");
		dialog.Filter="Microsoft Office Excel(*.xls)|*.xsl";
		dialog.FilterIndex = 2;
		dialog.DialogTitle="文件另存为";
		dialog.MaxFileSize=128;
		dialog.ShowSave();
		return dialog.FileName;
	}catch(ex){
		return -1;
	}
}

function createFile(fileContent,fName,isOpen){
	try{
//		var downDir=BrowseFolder();
		var downDir=showFileDialog();
		if(downDir==-1){
			downDir=BrowseFolder();
		}
		if(downDir==-1){
			downDir=prompt("请输入文件保存路径:","");
		}

		if(!downDir){
			return ;
		}
		var patrnDriver=/[C|c|D|d|E|e]{1}:/;
		if(!patrnDriver.exec(downDir)){
			alert('无效的路径.');
			return;
		}

		var lastOp=downDir.lastIndexOf('\\');
		if(lastOp<2){
			lastOp=downDir.lastIndexOf('/');
		}
		var fPath='';
		var patrnXls=/\.xls$/i;
		if(!patrnXls.exec(downDir)){
			if(lastOp!=downDir.length-1){
				fPath=downDir+'\\'+getCurrentTime()+fName;
			}else{
				fPath=downDir+getCurrentTime()+fName;
			}
		}else{
			fPath=downDir;
		}
		var fso=new ActiveXObject("Scripting.FileSystemObject");
		var xlsFile=fso.CreateTextFile(fPath,true);
		if(!fso.FileExists(fPath)){
			alert("无效的路径.");
		}
		fileContent.border=1;
		xlsFile.write(fileContent);
		xlsFile.close();
		if(isOpen){
			var xlApp=new ActiveXObject("Excel.Application"); 
			xlApp.Visible=true;
			xlApp.Workbooks.Open(fPath); 
		}
		return fPath;
	}catch(e){
		alert(e.description);
	}

}