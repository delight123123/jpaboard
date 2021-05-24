<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
    <div id="weatherdiv">
    	<form action="<c:url value='/weather'/>" method="post" name="wearthfrm">
    		<fieldset>
    			<input value="" type="hidden" id="xx" name="xx">
				<input value="" type="hidden" id="yy" name="yy">
    		</fieldset>
    	</form>
    </div>
         <!-- ì¬ê¸°ë¶í° bottom -->
          <!-- partial:../../partials/_footer.html -->
          <footer class="footer">
            <div class="d-sm-flex justify-content-center justify-content-sm-between">
              <span class="text-muted text-center text-sm-left d-block d-sm-inline-block"> Singleproject - 포트폴리오</span>
              <span class="float-none float-sm-right d-block mt-1 mt-sm-0 text-center"> ${sessionScope.userid} 님 환영합니다 <i class="mdi mdi-heart text-danger"></i></span>
            </div>
          </footer>
          <!-- partial -->
        </div>
        <!-- main-panel ends -->
      </div>
      <!-- page-body-wrapper ends -->
    </div>
    
    <!-- container-scroller -->
    <!-- plugins:js -->
    <script src="<c:url value='/resources/assets/vendors/js/vendor.bundle.base.js'/>" ></script>
    <style type="text/css">
    	#weatherdiv{
    		
    	}
    </style>
    <!-- endinject -->
    <!-- Plugin js for this page -->
    <!-- End plugin js for this page -->
    <!-- inject:js -->
    <script type="text/javascript" src="<c:url value='/resources/js/jquery-3.4.1.min.js'/>"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script src="<c:url value='/resources/assets/js/off-canvas.js'/>" ></script>
    <script src="<c:url value='/resources/assets/js/hoverable-collapse.js'/>" ></script>
    <script src="<c:url value='/resources/assets/js/misc.js'/>" ></script>
    <script type="text/javascript">
    $(function() {
		
    	$("#logout").click(function() {
			if(confirm("로그아웃 하시겠습니까?")){
				location.href="<c:url value='/logout'/>";
			}
		});
		
    	$("#pwdcg").click(function() {
			window.open("/pwdCg","비밀번호 변경",
			"width=500,height=500,left=0,top=0,location=yes,resizable=yes");
		});
		
		
		
		$("#weathergo").click(function() {
			
			if (navigator.geolocation) {
			    //위치 정보를 얻기
			    navigator.geolocation.getCurrentPosition (function(pos) {
			        //$('#latitude').html(pos.coords.latitude);     // 위도
			        //$('#longitude').html(pos.coords.longitude); // 경도
			    	var x=pos.coords.latitude;
			    	var y=pos.coords.longitude;
			    	$("#xx").val(x);
			    	$("#yy").val(y);
			    	//alert($("#xx").val()+","+$("#yy").val())
			    	$("form[name=wearthfrm]").submit();
			    	//alert(currentLatitude+", "+currentLongitude);
			    });
			} else {
			    alert("이 브라우저에서는 Geolocation이 지원되지 않습니다.")
			}
		});
	});
    
    function dufqkesp() {
    	if (navigator.geolocation) {
		    //위치 정보를 얻기
		    navigator.geolocation.getCurrentPosition (function(pos) {
		        //$('#latitude').html(pos.coords.latitude);     // 위도
		        //$('#longitude').html(pos.coords.longitude); // 경도
		    	var x=pos.coords.latitude;
		    	var y=pos.coords.longitude;
		    	$("#xx").val(x);
		    	$("#yy").val(y);
		    	//alert($("#xx").val()+","+$("#yy").val())
		    	$("form[name=wearthfrm]").submit();
		    	//alert(currentLatitude+", "+currentLongitude);
		    });
		} else {
		    alert("이 브라우저에서는 Geolocation이 지원되지 않습니다.")
		}
	}

	
    
    </script>
    <!-- endinject -->
    <!-- Custom js for this page -->
    <!-- End custom js for this page -->
    
  </body>
</html>