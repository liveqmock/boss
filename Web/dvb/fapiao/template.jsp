<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>发票选取</title>
    <link href="<%=request.getContextPath()%>/utils/jquery-ui.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/utils/jtable.2.4.0/themes/metro/blue/jtable.css" rel="stylesheet" type="text/css" >
    <script src="<%=request.getContextPath()%>/utils/external/jquery/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/utils/jquery-ui.js"></script>
    <script src="<%=request.getContextPath()%>/utils/jtable.2.4.0/jquery.jtable.js"></script>
</head>
<script type="text/javascript">

    $(document).ready(function () {

        $('#FapiaoTableContainer').jtable({
            title: '发票检索',
            paging: true, //Enable paging
            pageSize: 10, //Set page size (default: 10)
            sorting: false, //Enable sorting
            selecting: true, //Enable selecting
            multiselect: false, //Allow multiple selecting
            selectingCheckboxes: true, //Show checkboxes on first column
            selectOnRowClick: false, //Enable this to only select using checkboxes
            actions: {
                listAction: '<%=request.getContextPath()%>/ajax/fapiao.ajax?action=list'
            },
            fields: {
                batch: {
                    title: '批次号',
                    width: '23%'
                },
                fapiaodaima: {
                    title: '发票代码',
                    width: '23%'
                },

                fapiaohaoma: {
                    title: '发票号码',
                    width: '23%'
                }
            }

        });

        //Load Fapiao list from server
        $('#FapiaoTableContainer').jtable('load');
        $('#LoadRecordsButton').click(function (e) {
            e.preventDefault();
            $('#FapiaoTableContainer').jtable('load', {
                fapiaodaima: $.trim( $('#fapiaodaima').val()),
                fapiaohaoma: $.trim($('#fapiaohaoma').val())
            });
        });
        $("#selectBtn").click(function(){
            var $selectedRows = $('#FapiaoTableContainer').jtable('selectedRows');
            if ($selectedRows.length > 0) {
                //Show selected rows
                $selectedRows.each(function () {
                    var record = $(this).data('record');
                    window.returnValue= record.batch+','+ record.fapiaodaima+","+ record.fapiaohaoma;
                    window.close();
                });
            }
        })
    });

</script>
<body>
<div class="filtering">
    <form>
        <lable>发票代码</lable>: <input type="text" name="fapiaodaima" id="fapiaodaima" />
        <lable>发票号码</lable>: <input type="text" name="fapiaohaoma" id="fapiaohaoma" />
        <button type="submit" id="LoadRecordsButton">查询</button>
        <input type="button" id="selectBtn" value="选中"/>
    </form>

</div>
<div  class="filtering">

</div>
<div id="FapiaoTableContainer"></div>
</body>
</html>