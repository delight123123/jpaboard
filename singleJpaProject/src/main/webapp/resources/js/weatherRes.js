function getSKYval(pty) {
	if (pty == '1' ) return "맑음";
		else if (pty=='3') return "구름많음";
		else if (pty=='4') return "흐림";

}

function getPTYval(pty) {
	if (pty == '0' ) return "없음";
		else if (pty=='1') return "비";
		else if (pty=='2') return "비/눈";
		else if (pty=='3') return "눈";
		else if (pty=='4') return "소나기";
		else if (pty=='5') return "빗방울";
		else if (pty=='6') return "빗방울/눈날림";
		else if (pty=='7') return "눈날림";
}

function getRval(f) {
	if (f < 0.1) return "0";
		else if (f >= 0.1 && f < 1.0) return "1mm미만";
		else if (f >= 1.0 && f < 5.0) return "1~4mm";
		else if (f >= 5.0 && f < 10.0) return "5~9mm";
		else if (f >= 10.0 && f < 20.0) return "10~19mm";
		else if (f >= 20.0 && f < 40.0) return "20~39mm";
		else if (f >= 40.0 && f < 70.0) return "40~69mm";
		else return "70mm이상";
}

function getVECval(pty) {
	if (pty == '0' ) return "N";
		else if (pty=='1') return "NNE";
		else if (pty=='2') return "NE";
		else if (pty=='3') return "ENE";
		else if (pty=='4') return "E";
		else if (pty=='5') return "ESE";
		else if (pty=='6') return "SE";
		else if (pty=='7') return "SSE";
		else if (pty=='8') return "S";
		else if (pty=='9') return "SSW";
		else if (pty=='10') return "SW";
		else if (pty=='11') return "WSW";
		else if (pty=='12') return "W";
		else if (pty=='13') return "WNW";
		else if (pty=='14') return "NW";
		else if (pty=='15') return "NNW";
		else if (pty=='16') return "N";
}


function getSval(f){
	if (f < 0.1) return "0 또는 없음";
		else if (f >= 0.1 && f < 1.0) return "1cm미만";
		else if (f >= 1.0 && f < 5.0) return "1~4cm";
		else if (f >= 5.0 && f < 10.0) return "5~9cm";
		else if (f >= 10.0 && f < 20.0) return "10~19cm";
		else return "20cm 이상";
}

