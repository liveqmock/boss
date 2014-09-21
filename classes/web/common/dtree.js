
var imgPath=["../images/icon/expand.gif","../images/icon/expand2.gif","../images/icon/node.gif"]

function dtree(obj,target,check){
	this.obj=obj;
	this.target=target
	this.child=0
	this.node=0
	this.level=0
	this.msg=[]
	this.icon=[]
	this.showCheck=check
	this.html="<table id='deeptree' onselectstart='return false' cellspacing=0 cellpadding=0 border=0>"
	/*
	for(i=0;i<imgPath.length;i++){
	var tem=new Image()
	this.icon[i]=tem.src=imgPath[i]
}
*/

}

function getTreeNodeShowText(txt, curLevel)
{
	maxLen = 24 - curLevel * 2;
	curLen = getStringByteLength(txt);
	
	if (curLen>maxLen) txtShow= truncateStringByLength(txt, maxLen-3) + '...';
	 else txtShow = txt;
	
	return txtShow;
	
}

dtree.prototype.addFolder=function(txt,shrinkIcon,expandIcon,show,link){
	if (shrinkIcon==null) shrinkIcon=imgPath[0];
	if (expandIcon==null) expandIcon=imgPath[1]; 
	//txtlength=18-this.level*2;
	//if (txt.length>txtlength) txtshow=txt.substring(0,txtlength)+'...';
	//else txtshow=txt;
	txtshow=getTreeNodeShowText(txt, this.level);
	
	this.msg[this.node]=[txt,link?link:'']
	this.html+="<tr><td class='node'><img src='"+(show?expandIcon:shrinkIcon)+"' id='img"+this.child+"' border=0 align='absmiddle' onclick='";
	this.html+=this.obj+".expand("+this.child+",";
	this.html+='"';
	this.html+=shrinkIcon;
	this.html+='","';
	this.html+=expandIcon;
	this.html+='"';
	this.html+=")'>&nbsp;<input type='checkbox' name='treeFolder' onclick='"+this.obj+".checkAll(this,"+this.child+",event)' style='display:"+(this.showCheck?'':'none')+"'><span onmouseover='doOver(this)' onmouseout='doOut(this)' onmousedown='"+this.obj+".Light(this,"+this.node+");";
	this.html+=this.obj+".expand("+this.child+",";
	this.html+='"';
	this.html+=shrinkIcon;
	this.html+='","';
	this.html+=expandIcon;
	this.html+='"';
	this.html+=")' title='"+txt+"'>"+txtshow+"</span></td></tr><tr id='child"+this.child+"' style='display:"+(show?'':'none')+"'><td class='node'>";
	this.html+="<table cellspacing=0 cellpadding=0 border=0 style='margin-left:18;'>";
	this.child++;
	this.node++;
	this.level++;
}

dtree.prototype.addNode=function(txt,icon,link,sel){
	if (icon==null) icon=imgPath[2];
	//txtlength=13-this.level*1.5;
	//if (txt.length>txtlength) txtshow=txt.substring(0,txtlength)+'...';
	//else txtshow=txt;
	
	txtshow=getTreeNodeShowText(txt, this.level);
	
	this.msg[this.node]=[txt,link?link:'']
	this.html+="<tr>"
	if (sel) this.html+="<td class='nodesel'>";
	 else this.html+="<td class='node'>";
	this.html+="<img src='"+icon+"' align='absmiddle'>&nbsp;<input type='checkbox' name='treeNode' onclick='"+this.obj+".parentCheck(this,event)' style='display:"+(this.showCheck?'':'none')+"'><span onmouseover='doOver(this)' onmouseout='doOut(this)' onmousedown='"+this.obj+".Light(this,"+this.node+")' title='"+txt+"'>"+txtshow+"</span></td></tr>"
	this.node++;
}

dtree.prototype.endFolder=function(){
	this.html+="</table></td></tr>"
	this.level--;
}


dtree.prototype.expand=function(childNum,shrinkIcon,expandIcon){
	var isExpand=document.getElementById("child"+childNum).style.display
	
        if (shrinkIcon==null) shrinkIcon=imgPath[0];
	if (expandIcon==null) expandIcon=imgPath[1]; 
	
        document.getElementById("img"+childNum).src=isExpand=='none'?expandIcon:shrinkIcon
        document.getElementById("child"+childNum).style.display=isExpand=='none'?'':'none'
}

dtree.prototype.expandAll=function(flag){
	if(this.child>0)
	for(i=0;i<this.child;i++){
	//document.getElementById("img"+i).src=flag?imgPath[1]:imgPath[0]
	document.getElementById("child"+i).style.display=flag?'':'none'
}
}

dtree.prototype.checkAll=function(obj,childNum,evt){
	obj.blur()
	var evt=window.event
	if(!evt.shiftKey)return;
	var child=document.getElementById("child"+childNum)
	var node=child.getElementsByTagName("INPUT")
	for(i=0;i<node.length;i++)node[i].checked=obj.checked
	this.parentCheck(obj,evt)
}

dtree.prototype.parentCheck=function(obj,evt){
	obj.blur()
	evt=evt?evt:(window.event)?window.event:""
	if(!evt.shiftKey)return;
	for(i=this.child-1;i>=0;i--){
	var checkParent=true
	var c=document.getElementById("child"+i)
	var node=c.getElementsByTagName("INPUT")
	for(j=0;j<node.length;j++)if(!node[j].checked)checkParent=false
	document.getElementById("img"+i).nextSibling.checked=checkParent
	}
}

dtree.prototype.getCheckedValue=function(){
	var value=[]
	var node=document.getElementById('deeptree').getElementsByTagName("INPUT")
	for(i=0;i<node.length;i++)if(node[i].checked&&node[i].name=="treeNode")value[value.length]=this.msg[i][0]
	return value

}
dtree.prototype.init=function(){
	this.html+="</table>"
	document.write(this.html)
}

var tem=null
function doOver(o){
o.className='NodeOver'
}

function doOut(o){
o.className=(tem==o?'NodeFocus':'')
}
dtree.prototype.Light=function(o,nodeNum){
if(!tem)tem=o
tem.className=''
o.className='NodeFocus'
tem=o
if(this.msg[nodeNum][1])window.open(this.msg[nodeNum][1],this.target);//ÐÞ¸ÄÄ¿±ê¿ò¼Ü
}
