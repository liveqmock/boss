function L_calendar(){}
L_calendar.prototype={
	_VersionInfo:"Version:1.0&#13;作者: ",
	Moveable:true,
	NewName:"",
	insertId:"",
	ClickObject:null,
	InputObject:null,
	InputDate:null,
	IsOpen:false,
	MouseX:0,
	MouseY:0,
	GetDateLayer:function(){
		
		return window.L_DateLayer;
		},
	L_TheYear:new Date().getFullYear(), //定义年的变量的初始值
	L_TheYearLength:5, //定义年范围间距初始值
	L_TheMonth:new Date().getMonth()+1,//定义月的变量的初始值
	L_WDay:new Array(42),//定义写日期的数组
	MonHead:new Array(31,28,31,30,31,30,31,31,30,31,30,31),    		   //定义阳历中每个月的最大天数
	GetY:function(){
		var obj;
		if (arguments.length > 0){
			obj==arguments[0];
			}
		else{
			obj=this.InputObject;
			}
		if(obj!=null){
			var y = obj.offsetTop;
			while (obj = obj.offsetParent) y += obj.offsetTop;
			return y;}
		else{return 0;}
		},
	GetX:function(){
		var obj;
		if (arguments.length > 0){
			obj==arguments[0];
			
			}
		else{
			obj=this.InputObject;
			}
		if(obj!=null){
			var y = obj.offsetLeft;
			while (obj = obj.offsetParent) y += obj.offsetLeft;
			return y;}
		else{return 0;}
		},
	CreateHTML:function(){
		var htmlstr="";
		htmlstr+="<div id=\"L_calendar\">\r\n";
		htmlstr+="<span id=\"SelectYearLayer\" style=\"z-index: 9999;position: absolute;top: 3; left: 23;display: none\"></span>\r\n";
		htmlstr+="<span id=\"SelectMonthLayer\" style=\"z-index: 9999;position: absolute;top: 3; left: 81;display: none\"></span>\r\n";
		htmlstr+="<div id=\"L_calendar-year-month\"><div id=\"L_calendar-PrevY\" onmouseover=\"this.style.background='#8FB8FF'\"  onmouseout=\"this.style.background='#FFFFFF'\" onclick=\"parent."+this.NewName+".PrevY()\" title=\"前一年\"><b>&lt;&lt;</b></div><div id=\"L_calendar-year\" onmouseover=\"style.backgroundColor='#8FB8FF'\" onmouseout=\"style.backgroundColor='white'\" onclick=\"parent."+this.NewName+".SelectYearInnerHTML('"+this.L_TheYearLength+"')\"></div><div id=\"L_calendar-month\"  onmouseover=\"style.backgroundColor='#8FB8FF'\" onmouseout=\"style.backgroundColor='white'\" onclick=\"parent."+this.NewName+".SelectMonthInnerHTML()\"></div><div id=\"L_calendar-NextY\" onmouseover=\"this.style.background='#8FB8FF'\"  onmouseout=\"this.style.background='#FFFFFF'\" onclick=\"parent."+this.NewName+".NextY()\" title=\"后一年\"><b>&gt;&gt;</b></div></div>\r\n";
		htmlstr+="<div id=\"L_calendar-week\"><ul onmouseup=\"StopMove()\"><li>日</li><li>一</li><li>二</li><li>三</li><li>四</li><li>五</li><li>六</li></ul></div>\r\n";
		htmlstr+="<div id=\"L_calendar-day\">\r\n";
		htmlstr+="<ul>\r\n";
		for(var i=0;i<this.L_WDay.length;i++){
			htmlstr+="<li id=\"L_calendar-day_"+i+"\" style=\"background:#e0e0e0\" onmouseover=\"this.style.background='#8FB8FF'\"  onmouseout=\"this.style.background='#EFEFEF'\"></li>\r\n";
			}
		htmlstr+="</ul>\r\n";
		htmlstr+="<span></span>\r\n";
		htmlstr+="<span id=\"L_calendar-clear\" onmouseover=\"this.style.background='#8FB8FF'\"  onmouseout=\"this.style.background='#D1EFFF'\" onclick=\"parent."+this.NewName+".Clear()\"><b>清空</b></span>\r\n";
		htmlstr+="<span id=\"L_calendar-today\" onmouseover=\"this.style.background='#8FB8FF'\"  onmouseout=\"this.style.background='#D1EFFF'\" onclick=\"parent."+this.NewName+".Today()\"><b>今天</b></span>\r\n";
		htmlstr+="<span id=\"L_calendar-close\" onmouseover=\"this.style.background='#8FB8FF'\"  onmouseout=\"this.style.background='#D1EFFF'\" onclick=\"parent."+this.NewName+".Close()\"><b>关闭</b></span>\r\n";
		htmlstr+="</div>\r\n";
		//htmlstr+="<div id=\"L_calendar-control\"></div>\r\n";
		htmlstr+="</div>\r\n";
		htmlstr+="<scr" + "ipt type=\"text/javas" + "cript\">\r\n";
		htmlstr+="var MouseX,MouseY;";
		htmlstr+="var Moveable="+this.Moveable+";\r\n";
		htmlstr+="var MoveaStart=false;\r\n";
		htmlstr+="document.onmousemove=function(e)\r\n";
		htmlstr+="{\r\n";
		htmlstr+="var DateLayer=parent.document.getElementById(\"L_DateLayer\");\r\n";
		htmlstr+="	e = window.event || e;\r\n";
		htmlstr+="var DateLayerLeft=DateLayer.style.posLeft || parseInt(DateLayer.style.left.replace(\"px\",\"\"));\r\n";
		htmlstr+="var DateLayerTop=DateLayer.style.posTop || parseInt(DateLayer.style.top.replace(\"px\",\"\"));\r\n";
		htmlstr+="if(MoveaStart){DateLayer.style.left=(DateLayerLeft+e.clientX-MouseX)+\"px\";DateLayer.style.top=(DateLayerTop+e.clientY-MouseY)+\"px\"}\r\n";
		htmlstr+=";\r\n";
		htmlstr+="}\r\n";
		
		htmlstr+="document.getElementById(\"L_calendar-week\").onmousedown=function(e){\r\n";
		htmlstr+="if(Moveable){MoveaStart=true;}\r\n";
		htmlstr+="	e = window.event || e;\r\n";
		htmlstr+="  MouseX = e.clientX;\r\n";
		htmlstr+="  MouseY = e.clientY;\r\n";
		htmlstr+="	}\r\n";
		
		htmlstr+="function StopMove(){\r\n";
		htmlstr+="MoveaStart=false;\r\n";
		htmlstr+="	}\r\n";
		htmlstr+="</scr"+"ipt>\r\n";
		var stylestr="";
		stylestr+="<style type=\"text/css\">";
		stylestr+="body{background:#fff;font-size:12px;margin:0px;padding:0px;text-align:left}\r\n";
		stylestr+="#L_calendar{border:1px solid #2577FF;width:158px;padding:1px;height:180px;z-index:9998;text-align:center}\r\n";
		stylestr+="#L_calendar-year-month{height:23px;line-height:23px;z-index:9998;}\r\n";
		stylestr+="#L_calendar-year{line-height:23px;width:60px;float:left;z-index:9998;position: absolute;top: 3; left: 23;cursor:default}\r\n";
		stylestr+="#L_calendar-month{line-height:23px;width:48px;float:left;z-index:9998;position: absolute;top: 3; left: 81;cursor:default}\r\n";
		stylestr+="#L_calendar-PrevM{position: absolute;top: 3; left: 20;cursor:pointer}";
		stylestr+="#L_calendar-NextM{position: absolute;top: 3; left: 130;cursor:pointer}";
		stylestr+="#L_calendar-PrevY{position: absolute;top: 3; left: 3;cursor:pointer}";
		stylestr+="#L_calendar-NextY{position: absolute;top: 3; left: 143;cursor:pointer}";
		stylestr+="#L_calendar-week{height:23px;line-height:23px;z-index:9998;}\r\n";
		stylestr+="#L_calendar-day{height:136px;z-index:9998;}\r\n";
		stylestr+="#L_calendar-week ul{cursor:move;list-style:none;margin:0px;padding:0px;}\r\n";
		stylestr+="#L_calendar-week li{width:20px;height:20px;float:left;;margin:1px;padding:0px;text-align:center;background:#D1EFFF}\r\n";
		stylestr+="#L_calendar-day ul{list-style:none;margin:0px;padding:0px;}\r\n";
		stylestr+="#L_calendar-day li{cursor:pointer;width:20px;height:20px;float:left;;margin:1px;padding:0px;}\r\n";
		stylestr+="#L_calendar-control{height:25px;z-index:9998;}\r\n";
		stylestr+="#L_calendar-clear{cursor:pointer;float:left;width:50px;height:20px;line-height:20px;margin:1px;text-align:center;background:#D1EFFF}";
		stylestr+="#L_calendar-today{cursor:pointer;float:left;width:50px;height:20px;line-height:20px;margin:1px;text-align:center;background:#D1EFFF}";
		stylestr+="#L_calendar-close{cursor:pointer;float:left;width:50px;height:20px;line-height:20px;margin:1px;text-align:center;background:#D1EFFF}";
		stylestr+="</style>";
		var TempLateContent="<html>\r\n";
		TempLateContent+="<head>\r\n";
		TempLateContent+="<title></title>\r\n";
		TempLateContent+=stylestr;
		TempLateContent+="</head>\r\n";
		TempLateContent+="<body>\r\n";
		TempLateContent+=htmlstr;
		TempLateContent+="</body>\r\n";
		TempLateContent+="</html>\r\n";
		this.GetDateLayer().document.writeln(TempLateContent);
		this.GetDateLayer().document.close();
		},
	InsertHTML:function(id,htmlstr){
		var L_DateLayer=this.GetDateLayer();
		if(L_DateLayer){L_DateLayer.document.getElementById(id).innerHTML=htmlstr;}
		},
	WriteHead:function (yy,mm)  //往 head 中写入当前的年与月
  	{
		this.InsertHTML("L_calendar-year",yy + " 年");
		this.InsertHTML("L_calendar-month",mm + " 月");
  	},
	IsPinYear:function(year)            //判断是否闰平年
  	{
    	if (0==year%4&&((year%100!=0)||(year%400==0))) return true;else return false;
  	},
	GetMonthCount:function(year,month)  //闰年二月为29天
  	{
    	var c=this.MonHead[month-1];if((month==2)&&this.IsPinYear(year)) c++;return c;
  	},
	GetDOW:function(day,month,year)     //求某天的星期几
  	{
    	var dt=new Date(year,month-1,day).getDay()/7; return dt;
  	},
	GetText:function(obj){
		if(obj.innerText){return obj.innerText}
		else{return obj.textContent}
		},
	PrevM:function()  //往前翻月份
  	{
    	if(this.L_TheMonth>1){this.L_TheMonth--}else{this.L_TheYear--;this.L_TheMonth=12;}
    	this.SetDay(this.L_TheYear,this.L_TheMonth);
  	},
	NextM:function()  //往后翻月份
  	{
    	if(this.L_TheMonth==12){this.L_TheYear++;this.L_TheMonth=1}else{this.L_TheMonth++}
    	this.SetDay(this.L_TheYear,this.L_TheMonth);
  	},
  	PrevY:function()  //往前翻年份
  	{
    	this.L_TheYear--;
    	this.SetDay(this.L_TheYear,this.L_TheMonth);
  	},
	NextY:function()  //往后翻年份
  	{
    	this.L_TheYear++;
    	this.SetDay(this.L_TheYear,this.L_TheMonth);
  	},
  Clear:function()  //Clear Button
  	{
		  this.InputObject.value="";
    	this.CloseLayer();
  	},
	Today:function()  //Today Button
  	{
		var today;
    	this.L_TheYear = new Date().getFullYear();
    	this.L_TheMonth = new Date().getMonth()+1;
    	var month=this.L_TheMonth+"";
    	if(month.length==1)month="0"+month;
    	today=new Date().getDate()+"";
    	if(today.length==1)today="0"+today;
    	if(this.InputObject){
		this.InputObject.value=this.L_TheYear + "-" + month + "-" + today;
    	}
    	this.CloseLayer();
  	},
  Close:function()  //Close Button
  	{
    	this.CloseLayer();
  	},
	SetDay:function (yy,mm)   //主要的写程序**********
	{
	    
  		this.WriteHead(yy,mm);
	  	//设置当前年月的公共变量为传入值
  		this.L_TheYear=yy;
  		this.L_TheMonth=mm;
  		for (var i = 0; i < 42; i++){this.L_WDay[i]=""};  //将显示框的内容全部清空
  		var day1 = 1,day2=1,firstday = new Date(yy,mm-1,1).getDay();  //某月第一天的星期几
 
  		for (i=0;i<firstday;i++)this.L_WDay[i]=this.GetMonthCount(mm==1?yy-1:yy,mm==1?12:mm-1)-firstday+i+1	//上个月的最后几天
  		for (i = firstday; day1 < this.GetMonthCount(yy,mm)+1; i++){this.L_WDay[i]=day1;day1++;}
  		for (i=firstday+this.GetMonthCount(yy,mm);i<42;i++){this.L_WDay[i]=day2;day2++}
  		for (i = 0; i < 42; i++)
		 {
			var da=this.GetDateLayer().document.getElementById("L_calendar-day_"+i+"");
			var month,day;
			
    	if (this.L_WDay[i]!="")
			{ 
			   if(i<firstday){
					da.innerHTML="<b style=\"color:#EFEFEF\">" + this.L_WDay[i] + "</b>";
					month=(mm==0?12:mm-1);
					day=this.L_WDay[i];
				}
				else if(i>=firstday+this.GetMonthCount(yy,mm)){
					da.innerHTML="<b style=\"color:#EFEFEF\">" + this.L_WDay[i] + "</b>";
				  month=(mm==0?12:mm+1);
				  day=this.L_WDay[i];
				}
				//当前日期在这里出现
				else{
				if(yy == new Date().getFullYear()&& mm==new Date().getMonth()+1&&(this.L_WDay[i])==new Date().getDate())
					da.innerHTML="<b style=\"color:red\">" + this.L_WDay[i] + "</b>";
				else
					da.innerHTML="<b style=\"color:#000\">" + this.L_WDay[i] + "</b>";
					month=(mm==0?12:mm);
					day=this.L_WDay[i];
					if(document.all){
						da.onclick=Function(this.NewName+".DayClick("+month+","+day+")");
					}
					else{
						da.setAttribute("onclick",this.NewName+".DayClick("+month+","+day+")");
					}
				}
				da.title=month+" 月"+day+" 日";
				da.style.background= "#EFEFEF";
				
      }
  		}
	},
	SelectYearInnerHTML:function (strYearLength) //年份的下拉框
	{
 	 var m = this.L_TheYear+"";
 	 	if (m.match(/\D/)!=null){alert("年份输入参数不是数字！");return;}
 	 	var m = (m) ? m : new Date().getFullYear();
		if (m < 1000 || m > 9999) {alert("年份值不在 1000 到 9999 之间！");return;}
		  var n = m - strYearLength;
		  //if(strYear!=new Date().getFullYear())
  		 //n = m-strYearLength*2;

  		if (n < 1000) n = 1000;
  		if (strYearLength*2+n > 9999) n = 9974;
  		var s = "<select name=\"L_SelectYear\" id=\"L_SelectYear\" style='font-size: 12px' "
     		s += "onblur='document.getElementById(\"SelectYearLayer\").style.display=\"none\"' "
     		s += "onchange='document.getElementById(\"SelectYearLayer\").style.display=\"none\";"
     		s += "parent."+this.NewName+".L_TheYear = this.value; parent."+this.NewName+".SetDay(parent."+this.NewName+".L_TheYear,parent."+this.NewName+".L_TheMonth)'>\r\n";
  		var selectInnerHTML = s;
  		for (var i = n; i < n + strYearLength*2; i++)
  		{
    		if (i == m)
       		{selectInnerHTML += "<option value='" + i + "' selected>" + i + "年" + "</option>\r\n";}
    		else {selectInnerHTML += "<option value='" + i + "'>" + i + "年" + "</option>\r\n";}
 		}
  		selectInnerHTML += "</select>";
		var DateLayer=this.GetDateLayer();
  		DateLayer.document.getElementById("SelectYearLayer").style.display="";
  		DateLayer.document.getElementById("SelectYearLayer").innerHTML = selectInnerHTML;
  		DateLayer.document.getElementById("L_SelectYear").focus();
		},
	SelectMonthInnerHTML:function (strMonth) //月份的下拉框
	{
  		var m = this.L_TheMonth+"";
  		if (m.match(/\D/)!=null){alert("月份输入参数不是数字！");return;}
  		m = (m) ? m : new Date().getMonth() + 1;

 		var s = "<select name=\"L_SelectYear\" id=\"L_SelectMonth\" style='font-size: 12px' "
     		s += "onblur='document.getElementById(\"SelectMonthLayer\").style.display=\"none\"' "
     		s += "onchange='document.getElementById(\"SelectMonthLayer\").style.display=\"none\";"
     		s += "parent."+this.NewName+".L_TheMonth = this.value; parent."+this.NewName+".SetDay(parent."+this.NewName+".L_TheYear,parent."+this.NewName+".L_TheMonth)'>\r\n";
  		var selectInnerHTML = s;
  		for (var i = 1; i < 13; i++)
  		{
    		if (i == m)
       		{selectInnerHTML += "<option value='"+i+"' selected>"+i+"月"+"</option>\r\n";}
    		else {selectInnerHTML += "<option value='"+i+"'>"+i+"月"+"</option>\r\n";}
  		}
  		selectInnerHTML += "</select>";
		var DateLayer=this.GetDateLayer();
  		DateLayer.document.getElementById("SelectMonthLayer").style.display="";
  		DateLayer.document.getElementById("SelectMonthLayer").innerHTML = selectInnerHTML;
  		DateLayer.document.getElementById("L_SelectMonth").focus();
	},
	DayClick:function(mm,dd)  //点击显示框选取日期，主输入函数*************
	{
		var yy=this.L_TheYear;
		//判断月份，并进行对应的处理
		if(mm<1){yy--;mm=12+mm;}
		else if(mm>12){yy++;mm=mm-12;}
  		if (mm < 10){mm = "0" + mm;}
  		if (this.InputObject)
  		{if (!dd) {return;}
    	if ( dd < 10){dd = "0" + dd;}
    	this.InputObject.value= yy + "-" + mm + "-" + dd ; //注：在这里你可以输出改成你想要的格式
    	this.CloseLayer();
 		}
  		else {this.CloseLayer(); alert("您所要输出的控件对象并不存在！");}
	},
	SetDate:function(){
		if (arguments.length <  1){alert("对不起！传入参数太少！");return;}
		else if (arguments.length >  3){alert("对不起！传入参数太多！");return;}
		this.InputObject= arguments[1];//输入框
		this.ClickObject=arguments[0];//点击的对象
		var currentFlag = "Y";
		if(arguments.length >  2)
			currentFlag = arguments[2];	
		if("N"==currentFlag){
			this.L_TheYear=new Date().getFullYear()-30;
			this.L_TheMonth=1;
			this.L_TheYearLength=31;
			}
		else{
			this.L_TheYear=new Date().getFullYear();
			this.L_TheMonth=new Date().getMonth() + 1;
			this.L_TheYearLength=5;
			}
		this.CreateHTML();
		var top=this.GetY();
		var left=this.GetX();
		var DateLayer=document.getElementById("L_DateLayer");
		DateLayer.style.top=top+this.InputObject.clientHeight+5+"px";
		DateLayer.style.left=left+"px";
		DateLayer.style.display="block";
		if(document.all){
			this.GetDateLayer().document.getElementById("L_calendar").style.width="160px";
			this.GetDateLayer().document.getElementById("L_calendar").style.height="200px"
			}
		else{
			this.GetDateLayer().document.getElementById("L_calendar").style.width="154px";
			this.GetDateLayer().document.getElementById("L_calendar").style.height="180px"
			DateLayer.style.width="158px";
			DateLayer.style.height="250px";
			}
		//alert(DateLayer.style.display)
		this.SetDay(this.L_TheYear,this.L_TheMonth);
		},
	CloseLayer:function(){
		try{
			var DateLayer=document.getElementById("L_DateLayer");
			if((DateLayer.style.display=="" || DateLayer.style.display=="block") && arguments[0]!=this.ClickObject && arguments[0]!=this.InputObject){
				DateLayer.style.display="none";
			}
		}
		catch(e){}
		}
	}
	
document.writeln('<iframe id="L_DateLayer" name="L_DateLayer" frameborder="0" style="position:absolute;width:160px; height:210px;z-index:9998;display:none;"></iframe>');
var MyCalendar=new L_calendar();
MyCalendar.NewName="MyCalendar";
document.onclick=function(e)
{
	e = window.event || e;
  	var srcElement = e.srcElement || e.target;
	MyCalendar.CloseLayer(srcElement);
}