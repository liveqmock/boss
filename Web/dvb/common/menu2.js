function drawSubMenu2(id){
  var subMenu = "mnu2"+id;
  var cid2 =document.frmTree.tableId.value ;
  //var img     = eval("arr2"+id);
  if ( document.all(subMenu).style.display=="" ){
	  hiddenSubMenu2(id);    
  }else{
    hiddenSubMenu2(cid2);
    showSubMenu2(id);
  }
}

function showSubMenu2(id)
{
  var subMenu = "mnu2"+id;
  
  if(document.all(subMenu)==null || document.all("arr2"+id)==null)
  	return;
  	
  var img     = eval("arr2"+id);
	document.all(subMenu).style.display="";
  img.src = "img/expand-1.gif";
  document.frmTree.tableId.value = id;
}


function hiddenSubMenu2(id){
  if (id == -1) 
    return;
  var subMenu = "mnu2"+id;
  
  if(document.all(subMenu)==null || document.all("arr2"+id)==null)
  	return;
  	
  var img     = eval("arr2"+id);
	document.all(subMenu).style.display="none";
  img.src = "img/expand-0.gif";
}


var act_a;
function chg_color(obj){
  if (act_a)
    act_a.className = "zlm";
  obj.className = "zlm1";
  act_a = obj;
}