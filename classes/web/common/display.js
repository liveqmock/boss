
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_showHideLayers() { //v6.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


function calendar(t) {
	var oImg=t;
	var eT=0,eL=0,p=oImg;
	var sT=document.body.scrollTop,sL=document.body.scrollLeft;
	var eH=oImg.height,eW=oImg.width;
	while(p&&p.tagName!="BODY"){eT+=p.offsetTop;eL+=p.offsetLeft;p=p.offsetParent;}
	var top= window.screenTop+eT+25;//(document.body.clientHeight-(eT-sT)-eH>=228)?eT+eH:eT-228;
	var left=window.screenLeft+eL;//(document.body.clientWidth-(eL-sL)>=206)?eL:eL+eW-206;
	var sPath = "calendar1.htm";
	var dp;
	strFeatures = "dialogLeft="+left+"px;dialogTop="+top+"px;dialogWidth=206px;dialogHeight=228px;help=no";
	st = t.value;
	sDate = showModalDialog(sPath,st,strFeatures);
	dp = formatDate(sDate,0);
	
	if (dp!="")
	{
	    t.value=formatDate(sDate,0);
	}
}

function formatDate(sDate) {
	var sScrap = "";
	var dScrap = new Date(sDate);
	if (dScrap == "NaN") return sScrap;
	
	iDay = dScrap.getDate();
	iMon = dScrap.getMonth();
	iYea = dScrap.getFullYear();
	sScrap = iYea + "-" + (iMon + 1) + "-" + iDay ;
	return sScrap;
}
//调出地区选择窗口
function sel_district(type,id,desc)
{
   var  strFeatures = "top=120px,left=120px,width=350px,height=400px,resizable=no,toolbar=no,scrollbars=no";
   window.open("district_select.screen?showType=dist&dsType="+type+"&dsID="+id+"&dsDesc="+desc,"地区选择",strFeatures);
}
//调出地区选择窗口是checkbox的
function sel_districts(type,id,desc,labelType)
{
   var  strFeatures = "top=120px,left=120px,width=350px,height=400px,resizable=no,toolbar=no,scrollbars=no";
   window.open("district_select.screen?showType=dist&dsType="+type+"&dsID="+id+"&dsDesc="+desc+"&labType="+labelType,"地区选择",strFeatures);
}
//调出地区选择窗口
function sel_organization(type,id,desc)
{
   var  strFeatures = "top=120px,left=120px,width=350px,height=400px,resizable=no,toolbar=no,scrollbars=no";
   window.open("organization_select.screen?showType=org&dsType="+type+"&dsID="+id+"&dsDesc="+desc,"地区选择",strFeatures);
}
function query_window(strTitle,valueControlID,descControlID,strUrl)
{
   var strFeatures = "top=120px,left=120px,width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes";
   var vUrl='';
   if(strUrl.indexOf('?')>0){
	   vUrl=strUrl+"&";
	 }else{
	   vUrl=strUrl+"?";
	 }
   vUrl=vUrl+"txtFrom=1&txtTo=10&valueControlID="+valueControlID+"&descControldID="+descControlID;
   window.open(vUrl,strTitle,strFeatures);
}

function download(frm,fn){
	if(!frm||!frm.action){
		alert("参数错误.");
		return;
	}
	var oldac=frm.action;
	var ac=oldac;
	ac+="?QueryWebActionIsDown="+true;
	ac+="&DownloadWebActionFileName="+fn;
	ac+="&DownloadWebActionFileType=html";
	frm.action=ac;
	frm.submit();
	frm.action=oldac;
}