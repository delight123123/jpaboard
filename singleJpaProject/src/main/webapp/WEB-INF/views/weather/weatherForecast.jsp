<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../inc/mainTop.jsp" %>
<script type="text/javascript" src="<c:url value='/resources/js/weatherRes.js'/>"></script>
<div class="content-wrapper">
	<div class="card">
		<div class="card-body">
			<div class="search" style="">
		            <input id="address" type="text" placeholder="검색할 주소" value="" />
		            <input id="submit" type="button" value="주소 검색"/>
		    </div>
			<div id="map" style="width:100%;height:600px;"></div>
		</div>
	</div>
	<div class="card">
		<div class="card-body">
			<div id="wea">
				<table class="table text-center" style="width: 100%;">
					<colgroup>
						<col style="width:10%;" />
						<col style="width:10%;" />
						<col style="width:30%;" />
						<col style="width:10%;" />
						<col style="width:10%;" />	
						<col style="width:30%;" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">발표일자</th>
							<th scope="col">발표시각</th>
							<th scope="col">카테고리</th>
							<th scope="col">예보일자</th>
							<th scope="col">예보시각</th>
							<th scope="col">예보 값</th>
						</tr>
					</thead>
					<tbody id="weaTableBody">
					
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<input value="${xx }" type="hidden" id="xxx">
<input value="${yy }" type="hidden" id="yyy">

<%@include file="../inc/mainBottom.jsp" %>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=it8ck8tkfa&submodules=geocoder"></script>
<script type="text/javascript">


	
	$(function() {
		
		$("#weather").addClass("active");
		dufqkesp();
		weatherAjax(document.getElementById('xxx').value, document.getElementById('yyy').value);
	});
	

	//--------------------------------------------------------------------------------------
	//날씨 ajax 함수 만들자리
	function weatherAjax(lat,lng) {
		var toXY="toXY";
		$("#weaTableBody").html("");
		$.ajax({
			url:"<c:url value='/weatherAjax'/>",
			type:"post",
			data:
				{
					code : toXY,
					mapLat : lat,
					mapLng : lng
				}
			,
			success:function(res){
				 //alert(res);
				 $.each(res,function(index, arr){
					
					 $("#weaTableBody").append(weaRes(arr));
				 });
				
			},
			error:function(xhr,status,error){
				alert("Error : "+status+", "+error);
			}
		});
	};
	

</script>
<script>
var contentString='내 위치';

var map = new naver.maps.Map("map", {
	  center: new naver.maps.LatLng(document.getElementById('xxx').value, document.getElementById('yyy').value),
	  zoom: 15,
	  mapTypeControl: true
	});

