package site.metacoding.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.metacoding.service.BookService;
import site.metacoding.web.dto.response.BookRespDto;
import site.metacoding.web.dto.request.BookSaveReqDto;
import site.metacoding.web.dto.response.CMRespDto;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BookApiController {

    private final BookService bookService;

    // 1. 책등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto
        ,BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Map<String,String> errorMap = new HashMap<>();
            for(FieldError fe : bindingResult.getFieldErrors()){
                errorMap.put(fe.getField(),fe.getDefaultMessage());
            }
            System.out.println("===========================");
            System.out.println(errorMap.toString());
            System.out.println("===========================");

            throw new RuntimeException(errorMap.toString());
//            return new ResponseEntity<>(CMRespDto.builder()
//                    .code(-1).msg(errorMap.toString()).body(bookRespDto).build()
//                    ,HttpStatus.BAD_REQUEST);//400 = 요청이 잘못됨
        }
        //Exception이 안일어날때만, 책 등록하기 메서드 수행되게게
       BookRespDto bookRespDto = bookService.책등록하기(bookSaveReqDto);
        return new ResponseEntity<>(CMRespDto.builder()
                .code(1).msg("글 저장 성공").body(bookRespDto).build()
                ,HttpStatus.CREATED);//201,INSERT성공
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