function weaRes(res) {
	var result;

	if (res.category== 'POP') {
		result += "<tr>";
		result += "<td>" + res.baseDate + "</td>";
		result += "<td>" + res.baseTime + "</td>";
		result += "<td>" + "강수확률" + "</td>";
		result += "<td>" + res.fcstDate + "</td>";
		result += "<td>" + res.fcstTime + "</td>";
		result += "<td>" + res.fcstValue + "%</td>";
		result += "</tr>";

		return result;
	}else if(res.category == 'PTY') {
		result += "<tr>";
		result += "<td>" + res.baseDate + "</td>";
		result += "<td>" + res.baseTime + "</td>";
		result += "<td>" + "강수형태" + "</td>";
		result += "<td>" + res.fcstDate + "</td>";
		result += "<td>" + res.fcstTime + "</td>";
		result += "<td>" + getPTYval(res.fcstValue) + "</td>";
		result += "</tr>";

		return result;
	}else if(res.category == 'R06') {
		result+= "<tr>";
		result+= "<td>" + res.baseDate + "</td>";
		result += "<td>" + res.baseTime + "</td>";
		result += "<td>" + "6시간 강수량" + "</td>";
		result += "<td>" + res.fcstDate + "</td>";
		result += "<td>" + res.fcstTime + "</td>";
		result += "<td>" + getRval(res.fcstValue) + "</td>";
		result += "</tr>";

		return result;
	}else if(res.category == 'REH') {
		result +="<tr>";
		result += "<td>" + res.baseDate + "</td>";
		result += "<td>" + res.baseTime + "</td>";
		result += "<td>" + "습도" + "</td>";
		result += "<td>" + res.fcstDate + "</td>";
		result += "<td>" + res.fcstTime + "</td>";
		result += "<td>" + res.fcstValue + "%</td>";
		result += "</tr>";

		return result;
	}else if(res.category == 'S06') {
		result += "<tr>";
		result += "<td>" + res.baseDate + "</td>";
		result += "<td>" + res.baseTime + "</td>";
		result += "<td>" + "6시간 신적설" + "</td>";
		result += "<td>" + res.fcstDate + "</td>";
		result += "<td>" + res.fcstTime + "</td>";
		result += "<td>" + getSval(res.fcstValue) + "</td>";
		result += "</tr>";

		return result;
	}else if(res.category == 'SKY') {
		result += "<tr>";
		result += "<td>" + res.baseDate + "</td>";
		result += "<td>" + res.baseTime + "</td>";
		result += "<td>" + "하늘상태" + "</td>";
		result += "<td>" + res.fcstDate + "</td>";
		result += "<td>" + res.fcstTime + "</td>";
		result += "<td>" + getSKYval(res.fcstValue) + "</td>";
		result += "</tr>";

		return result;
	}else if(res.category == 'T3H') {
		result += "<tr>";
		result += "<td>" + res.baseDate + "</td>";
		result += "<td>" + res.baseTime + "</td>";
		result += "<td>" + "3시간 기온" + "</td>";
		result += "<td>" + res.fcstDate + "</td>";
		result += "<td>" + res.fcstTime + "</td>";
		result += "<td>" + res.fcstValue + "℃</td>";
		result += "</tr>";

		return result;
	}else if(res.category == 'TMN') {
		result += "<tr>";
		result += "<td>" + res.baseDate + "</td>";
		result += "<td>" + res.baseTime + "</td>";
		result += "<td>" + "아침 최저기온" + "</td>";
		result += "<td>" + res.fcstDate + "</td>";
		result += "<td>" + res.fcstTime + "</td>";
		result += "<td>" + res.fcstValue + "℃</td>";
		result += "</tr>";

		return result;
	}else if (res.category == 'TMX') {
		result += "<tr>";
		result += "<td>" + res.baseDate + "</td>";
		result += "<td>" + res.baseTime + "</td>";
		result += "<td>" + "낮 최고기온" + "</td>";
		result += "<td>" + res.fcstDate + "</td>";
		result += "<td>" + res.fcstTime + "</td>";
		result += "<td>" + res.fcstValue + "℃</td>";
		result += "</tr>";

		return result;
	}else if(res.category == 'UUU') {
		result += "<tr>";
		result += "<td>" + res.baseDate + "</td>";
		result += "<td>" + res.baseTime + "</td>";
		result += "<td>" + "풍속(동서성분)" + "</td>";
		result += "<td>" + res.fcstDate + "</td>";
		result += "<td>" + res.fcstTime + "</td>";
		result += "<td>" + res.fcstValue + "m/s</td>";
		result += "</tr>";

		return result;
	}else if(res.category == 'VVV') {
		result += "<tr>";
		result += "<td>" + res.baseDate + "</td>";
		result += "<td>" + res.baseTime + "</td>";
		result += "<td>" + "풍속(남북성분)" + "</td>";
		result += "<td>" + res.fcstDate + "</td>";
		result += "<td>" + res.fcstTime + "</td>";
		result += "<td>" + res.fcstValue + "m/s</td>";
		result += "</tr>";

		return result;
	}else if(res.category == 'WAV') {
		result += "<tr>";
		result+= "<td>" + res.baseDate + "</td>";
		result+= "<td>" + res.baseTime + "</td>";
		result+= "<td>" + "파고" + "</td>";
		result+= "<td>" + res.fcstDate + "</td>";
		result+= "<td>" + res.fcstTime + "</td>";
		result+= "<td>" + res.fcstValue + "M</td>";
		result+= "</tr>";

		return result;
	} else if(res.category == 'VEC') {
		result += "<tr>";
		result += "<td>" + res.baseDate + "</td>";
		result += "<td>" + res.baseTime + "</td>";
		result += "<td>" + "풍향" + "</td>";
		result += "<td>" + res.fcstDate + "</td>";
		result += "<td>" + res.fcstTime + "</td>";
		result += "<td>" + getVECval(res.fcstValue) + "</td>";
		result += "</tr>";

		return result;
	} else if(res.category == 'WSD') {
		result += "<tr>";
		result += "<td>" + res.baseDate + "</td>";
		result += "<td>" + res.baseTime + "</td>";
		result += "<td>" + "풍속" + "</td>";
		result += "<td>" + res.fcstDate + "</td>";
		result += "<td>" + res.fcstTime + "</td>";
		result += "<td>" + res.fcstValue + "m/s</td>";
		result += "</tr>";
	}
	
	return result;
}