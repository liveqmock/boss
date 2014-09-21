/*
 *   
 * btnBack_onclick()                              ����ǰһҳ
 * getStringByteLength(strValue)                  �����ַ����ĳ��ȡ�����������ַ�����һ��������ռ��2���ֽ�
 * checkOverStr(strSource, maxLength)             ���ǵ������ַ������⣬�ж��ı��ĳ����Ƿ񳬹��˸����ĳ���
 * truncateStringByLength(strSource, maxLength)   ���ǵ������ַ������⣬��һ���������ַ���strSource��0λ��ȡ�����������λmaxLength
 * getSelectedIndex(sel, str)                     ����������б�ѡ�е���
 * select_all_by_name(FormName,CheckBoxName,CheckFlag) ͨ�������ı�FormNameѡ�и�ѡ����ΪcheckBoxName������ѡ��
 * SwtichAllChoose(FormName,CheckBoxName,SwtichBoxName) ͨ��һ��SwtichBoxName��ѡ���ѡ�л���ȡ��������FormName�ĸ�ѡ����CheckBoxName��ȫѡ�л���ȫȡ��
 * check_chosen(FormName,CheckBoxName)            ����ı��ĸ�ѡ���Ƿ��Ѿ�ѡ����
 *
 */
 
function btnBack_onclick()
{
	history.go(-1);
}

/************************************
Method:			getStringByteLength
purpose:		��ȷ�ж������ַ��ĳ���
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
 * ���ǵ������ַ������⣬�ж��ı��ĳ����Ƿ񳬹��˸����ĳ���
 */
function checkOverStr(strSource, maxLength){
   if (getStringByteLength(strSource)> maxLength){
   	return true;
   } else{
        return false;
   } 
	
}

/*
�������ĵĽ�ȡ��ʽ
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

//����������б�ѡ�е���
function getSelectedIndex(sel, str)
{
    for (var i=0; i<sel.options.length; i++)  
    {
        if (sel.options(i).value == str) return i;      
    }

    return -1;
}

//ѡ������
function selectall(idname,type) {
    var e = document.forms[idname];
    for (var i=0;i<e.elements.length;i++)
    	e.elements[i].checked = type;
}

//ѡ������
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

//ͨ��һ��Ҫ��������ѡ��ѡ�л��߶�ȡ���ĸ�ѡ�ı������������ѡ����ѡ�л���ȡ��
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


//����ı��ĸ�ѡ���Ƿ��Ѿ�ѡ����
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

 
 /*����ƽ�Ʒ���
 sourceDate,�ַ���,��'2007-4-3',
 dateType,�ַ���,y,m,d,
 dateLength,int,ʱ�䳤��,
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
		var Message="��ѡ���ļ�����λ��"; 
		var Shell=new ActiveXObject("Shell.Application"); 
		var Folder=Shell.BrowseForFolder(0,Message,0x0040,0x11); 
		if(Folder!=null) 
		{ 
			Folder=Folder.items();//����FolderItems���� 
			Folder=Folder.item();//����Folderitem���� 
			Folder=Folder.Path;//����·�� 
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
		dialog.DialogTitle="�ļ����Ϊ";
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
			downDir=prompt("�������ļ�����·��:","");
		}

		if(!downDir){
			return ;
		}
		var patrnDriver=/[C|c|D|d|E|e]{1}:/;
		if(!patrnDriver.exec(downDir)){
			alert('��Ч��·��.');
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
			alert("��Ч��·��.");
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