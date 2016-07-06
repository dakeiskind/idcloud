var myDestktop;
var userLevelCodeAndName = $.extend({}, vrCodeAndName, {
    getDatas: function () {
        var sup = this;
        if (this.datas == null) {
            $.ajax({
                       url: ws_url + "/rest/level/findAll",
                       type: 'POST',
                       async: false,
                       data: "{}",
                       contentType: 'application/json',
                       dataType: 'json',
                       success: function (data) {
                           sup.datas = [];
                           for (var i = 0; i < data.length; i++) {

                               sup.datas.push({
                                                  "codeValue": data[i]["levelSid"],
                                                  "codeDisplay": data[i]["levelName"]
                                              });
                           }

                       }
                   });
        }
        return this.datas;
    }
});
function MyDestktop() {
    //getModuleRights();
    var me = this;
    this.searchOrderObj = {};
    this.fetchSearchData = function () {
        this.searchOrderObj['qm.userLevel'] =
            $('#userLevelCodeAndNameSearch').val() == 'all' ? null : $(
                '#userLevelCodeAndNameSearch').val();
        this.searchOrderObj['qm.userGroup'] =
            $('#userGroupCodeAndNameSearch').val() == 'all' ? null : $(
                '#userGroupCodeAndNameSearch').val();
        this.searchOrderObj['qm.distributeStatus'] =
            $('#distributeStatusSearch').val() == 'all' ? null : $('#distributeStatusSearch').val();
//           this.searchOrderObj['qm.couponCode']=$('#couponCodeSearch').val();
    };
    this.initialize = function () {
        me.initSearchCondition();
        me.initInput();
        me.initContentList();
        me.initOperation();
        //me.initValidation();
        //me.initOperationBtnStatus();
    };
    this.initSearchCondition = function () {
        //var codeobj = new
        // codeModel({width:100,height:22,dropDownWidth:100,autoDropDownHeight:true});
        // codeobj.initSelectByData('userGroupCodeAndNameSearch',codeData["USER_GROUP"],'codeDisplay','codeValue',true);
        // codeobj.initSelectByData('distributeStatusSearch',codeData["DISTRIBUTION_STATUS"],'codeDisplay','codeValue',true);
        var codesearch = new codeModel({width: 100, autoDropDownHeight: true});
        codesearch.getCommonCode("userGroupCodeAndNameSearch", "USER_GROUP", true);
        codesearch.getCommonCode("distributeStatusSearch", "DISTRIBUTION_STATUS", true);

        $('#userLevelCodeAndNameSearch').jqxDropDownList({
                                                             selectedIndex: 0,
                                                             source: userLevelCodeAndName.getDatas(),
                                                             displayMember: "codeDisplay",
                                                             valueMember: "codeValue",
                                                             width: 120,
                                                             height: 22,
                                                             dropDownHeight: 125
                                                         });
        $("#userLevelCodeAndNameSearch")
            .jqxDropDownList('insertAt', {label: '全部', value: 'all'}, 0);
        $("#searchBtn").jqxButton({theme: currentTheme});
        $('#searchBtn').click(function () {
            me.fetchSearchData();
            me.search();
            me.initOperationBtnStatus();
        });
//           $('#couponCodeSearch').jqxInput({placeHolder: "", height: 22, width: 120, minLength:
// 1,theme:currentTheme,disabled:false});
    };
    this.getRowData = function (couponSid) {
        if (this.dataAdapter.records == null) {
            return null;
        }
        for (var i = 0; i < this.dataAdapter.records.length; i++) {
            var dt = this.dataAdapter.records[i];
            if (dt.couponSid == couponSid) {
                return dt;
            }
        }
        return null;
    };
    this.dataAdapter;
    this.initContentList = function () {
        me.fetchSearchData();
        //this.dataAdapter =
        // Core.NewDataAdapter({gridId:"mygrid",url:ws_url+"/rest/marketing/coupons",params:me.searchOrderObj,type:'GET'
        // , setting:{formatData: function (data) { if(data['sortdatafield']==='userLevelName'){
        // data['sortdatafield']='userLevel'; }else if(data['sortdatafield']==='userGroupName'){
        // data['sortdatafield']='userGroup'; }else
        // if(data['sortdatafield']==='distributeChannelName'){
        // data['sortdatafield']='distributeChannel'; }else
        // if(data['sortdatafield']==='distributeStatusName'){
        // data['sortdatafield']='distributeStatus'; } return data; } }});
        this.dataAdapter = Core.getPagingDataAdapter({
                                                         gridId: "mygrid",
                                                         url: ws_url + "/rest/coupons/getCoupons",
                                                         params: me.searchObj
                                                     });

        $("#mygrid").jqxGrid({
                                 width: "100%"
                                 , theme: currentTheme
                                 , source: me.dataAdapter
                                 , pageable: true
                                 , pagesize: 10
                                 //pagesizeoptions: pagesizeoptions
                                 , autoHeight: true
                                 , sortable: true
                                 , localization: gridLocalizationObj
                                 , selectionmode: "singlerow"
                                 , virtualmode: true
                                 , altrows: true
                                 , enabletooltips: true
                                 , enablebrowserselection: true
                                 , rendergridrows: function () {
                var rows = me.dataAdapter.records;
                //for(var i=0;i<rows.length;i++){
                //    var row=rows[i];
                //    if(row!=null){
                //        row['userLevelName']=userLevelCodeAndName.getNameByCode(row['userLevel']);
                //        row['userGroupName']=getListColumnByCode(codeData["USER_GROUP"],row['userGroup']);
                // var
                // vlarrays=row['distributeChannel']==null?[]:row['distributeChannel'].split(',');
                // var display=''; for(var j=0;j<vlarrays.length;j++){ if(j!=0){ display+=","; }
                // var
                // displayName=getListColumnByCode(codeData["COUPON_DISTRIBUTE_CHANNEL"],vlarrays[j]);
                // display+=displayName; } row['distributeChannelName']=display;
                // row['distributeStatusName']=getListColumnByCode(codeData["DISTRIBUTION_STATUS"],row['distributeStatus']);
                // } }
                return rows;
            },
                                 clipboard: true,
                                 columnsresize: true,
                                 columns: [
                                     {text: '优惠卷代码', datafield: 'couponCode', width: 100},
                                     {
                                         text: '有效期开始',
                                         datafield: 'validStartDt',
                                         width: 100,
                                         type: 'date',
                                         format: "yyyy-MM-dd HH:mm:ss"
                                     },
                                     {
                                         text: '有效期截至',
                                         datafield: 'validToDt',
                                         width: 100,
                                         type: 'date',
                                         format: "yyyy-MM-dd HH:mm:ss"
                                     },
                                     {
                                         text: '折扣率0-1',
                                         datafield: 'discountRate',
                                         width: 80,
                                         cellsalign: 'right',
                                         cellsformat: 'f2'
                                     },
                                     {
                                         text: '用户级别',
                                         datafield: 'userLevelName',
                                         width: 100,
                                         enabletooltips: false,
                                         cellsrenderer: function (rowIndex, columnfield, value,
                                                                  defaulthtml, columnproperties,
                                                                  rowdata) {
                                             var html = "<div style=\"overflow: hidden; text-overflow: ellipsis; padding-bottom: 2px; text-align: left; margin-right: 2px; margin-left: 4px; margin-top: 4px;\">";
                                             if (rowdata.userLevel != null && rowdata.userLevel
                                                                              != "") {
                                                 return html + value + "</div>";
                                             } else {
                                                 return html + '所有级别</div>';
                                             }
                                         }
                                     },
                                     {
                                         text: '用户群体', datafield: 'userGroupName', width: 100,
                                         cellsrenderer: function (rowIndex, columnfield, value,
                                                                  defaulthtml, columnproperties,
                                                                  rowdata) {
                                             var html = "<div style=\"overflow: hidden; text-overflow: ellipsis; padding-bottom: 2px; text-align: left; margin-right: 2px; margin-left: 4px; margin-top: 4px;\">";
                                             if (rowdata.userGroup != null && rowdata.userGroup
                                                                              != "") {
                                                 return html + value + "</div>";
                                             } else {
                                                 return html + '所有群体</div>';
                                             }
                                         }
                                     },
                                     {text: '分发渠道', datafield: 'distributeChannelName', width: 100},
                                     {
                                         text: '分发状态',
                                         datafield: 'distributeStatusName',
                                         align: 'center',
                                         width: 100,
                                         cellsalign: 'center'
                                     },
                                     {text: '备注', datafield: 'remarks'},
                                     {
                                         text: '操作',
                                         datafield: 'couponSid',
                                         align: 'center',
                                         sortable: false,
                                         enabletooltips: false,
                                         width: 40,
                                         cellsrenderer: function (rowIndex, columnfield, value,
                                                                  defaulthtml, columnproperties,
                                                                  rowdata) {
                                             var html = '<div style="text-align: center;">';
                                             //末分发或分发失败
                                             if (rowdata.distributeStatus == 0
                                                 || rowdata.distributeStatus == 2) {
                                             //    html +=
                                             //        '<a class="icon-mail-8 icons-18-green2" title="分发" href="javascript:myDestktop.distributeCoupon(\''
                                             //        + rowdata.couponSid + '\',\''
                                             //        + rowdata.couponCode + '\');void(0);"></a>';
                                             //} else {
                                             //    html +=
                                             //        '<i class="icon-mail-8 icons-18-disabled" title="确定将所选优惠券\:"></i>';
                                             //}
                                             //html += '</div>';
                                                 html +=
                                                     '<a class="icon-mail-8 icons-18-green2" title="激活" href="javascript:myDestktop.distributeCoupon(\''
                                                     + rowdata.couponSid + '\',\''
                                                     + rowdata.couponCode + '\');void(0);"></a>';
                                             } else {
                                                 html +=
                                                     '<i class="icon-mail-8 icons-18-disabled" title="确定将所选优惠券\:"></i>';
                                             }
                                             html += '</div>';
                                             return html;
                                         }
                                     },
                                     {text: '操作人', datafield: 'updatedBy', width: 100},
                                     {
                                         text: '操作时间',
                                         datafield: 'updatedDt',
                                         width: 100,
                                         type: 'date',
                                         format: "yyyy-MM-dd HH:mm:ss"
                                     },
                                 ],
                                 showtoolbar: true,
                                 // 设置toolbar操作按钮
                                 rendertoolbar: function (toolbar) {
                                     //var container = $("<div class='jqxGridToolbarDiv'
                                     // style='width:100%;margin-left: 4px;margin-top:
                                     // 5px;padding-top:5px;'></div>"); var addBtn = $("<div
                                     // class='mod'><div class='button_01' id='addBtn'><i
                                     // class='icons-blue icon-plus-3'></i>添加</div></div>"); var
                                     // editBtn = $("<div class='mod'><div class='button_01'
                                     // id='editBtn'><i class='icons-blue
                                     // icon-edit'></i>编辑</div></div>"); var deleteBtn = $("<div
                                     // class='mod'><div class='button_01' id='deleteBtn'><i
                                     // class='icons-blue icon-cancel-4'></i>删除</div></div>");
                                     // container.append(addBtn); container.append(editBtn);
                                     // container.append(deleteBtn); toolbar.append(container);
                                     var container = $(
                                         "<div  id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                                     var addBtn = $(
                                         "<div><a id='addBtn'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
                                     var editBtn = $(
                                         "<div><a id='editBtn' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit' ></i>编辑&nbsp;&nbsp;</a></div>");
                                     var deleteBtn = $(
                                         "<div><a id='deleteBtn' onclick='removeUserItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                                     toolbar.append(container);
                                     container.append(addBtn);
                                     container.append(editBtn);
                                     container.append(deleteBtn);

                                 }
                             });
        $("#deleteBtn")
            .jqxButton({width: '60', theme: currentTheme, height: '18px', disabled: true});
        $("#editBtn").jqxButton({width: '80', theme: currentTheme, height: '18px', disabled: true});
        $("#addBtn").jqxButton({width: '60', height: '18px', theme: currentTheme});
    };
    // 初始化页面中的各种组件，如:输入框、按钮、下拉列表框等
    this.initInput = function () {
        this.initAddWindow();
        this.initModWindow();
        this.initDistributeWindow();
    };
    this.initAddWindow = function () {
        //---新增虚拟空间---
        $('#addvalidStartDt').jqxDateTimeInput({
                                                   width: 156,
                                                   height: 22,
                                                   showFooter: true,
                                                   theme: currentTheme,
                                                   allowNullDate: true,
                                                   culture: 'zh-CN',
                                                   formatString: 'd',
                                                   value: null
                                               });
        $('#addvalidToDt').jqxDateTimeInput({
                                                width: 156,
                                                height: 22,
                                                showFooter: true,
                                                theme: currentTheme,
                                                allowNullDate: true,
                                                culture: 'zh-CN',
                                                formatString: 'd',
                                                value: null
                                            });
        //var codeobj = new
        // codeModel({width:150,height:22,dropDownWidth:150,autoDropDownHeight:true});
        // codeobj.initSelectByData('adduserGroup',codeData["USER_GROUP"],'codeDisplay','codeValue',true);
        var codesearch = new codeModel({
            placeHolder: '请选择',
            width: 150,
            height: 22,
            dropDownWidth: 150,
            autoDropDownHeight: true,
            checkboxes: true
        });
        //codeobj.initSelectByData('adddistributeChannel',codeData["COUPON_DISTRIBUTE_CHANNEL"],'codeDisplay','codeValue');
        // var codesearch = new codeModel({width:100,autoDropDownHeight:true});
        codesearch.getCommonCode("adduserGroup", "USER_GROUP", true);
        codesearch.getCommonCode("adddistributeChannel", "COUPON_DISTRIBUTE_CHANNEL", true);

        $("#adduserLevel").jqxDropDownList({
                                               placeHolder: '请选择',
                                               selectedIndex: 0,
                                               source: userLevelCodeAndName.getDatas(),
                                               displayMember: "codeDisplay",
                                               valueMember: "codeValue",
                                               width: 156,
                                               height: 22,
                                               autoDropDownHeight: true
                                           });
        $("#adduserLevel").jqxDropDownList('insertAt', {label: '全部', value: ''}, 0);
        $("#adddiscountRate")
            .jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1, theme: currentTheme});

        $("#addremarks").jqxInput(
            {placeHolder: "", height: 120, width: 426, minLength: 1, theme: currentTheme});
        $("#saveAdd").jqxButton({theme: currentTheme});
        $("#saveAdd").click(function () {
            //判断是否验证通过
            if (!$('#addWindow').jqxValidator('validate')) {
                //return; //---------wsl
            }
            me.add();

        });
        $("#returnAdd").jqxButton({theme: currentTheme});
        $("#returnAdd").click(function () {
            $('#addWindow').jqxValidator('hide');
            $("#addWindow").jqxWindow('close');
        });
        $("#addWindow").on('close', function () {
            $('#addWindow').jqxValidator('hide');
        });
    };
    this.initModWindow = function () {
        //---修改虚拟空间---
        $('#modcouponCode').jqxInput({
                                         placeHolder: "",
                                         height: 22,
                                         width: 150,
                                         minLength: 1,
                                         theme: currentTheme,
                                         disabled: true
                                     });
        $('#modvalidStartDt').jqxDateTimeInput({
                                                   width: 156,
                                                   height: 22,
                                                   showFooter: true,
                                                   theme: currentTheme,
                                                   allowNullDate: true,
                                                   culture: 'zh-CN',
                                                   formatString: 'd',
                                                   value: null
                                               });
        $('#modvalidToDt').jqxDateTimeInput({
                                                width: 156,
                                                height: 22,
                                                showFooter: true,
                                                theme: currentTheme,
                                                allowNullDate: true,
                                                culture: 'zh-CN',
                                                formatString: 'd',
                                                value: null
                                            });
        //var codeobj = new
        // codeModel({width:150,height:22,dropDownWidth:150,autoDropDownHeight:true});
        // codeobj.initSelectByData('moduserGroup',codeData["USER_GROUP"],'codeDisplay','codeValue',true);
        // var codeobj = new
        // codeModel({placeHolder:'请选择',width:150,height:22,dropDownWidth:150,autoDropDownHeight:true,checkboxes:true});
        // codeobj.initSelectByData('moddistributeChannel',codeData["COUPON_DISTRIBUTE_CHANNEL"],'codeDisplay','codeValue');

        var codesearch = new codeModel({
            placeHolder: '请选择',
            width: 150,
            height: 22,
            dropDownWidth: 150,
            autoDropDownHeight: true,
            checkboxes: true
        });
        codesearch.getCommonCode("moduserGroup", "USER_GROUP", true);
        codesearch.getCommonCode("moddistributeChannel", "COUPON_DISTRIBUTE_CHANNEL", true);

        $("#moduserLevel").jqxDropDownList({
                                               placeHolder: '请选择',
                                               selectedIndex: 0,
                                               source: userLevelCodeAndName.getDatas(),
                                               displayMember: "codeDisplay",
                                               valueMember: "codeValue",
                                               width: 156,
                                               height: 22,
                                               autoDropDownHeight: true
                                           });

        $("#moduserLevel").jqxDropDownList('insertAt', {label: '全部', value: ''}, 0);

        $("#moddiscountRate")
            .jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1, theme: currentTheme});

        $("#modremarks").jqxInput(
            {placeHolder: "", height: 120, width: 426, minLength: 1, theme: currentTheme});

        $("#saveModifyBtn").jqxButton({theme: currentTheme});
        $("#saveModifyBtn").on('click', function () {
            //判断是否验证通过
            if (!$('#modifyWindow').jqxValidator('validate')) {
                //return; ----------wsl
            }
            me.modify();

        });
        $("#returnModifyBtn").jqxButton({theme: currentTheme});
        $("#returnModifyBtn").on('click', function () {
            $('#modifyWindow').jqxValidator('hide');
            $("#modifyWindow").jqxWindow('close');
        });
        $("#modifyWindow").on('close', function () {
            $('#modifyWindow').jqxValidator('hide');
        });
    };
    this.initDistributeWindow = function () {
        //var codeobj = new codeModel({width:200,height:125,dropDownWidth:200});
        //codeobj.initSelectByData('addDistributeChannel',codeData["COUPON_DISTRIBUTE_CHANNEL"],'codeDisplay','codeValue',true);
        var codesearch = new codeModel({
            placeHolder: '请选择',
            width: 150,
            height: 22,
            dropDownWidth: 150,
            autoDropDownHeight: true,
            checkboxes: true
        });
        codesearch.getCommonCode("moduserGroup", "USER_GROUP", true);
        codesearch.getCommonCode("addDistributeChannel", "COUPON_DISTRIBUTE_CHANNEL", true);

        $("#saveDistributeBtn").jqxButton({theme: currentTheme});
        $("#saveDistributeBtn").on('click', function () {
            //判断是否验证通过
            if (!$('#distributeWindow').jqxValidator('validate')) {
                return;
            }
            me.distribute();

        });
        $("#returnDistributeBtn").jqxButton({theme: currentTheme});
        $("#returnDistributeBtn").on('click', function () {
            $('#distributeWindow').jqxValidator('hide');
            $("#distributeWindow").jqxWindow('close');
        });
        $("#distributeWindow").on('close', function () {
            $('#distributeWindow').jqxValidator('hide');
        });
    };
    this.initValidation = function () {
        $('#addWindow').jqxValidator({
                                         rules: [
                                             {
                                                 input: '#addvalidStartDt',
                                                 message: '有效开始时期不能为空！',
                                                 action: 'keyup',
                                                 rule: function () {
                                                     var v = $('#addvalidStartDt').val();
                                                     if (v == null || v == '') {
                                                         return false;
                                                     } else {
                                                         return true;
                                                     }

                                                 }
                                             },

                                             {
                                                 input: '#addvalidToDt',
                                                 message: '有效结束日期不能为空\!',
                                                 action: 'keyup',
                                                 rule: function () {
                                                     var v = $('#addvalidToDt').val();
                                                     if (v == null || v == '') {
                                                         return false;
                                                     } else {
                                                         return true;
                                                     }

                                                 }
                                             },
                                             {
                                                 input: '#adddiscountRate',
                                                 message: '折扣率必须不能空且是数字大于0小于等于1！',
                                                 action: 'keyup',
                                                 rule: function () {
                                                     var v = $('#adddiscountRate').val();
                                                     if (v === null || v === '') {
                                                         return false;
                                                     } else {
                                                         if (isNaN(v)) {
                                                             return false;
                                                         } else {
                                                             var vn = parseFloat(v);
                                                             if (vn > 0 && vn <= 1) {
                                                                 return true;
                                                             } else {
                                                                 return false;
                                                             }
                                                         }
                                                     }

                                                 }
                                             },
//		                                      { input:
// '#adduserLevel',message:$.i18n.prop('module.admin.favourable.billingcoupon.validation.userLevelIsRequired'),action:'keyup',rule:
// function(){ var v=$('#adduserLevel').val(); if(v==null||v==''){ return false; }else{ return
// true; }  } }, { input:
// '#adduserGroup',message:$.i18n.prop('module.admin.favourable.billingcoupon.validation.userGroupIsRequired'),action:'keyup',rule:
// function(){ var v=$('#adduserGroup').val(); if(v==null||v==''){ return false; }else{ return
// true; }  } },
                                             {
                                                 input: '#adddistributeChannel',
                                                 message: '分发渠道不能为空！',
                                                 action: 'keyup',
                                                 rule: function () {
                                                     var v = $('#adddistributeChannel').val();
                                                     if (v == null || v == '') {
                                                         return false;
                                                     } else {
                                                         return true;
                                                     }

                                                 }
                                             }]
                                     });
        $('#modifyWindow').jqxValidator({
                                            rules: [
                                                {
                                                    input: '#modvalidStartDt',
                                                    message: '有效开始时期不能为空！',
                                                    action: 'keyup',
                                                    rule: function () {
                                                        var v = $('#modvalidStartDt').val();
                                                        if (v == null || v == '') {
                                                            return false;
                                                        } else {
                                                            return true;
                                                        }

                                                    }
                                                },
                                                {
                                                    input: '#modvalidToDt',
                                                    message: '有效结束日期不能为空\!',
                                                    action: 'keyup',
                                                    rule: function () {
                                                        var v = $('#modvalidToDt').val();
                                                        if (v == null || v == '') {
                                                            return false;
                                                        } else {
                                                            return true;
                                                        }

                                                    }
                                                },
                                                {
                                                    input: '#moddiscountRate',
                                                    message: '折扣率必须不能空且是数字大于等于0小于1！',
                                                    action: 'keyup',
                                                    rule: function () {
                                                        var v = $('#moddiscountRate').val();
                                                        if (v === null || v === '') {
                                                            return false;
                                                        } else {
                                                            if (isNaN(v)) {
                                                                return false;
                                                            } else {
                                                                var vn = parseFloat(v);
                                                                if (vn > 0 && vn <= 1) {
                                                                    return true;
                                                                } else {
                                                                    return false;
                                                                }
                                                            }
                                                        }

                                                    }
                                                },
//		                                      { input:
// '#moduserLevel',message:$.i18n.prop('module.admin.favourable.billingcoupon.validation.userLevelIsRequired'),action:'keyup',
// rule: function(){ var v=$('#moduserLevel').val(); if(v==null||v==''){ return false; }else{
// return true; }  } },  { input:
// '#moduserGroup',message:$.i18n.prop('module.admin.favourable.billingcoupon.validation.userGroupIsRequired'),action:'keyup',
// rule: function(){ var v=$('#moduserGroup').val(); if(v==null||v==''){ return false; }else{
// return true; }  } },
                                                {
                                                    input: '#moddistributeChannel',
                                                    message: '分发渠道不能为空！',
                                                    action: 'keyup',
                                                    rule: function () {
                                                        var v = $('#moddistributeChannel').val();
                                                        if (v == null || v == '') {
                                                            return false;
                                                        } else {
                                                            return true;
                                                        }

                                                    }
                                                }]
                                        });

        $('#distributeWindow').jqxValidator({
                                                rules: [
                                                    {
                                                        input: '#addDistributeChannel',
                                                        message: '分发渠道不能为空！',
                                                        action: 'keyup',
                                                        rule: function () {
                                                            var v = $('#addDistributeChannel')
                                                                .val();
                                                            if (v == null || v == '') {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }

                                                        }
                                                    }]
                                            });
    };
    this.clearAddinfo = function () {
        $("#addvalidStartDt").val("");
        $("#addvalidToDt").val("");
        $("#adddiscountRate").val("");
        $("#adduserLevel").val("");
        $("#adduserGroup").val("");
        //$("#adddistributeChannel").jqxDropDownList({
        //placeHolder:'请选择',
        //	selectedIndex : 0,
        //	source :couponDistributeChannel.getDatas(),
        //	displayMember : "codeDisplay",
        //	valueMember : "codeValue",
        //	width : 156,
        //	height : 22,
        //	autoDropDownHeight : true,
        //	checkboxes:true
        //});
        $("#addremarks").val("");
    };
    this.initOperation = function () {
        $('#addWindow').jqxWindow({
                                      resizable: false,
                                      autoOpen: false,
                                      showCollapseButton: true,
                                      height: 330,
                                      width: 600,
                                      isModal: true,
                                      theme: currentTheme,
                                      cancelButton: $("#returnAdd"),
                                  });
        $("#addBtn").on('click', function () {

            me.clearAddinfo();
            $('#addWindow').jqxWindow('open');
        });
        $('#deleteBtn').click(function () {
            me.remove();

        });
        // 控制按钮是否可用
        //$('#modifyWindow').jqxWindow({
        //    showCollapseButton: true, height: 374, width: 600,isModal: true,theme:currentTheme
        //});
        $('#modifyWindow').jqxWindow({
                                         resizable: false,
                                         autoOpen: false,
                                         showCollapseButton: true,
                                         height: 330,
                                         width: 600,
                                         isModal: true,
                                         theme: currentTheme,
                                         cancelButton: $("#returnModifyBtn"),
                                     });
        $("#mygrid").on('rowselect', function (event) {

            var args = event.args;
            var index = args.rowindex;
            var data = $('#mygrid').jqxGrid('getrowdata', index);

            $("#editBtn").jqxButton({disabled: false});
            $("#deleteBtn").jqxButton({disabled: false});
        });
        $('#editBtn').click(function () {
            var rowindex = $('#mygrid').jqxGrid('getselectedrowindex');
            if (rowindex == -1) {
                Core.alert({message: '请先选择一条数据\!'});
                return;
            }

            var data = $('#mygrid').jqxGrid('getrowdata', rowindex);
            if (data.distributeStatus != 0) {
                Core.alert({message: '只有末分发的优惠卷才可以编辑\!'});
                return;
            }
            $("#modcouponSid").val(data.couponSid);
            $("#modcouponCode").val(data.couponCode);
            //$("#modvalidStartDt").val((data.validStartDt==null||data.validStartDt=='')?'':(new
            // Date(data.validStartDt).format('yyyy-MM-dd')));
            $("#modvalidStartDt").val((data.validStartDt == null || data.validStartDt == '') ? ''
                                          : data.validStartDt.split(" ")[0]);
            $("#modvalidToDt").val(
                (data.validToDt == null || data.validToDt == '') ? '' : data.validToDt.split(
                    " ")[0]);
            $("#moddiscountRate").val(data.discountRate);
            $("#moduserLevel").val(data.userLevel);
            $("#moduserGroup").val(data.userGroup);
            $("#modDistributeStatus").val(data.distributeStatus);

            var dcs = data.distributeChannel == null || data.distributeChannel == '' ? []
                : data.distributeChannel.split(',');
            for (var i = 0; i < dcs.length; i++) {
                $("#moddistributeChannel").jqxDropDownList('checkItem', dcs[i]);
            }
            $("#modremarks").val(data.remarks);

            //$('#modifyWindow').jqxWindow({
            //    showCollapseButton: true, height: 374, width: 600,isModal: true,theme:currentTheme
            //});
            $('#modifyWindow').jqxWindow('open');
        });
    };
    this.initOperationBtnStatus = function () {

        $("#addBtn").jqxButton({disabled: false, theme: currentTheme});
        $("#editBtn").jqxButton({disabled: true, theme: currentTheme});
        $("#deleteBtn").jqxButton({disabled: true, theme: currentTheme});
        //hideBtns();
    };
    this.add = function () {
        console.log("addCoupon~~~~Start");
        $("#saveAdd").jqxButton({disabled: true});
        var url = ws_url + "/rest/coupons/addCoupon";
        var requestData = {};
        requestData.couponSid = $("#addcouponSid").val();
        requestData.couponCode = $("#addcouponCode").val();
        requestData.validStartDtStr = new Date($("#addvalidStartDt").val()).format('yyyy-MM-dd');
        requestData.validToDtStr = new Date($("#addvalidToDt").val()).format('yyyy-MM-dd');
        requestData.discountRate = $("#adddiscountRate").val();
        requestData.userLevel = $("#adduserLevel").val() == '全部' ? null : $("#adduserLevel").val();
        requestData.userGroup = $("#adduserGroup").val() == '全部' ? null : $("#adduserGroup").val();
        requestData.userLevel = $("#adduserLevel").val() == "" ? null : $("#adduserLevel").val();
        requestData.userGroup = $("#adduserGroup").val() == "" ? null : $("#adduserGroup").val();
        requestData.distributeChannel = $("#adddistributeChannel").val();
        requestData.remarks = $("#addremarks").val();
        console.log("进入addCoupon接口");
        console.log(JSON.stringify(requestData));
        Core.AjaxRequest({
                             url: url
                             , type: 'POST'
                             , params: requestData
                             , showMsg: true
                             , async: false
                             , callback: function (data) {
                me.fetchSearchData();
                me.refresh();
                me.initOperationBtnStatus();
                $('#addWindow').jqxValidator('hide');
                $("#addWindow").jqxWindow('close');
                Core.alert({message:"添加成功！"});
            }
                             , failure: function (data) {
                me.refresh();
                $('#addWindow').jqxValidator('hide');
                $("#addWindow").jqxWindow('close');
            }
                         });
        $("#saveAdd").jqxButton({disabled: false});
    };
    this.remove = function () {

        var rowindexs = $('#mygrid').jqxGrid('selectedrowindex');
        if (rowindexs >= 0) {
            Core.confirm({
                             title: '删除'
                             , message: '确定要删优惠卷吗？'
                             , confirmCallback: function () {
                    var data = $('#mygrid').jqxGrid('getrowdata', rowindexs);
                    var url = ws_url + "/rest/marketing/coupons/" + data.couponSid;

                    Core.AjaxRequest({
                                         url: url
                                         , showMsg: true
                                         , type: 'DELETE'
                                         , async: false
                                         , callback: function (data) {

                        }
                                         , failure: function (data) {
                        }
                                     });
                    $('#mygrid').jqxGrid('updatebounddata', 'cells');
                    $('#mygrid').jqxGrid('clearselection');
                }
                         });

        } else {
            Core.alert({message: '请先选择一条数据\!'});
        }
    };

    this.modify = function () {
        $("#saveModifyBtn").jqxButton({disabled: true});
        var url = ws_url + "/rest/marketing/coupons";
        var requestData = {};
        requestData.couponSid = $("#modcouponSid").val();
        requestData.couponCode = $("#modcouponCode").val();
        requestData.validStartDt = $("#modvalidStartDt").val();
        requestData.validToDt = $("#modvalidToDt").val();
        requestData.discountRate = $("#moddiscountRate").val();
        requestData.userLevel = $("#moduserLevel").val() == '全部' ? null : $("#moduserLevel").val();
        requestData.userGroup = $("#moduserGroup").val() == '全部' ? null : $("#moduserGroup").val();
        requestData.userLevel = $("#moduserLevel").val() == "" ? null : $("#moduserLevel").val();
        requestData.userGroup = $("#moduserGroup").val() == "" ? null : $("#moduserGroup").val();
        requestData.distributeChannel = $("#moddistributeChannel").val();
        requestData.distributeStatus = $("#modDistributeStatus").val();
        requestData.remarks = $("#modremarks").val();

        Core.AjaxRequest({
                             url: url
                             , params: requestData
                             , showMsg: true
                             , type: 'PUT'
                             , async: false
                             , callback: function (data) {
                me.refresh();
                $('#modifyWindow').jqxValidator('hide');
                $("#modifyWindow").jqxWindow('close');
            }
                             , failure: function (data) {
            }
                         });
        $("#saveModifyBtn").jqxButton({disabled: false});
    };
    this.showDistribute = function (couponSid) {

        $('#addDistributeCouponSid').val(couponSid);
        $('#distributeWindow').jqxWindow({
                                             showCollapseButton: true,
                                             height: 400,
                                             width: 400,
                                             isModal: true,
                                             theme: currentTheme
                                         });
        $('#distributeWindow').jqxWindow('open');
    };
    this.distributeCoupon = function (couponSid, couponCode) {
        Core.confirm({
                         width: 400,
                         message: '确定将所选优惠券\:' + couponCode + '激活吗？',
                         confirmCallback: function () {

                             var url = ws_url + "/rest/coupons/" + couponSid
                                       + "/distribute/";
                             console.log("Start");
                             var requestData = {};
                             Core.AjaxRequest({
                                                  url: url
                                                  , params: requestData
                                                  , showMsg: true
                                                  , type: 'POST'
                                                  , async: false
                                                  , callback: function (data) {
                                                   me.refresh();
                                                 Core.alert({message:"优惠券成功激活！"});

                                 }
                                                  , failure: function (data) {
                                 }
                                              });
                         }
                     });
    };
    this.distribute = function () {
        console.log("新增Start");
        $("#saveDistributeBtn").jqxButton({disabled: true});
        var url = ws_url + "/rest/coupons/marketing/" + $('#addDistributeCouponSid').val()
                  + "/distribute/";
        var requestData = {};
        requestData.couponSid = $("#addcouponSid").val();
        requestData.couponCode = $("#addcouponCode").val();
        requestData.validStartDt = new Date($("#addvalidStartDt").val()).format('yyyy-MM-dd');
        requestData.validToDt = new Date($("#addvalidToDt").val()).format('yyyy-MM-dd');
        requestData.discountRate = $("#adddiscountRate").val();
        requestData.userLevel = $("#adduserLevel").val() == '全部' ? null : $("#adduserLevel").val();
        requestData.userGroup = $("#adduserGroup").val() == '全部' ? null : $("#adduserGroup").val();
        requestData.userLevel = $("#adduserLevel").val() == "" ? null : $("#adduserLevel").val();
        requestData.userGroup = $("#adduserGroup").val() == "" ? null : $("#adduserGroup").val();
        requestData.remarks = $("#addremarks").val();

        Core.AjaxRequest({
                             url: url
                             , params: requestData
                             , showMsg: true
                             , async: false
                             , type: 'GET'
                             , callback: function (data) {
                me.refresh();
                $('#distributeWindow').jqxValidator('hide');
                $("#distributeWindow").jqxWindow('close');
            }
                             , failure: function (data) {
            }
                         });
        $("#saveDistributeBtn").jqxButton({disabled: false});
    };
    this.search = function () {
        $("#mygrid").jqxGrid('applyfilters');
        $('#mygrid').jqxGrid('refreshfilterrow');
        $('#mygrid').jqxGrid('clearselection');
    };
    this.refresh = function () {
        $('#mygrid').jqxGrid('clearselection');
        $('#mygrid').jqxGrid('clear');
        $("#mygrid").jqxGrid('updatebounddata', 'cells');
    };
}
$(function () {
    myDestktop = new MyDestktop();

    // 初始化页面组件
    myDestktop.initialize();
});