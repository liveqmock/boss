function firstPage_onClick(frm, to)
{
        frm.txtFrom.value = 1;
        frm.txtTo.value = to;
        frm.txtPage.value = 0;
	frm.submit();
}

function previous_onClick(frm, from, to)
{

        frm.txtFrom.value = from;
        frm.txtTo.value = to;
        frm.txtPage.value = 0;
	frm.submit();
}

function next_onClick(frm, from, to)
{
        frm.txtFrom.value = from;
        frm.txtTo.value = to;
        frm.txtPage.value = 0;
	frm.submit();
}

function lastPage_onClick(frm, from , to)
{
        frm.txtFrom.value = from;
        frm.txtTo.value = to;
        frm.txtPage.value = 0;
        frm.submit();
}

function check_pageNumber(frm, pageamount)
{
	if (frm.txtPage != null)
	{
		if (!check_Num(frm.txtPage, true, "页号"))
		    return false;
		    
		if ((frm.txtPage.value<=0)||(frm.txtPage.value>pageamount))
        {
            alert("页号只能是1到"+pageamount);
        	frm.txtPage.focus();
        	return false;
        }
                
        return true;
		
	}
        
        return false;
}

function goto_onClick(frm, pageamount)
{
	if (check_pageNumber(frm, pageamount)) 
	{
		return true;
	}
	else
		return false;
}