var marker = new naver.maps.Marker({
    position: new naver.maps.LatLng(document.getElementById('xxx').value, document.getElementById('yyy').value),
    title: contentString,
    map: map
});

	var infoWindow = new naver.maps.InfoWindow({
	  anchorSkew: true
	});
	
	map.setCursor('pointer');
	
	function searchCoordinateToAddress(latlng) {
	
	  infoWindow.close();
	
	  naver.maps.Service.reverseGeocode({
	    coords: latlng,
	    orders: [
	      naver.maps.Service.OrderType.ADDR,
	      naver.maps.Service.OrderType.ROAD_ADDR
	    ].join(',')
	  }, function(status, response) {
	     if (status === naver.maps.Service.Status.ERROR) {
	      if (!latlng) {
	        return alert('ReverseGeocode Error, Please check latlng');
	      }
	      if (latlng.toString) {
	        return alert('ReverseGeocode Error, latlng:' + latlng.toString());
	      }
	      if (latlng.x && latlng.y) {
	        return alert('ReverseGeocode Error, x:' + latlng.x + ', y:' + latlng.y);
	      }
	      return alert('ReverseGeocode Error, Please check latlng');
	    } 
	
	    var address = response.v2.address,
	        htmlAddresses = [];
	
	    if (address.jibunAddress !== '') {
	        htmlAddresses.push('[지번 주소] ' + address.jibunAddress);
	    }
	
	    if (address.roadAddress !== '') {
	        htmlAddresses.push('[도로명 주소] ' + address.roadAddress);
	    }
	
	    infoWindow.setContent([
	      '<div style="padding:10px;min-width:200px;line-height:150%;">',
	      '<h4 style="margin-top:5px;">검색 좌표</h4><br />',
	      htmlAddresses.join('<br />'),
	      '</div>'
	    ].join('\n'));
	
	    infoWindow.open(map, latlng);
	  });
	}
	
	function searchAddressToCoordinate(address) {
	  naver.maps.Service.geocode({
	    query: address
	  }, function(status, response) {
	    if (status === naver.maps.Service.Status.ERROR) {
	      if (!address) {
	        return alert('Geocode Error, Please check address');
	      }
	      return alert('Geocode Error, address:' + address);
	    }
	
	    if (response.v2.meta.totalCount === 0) {
	      return alert('동까지 입력해주세요.');
	    }
	
	    var htmlAddresses = [],
	      item = response.v2.addresses[0],
	      point = new naver.maps.Point(item.x, item.y);

	    document.getElementById('xxx').value=item.y;
        document.getElementById('yyy').value=item.x;
      
	
	    if (item.roadAddress) {
	      htmlAddresses.push('[도로명 주소] ' + item.roadAddress);
	    }
	
	    if (item.jibunAddress) {
	      htmlAddresses.push('[지번 주소] ' + item.jibunAddress);
	    }
	
	    if (item.englishAddress) {
	      htmlAddresses.push('[영문명 주소] ' + item.englishAddress);
	    }
	
	    infoWindow.setContent([
	      '<div style="padding:10px;min-width:200px;line-height:150%;">',
	      '<h4 style="margin-top:5px;">검색 주소 : '+ address +'</h4><br />',
	      htmlAddresses.join('<br />'),
	      '</div>'
	    ].join('\n'));
	
	    map.setCenter(point);
	    infoWindow.open(map, point);
	  });
	  
	//여기 아래에 예보 ajax 함수 넣어주면 될 듯
		weatherAjax(document.getElementById('xxx').value, document.getElementById('yyy').value);
	    
	}
	
	function initGeocoder() {
	  if (!map.isStyleMapReady) {
	    return;
	  }
	
	  map.addListener('click', function(e) {
	    searchCoordinateToAddress(e.coord);
	    document.getElementById('xxx').value=e.coord.y;
	    document.getElementById('yyy').value=e.coord.x;
	  //여기 아래에 예보 ajax 함수 넣어주면 될 듯
		weatherAjax(document.getElementById('xxx').value, document.getElementById('yyy').value);
	  });
	
	  $('#address').on('keydown', function(e) {
	    var keyCode = e.which;
	
	    if (keyCode === 13) { // Enter Key
	      searchAddressToCoordinate($('#address').val());
	    }
	  });
	
	  $('#submit').on('click', function(e) {
	    e.preventDefault();
	
	    searchAddressToCoordinate($('#address').val());
	    

	  });
	    searchAddressToCoordinate($('#address').val());
	    
	}
	
	naver.maps.onJSContentLoaded = initGeocoder;
	naver.maps.Event.once(map, 'init_stylemap', initGeocoder);
	
	
	function dufqkesp() {
    	if (navigator.geolocation) {
		    //위치 정보를 얻기
		    navigator.geolocation.getCurrentPosition (function(pos) {
		        //$('#latitude').html(pos.coords.latitude);     // 위도
		        //$('#longitude').html(pos.coords.longitude); // 경도
		    	var x=pos.coords.latitude;
		    	var y=pos.coords.longitude;
		    	$("#xxx").val(x);
		    	$("#yyy").val(y);
		    	//alert($("#xx").val()+","+$("#yy").val())
		    	//$("form[name=wearthfrm]").submit();
		    	//alert(currentLatitude+", "+currentLongitude);
		    });
		} else {
		    alert("이 브라우저에서는 Geolocation이 지원되지 않습니다.")
		}
	}
</script>

