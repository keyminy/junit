package site.metacoding.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.metacoding.service.BookService;
import site.metacoding.web.dto.response.BookRespDto;
import site.metacoding.web.dto.request.BookSaveReqDto;
import site.metacoding.web.dto.response.CMRespDto;

@RestController
@RequiredArgsConstructor
public class BookApiController {

    private final BookService bookService;

    // 1. 책등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody BookSaveReqDto bookSaveReqDto){
        BookRespDto bookRespDto = bookService.책등록하기(bookSaveReqDto);
        CMRespDto<?> cmRespDto = CMRespDto.builder()
                                            .code(1).msg("글 저장 성공").body(bookRespDto).build();
        return new ResponseEntity<>(cmRespDto,HttpStatus.CREATED);//201,INSERT성공
    }
    // 2. 책목록보기
    public ResponseEntity<?> getBookList(){
        return null;
    }
    // 3.책 한 건 보기
    public ResponseEntity<?> getBookOne(){
        return null;
    }

    // 4. 책 삭제하기
    public ResponseEntity<?> deleteBook(){
        return null;
    }

    // 5. 책 수정하기
    public ResponseEntity<?> updateBook() {
        return null;
    }
}
