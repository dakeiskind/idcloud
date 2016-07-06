var userModel = function () {
    var me = this;
    this.items = ko.observableArray();
    this.searchObj = {
        "qm.accountLike": null,
        "qm.roleSid": null,
        "qm.status": null
    };
    // 查询用户名是否重复
    this.searchUserByName = function (name) {
        var Todata = null;
        Core.AjaxRequest({
                             url: ws_url + "/rest/user/findAll",
                             type: 'post',
                             params: {
                                 accountRepeat: name
                             },
                             async: false,
                             callback: function (data) {
                                 Todata = data;
                             }
                         });
        return Todata;
    };
    // 用户数据
    this.searchUserInfo = function () {
        $("#userdatagrid").jqxGrid("gotopage", 0);
        var dataAdapter = Core.getPagingDataAdapter({
                                                        gridId: "userdatagrid",
                                                        url: ws_url + "/rest/user/findByPage",
                                                        params: me.searchObj
                                                    });
        $("#userdatagrid").jqxGrid({
                                       source: dataAdapter,
                                       rendergridrows: function () {
                                           return dataAdapter.records;
                                       }
                                   });
    };

    // 初始化页面中的各种组件，如：输入框、按钮、下拉列表框等
    this.initInput = function () {
        // 初始化查询组件
        $("#search-account")
            .jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1, theme: currentTheme});
        $("#search-owner-mgtobj")
            .jqxInput({placeHolder: "", height: 22, width: 100, minLength: 1, theme: currentTheme});
        $("#search-button").jqxButton({width: '50px', height: '25px', theme: currentTheme});

        //重置密码组件初始化
        $("#passwordInput")
            .jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1, theme: currentTheme});
        $("#passwordConfirmInput")
            .jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1, theme: currentTheme});
        $("#passwdSave").jqxButton({width: '50', height: "25", theme: currentTheme});
        $("#passwdCancel").jqxButton({width: '50', height: "25", theme: currentTheme});

        // 初始化下拉列表框
        var codesearch = new codeModel({width: 100, autoDropDownHeight: true});
        codesearch.getCommonCode("search-status", "USER_STATUS", true);
        codesearch.getCustomCode("search-role", "/user/findAllRole", "roleName", "roleSid", true);

        //初始化礼品卡选择框
        //$("#edit-user-name").jqxInput({placeHolder: "选择礼品卡", height: 22, width: 150,
        // minLength: 1,theme:currentTheme});
    };

    this.setEditUserForm = function (data) {
        $("#edit-account").html(data.account);
        $("#edit-name").jqxInput({value: data.realName});
        $("#edit-mobil").jqxInput({value: (data.mobile == null) ? "" : data.mobile});
        $("#edit-email").jqxInput({value: data.email});
        $("#edit-type").val(data.userType);
        $("#edit-status").val(data.status);
    };
    // 初始化验证规则
    this.initValidator = function () {
        // 重置密码
        $('#changePasswdForm').jqxValidator({
                                                rules: [
                                                    {
                                                        input: '#passwordInput',
                                                        message: '不能为空',
                                                        action: 'keyup, blur',
                                                        rule: 'required'
                                                    },
                                                    {
                                                        input: '#passwordInput',
                                                        message: '请输入1-16位密码',
                                                        action: 'keyup, blur',
                                                        rule: 'length=1,16'
                                                    },
                                                    {
                                                        input: '#passwordConfirmInput',
                                                        message: '不能为空',
                                                        action: 'keyup, blur',
                                                        rule: 'required'
                                                    },
                                                    {
                                                        input: '#passwordConfirmInput',
                                                        message: '密码输入不一致',
                                                        action: 'keyup, focus',
                                                        rule: function (input, commit) {
                                                            if (input.val() === $('#passwordInput')
                                                                    .val()) {
                                                                return true;
                                                            }
                                                            return false;
                                                        }
                                                    }
                                                ]
                                            });

        // 新增用户表单验证成功
        $('#addUserForm').on('validationSuccess', function (event) {

            var user = Core.parseJSON($("#addUserForm").serializeJson());

            // delete user['orgSid'];

            var roles = $("#add-rolesHolder").find('[id^=add-role]');

            var roleArr = [];
            roles.each(function () {
                if ($(this).find("input[name^=add-role]").val() == "true") {
                    roleArr.push($(this).attr("data"));
                }
            });
            if (user.userType == '01') {
                user['roleArr'] = roleArr;
                delete user['orgSid'];
                delete user['mgtObjSid'];
                Core.AjaxRequest({
                                     url: ws_url + "/rest/user/platform/insertPlatformUser",
                                     params: user,
                                     callback: function (data) {
                                         searchUser();
                                         $("#addUserWindow").jqxWindow('close');
                                     },
                                     failure: function (data) {
                                         if (data.data != "unSelected") {
                                             $("#addUserWindow").jqxWindow('close');
                                         }
                                     }
                                 });
            } else if (user.userType == '02') {
                delete user['orgSid'];
                //var item = $('#add-mgtObjList').jqxTree('getSelectedItem');
//		    			 if(null==item){
//		    				 Core.confirm({
//		    					  title:"提示",
//		    					  message:'请选择关联项目！',
//		    				  });
//		    			 }else{
                //user['userMgtObj'] = item.value;
                user['roleArr'] = roleArr;
//				    		 console.log(JSON.stringify(user));
                Core.AjaxRequest({
                                     url: ws_url + "/rest/user/platform/insertPlatformUser",
                                     params: user,
                                     callback: function (data) {
                                         if (data.message != "该项目已被关联，请重新选择！") {
                                             searchUser();
                                             $("#addUserWindow").jqxWindow('close');
                                         }
                                     },
                                     failure: function (data) {
                                         if (data.data != "unSelected") {
                                             $("#addUserWindow").jqxWindow('close');
                                         }
                                     }
                                 });
//		    			 }
            }

        });

        //  编辑用户表单验证成功
        $('#editUserForm').on('validationSuccess', function (event) {

            var user = Core.parseJSON($("#editUserForm").serializeJson());

            delete user['mgtObjSid'];

//		    		 var roles = $("#edit-rolesHolder").find('[id^=edit-role]');
//		    		 
//		    		 var roleArr = [];
//		    		 roles.each(function () {
//		    			 if($(this).find("input[name^=edit-role]").val() == "true") {
//		    				 roleArr.push($(this).attr("data"));
//		    			 }
//		    		 });
            var roleArr = [];
            if (user.userType == '01') {
                delete user['mgtObjSid'];
                var roles = $("#edit-rolesHolder").find('[id^=edit-role]');
                roles.each(function () {
                    if ($(this).find("input[name^=edit-role]").val() == "true") {
                        roleArr.push($(this).attr("data"));
                    }
                });
            } else if (user.userType == '02') {
//		    			 var item = $('#edit-mgtObjSid').val();
//		    			 user['mgtObjSid'] = item;
                delete user['mgtObjSid'];
            } else {
                user['userType'] = '02';
            }
            user['roleArr'] = roleArr;
            Core.AjaxRequest({
                                 url: ws_url + "/rest/user/platform/updatePlatformUser",
                                 params: user,
                                 callback: function (data) {
                                     searchUser();
                                     $("#editUserWindow").jqxWindow('close');
                                 },
                                 failure: function (data) {
                                     if (data.data != "unSelected") {
                                         $("#editUserWindow").jqxWindow('close');
                                     }
                                 }
                             });
        });

        //  修改密码验证成功
        $('#changePasswdForm').on('validationSuccess', function (event) {
            var userSids = $("#passwdUserSids").val();
            var newPassword = $("#passwordConfirmInput").val();

            Core.AjaxRequest({
                                 url: ws_url + "/rest/user/modifyPassword",
                                 params: {
                                     userSids: userSids,
                                     newPassword: newPassword
                                 },
                                 callback: function (data) {
                                     Core.alert({
                                                    title: "提示",
                                                    message: "密码修改成功！",
                                                    callback: function () {
                                                        // 清除选中项
                                                        $('#userdatagrid')
                                                            .jqxGrid('clearselection');
                                                        // 刷新列表
                                                        searchUser();
                                                        // 关闭window
                                                        $("#changePasswdWindow").jqxWindow('close');
                                                    }
                                                });
                                 },
                                 failure: function (data) {
                                     Core.alert({
                                                    title: "提示",
                                                    message: "密码修改失败！",
                                                    callback: function () {
                                                        $("#changePasswdWindow").jqxWindow('close');
                                                    }
                                                });
                                 }
                             });
        });
    };
    // 初始化用户datagrid的数据源
    this.initUserDatagrid = function () {
        var dataAdapter = Core.getPagingDataAdapter({
                                                        gridId: "userdatagrid",
                                                        url: ws_url + "/rest/user/findByPage",
                                                        params: me.searchObj
                                                    });

        $("#userdatagrid").jqxGrid({
                                       width: "99.8%",
                                       theme: currentTheme,
                                       source: dataAdapter,
                                       virtualmode: true,
                                       rendergridrows: function () {
                                           return dataAdapter.records;
                                       },
                                       columnsresize: true,
                                       pageable: true,
                                       pagesize: 10,
                                       autoheight: true,
                                       autowidth: false,
                                       sortable: true,
                                       selectionmode: "checkbox",
                                       localization: gridLocalizationObj,
                                       columns: [
                                           {
                                               text: '用户帐号',
                                               datafield: 'account',
                                               cellsrenderer: function (row, columnfield, value,
                                                                        defaulthtml,
                                                                        columnproperties) {
                                                   var td = $("#userdatagrid")
                                                       .jqxGrid('getrowdata', row);
                                                   if (td.status == "1" || td.status == "0") {
                                                       return "<div style='padding-top:3px;'><a class='datagrid-link' onclick='popEditUserItemWindow("
                                                              + row + ")' href='#'>&nbsp;" + value
                                                              + "</a></div>";
                                                   } else {
                                                       return "<div style='background-color: rgb(205, 205, 56);width: 100%;height: 100%;line-height:25px;'><span>&nbsp;"
                                                              + value + "</span></div>";
                                                   }
                                               }
                                           },
                                           {
                                               text: '用户姓名',
                                               datafield: 'realName',
                                               width: 130,
                                               cellsrenderer: function (row, columnfield, value,
                                                                        defaulthtml,
                                                                        columnproperties) {
                                                   var td = $("#userdatagrid")
                                                       .jqxGrid('getrowdata', row);
                                                   if (td.status == "2" || td.status == "3") {
                                                       return "<div style='line-height:25px;background-color: rgb(205, 205, 56);width: 100%;height: 100%;'><span>&nbsp;"
                                                              + value + "</span></div>";
                                                   }
                                               }
                                           },
                                           {
                                               text: '用户类型',
                                               datafield: 'userTypeName',
                                               align: 'center',
                                               cellsalign: 'center',
                                               width: 80,
                                               cellsrenderer: function (row, columnfield, value,
                                                                        defaulthtml,
                                                                        columnproperties) {
                                                   var td = $("#userdatagrid")
                                                       .jqxGrid('getrowdata', row);
                                                   if (td.status == "2" || td.status == "3") {
                                                       return "<div style='line-height:25px;background-color: rgb(205, 205, 56);width: 100%;height: 100%;text-align:center;'><span>&nbsp;"
                                                              + value + "</span></div>";
                                                   }
                                               }
                                           },
                                           {
                                               text: '所属角色',
                                               datafield: 'roleName',
                                               width: 200,
                                               cellsrenderer: function (row, columnfield, value,
                                                                        defaulthtml,
                                                                        columnproperties) {
                                                   var td = $("#userdatagrid")
                                                       .jqxGrid('getrowdata', row);
                                                   if (td.status == "2" || td.status == "3") {
                                                       return "<div style='line-height:25px;background-color: rgb(205, 205, 56);width: 100%;height: 100%;'><span>&nbsp;"
                                                              + value + "</span></div>";
                                                   }
                                               }
                                           },
                                           {
                                               text: '所属项目',
                                               datafield: 'projectName',
                                               width: 150,
                                               cellsrenderer: function (row, columnfield, value,
                                                                        defaulthtml,
                                                                        columnproperties) {
                                                   var td = $("#userdatagrid")
                                                       .jqxGrid('getrowdata', row);
                                                   if (td.status == "2" || td.status == "3") {
                                                       return "<div style='line-height:25px;background-color: rgb(205, 205, 56);width: 100%;height: 100%;'><span>&nbsp;"
                                                              + value + "</span></div>";
                                                   }
                                               }
                                           },
                                           {
                                               text: '电话',
                                               datafield: 'mobile',
                                               width: 100,
                                               cellsrenderer: function (row, columnfield, value,
                                                                        defaulthtml,
                                                                        columnproperties) {
                                                   var td = $("#userdatagrid")
                                                       .jqxGrid('getrowdata', row);
                                                   if (td.status == "2" || td.status == "3") {
                                                       return "<div style='line-height:25px;background-color: rgb(205, 205, 56);width: 100%;height: 100%;'><span>&nbsp;"
                                                              + value + "</span></div>";
                                                   }
                                               }
                                           },
                                           {
                                               text: '邮箱',
                                               datafield: 'email',
                                               width: 120,
                                               cellsrenderer: function (row, columnfield, value,
                                                                        defaulthtml,
                                                                        columnproperties) {
                                                   var td = $("#userdatagrid")
                                                       .jqxGrid('getrowdata', row);
                                                   if (td.status == "2" || td.status == "3") {
                                                       return "<div style='line-height:25px;background-color: rgb(205, 205, 56);width: 100%;height: 100%;'><span>&nbsp;"
                                                              + value + "</span></div>";
                                                   }
                                               }
                                           },
                                           {
                                               text: '状态',
                                               datafield: 'statusName',
                                               align: 'center',
                                               cellsalign: 'center',
                                               width: 60,
                                               cellsrenderer: function (row, columnfield, value,
                                                                        defaulthtml,
                                                                        columnproperties) {
                                                   var td = $("#userdatagrid")
                                                       .jqxGrid('getrowdata', row);
                                                   if (td.status == "2" || td.status == "3") {
                                                       return "<div style='line-height:25px;background-color: rgb(205, 205, 56);width: 100%;height: 100%;'><span>&nbsp;"
                                                              + value + "</span></div>";
                                                   }
                                               }
                                           },
                                           {
                                               text: '操作',
                                               datafield: '',
                                               width: 80,
                                               cellsrenderer: function (row, columnfield, value,
                                                                        defaulthtml,
                                                                        columnproperties) {
                                                   var td = $("#userdatagrid")
                                                       .jqxGrid('getrowdata', row);
                                                   if (td.status == "1" || td.status == "0") {
                                                       return "<div class='customButton' style='margin-top:2px;margin-left:10px;float:left' onclick='popEditUserItemWindow("
                                                              + row + ")'>编辑</div>";
                                                   } else {
                                                       return "<div style='line-height:25px;background-color: rgb(205, 205, 56);width: 100%;height: 100%;'></div>";
                                                   }
                                               }
                                           }
                                       ],
                                       showtoolbar: true,
                                       // 设置toolbar操作按钮
                                       rendertoolbar: function (toolbar) {
                                           var container = $("<div id='btnUserGroup' style='width:100%;margin-left: 4px;margin-top: 5px;padding-top:5px;'></div>");
                                           var addBtn = $("<div><a id='jqxAddBtn' onclick ='popAddUserItemWindow()'>&nbsp;&nbsp;<i class='icons-blue icon-plus-3'></i>新增&nbsp;&nbsp;</a></div>");
                                           var exportBtn = $("<div><a id='jqxExportBtn' onclick ='exportUserItemWindow()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-edit'></i>导出&nbsp;&nbsp;</a></div>");
                                           var deleteBtn = $("<div><a id='jqxDeleteBtn' onclick='removeUserItem()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-cancel-3'></i>删除&nbsp;&nbsp;</a></div>");
                                           var changeBtn = $("<div><a id='jqxChangeBtn' onclick='changePassword()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-key-1'></i>重置密码&nbsp;&nbsp;</a></div>");
                                           var checkBtn = $("<div><a id='jqxCheckBtn' onclick='checkUser()' style='margin-left:-1px'>&nbsp;&nbsp;<i class='icons-blue icon-ok-3'></i>审核&nbsp;&nbsp;</a></div>");
                                           var binding = $( "<div  <a id='bindingBtn' onclick='popGiftItemWindow()'" + "  style='margin-left:-1px;margin-top: -5px'><i  class='icons-blue icon-edit'></i>礼品卡分发</a></div>");
                                           var bindingCoupon = $( "<div  <a id='bindingCoupon'  onclick='popCouponItemWindow()'  style='margin-left:-1px;margin-top: -5px'><i  class='icons-blue icon-edit'></i>优惠券分发</a></div>");
                                           toolbar.append(container);
                                           container.append(addBtn);
                                           container.append(deleteBtn);
                                           container.append(changeBtn);
                                           container.append(checkBtn);
                                           container.append(exportBtn);
                                           container.append(binding);
                                           container.append(bindingCoupon);
                                       }
                                   });

        // 控制按钮是否可用
        $("#userdatagrid").on('rowselect', function (event) {
            var args = event.args;
            var index = args.rowindex;
            var data = $('#userdatagrid').jqxGrid('getrowdata', index);
            if (data.status == "2") {
                $("#jqxDeleteBtn").jqxButton({disabled: false});
                $("#jqxChangeBtn").jqxButton({disabled: false});
                $("#jqxCheckBtn").jqxButton({disabled: false});
                $("#bindingBtn").jqxButton({disabled: false});
                $("#bindingCoupon").jqxButton({disabled: false});
            } else {
                $("#jqxDeleteBtn").jqxButton({disabled: false});
                $("#jqxChangeBtn").jqxButton({disabled: false});
                $("#jqxCheckBtn").jqxButton({disabled: true});
            }
        });

        $("#jqxDeleteBtn")
            .jqxButton({width: '60', theme: currentTheme, height: '18px', disabled: true});
        $("#jqxChangeBtn")
            .jqxButton({width: '80', theme: currentTheme, height: '18px', disabled: true});
        $("#jqxCheckBtn")
            .jqxButton({width: '60', theme: currentTheme, height: '18px', disabled: true});
        $("#jqxAddBtn").jqxButton({width: '60', height: '18px', theme: currentTheme});
        $("#jqxExportBtn").jqxButton({width: '70', height: '18px', theme: currentTheme});
        $("#bindingBtn").jqxButton({width: '75', theme: currentTheme, height: '16px', disabled: false});
        $("#bindingCoupon").jqxButton({width: '75', theme: currentTheme, height: '16px', disabled: false});
    };

    //加载可用优惠券grid
    this.initCouponBinding = function() {
        var dataAdapter = Core.getPagingDataAdapter({
                                                        gridId: "jqxgridGift",
                                                        url: ws_url
                                                             + "/rest/coupons/getAvailableCoupon",
                                                        type: "GET"
                                                    });
        $("#jqxGridCoupon").jqxGrid({
                                      width: "99.8%",
                                      theme: currentTheme,
                                      source: dataAdapter,
                                      virtualmode: true,
                                      rendergridrows: function () {
                                          for (var i = 0; i < dataAdapter.records.length; i++) {
                                              dataAdapter.records[i].distributeStatus == '1'
                                                  ? dataAdapter.records[i].distributeStatus =
                                                  '未分发'
                                                  : dataAdapter.records[i].distributeStatus =
                                                  '已分发';
                                          }
                                          return dataAdapter.records;
                                      },
                                      columnsresize: true,
                                      pageable: true,
                                      pagesize: 10,
                                      autoheight: true,
                                      autowidth: false,
                                      sortable: true,
                                      selectionmode: "checkbox",
                                      localization: gridLocalizationObj,
                                      columns: [
                                          {text: "优惠券名称", datafield: 'couponCode'},
                                          {
                                              text: "折扣率(0-1)",
                                              datafield: 'discountRate',
                                              align: 'center',
                                              cellsalign: 'right',
                                              cellsformat: 'c2',
                                              width: 80
                                          },
                                          {
                                              text: "有效期开始",
                                              datafield: 'validStartDt',
                                              align: 'center',
                                              cellsalign: 'center',
                                              cellsrenderer: dateRender
                                          },
                                          {
                                              text: "有效期截止",
                                              datafield: 'validToDt',
                                              align: 'center',
                                              cellsalign: 'center',
                                              cellsrenderer: dateRender
                                          },
                                          {
                                              text: "分发状态",
                                              datafield: 'distributeStatus',
                                              align: 'center',
                                              cellsalign: 'center'
                                          }
                                      ]
                                  });

    };

    //加载可用礼品卡grid
    this.initGfitBinding = function () {
        var dataAdapter = Core.getPagingDataAdapter({
                                                        gridId: "jqxgridGift",
                                                        url: ws_url
                                                             + "/rest/giftCards/getAvailableBatchs",
                                                        type: "GET"
                                                    });
        $("#jqxgridGift").jqxGrid({
                                      width: "99.8%",
                                      theme: currentTheme,
                                      source: dataAdapter,
                                      virtualmode: true,
                                      rendergridrows: function () {
                                          for (var i = 0; i < dataAdapter.records.length; i++) {
                                              dataAdapter.records[i].status == '1'
                                                  ? dataAdapter.records[i].statusName =
                                                  '已激活'
                                                  : dataAdapter.records[i].statusName =
                                                  '未激活';
                                          }
                                          return dataAdapter.records;
                                      },
                                      columnsresize: true,
                                      pageable: true,
                                      pagesize: 10,
                                      autoheight: true,
                                      autowidth: false,
                                      sortable: true,
                                      selectionmode: "checkbox",
                                      localization: gridLocalizationObj,
                                      columns: [
                                          {text: "生成批次号", datafield: 'batchNo'},
                                          {text: "礼品卡名称", datafield: 'cardName'},
                                          {text: "礼品卡卡号", datafield: 'cardNo'},
                                          {text: "礼品卡密", datafield: 'cardPassword'},
                                          {
                                              text: "面额",
                                              datafield: 'faceValue',
                                              align: 'center',
                                              cellsalign: 'right',
                                              cellsformat: 'c2',
                                              width: 80
                                          },
                                          {
                                              text: "有效期开始",
                                              datafield: 'validStart',
                                              align: 'center',
                                              cellsalign: 'center',
                                              cellsrenderer: dateRender
                                          },
                                          {
                                              text: "有效期截止",
                                              datafield: 'validTo',
                                              align: 'center',
                                              cellsalign: 'center',
                                              cellsrenderer: dateRender
                                          },
                                          {
                                              text: "状态",
                                              datafield: 'statusName',
                                              align: 'center',
                                              cellsalign: 'center'
                                          }
                                      ]
                                  });

    };

    // 初始化操作按钮
    this.initOperationBtn = function () {
        $("#jqxDeleteBtn").jqxButton({disabled: true});
        $("#jqxChangeBtn").jqxButton({disabled: true});
        $("#jqxCheckBtn").jqxButton({disabled: true});
        $("#binding").jqxButton({disabled: true});
    };
    // 初始化弹出window
    this.initPopWindow = function () {
        //初始化优惠券绑定窗口
        $("#addCouponWindow").jqxWindow({
                                          showCollapseButton: false,
                                          width: 900,
                                          height: 450,
                                          isModal: true,
                                          autoOpen: false,
                                          resizable: false,
                                          theme: currentTheme,
                                          cancelButton: $("#cancelBindingCouponBtn"),
                                          initContent: function () {
                                          }
                                      });
        //初始化礼品卡绑定窗口
        $("#addGiftWindow").jqxWindow({
                                          showCollapseButton: false,
                                          width: 900,
                                          height: 450,
                                          isModal: true,
                                          autoOpen: false,
                                          resizable: false,
                                          theme: currentTheme,
                                          cancelButton: $("#cancelBindingBtn"),
                                          initContent: function () {
                                          }
                                      });
        $("#addUserWindow").jqxWindow({
                                          width: 600,
                                          height: 300,
                                          resizable: false,
                                          isModal: true,
                                          autoOpen: false,
                                          cancelButton: $("#Cancel"),
                                          theme: currentTheme,
                                          modalOpacity: 0.3,
                                          initContent: function () {
                                              // 初始化新增用户页面组件
                                              $("#add-account").jqxInput({
                                                                             placeHolder: "",
                                                                             height: 22,
                                                                             width: 170,
                                                                             minLength: 1,
                                                                             theme: currentTheme
                                                                         });
                                              $("#add-password").jqxInput({
                                                                              placeHolder: "",
                                                                              height: 22,
                                                                              width: 170,
                                                                              minLength: 1,
                                                                              theme: currentTheme
                                                                          });
                                              $("#add-name").jqxInput({
                                                                          placeHolder: "",
                                                                          height: 22,
                                                                          width: 170,
                                                                          minLength: 1,
                                                                          theme: currentTheme
                                                                      });
                                              $("#add-mobil").jqxInput({
                                                                           placeHolder: "",
                                                                           height: 22,
                                                                           width: 170,
                                                                           minLength: 0,
                                                                           maxLength: 32,
                                                                           theme: currentTheme
                                                                       });
                                              $("#add-email").jqxInput({
                                                                           placeHolder: "",
                                                                           height: 22,
                                                                           width: 170,
                                                                           minLength: 1,
                                                                           theme: currentTheme
                                                                       });

                                              $("#Save").jqxButton(
                                                  {width: '50', height: "25", theme: currentTheme});
                                              $("#Cancel").jqxButton(
                                                  {width: '50', height: "25", theme: currentTheme});

//						$("#addUserMgtSid").jqxDropDownButton({
//	        				width: 170, 
//	        	        	height: 22,
//	        	        	dropDownWidth : 170,
//	        	        	dropDownHeight : 150,
//	        	        	theme:currentTheme
//	        			});

                                              $("#add-type").on('select', function () {
                                                  var type = $(this).val();
                                                  me.fillRoleItems('add', type);
                                                  if (type == "01") {
                                                      //如果是后台用户
                                                      //	$("#tenantTrs").hide();
                                                      me.setUserTypeValidator('addUserForm', type);
                                                  } else if (type == "02") {
                                                      //如果是前台用户
                                                      //$("#tenantTrs").show();
                                                      me.setUserTypeValidator('addUserForm', type);
                                                  }
                                              });

//           				$('#add-mgtObjList').on('select', function (event) {
//	                        var args = event.args;
//	                        var item = $('#add-mgtObjList').jqxTree('getItem', args.element);
//
//	                        var dropDownContent = '<div style="position: relative; margin-left:
// 3px; margin-top: 5px;">' + item.label + '</div>';
// $("#addUserMgtSid").jqxDropDownButton('setContent', dropDownContent);
// $('#addUserMgtSid').jqxDropDownButton('close');  });

                                          }
                                      });
        $("#editUserWindow").jqxWindow({
                                           width: 600,
                                           height: 300,
                                           resizable: false,
                                           isModal: true,
                                           autoOpen: false,
                                           cancelButton: $("#editCancel"),
                                           theme: currentTheme,
                                           modalOpacity: 0.3,
                                           initContent: function () {
                                               // 初始化编辑用户页面组件
                                               $("#edit-name").jqxInput({
                                                                            placeHolder: "",
                                                                            height: 22,
                                                                            width: 170,
                                                                            minLength: 1,
                                                                            theme: currentTheme
                                                                        });
                                               $("#edit-mobil").jqxInput({
                                                                             placeHolder: "",
                                                                             height: 22,
                                                                             width: 170,
                                                                             minLength: 0,
                                                                             maxLength: 32,
                                                                             theme: currentTheme
                                                                         });
                                               $("#edit-email").jqxInput({
                                                                             placeHolder: "",
                                                                             height: 22,
                                                                             width: 170,
                                                                             minLength: 1,
                                                                             theme: currentTheme
                                                                         });

                                               $("#editSave").jqxButton({
                                                                            width: '50',
                                                                            height: "25",
                                                                            theme: currentTheme
                                                                        });
                                               $("#editCancel").jqxButton({
                                                                              width: '50',
                                                                              height: "25",
                                                                              theme: currentTheme
                                                                          });

                                               $("#edit-type").on('select', function () {
                                                   var type = $(this).val();
                                                   me.fillRoleItems('edit', type);
                                                   var rowindex = $('#userdatagrid')
                                                       .jqxGrid('getselectedrowindex');
                                                   if (rowindex >= 0) {
                                                       var data = $('#userdatagrid')
                                                           .jqxGrid('getrowdata', rowindex);
                                                       var roles = data.roles;
                                                       for (var i = 0; i < roles.length; i++) {
                                                           var checkbox = $("#edit-rolesHolder")
                                                               .find('[id^=edit-role][data='
                                                                     + roles[i].roleSid + ']');
                                                           if (checkbox && checkbox.length > 0) {
                                                               checkbox.jqxCheckBox('check');
                                                               checkbox.val(true);
                                                           }
                                                       }
                                                   }
                                                   if (type == "01") {
                                                       //如果是后台用户
                                                       $("#tenantEditTrs").hide();
                                                       me.setUserTypeValidator('editUserForm',
                                                                               type);
                                                   } else if (type == "02") {
                                                       //如果是前台用户
                                                       $("#tenantEditTrs").show();
                                                       me.setUserTypeValidator('editUserForm',
                                                                               type);
                                                   }

                                               });

                                               $('#edit-mgtObjList').on('select', function (event) {
                                                   var args = event.args;
                                                   var item = $('#edit-mgtObjList')
                                                       .jqxTree('getItem', args.element);
                                                   var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">'
                                                                         + item.label + '</div>';
                                                   $("#editUserMgtSid")
                                                       .jqxDropDownButton('setContent',
                                                                          dropDownContent);
                                                   $('#editUserMgtSid').jqxDropDownButton('close');
                                                   $('#edit-mgtObjSid').val(item.value);
                                               });
                                           }
                                       });
        $("#changePasswdWindow").jqxWindow({
                                               width: 300,
                                               height: 170,
                                               resizable: false,
                                               isModal: true,
                                               autoOpen: false,
                                               cancelButton: $("#passwdCancel"),
                                               theme: currentTheme,
                                               modalOpacity: 0.3
                                           });
    };

    this.fillRoleItems = function (oper, type) {
        var roleType = "";
        if (type == "01") {
            roleType = "02";
        } else if (type == "02") {
            roleType = "01";
        }
        Core.AjaxRequest({
                             url: ws_url + "/rest/user/platform/roles/",
                             type: "post",
                             async: false,
                             params: {roleType: roleType},
                             callback: function (data) {
                                 $("#" + oper + "-rolesHolder").html("");
                                 for (var i = 0; i < data.length; i++) {
                                     var checkBoxElem = $(
                                         "<div id='" + oper + "-role-" + i + "' class='" + oper
                                         + "-roles' data='" + data[i].roleSid
                                         + "' style='float: left;margin-top:10px;'>"
                                         + data[i].roleName + "</div>");
                                     $("#" + oper + "-rolesHolder").append(checkBoxElem);
                                     checkBoxElem.jqxCheckBox(
                                         {width: 90, height: 22, theme: currentTheme});
                                 }
                             },
                             failure: function (data) {

                             }
                         });
    };

    this.setUserTypeValidator = function (formId, userType) {
        var lastRoleId = null;
        var rulesArray = null;
        if (formId == 'addUserForm') {
            $('#' + formId).jqxValidator('hide');
            lastRoleId = $("#add-rolesHolder").find('[id^=add-role]:last').attr('id');
            rulesArray = [
                {input: '#add-account', message: '不能为空', action: 'keyup, blur', rule: 'required'},
                {
                    input: '#add-account',
                    message: '用户账号不能超过16个字符',
                    action: 'blur',
                    rule: 'length=1,16'
                },
                {
                    input: '#add-account',
                    message: ' 必须输入英文或数字',
                    action: 'keyup, blur',
                    rule: function (input, commit) {
                        if (/[\u4E00-\u9FA5]/g.test(input.val())) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                },
                {
                    input: '#add-account',
                    message: '用户账号重复，请重新输入',
                    action: 'blur',
                    rule: function (input, commit) {
                        var list = me.searchUserByName(input.val());
                        if (list.length > 0) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                },
                {input: '#add-name', message: '不能为空', action: 'keyup, blur', rule: 'required'},
                {
                    input: '#add-name',
                    message: '用户名称不能超过32个字符',
                    action: 'keyup, blur',
                    rule: 'length=1,32'
                },

                {input: '#add-password', message: '不能为空', action: 'keyup, blur', rule: 'required'},
                {
                    input: '#add-password',
                    message: '密码不能超过16位',
                    action: 'keyup, blur',
                    rule: 'length=1,16'
                },

                {input: '#add-email', message: '不能为空', action: 'keyup, blur', rule: 'required'},
                {input: '#add-email', message: '请输入正确的邮箱地址', action: 'keyup', rule: 'email'},

                {
                    input: '#' + lastRoleId,
                    message: '请选择一个角色',
                    action: 'keyup, blur',
                    position: 'right:-5,-5',
                    rule: function (input, commit) {
                        if ($("#add-rolesHolder").find('.jqx-checkbox-check-checked').length
                            === 0) {
                            return false;
                        }
                        return true;
                    }
                }

            ];
            $('#' + formId).jqxValidator({
                                             rules: rulesArray
                                         });
        } else if (formId == 'editUserForm') {
            $('#' + formId).jqxValidator('hide');
            lastRoleId = $("#edit-rolesHolder").find('[id^=edit-role]:last').attr('id');
            rulesArray = [
                {input: '#edit-name', message: '不能为空', action: 'keyup, blur', rule: 'required'},
                {
                    input: '#edit-name',
                    message: '用户名称不能超过32个字符',
                    action: 'keyup, blur',
                    rule: 'length=1,32'
                },

                {input: '#edit-email', message: '不能为空', action: 'keyup, blur', rule: 'required'},
                {input: '#edit-email', message: '请输入正确的邮箱地址', action: 'keyup', rule: 'email'},

                {
                    input: '#' + lastRoleId,
                    message: '请选择一个角色',
                    action: 'keyup, blur',
                    position: 'right:-5,-5',
                    rule: function (input, commit) {
                        if ($("#edit-rolesHolder").find('.jqx-checkbox-check-checked').length
                            === 0) {
                            return false;
                        }
                        return true;
                    }
                }

            ];

            $('#' + formId).jqxValidator({
                                             rules: rulesArray
                                         });
        }
    };

};

// datagrid上点击名称，弹出编辑window
function popEditUserFromDataGrid(row) {

}

// 弹出新增用户window
function popAddUserItemWindow() {
    var user = new userModel();
    // 初始化用户新增页面
    $("#add-account").val("");
    $("#add-password").val("");
    $("#add-name").val("");
    $("#add-mobil").val("");
    $("#add-email").val("");
    // $("#addUserMgtSid").jqxDropDownButton('setContent', "");
    var codeadd = new codeModel({width: 170, autoDropDownHeight: true});
    codeadd.getCommonCode("add-type", "USER_TYPE");
    codeadd.getCommonCode("add-status", "USER_STATUS");
    //
    Core.AjaxRequest({
                         url: ws_url + "/rest/mgtObj/find",
                         //url : ws_url + "/rest/mgtObj/findByParams",
                         //url : ws_url + "/rest/mgtObj/findAllProject",
                         type: "post",
                         params: {},
                         async: false,
                         callback: function (data) {
                             //console.log(JSON.stringify(data));
                             if (data.length > 0) {
                                 var dataAdapter = new $.jqx.dataAdapter(data);
                                 //console.log(JSON.stringify(dataAdapter));
                                 dataAdapter.dataBind();
                                 var records = dataAdapter.getRecordsHierarchy('mgtObjSid',
                                                                               'parentId', 'items',
                                                                               [{
                                                                                   name: 'name',
                                                                                   map: 'label'
                                                                               }, {
                                                                                   name: 'mgtObjSid',
                                                                                   map: 'value'
                                                                               }]);

//		            $('#add-mgtObjList').jqxTree({source: records, height: '100%', width: '100%',
// theme : currentTheme}); $('#add-mgtObjList').jqxTree('expandAll');
// $('#add-mgtObjList').jqxTree('selectItem', null);
                             }
                         }
                     });
    var type = $("#add-type").val();
    user.fillRoleItems('add', type);
    if (type == "01") {
        //如果是后台用户
        $(".frontTr").hide();
        user.setUserTypeValidator('addUserForm', type);
    } else if (type == "02") {
        //如果是前台用户
        $(".frontTr").show();
        user.setUserTypeValidator('addUserForm', type);
    }

    // 初始化新增window位置
    var windowW = $(window).width();
    var windowH = $(window).height();
    $("#addUserWindow").jqxWindow({position: {x: (windowW - 500) / 2, y: (windowH - 260) / 2}});
    $("#addUserWindow").jqxWindow('open');
}

// 提交新增用户数据
function submitAddUserInfo() {
    // 判断是否通过了验证
    $('#addUserForm').jqxValidator('validate');
}

// 弹出编辑用户window
function popEditUserItemWindow(row) {
    var user = new userModel();
    $("#editUserMgtSid").jqxDropDownButton({
                                               width: 170,
                                               height: 22,
                                               dropDownWidth: 170,
                                               dropDownHeight: 150,
                                               theme: currentTheme
                                           });
    if (row >= 0) {
        var data = $('#userdatagrid').jqxGrid('getrowdata', row);
//  		    var codeadd = new codeModel({width:170,autoDropDownHeight:true});
//  			codeadd.getCommonCode("edit-type","USER_TYPE");
//  			codeadd.getCommonCode("edit-status","USER_STATUS");

        $("#userSid").val(data.userSid);
//	  		  Core.AjaxRequest({
//	  			url : ws_url + "/rest/mgtObj/find",
//	  			type:"post",
//	  			params:{},
//	  			async:false,
//	  			callback : function(data) {
//	  				if(data.length>0){
//	  					var dataAdapter = new $.jqx.dataAdapter(data);
//	  		            dataAdapter.dataBind();
//	  		            var records = dataAdapter.getRecordsHierarchy('mgtObjSid', 'parentId',
// 'items', [{ name: 'name', map: 'label'},{ name: 'mgtObjSid', map: 'value'}]);
// $('#edit-mgtObjList').jqxTree({source: records, height: '100%', width: '100%', theme :
// currentTheme}); $('#edit-mgtObjList').jqxTree('expandAll');
// $('#edit-mgtObjList').jqxTree('selectItem', null); } } });
        var type = data.userType;

        user.fillRoleItems('edit', type);
        var roles = data.roles;
        for (var i = 0; i < roles.length; i++) {
            var checkbox = $("#edit-rolesHolder")
                .find('[id^=edit-role][data=' + roles[i].roleSid + ']');
            if (checkbox && checkbox.length > 0) {
                checkbox.jqxCheckBox('check');
                checkbox.val(true);
            }
        }
        if (type == "01") {
            //如果是后台用户
            $("#tenantEditTrs").hide();
            $("#rolesTr").show();
            $("#typeAndStatus").hide();
            user.setUserTypeValidator('editUserForm', type);
        } else if (type == "02") {
            //如果是前台用户
            $("#tenantEditTrs").hide();
            $("#rolesTr").hide();
            $("#typeAndStatus").hide();
            user.setUserTypeValidator('editUserForm', type);
        }
//  			if(data.mgtObjSid) {
//  				$('#edit-mgtObjSid').val(data.mgtObjSid);
//	    			var items = $('#edit-mgtObjList').jqxTree('getItems');
//	    			for(var i = 0; i < items.length; i++) {
//	    				if(items[i].value == data.mgtObjSid) {
//	    					var dropDownContent = '<div style="position: relative; margin-left:
// 3px; margin-top: 5px;">' + items[i].label + '</div>';
// $("#editUserMgtSid").jqxDropDownButton('setContent', dropDownContent); } } }
        // 将常用的字段可以使用这个方法设置数据
        user.setEditUserForm(data);
        $("#edit-userBizSid").val(data.bizSid);
        var windowW = $(window).width();
        var windowH = $(window).height();
        $("#editUserWindow")
            .jqxWindow({position: {x: (windowW - 500) / 2, y: (windowH - 260) / 2}});
        $("#editUserWindow").jqxWindow('open');
    }
}

/** 提交编辑用户信息 */
function submitEditUserInfo() {
    // 判断是否通过了验证
    $('#editUserForm').jqxValidator('validate');
}

//优惠券分发窗口
function popCouponItemWindow() {
    var row = $('#userdatagrid').jqxGrid('getselectedrowindexes');
    var user = new userModel();
    if (row.length > 0) {
        var data = $('#userdatagrid').jqxGrid('getrowdata', row[0]);
        $("#coupon-user-sid").val(data.userSid);
        $("#coupon-user-name").val(data.account);
        var windowW = $(window).width();
        var windowH = $(window).height();
        $("#addCouponWindow").jqxWindow({position: {x: (windowW - 500) / 2, y: (windowH - 260) / 2}});
        $("#addCouponWindow").jqxWindow('open');
    }
}

//弹出礼品卡分发窗口
function popGiftItemWindow() {
    var row = $('#userdatagrid').jqxGrid('getselectedrowindexes');
    var user = new userModel();
    if (row.length > 0) {
        var data = $('#userdatagrid').jqxGrid('getrowdata', row[0]);
        $("#edit-user-sid").val(data.userSid);
        $("#edit-user-name").val(data.account);
        var windowW = $(window).width();
        var windowH = $(window).height();
        $("#addGiftWindow").jqxWindow({position: {x: (windowW - 500) / 2, y: (windowH - 260) / 2}});
        $("#addGiftWindow").jqxWindow('open');
    }
}

function couponBindingSubmit(){
   //提交分发给选中的用户
    var row = $("#jqxGridCoupon").jqxGrid('getselectedrowindexes');
    console.log(row);
    if (row.length == 1){
        var data = $('#jqxGridCoupon').jqxGrid('getrowdata', row[0]);

        Core.AjaxRequest({
                             url: ws_url + "/rest/couponDis/bindingBydistributionDetailSid"
                             ,
                             params: {
                                 distributionDetailSid: data.distributionDetailSid,
                                 userSid: $("#coupon-user-sid").val()
                             }
                             ,
                             showMsg: true
                             ,
                             callback: function (data) {
                                 Core.alert({message: "分发成功！"});
                                 $('#addCouponWindow').jqxWindow('close');

                             }
                             ,
                             failure: function (data) {
                             }
                         });
    }
}

function giftBindingSubmit() {
    //提交分发给该用户
    var row = $('#jqxgridGift').jqxGrid('getselectedrowindexes');
    console.log($("#edit-user-sid").val());
    console.log($("#edit-user-name").val());
    console.log(row.length);
    if (row.length == 1) {
        var data = $('#jqxgridGift').jqxGrid('getrowdata', row[0]);
        console.log(JSON.stringify(data));
        console.log(data.cardSid);

        Core.AjaxRequest({
                             url: ws_url + "/rest/giftCards/bindingByCardSids"
                             ,
                             params: {
                                 cardSid: data.cardSid,
                                 disOwner: $('#edit-user-name').text(),
                                 distributeUserSid: $("#edit-user-sid").val()
                             }
                             ,
                             showMsg: true
                             ,
                             callback: function (data) {
                                 Core.alert({message: "分发成功！"});
                                 $('#addGiftWindow').jqxWindow('close');

                             }
                             ,
                             failure: function (data) {
                             }
                         });
    }
}

/** 删除用户 */
function removeUserItem() {
    var user = new userModel();
    var userSids = "";
    var rowindex = $('#userdatagrid').jqxGrid('getselectedrowindexes');

    if (rowindex.length > 0) {
        for (var i = 0; i < rowindex.length; i++) {
            var data = $('#userdatagrid').jqxGrid('getrowdata', rowindex[i]);
            if (i == rowindex.length - 1) {
                if (data != null && data !== "" && data != 'undefined') {
                    userSids += data.userSid;
                }

            } else {
                if (data != null && data !== "" && data != 'undefined') {
                    userSids += data.userSid + ",";
                }
            }
        }

        Core.confirm({
                         title: "提示",
                         message: "确定要删除选中的用户吗？",
                         confirmCallback: function () {
                             Core.AjaxRequest({
                                                  url: ws_url + "/rest/user/deleteUser?userSids="
                                                       + userSids,
                                                  type: "get",
                                                  callback: function (data) {
                                                      // 清除选择项
                                                      $('#userdatagrid').jqxGrid('clearselection');
                                                      // 刷新datagrid
                                                      searchUser();
                                                      user.initOperationBtn();
                                                  }
                                              });
                         }
                     });
    }
}

/** 弹出修改用户密码window */
function changePassword() {
    var userSids = "";
    var rowindex = $('#userdatagrid').jqxGrid('getselectedrowindexes');
    if (rowindex.length > 0) {
        for (var i = 0; i < rowindex.length; i++) {
            var data = $('#userdatagrid').jqxGrid('getrowdata', rowindex[i]);
            if (i == rowindex.length - 1) {
                if (data != null && data !== "" && data != 'undefined') {
                    userSids += data.userSid;
                }
            } else {
                if (data != null && data !== "" && data != 'undefined') {
                    userSids += data.userSid + ",";
                }
            }
        }

        $("#passwdUserSids").val(userSids);
        var windowW = $(window).width();
        var windowH = $(window).height();
        // 情况上次修改残留数据
        $("#passwordInput").val("");
        $("#passwordConfirmInput").val("");

        $("#changePasswdWindow")
            .jqxWindow({position: {x: (windowW - 300) / 2, y: (windowH - 150) / 2}});
        $("#changePasswdWindow").jqxWindow('open');

    }
}

/** 提交新密码 */
function passwd_submit() {
    $('#changePasswdForm').jqxValidator('validate');
}

/** 审核用户 */
function checkUser() {
    var user = new userModel();

    var userSids = "";
    var rowindex = $('#userdatagrid').jqxGrid('getselectedrowindexes');
    if (rowindex.length > 0) {
        for (var i = 0; i < rowindex.length; i++) {
            var data = $('#userdatagrid').jqxGrid('getrowdata', rowindex[i]);
            if ("2" != data.status) {
                Core.alert({
                               title: "提示",
                               message: "用户的状态必须全部为待审核！"
                           });
                return;
            }
            if (i == rowindex.length - 1) {
                if (data != null && data !== "" && data != 'undefined') {
                    userSids += data.userSid;
                }

            } else {
                if (data != null && data !== "" && data != 'undefined') {
                    userSids += data.userSid + ",";
                }

            }
        }

        Core.confirm({
                         title: "请选择",
                         message: "您确定审核通过该用户吗？",
                         confirmCallback: function () {
                             Core.AjaxRequest({
                                                  url: ws_url + "/rest/user/operationUser?userSids="
                                                       + userSids + "&action=" + "3",
                                                  type: "get",
                                                  callback: function (data) {
                                                      // 清除选择项
                                                      $('#userdatagrid').jqxGrid('clearselection');
                                                      // 刷新列表
                                                      searchUser();

                                                      //更新菜单
                                                      /** 获取当前用户的角色和权限数据，生成导航条 */
                                                      if (currentUser != null || currentUser
                                                                                 != 'undefined') {
                                                          Core.AjaxRequest({
                                                                               url: ws_url
                                                                                    + "/rest/user/platform/findMoudules/"
                                                                                    + currentUser.userSid,
                                                                               type: "GET",
                                                                               async: false,
                                                                               callback: function (data) {

                                                                                   window.parent.createNavList(
                                                                                       data);
                                                                                   window.parent.moudulesData =
                                                                                       data;

                                                                                   window.parent.$(
                                                                                       "#menuContent .liShow")
                                                                                       .removeClass(
                                                                                           "liShow");
                                                                                   window.parent.$(
                                                                                       "#menuContent")
                                                                                       .find(
                                                                                           "b:contains('系统管理')")
                                                                                       .parents(
                                                                                           "li")
                                                                                       .addClass(
                                                                                           "liShow");
                                                                               }
                                                                           });
                                                      }
                                                  },
                                                  failure: function (data) {
                                                  }
                                              });
                         }
                     });

    }
}

// 导出用户列表
function exportUserItemWindow() {
    this.searchObj = {
        accountLike: $("#search-account").val(),
        projectNameLike: $("#search-owner-mgtobj").val(),
        roleSid: $("#search-role").val() == "全部" ? "" : $("#search-role").val(),
        status: $("#search-status").val() == "全部" ? "" : $("#search-status").val()
    };

    var params = JSON.stringify(searchObj);
    window.location = ws_url + "/rest/user/exportUser/" + params;
}

/** 查询用户 */
function searchUser() {
    var user = new userModel();
    user.searchObj["qm.accountLike"] = $("#search-account").val();
    user.searchObj["qm.projectNameLike"] = $("#search-owner-mgtobj").val();
    user.searchObj["qm.roleSid"] = $("#search-role").val() == "全部" ? "" : $("#search-role").val();
    user.searchObj["qm.status"] =
        $("#search-status").val() == "全部" ? "" : $("#search-status").val();
    user.searchUserInfo();
}
  