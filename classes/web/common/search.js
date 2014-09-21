var cid = -1;
function drawSubMenu(id){
  var subMenu = "mnu"+id;
  if ( document.all(subMenu).style.display=="" ){
	  hiddenSubMenu(id);    
  }else{
    hiddenSubMenu(cid);
    showSubMenu(id);
  }
}

function drawSelfMenu(id){
  var subMenu = "mnu"+id;
  if ( document.all(subMenu).style.display=="" ){
	  hiddenSubMenu(id);    
  }else{
    showSubMenu(id);
  }
}

function showSubMenu(id)
{
  var subMenu = "mnu"+id;
  var img = "arr" +id;
  document.all(subMenu).style.display="";
  document.all(img).src = "img/icon_top.gif";
  cid = id;
}


function hiddenSubMenu(id){
  if (id == -1) 
    return;
  var subMenu = "mnu"+id;
  var img = "arr" +id;
  document.all(subMenu).style.display="none";
  document.all(img).src = "img/icon_bottom.gif";
}



var act_a;
function chg_color(obj){
  if (act_a)
    act_a.className = "zlm";
  obj.className = "zlm1";
  act_a = obj;
}