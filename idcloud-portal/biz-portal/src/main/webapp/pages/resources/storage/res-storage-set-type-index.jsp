<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   
     <div id="setStorageTypeWindow">
            <div>设置存储类型</div>
            <div id="setStorageTypeForm" style="overflow: hidden;">
            	<input type="hidden" data-name="resStorageSids" id="set-storage-type-Sid"/>
            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
                    <tr>
                        <td align="right" width="100">存储类型:</td>
                        <td align="left" >
                            <div data-name="storageType" id="set-storage-type"></div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" height="30" colspan="2">
	                        <input style="margin-right: 5px;" onclick='submitStorageTypeInfo()' type="button" id="storageTypeSave" value="保存" />
	                        <input id="storageTypeCancel" type="button" value="取消" />
                        </td>
                    </tr>
                </table>
            </div>
     </div>
         
  <script type="text/javascript">
       var storageType = new setStorageTypeModel();
       storageType.initInput();
       storageType.initPopWindow();
  </script>