package com.single.jpaProjct.refund.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.single.jpaProjct.common.SearchVO;
import com.single.jpaProjct.common.Utility;

@Service
public class RefundService {

	@Autowired
	RefundRepository refundRepository;

	public int refundInsert(RefundVO vo) {
		int res = 0;

		RefundVO resVo = refundRepository.save(vo);

		if (resVo.getRefundNo() > 0) {
			res = 1;
		}

		return res;
	}

	public void refundCancel(Long refundNo) {
		refundRepository.deleteById(refundNo);
	}

	public Page<RefundVO> refundSearch(SearchVO searchVo) {
		String keyword = searchVo.getSearchKeyword();

		Pageable paging = PageRequest.of(searchVo.getCurrentPage(), 
				Utility.BLOCK_SIZE, Sort.Direction.DESC, "refundNo");

		Page<RefundVO> page = Page.empty();
		
		if(keyword.isEmpty() || keyword==null) {
			page=refundRepository.findAll(paging);
		}else {
			DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime start=LocalDateTime.parse(keyword+"00:00:00", dtf);
			LocalDateTime end=LocalDateTime.parse(keyword+"23:59:59", dtf);
			
			page=refundRepository.findAllByReportingDateBetween(Timestamp.valueOf(start),
					Timestamp.valueOf(end),paging);
		}
		
		return page;

	}
	
	public RefundVO refundSelByno(Long refundNo) {
		Optional<RefundVO> op=refundRepository.findById(refundNo);
		return op.get();
	}
}
