package site.metacoding.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CMRespDto<T> {
    private Integer code; // 1 : 성공, -1 : 실패
    private String msg; //에러메시지 혹은 성공에 대한 메시지
    private T body;

    //값을 넣을땐, Builder로 찾을 땐, Getter로
    @Builder
    public CMRespDto(Integer code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }
}
