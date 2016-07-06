<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
	  <style type="text/css">
    	#networkTabs{
    		width:99.8%;
    		height:99%;
    	}
    	#networkTabs ul{
    	    margin:0px;
    	    padding:0px;
    		width:99%;
    		margin-left:0.5%;
    		margin-top:5px;
    		height:30px;
    		background:#F4F4F4;
    		list-style:none;
    		border:1px solid #E5E5E5;
    	}
    	#networkTabs .network{
    	    margin:1px; 
    	    height:26px;
    	    line-height:30px;
    	    padding:1px 8px 1px 8px;
    	    cursor:pointer;
    		float:left;
    	}
    	
    	#networkTabs .network:hover{
    		background:#DEDEDE;
    	}
    	
    	#networkTabs .show{
    		background:#fff;
    		border:1px solid #e5e5e5;
    		border-bottom:0px;
    		height:27px;
    	}
    	
    	#networkTabs .content{
    		width:99%;
    		margin-left:0.5%;
    		height:92%;
    		display:none;
    		border:1px solid #e5e5e5;
    		border-top:0px;
    	}
    	#networkTabs .contentShow{
    		display:block;
    	}
    	
    	
    </style>

    <div id='networkTabs'>
	    <ul>
	        <li class="network show" style="margin-left: 30px;">VLAN管理</li>
	        <li class="network">IP管理</li>  
	    </ul>
	    <div class="content contentShow">  
           <%@ include file="res-network-vlan.jsp"%> 
	    </div>
	    <div class="content">
	        <%@ include file="res-network-ip.jsp"%> 
	    </div>
	</div>
	     
  <script type="text/javascript">
     
     $("#networkTabs .network").each(function(index){
    	 $(this).click(function(){
    		 $("#networkTabs .show").removeClass("show");
    		 $(this).addClass("show");
    		 
    		 $("#networkTabs .contentShow").removeClass("contentShow");
    		 $("#networkTabs .content").eq(index).addClass("contentShow");
    	 })
     });

  </script>