package com.jose1593.web.dto;

import lombok.Data;


@Data // 이게 lombok 두 개 합쳐진 모습이다.
public class BoardDTO { // lombok 사용해보기 (따로 getter, setter 해줄필요 없다.)
	private int bno, blike, commentcount, rowNum;
	private String btitle, bcontent, m_name, m_id, bdate, bip, uuid; 

}
